<template>
  <div class="login-page">
    <div class="login-bg" />
    <div class="login-grid" />
    <div class="login-card">
      <div class="login-logo">folio<span>.</span></div>
      <div class="login-sub">Portfolio Manager</div>

      <!-- renderButton は使わず、自前ボタンで prompt() を呼ぶ -->
      <button class="google-btn" :disabled="loading" @click="handleGoogleLogin">
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
          <path d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z" fill="#4285F4"/>
          <path d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z" fill="#34A853"/>
          <path d="M5.84 14.09c-.22-.66-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l3.66-2.84z" fill="#FBBC05"/>
          <path d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z" fill="#EA4335"/>
        </svg>
        {{ loading ? 'ログイン中...' : 'Googleでログイン' }}
      </button>

      <p v-if="error" class="error-msg">{{ error }}</p>

      <p class="login-hint">
        Google Identity Services でOAuth認証し、<br />
        <code>POST /api/auth/google</code> にIDトークンを送信します
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '../../stores/auth'
import {useApi} from "~/composables/useApi";
const client = useApi();

definePageMeta({
  middleware: 'auth',
  layout: 'default',
})

const auth = useAuthStore()

const runtimeConfig = useRuntimeConfig()
const loading = ref(false)
const error = ref('')

useHead({
  script: [{ src: 'https://accounts.google.com/gsi/client', async: true, defer: true }],
})

// GSI を初期化（renderButton は呼ばない）
onMounted(() => {
  console.log('0. onMounted started'); // これが出るか確認
  const initGSI = () => {
    if (!window.google?.accounts?.id) {
      console.log('0. Waiting for Google Library...');
      setTimeout(initGSI, 100);
      return
    }
    console.log('0. Google Library Loaded!'); // これが出るか確認

    window.google.accounts.id.initialize({
      client_id: runtimeConfig.public.googleClientId,
      callback: handleCredentialResponse,
      ux_mode: 'popup',
    })
  }
  initGSI()
})

onUnmounted(() => {
  window.google?.accounts?.id?.cancel()
})

// ボタンクリック → GSI ポップアップを手動で開く
function handleGoogleLogin() {
  if (!window.google?.accounts?.id) {
    error.value = 'Google認証の読み込みに失敗しました。ページを再読み込みしてください。'
    return
  }
  window.google.accounts.id.prompt((notification: PromptNotification) => {
    // ポップアップがブロックされた or キャンセルされた場合
    if (notification.isNotDisplayed() || notification.isSkippedMoment()) {
      // One Tap が表示できないとき redirect フローにフォールバック
      window.google!.accounts.id.initialize({
        client_id: runtimeConfig.public.googleClientId,
        callback: handleCredentialResponse,
        ux_mode: 'redirect',
        login_uri: `${window.location.origin}/auth/google/callback`,
      })
    }
  })
}

// GSI コールバック: IDトークンをバックエンドへ送信
// GSI コールバック: IDトークンをバックエンドへ送信
async function handleCredentialResponse(response: { credential: string }) {
  console.log('--- Google Login Flow Started ---');
  console.log('1. Received ID Token from Google:', response.credential.substring(0, 20) + '...');

  loading.value = true
  error.value = ''

  try {
    console.log('2. Sending ID Token to Backend API...');
    const { data, error } = await client.POST("/api/v1/auth/google", {
      body: {
        token: response.credential // バックエンドの期待するキー名に合わせてください
      }
    });
    // const result = await api.auth.googleLogin(response.credential)

    console.log('3. Backend API Response:', data);

    const accessToken = data?.token || data?.accessToken
    const userId = Number(data?.userId);

    if (accessToken) {
      // console.log('4. Login Success! Token:', accessToken.substring(0, 15) + '...');
      auth.login({
        userId: userId,
        name: data.userName,
        token: accessToken,
      })

      console.log('5. Redirecting to /portfolio...');
      await navigateTo('/portfolio')
    } else {
      console.error('Error: AccessToken is missing in API response');

    }
  } catch (e: any) {
    console.error('--- API Error Details ---');
    console.error('Message:', e.message);
    console.error('Response Data:', e.response?._data); // Nuxt(ofetch) のエラー詳細
    console.error('Status Code:', e.response?.status);
    error.value = 'APIエラー: ' + (e.message || '不明なエラー')
  } finally {
    loading.value = false
    console.log('--- Google Login Flow Finished ---');
  }
}

// 型宣言
interface PromptNotification {
  isNotDisplayed: () => boolean
  isSkippedMoment: () => boolean
  isDismissedMoment: () => boolean
}
declare global {
  interface Window {
    google?: {
      accounts: {
        id: {
          initialize: (config: object) => void
          renderButton: (el: HTMLElement, options: object) => void
          prompt: (callback?: (n: PromptNotification) => void) => void
          cancel: () => void
        }
      }
    }
  }
}
</script>

<!-- style は元のまま流用 -->
<style scoped>
/* ... 既存のスタイルをそのまま使用 ... */
</style>