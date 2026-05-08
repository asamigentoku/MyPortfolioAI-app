<script setup lang="ts">
import { useAuthStore } from '~/stores/auth'
import { useApi } from '~/composables/useApi'

definePageMeta({ layout: 'menu' })

const auth = useAuthStore()
const client = useApi()

const isPublic = ref(false)
const saving = ref(false)
const loading = ref(true)
const saveStatus = ref<'idle' | 'success' | 'error'>('idle')
const copied = ref(false)

const userId = computed(() => auth.user?.userId)

const publicUrl = computed(() => {
  if (!userId.value) return ''
  return `${window.location.origin}/portfolio/${userId.value}`
})

onMounted(async () => {
  if (!userId.value) return
  try {
    const { data } = await client.GET('/api/v1/users/{id}', {
      params: { path: { id: userId.value } },
    })
    isPublic.value = data?.isPublic ?? false
  } finally {
    loading.value = false
  }
})

async function saveVisibility() {
  if (!userId.value) return
  saving.value = true
  saveStatus.value = 'idle'
  try {
    await client.PUT('/api/v1/users/{id}', {
      params: { path: { id: userId.value } },
      body: {
        id: userId.value,
        name: auth.user?.name,
        isPublic: isPublic.value,
      },
    })
    saveStatus.value = 'success'
    setTimeout(() => { saveStatus.value = 'idle' }, 2500)
  } catch {
    saveStatus.value = 'error'
  } finally {
    saving.value = false
  }
}

async function copyUrl() {
  if (!publicUrl.value) return
  await navigator.clipboard.writeText(publicUrl.value)
  copied.value = true
  setTimeout(() => { copied.value = false }, 2000)
}
</script>

<template>
  <div class="page">

    <!-- ページヘッダー -->
    <div class="page-header">
      <div class="header-icon">
        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="#a78bfa" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="12" cy="12" r="3"/>
          <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1-2.83 2.83l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-4 0v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83-2.83l.06-.06A1.65 1.65 0 0 0 4.68 15a1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1 0-4h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 2.83-2.83l.06.06A1.65 1.65 0 0 0 9 4.68a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 4 0v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 2.83l-.06.06A1.65 1.65 0 0 0 19.4 9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 0 4h-.09a1.65 1.65 0 0 0-1.51 1z"/>
        </svg>
      </div>
      <div>
        <h1 class="page-title">設定</h1>
        <p class="page-sub">公開設定や共有URLを管理します</p>
      </div>
    </div>

    <div v-if="loading" class="loading">
      <span class="spinner" />
      <span>読み込み中...</span>
    </div>

    <div v-else class="sections">

      <!-- 公開設定 -->
      <section class="card">
        <div class="card-header">
          <div class="card-header-left">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/>
            </svg>
            公開設定
          </div>
        </div>

        <div class="card-body">
          <div class="setting-row">
            <div class="setting-info">
              <p class="setting-label">ポートフォリオを公開する</p>
              <p class="setting-desc">オンにすると、公開URLからあなたのポートフォリオを誰でも閲覧できます</p>
            </div>
            <button
              class="toggle"
              :class="{ on: isPublic }"
              role="switch"
              :aria-checked="isPublic"
              @click="isPublic = !isPublic"
            >
              <span class="toggle-thumb" />
            </button>
          </div>

          <div class="status-badge" :class="isPublic ? 'public' : 'private'">
            <span class="status-dot" />
            {{ isPublic ? '公開中' : '非公開' }}
          </div>
        </div>

        <div class="card-footer">
          <div class="save-feedback">
            <transition name="fade">
              <span v-if="saveStatus === 'success'" class="feedback success">
                <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
                  <polyline points="20 6 9 17 4 12"/>
                </svg>
                保存しました
              </span>
              <span v-else-if="saveStatus === 'error'" class="feedback error">
                <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/>
                </svg>
                保存に失敗しました
              </span>
            </transition>
          </div>
          <button class="btn-save" :disabled="saving" @click="saveVisibility">
            <span class="spinner-sm" v-if="saving" />
            {{ saving ? '保存中...' : '変更を保存' }}
          </button>
        </div>
      </section>

      <!-- 公開URL -->
      <section class="card">
        <div class="card-header">
          <div class="card-header-left">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M10 13a5 5 0 0 0 7.54.54l3-3a5 5 0 0 0-7.07-7.07l-1.72 1.71"/>
              <path d="M14 11a5 5 0 0 0-7.54-.54l-3 3a5 5 0 0 0 7.07 7.07l1.71-1.71"/>
            </svg>
            公開URL
          </div>
          <span v-if="!isPublic" class="disabled-note">公開設定をオンにすると有効になります</span>
        </div>

        <div class="card-body">
          <div class="url-row" :class="{ disabled: !isPublic }">
            <div class="url-display">
              <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="12" cy="12" r="10"/><line x1="2" y1="12" x2="22" y2="12"/>
                <path d="M12 2a15.3 15.3 0 0 1 4 10 15.3 15.3 0 0 1-4 10 15.3 15.3 0 0 1-4-10 15.3 15.3 0 0 1 4-10z"/>
              </svg>
              <span class="url-text">{{ publicUrl }}</span>
            </div>
            <div class="url-actions">
              <a
                :href="publicUrl"
                target="_blank"
                class="btn-icon"
                :class="{ disabled: !isPublic }"
                :tabindex="isPublic ? 0 : -1"
                title="新しいタブで開く"
              >
                <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M18 13v6a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h6"/>
                  <polyline points="15 3 21 3 21 9"/><line x1="10" y1="14" x2="21" y2="3"/>
                </svg>
              </a>
              <button
                class="btn-copy"
                :class="{ copied, disabled: !isPublic }"
                :disabled="!isPublic"
                @click="copyUrl"
                title="URLをコピー"
              >
                <transition name="swap" mode="out-in">
                  <span v-if="copied" key="check" class="copy-inner">
                    <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
                      <polyline points="20 6 9 17 4 12"/>
                    </svg>
                    コピー済み
                  </span>
                  <span v-else key="copy" class="copy-inner">
                    <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                      <rect x="9" y="9" width="13" height="13" rx="2" ry="2"/>
                      <path d="M5 15H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h9a2 2 0 0 1 2 2v1"/>
                    </svg>
                    URLをコピー
                  </span>
                </transition>
              </button>
            </div>
          </div>
        </div>
      </section>

      <!-- アカウント情報 -->
      <section class="card">
        <div class="card-header">
          <div class="card-header-left">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/>
            </svg>
            アカウント情報
          </div>
        </div>
        <div class="card-body">
          <div class="info-row">
            <span class="info-label">名前</span>
            <span class="info-value">{{ auth.user?.name ?? '—' }}</span>
          </div>
        </div>
      </section>

    </div>
  </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@300;400;500&family=DM+Serif+Display&display=swap');

.page {
  padding: 32px 32px 60px;
  max-width: 680px;
  margin: 0 auto;
  font-family: 'Noto Sans JP', sans-serif;
}

/* ── ページヘッダー ── */
.page-header {
  display: flex;
  align-items: flex-start;
  gap: 14px;
  margin-bottom: 28px;
}
.header-icon {
  width: 40px; height: 40px;
  border-radius: 10px;
  background: rgba(167,139,250,.12);
  border: 0.5px solid rgba(167,139,250,.25);
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  margin-top: 2px;
}
.page-title { font-size: 18px; font-weight: 500; color: var(--color-text-primary, #111); margin-bottom: 4px; }
.page-sub   { font-size: 12.5px; color: var(--color-text-tertiary, #9ca3af); }

/* ── ローディング ── */
.loading {
  display: flex;
  align-items: center;
  gap: 10px;
  color: var(--color-text-tertiary, #9ca3af);
  font-size: 13px;
  padding: 40px 0;
  justify-content: center;
}

/* ── セクション ── */
.sections { display: flex; flex-direction: column; gap: 14px; }

/* ── カード ── */
.card {
  background: var(--color-background-primary, #fff);
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  border-radius: 14px;
  overflow: hidden;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 18px;
  border-bottom: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  background: var(--color-background-secondary, #f9fafb);
  gap: 12px;
}
.card-header-left {
  display: flex;
  align-items: center;
  gap: 7px;
  font-size: 11px;
  font-weight: 500;
  letter-spacing: .08em;
  text-transform: uppercase;
  color: var(--color-text-tertiary, #9ca3af);
}
.disabled-note {
  font-size: 11px;
  color: var(--color-text-tertiary, #9ca3af);
  font-style: italic;
}

.card-body { padding: 18px; }

.card-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 18px;
  border-top: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  background: var(--color-background-secondary, #f9fafb);
}

/* ── 設定行 ── */
.setting-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
  margin-bottom: 14px;
}
.setting-info {}
.setting-label { font-size: 13.5px; font-weight: 500; color: var(--color-text-primary, #111); margin-bottom: 3px; }
.setting-desc  { font-size: 12px; color: var(--color-text-tertiary, #9ca3af); line-height: 1.5; }

/* ── ステータスバッジ ── */
.status-badge {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 4px 10px;
  border-radius: 99px;
  font-size: 11.5px;
  font-weight: 500;
  border: 0.5px solid;
}
.status-badge.public  { background: rgba(16,185,129,.08); border-color: rgba(16,185,129,.25); color: #059669; }
.status-badge.private { background: rgba(156,163,175,.08); border-color: rgba(156,163,175,.2); color: #9ca3af; }
.status-dot {
  width: 6px; height: 6px;
  border-radius: 50%;
  background: currentColor;
}

/* ── トグル ── */
.toggle {
  width: 44px; height: 24px;
  border-radius: 99px;
  border: 0.5px solid rgba(0,0,0,.12);
  background: #e5e7eb;
  position: relative;
  cursor: pointer;
  transition: background .2s, border-color .2s;
  flex-shrink: 0;
  padding: 0;
}
.toggle.on {
  background: linear-gradient(135deg, #7c3aed, #2563eb);
  border-color: transparent;
}
.toggle-thumb {
  position: absolute;
  top: 3px; left: 3px;
  width: 16px; height: 16px;
  border-radius: 50%;
  background: #fff;
  box-shadow: 0 1px 3px rgba(0,0,0,.15);
  transition: transform .2s;
}
.toggle.on .toggle-thumb { transform: translateX(20px); }

/* ── 保存フィードバック ── */
.save-feedback { min-height: 20px; }
.feedback {
  display: inline-flex;
  align-items: center;
  gap: 5px;
  font-size: 12px;
}
.feedback.success { color: #059669; }
.feedback.error   { color: #ef4444; }

/* ── 保存ボタン ── */
.btn-save {
  display: inline-flex;
  align-items: center;
  gap: 7px;
  padding: 8px 18px;
  border-radius: 9px;
  border: none;
  background: linear-gradient(135deg, #7c3aed, #2563eb);
  color: #fff;
  font-size: 13px;
  font-family: 'Noto Sans JP', sans-serif;
  font-weight: 500;
  cursor: pointer;
  box-shadow: 0 0 14px rgba(124,58,237,.25);
  transition: opacity .18s, transform .15s;
}
.btn-save:hover:not(:disabled) { opacity: .88; transform: translateY(-1px); }
.btn-save:disabled { opacity: .5; cursor: not-allowed; box-shadow: none; }

/* ── URL行 ── */
.url-row {
  display: flex;
  align-items: center;
  gap: 10px;
  background: var(--color-background-secondary, #f9fafb);
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  border-radius: 10px;
  padding: 10px 12px;
  transition: opacity .2s;
}
.url-row.disabled { opacity: .4; pointer-events: none; }
.url-display {
  display: flex;
  align-items: center;
  gap: 8px;
  flex: 1;
  min-width: 0;
  color: var(--color-text-tertiary, #9ca3af);
}
.url-text {
  font-size: 12.5px;
  color: var(--color-text-secondary, #374151);
  font-family: 'SF Mono', 'Fira Code', 'Menlo', monospace;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.url-actions {
  display: flex;
  align-items: center;
  gap: 6px;
  flex-shrink: 0;
}

.btn-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 30px; height: 30px;
  border-radius: 7px;
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  background: var(--color-background-primary, #fff);
  color: var(--color-text-tertiary, #9ca3af);
  text-decoration: none;
  transition: color .15s, background .15s;
}
.btn-icon:hover { color: var(--color-text-primary, #111); background: var(--color-background-secondary, #f3f4f6); }
.btn-icon.disabled { pointer-events: none; opacity: .4; }

.btn-copy {
  display: inline-flex;
  align-items: center;
  padding: 6px 12px;
  border-radius: 7px;
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  background: var(--color-background-primary, #fff);
  color: var(--color-text-secondary, #374151);
  font-size: 12px;
  font-family: 'Noto Sans JP', sans-serif;
  cursor: pointer;
  transition: background .15s, border-color .15s, color .15s;
  white-space: nowrap;
}
.btn-copy:hover:not(:disabled) {
  background: var(--color-background-secondary, #f3f4f6);
}
.btn-copy.copied {
  border-color: rgba(16,185,129,.3);
  color: #059669;
  background: rgba(16,185,129,.06);
}
.btn-copy:disabled { opacity: .4; cursor: not-allowed; }
.copy-inner { display: flex; align-items: center; gap: 5px; }

/* ── アカウント情報 ── */
.info-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 0;
  border-bottom: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  gap: 16px;
}
.info-row:last-child { border-bottom: none; }
.info-label { font-size: 12.5px; color: var(--color-text-tertiary, #9ca3af); flex-shrink: 0; }
.info-value { font-size: 13px; color: var(--color-text-primary, #111); text-align: right; }
.info-value.mono { font-family: 'SF Mono', 'Fira Code', 'Menlo', monospace; font-size: 12px; }

/* ── スピナー ── */
.spinner {
  width: 16px; height: 16px;
  border-radius: 50%;
  border: 2px solid rgba(167,139,250,.2);
  border-top-color: #a78bfa;
  animation: spin .7s linear infinite;
}
.spinner-sm {
  width: 12px; height: 12px;
  border-radius: 50%;
  border: 2px solid rgba(255,255,255,.25);
  border-top-color: #fff;
  animation: spin .7s linear infinite;
  display: inline-block;
}
@keyframes spin { to { transform: rotate(360deg); } }

/* ── トランジション ── */
.fade-enter-active, .fade-leave-active { transition: opacity .2s; }
.fade-enter-from, .fade-leave-to { opacity: 0; }

.swap-enter-active, .swap-leave-active { transition: opacity .15s; }
.swap-enter-from, .swap-leave-to { opacity: 0; }

@media (max-width: 600px) {
  .page { padding: 20px 16px 40px; }
  .url-row { flex-direction: column; align-items: flex-start; }
  .url-actions { width: 100%; }
  .btn-copy { flex: 1; justify-content: center; }
}
</style>
