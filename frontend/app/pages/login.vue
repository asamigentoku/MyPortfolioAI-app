<script setup lang="ts">
import { useAuthStore } from '../stores/auth'
import { useApi } from '~/composables/useApi'

const client = useApi()

definePageMeta({
  middleware: 'auth',
  layout: false,
})

const auth = useAuthStore()
const runtimeConfig = useRuntimeConfig()
const loading = ref(false)
const error = ref('')

useHead({
  script: [{ src: 'https://accounts.google.com/gsi/client', async: true, defer: true }],
})

onMounted(() => {
  const initGSI = () => {
    if (!window.google?.accounts?.id) {
      setTimeout(initGSI, 100)
      return
    }
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

function handleGoogleLogin() {
  if (!window.google?.accounts?.id) {
    error.value = 'Google認証の読み込みに失敗しました。ページを再読み込みしてください。'
    return
  }
  window.google.accounts.id.prompt((notification: PromptNotification) => {
    if (notification.isNotDisplayed() || notification.isSkippedMoment()) {
      window.google!.accounts.id.initialize({
        client_id: runtimeConfig.public.googleClientId,
        callback: handleCredentialResponse,
        ux_mode: 'redirect',
        login_uri: `${window.location.origin}/auth/google/callback`,
      })
    }
  })
}

async function handleCredentialResponse(response: { credential: string }) {
  loading.value = true
  error.value = ''
  try {
    const { data } = await client.POST('/api/v1/auth/google', {
      body: { token: response.credential },
    })
    const accessToken = data?.token || data?.accessToken
    const userId = Number(data?.userId)
    if (accessToken) {
      auth.login({ userId, name: data.userName, token: accessToken })
      await navigateTo('/portfolio')
    }
  } catch (e: any) {
    error.value = 'ログインに失敗しました。もう一度お試しください。'
  } finally {
    loading.value = false
  }
}

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

<template>
  <div class="root">
    <!-- 背景装飾 -->
    <div class="bg-grid" />
    <div class="bg-glow glow-purple" />
    <div class="bg-glow glow-blue" />

    <!-- ナビ -->
    <header class="nav">
      <NuxtLink to="/" class="nav-logo">
        <span class="logo-mark">✦</span>
        <span class="logo-text">Aether</span>
      </NuxtLink>
    </header>

    <!-- ログインカード -->
    <main class="main">
      <div class="card">
        <!-- カード内グロー -->
        <div class="card-glow" />

        <div class="card-inner">
          <!-- ヘッダー -->
          <div class="card-header">
            <div class="card-icon">
              <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="#a78bfa" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                <path d="M12 2L9.5 9.5 2 12l7.5 2.5L12 22l2.5-7.5L22 12l-7.5-2.5z"/>
              </svg>
            </div>
            <h1 class="card-title">ようこそ</h1>
            <p class="card-sub">Googleアカウントでログインして<br />AIポートフォリオを始めましょう</p>
          </div>

          <!-- Googleログインボタン -->
          <button
            class="google-btn"
            :class="{ loading }"
            :disabled="loading"
            @click="handleGoogleLogin"
          >
            <span class="google-icon" v-if="!loading">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none">
                <path d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z" fill="#4285F4"/>
                <path d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z" fill="#34A853"/>
                <path d="M5.84 14.09c-.22-.66-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l3.66-2.84z" fill="#FBBC05"/>
                <path d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z" fill="#EA4335"/>
              </svg>
            </span>
            <span class="spinner" v-else />
            {{ loading ? 'ログイン中...' : 'Google でログイン' }}
          </button>

          <!-- エラー -->
          <div v-if="error" class="error-box">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/>
            </svg>
            {{ error }}
          </div>

          <!-- 区切り -->
          <div class="divider">
            <span class="divider-line" />
            <span class="divider-text">セキュアな認証</span>
            <span class="divider-line" />
          </div>

          <!-- セキュリティ説明 -->
          <div class="features">
            <div class="feat">
              <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="#a78bfa" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
              </svg>
              <span>Google OAuth 2.0 で保護</span>
            </div>
            <div class="feat">
              <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="#a78bfa" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <rect x="3" y="11" width="18" height="11" rx="2" ry="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/>
              </svg>
              <span>パスワード不要</span>
            </div>
            <div class="feat">
              <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="#a78bfa" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <polyline points="20 6 9 17 4 12"/>
              </svg>
              <span>アカウント登録不要</span>
            </div>
          </div>
        </div>
      </div>

      <p class="back-link">
        <NuxtLink to="/">← トップページへ戻る</NuxtLink>
      </p>
    </main>
  </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@300;400;500&family=DM+Serif+Display&display=swap');

/* ── ベース ── */
.root {
  min-height: 100vh;
  background: #0c0c0f;
  color: #f0eeff;
  font-family: 'Noto Sans JP', sans-serif;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  position: relative;
}

/* ── 背景 ── */
.bg-grid {
  position: fixed;
  inset: 0;
  background-image:
    linear-gradient(rgba(255,255,255,.025) 1px, transparent 1px),
    linear-gradient(90deg, rgba(255,255,255,.025) 1px, transparent 1px);
  background-size: 40px 40px;
  pointer-events: none;
  z-index: 0;
}
.bg-glow {
  position: absolute;
  border-radius: 50%;
  filter: blur(90px);
  pointer-events: none;
  z-index: 0;
}
.glow-purple {
  width: 500px; height: 500px;
  top: -80px; left: -120px;
  background: radial-gradient(circle, rgba(124,58,237,.25) 0%, transparent 70%);
}
.glow-blue {
  width: 400px; height: 400px;
  bottom: -60px; right: -80px;
  background: radial-gradient(circle, rgba(37,99,235,.2) 0%, transparent 70%);
}

/* ── ナビ ── */
.nav {
  position: relative;
  z-index: 10;
  padding: 0 40px;
  height: 60px;
  display: flex;
  align-items: center;
  border-bottom: 0.5px solid rgba(255,255,255,.07);
}
.nav-logo {
  display: flex;
  align-items: center;
  gap: 8px;
  text-decoration: none;
}
.logo-mark { font-size: 18px; color: #a78bfa; }
.logo-text {
  font-family: 'DM Serif Display', serif;
  font-size: 19px;
  color: #f0eeff;
  letter-spacing: .02em;
}

/* ── メイン ── */
.main {
  position: relative;
  z-index: 1;
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px 24px;
}

/* ── カード ── */
.card {
  width: 100%;
  max-width: 400px;
  position: relative;
  background: rgba(255,255,255,.04);
  border: 0.5px solid rgba(255,255,255,.1);
  border-radius: 20px;
  overflow: hidden;
}
.card-glow {
  position: absolute;
  top: -80px; left: 50%;
  transform: translateX(-50%);
  width: 300px; height: 200px;
  background: radial-gradient(circle, rgba(124,58,237,.2) 0%, transparent 70%);
  filter: blur(40px);
  pointer-events: none;
}
.card-inner {
  padding: 40px 36px;
  display: flex;
  flex-direction: column;
  gap: 20px;
  position: relative;
}

/* ── カードヘッダー ── */
.card-header { text-align: center; display: flex; flex-direction: column; align-items: center; gap: 12px; }
.card-icon {
  width: 48px; height: 48px;
  border-radius: 14px;
  background: rgba(167,139,250,.12);
  border: 0.5px solid rgba(167,139,250,.25);
  display: flex;
  align-items: center;
  justify-content: center;
}
.card-title {
  font-family: 'DM Serif Display', serif;
  font-size: 26px;
  color: #f0eeff;
  line-height: 1;
}
.card-sub {
  font-size: 13px;
  color: rgba(255,255,255,.45);
  line-height: 1.7;
}

/* ── Googleボタン ── */
.google-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  width: 100%;
  padding: 13px 20px;
  border-radius: 12px;
  border: 0.5px solid rgba(255,255,255,.14);
  background: rgba(255,255,255,.06);
  color: #f0eeff;
  font-size: 14px;
  font-family: 'Noto Sans JP', sans-serif;
  font-weight: 500;
  cursor: pointer;
  transition: background .18s, border-color .18s, transform .15s;
  letter-spacing: .01em;
}
.google-btn:hover:not(:disabled) {
  background: rgba(255,255,255,.1);
  border-color: rgba(255,255,255,.2);
  transform: translateY(-1px);
}
.google-btn:disabled { opacity: .6; cursor: not-allowed; }
.google-btn.loading {
  background: linear-gradient(135deg, rgba(124,58,237,.2), rgba(37,99,235,.2));
  border-color: rgba(167,139,250,.3);
}
.google-icon { display: flex; align-items: center; }

/* スピナー */
.spinner {
  width: 16px; height: 16px;
  border-radius: 50%;
  border: 2px solid rgba(255,255,255,.2);
  border-top-color: #a78bfa;
  animation: spin .7s linear infinite;
  flex-shrink: 0;
}
@keyframes spin { to { transform: rotate(360deg); } }

/* ── エラー ── */
.error-box {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 14px;
  border-radius: 10px;
  background: rgba(239,68,68,.1);
  border: 0.5px solid rgba(239,68,68,.25);
  color: #fca5a5;
  font-size: 12.5px;
  line-height: 1.5;
}

/* ── 区切り ── */
.divider {
  display: flex;
  align-items: center;
  gap: 10px;
}
.divider-line {
  flex: 1;
  height: 0.5px;
  background: rgba(255,255,255,.08);
}
.divider-text {
  font-size: 11px;
  color: rgba(255,255,255,.25);
  letter-spacing: .06em;
  white-space: nowrap;
}

/* ── 特徴リスト ── */
.features { display: flex; flex-direction: column; gap: 10px; }
.feat {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 12.5px;
  color: rgba(255,255,255,.45);
}

/* ── 戻るリンク ── */
.back-link {
  margin-top: 20px;
  font-size: 12.5px;
}
.back-link a {
  color: rgba(255,255,255,.35);
  text-decoration: none;
  transition: color .18s;
}
.back-link a:hover { color: rgba(255,255,255,.7); }

@media (max-width: 480px) {
  .card-inner { padding: 32px 24px; }
  .nav { padding: 0 20px; }
}
</style>