<script setup lang="ts">
import type { components } from '../types/openapi'
import { useAuthStore } from '~/stores/auth'
import { useProfileStructureStore } from '~/stores/portfolio_cache'

definePageMeta({ layout: 'menu' })

type ProfileStructureResponse = components['schemas']['ProfileStructureResponse']

const client = useApi()
const auth = useAuthStore()
const profileStore = useProfileStructureStore()
const userId = computed(() => auth.user?.userId)

const profileText = ref('')
const loading = ref(false)
const error = ref('')

const charCount = computed(() => profileText.value.length)
const canSubmit = computed(() => profileText.value.trim().length > 0 && !loading.value)

const hasPrev = computed(() => profileStore.hasPrev)
const hasNext = computed(() => profileStore.hasNext)
const historyLabel = computed(() => profileStore.historyLabel)

async function generate() {
  if (!canSubmit.value) return
  loading.value = true
  error.value = ''
  profileStore.setResult(null)

  try {
    const { data, error: apiError } = await client.POST('/api/v1/make_portfolio/profile-structure', {
      body: { profileInfo: profileText.value },
    })
    if (apiError) throw new Error('APIエラーが発生しました')
    profileStore.setResult(data ?? null)

    if (data?.cacheKey) {
      profileStore.addCacheKey(data.cacheKey)
    }
  } catch (e: any) {
    error.value = e.message || '生成に失敗しました。もう一度お試しください。'
  } finally {
    loading.value = false
  }
}

async function loadByIndex(index: number) {
  if (index < 0 || index >= profileStore.cacheKeys.length) return
  loading.value = true
  error.value = ''

  try {
    const { data } = await client.POST('/api/v1/make_portfolio/profile-structure/cache_key', {
      body: profileStore.cacheKeys[index]!,
    })
    profileStore.setResult(data ?? null)
    profileStore.setIndex(index)
  } catch (e: any) {
    error.value = e.message || '取得に失敗しました。'
  } finally {
    loading.value = false
  }
}

async function savePortfolio() {
  if (!profileStore.result?.cacheKey || !userId.value) return
  loading.value = true
  error.value = ''
  try {
    const { error: apiError } = await client.POST('/api/v1/make_portfolio/profile-structure/save', {
      body: { cacheKey: profileStore.result.cacheKey, userId: userId.value },
    })
    if (apiError) throw new Error('保存に失敗しました')
    await navigateTo('/portfolio')
  } catch (e: any) {
    error.value = e.message || '保存に失敗しました。もう一度お試しください。'
  } finally {
    loading.value = false
  }
}
function goPrev() { if (hasPrev.value) loadByIndex(profileStore.currentIndex - 1) }
function goNext() { if (hasNext.value) loadByIndex(profileStore.currentIndex + 1) }
</script>

<template>
  <div class="page">
    <!-- ページヘッダー -->
    <div class="page-header">
      <div class="header-icon">
        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="#a78bfa" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
          <path d="M12 2L9.5 9.5 2 12l7.5 2.5L12 22l2.5-7.5L22 12l-7.5-2.5z"/>
        </svg>
      </div>
      <div>
        <h1 class="page-title">AI ポートフォリオ生成</h1>
        <p class="page-sub">プロフィール文を入力すると、AIが経歴・スキル・資格・プロジェクトを構造化して生成します</p>
      </div>
    </div>

    <!-- 入力エリア / 結果エリア -->
    <div class="content">

      <!-- 入力パネル -->
      <div class="panel">
        <div class="panel-head">
          <span class="panel-label">プロフィール入力</span>
          <span class="char-count" :class="{ warn: charCount > 2000 }">{{ charCount.toLocaleString() }} 文字</span>
        </div>

        <textarea
          v-model="profileText"
          class="textarea"
          placeholder="例：
私は5年間のバックエンド開発経験を持つエンジニアです。
主にJava / Spring Boot でREST APIを設計・開発してきました。
直近では在庫管理SaaSのアーキテクチャ設計を担当し、月間100万リクエストを処理するシステムを構築しました。
資格はAWS SAA、応用情報技術者を保有しています。
趣味でNuxt 3 / Vue.js を使ったフロントエンド開発もしています。"
          :disabled="loading"
        />

        <div class="panel-foot">
          <p class="hint">
            <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <circle cx="12" cy="12" r="10"/><line x1="12" y1="16" x2="12" y2="12"/><line x1="12" y1="8" x2="12.01" y2="8"/>
            </svg>
            職歴・スキル・資格・プロジェクトを含む文章を入力すると精度が上がります
          </p>
          <button class="btn-generate" :disabled="!canSubmit" @click="generate">
            <span v-if="!loading" class="btn-inner">
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M12 2L9.5 9.5 2 12l7.5 2.5L12 22l2.5-7.5L22 12l-7.5-2.5z"/>
              </svg>
              生成する
            </span>
            <span v-else class="btn-inner">
              <span class="spinner" />
              生成中...
            </span>
          </button>
        </div>
      </div>

      <!-- エラー -->
      <div v-if="error" class="error-box">
        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/>
        </svg>
        {{ error }}
      </div>

      <!-- 結果 -->
      <template v-if="profileStore.result">

        <!-- 保存・ナビゲーションバー -->
        <div class="history-bar">
          <!-- 矢印ナビ（保存済みキーが2件以上あるとき表示） -->
          <div class="nav-group" v-if="profileStore.cacheKeys.length > 1">
            <button
                class="nav-btn"
                :disabled="!hasPrev || loading"
                @click="goPrev"
                title="前の結果"
            >
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round">
                <polyline points="15 18 9 12 15 6"/>
              </svg>
            </button>
            <span class="nav-label">{{ historyLabel }}</span>
            <button
                class="nav-btn"
                :disabled="!hasNext || loading"
                @click="goNext"
                title="次の結果"
            >
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round">
                <polyline points="9 18 15 12 9 6"/>
              </svg>
            </button>
          </div>

          <div class="bar-actions">
            <button
                class="btn-save"
                :disabled="loading"
                @click="savePortfolio"
            >
              <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"/><polyline points="17 21 17 13 7 13 7 21"/><polyline points="7 3 7 8 15 8"/>
              </svg>
              保存する
            </button>
          </div>
        </div>

        <AiPortfolioResult :result="profileStore.result" />
      </template>

    </div>
  </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@300;400;500&family=DM+Serif+Display&display=swap');

.page {
  padding: 32px 32px 60px;
  max-width: 860px;
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
.page-title {
  font-size: 18px;
  font-weight: 500;
  color: var(--color-text-primary, #111);
  margin-bottom: 4px;
}
.page-sub {
  font-size: 12.5px;
  color: var(--color-text-tertiary, #9ca3af);
  line-height: 1.6;
}

/* ── コンテンツ ── */
.content { display: flex; flex-direction: column; gap: 16px; }

/* ── パネル ── */
.panel {
  background: var(--color-background-primary, #fff);
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  border-radius: 14px;
  overflow: hidden;
  transition: opacity .2s;
}

.panel-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 16px;
  border-bottom: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  background: var(--color-background-secondary, #f9fafb);
}
.panel-label {
  font-size: 11px;
  letter-spacing: .08em;
  text-transform: uppercase;
  color: var(--color-text-tertiary, #9ca3af);
  font-weight: 500;
}
.char-count {
  font-size: 11px;
  color: var(--color-text-tertiary, #9ca3af);
  font-variant-numeric: tabular-nums;
}
.char-count.warn { color: #f59e0b; }

.textarea {
  width: 100%;
  min-height: 240px;
  padding: 16px;
  font-size: 13.5px;
  font-family: 'Noto Sans JP', sans-serif;
  line-height: 1.8;
  color: var(--color-text-primary, #111);
  background: transparent;
  border: none;
  resize: vertical;
  outline: none;
  box-sizing: border-box;
}
.textarea::placeholder { color: var(--color-text-tertiary, #9ca3af); }
.textarea:disabled { opacity: .5; cursor: not-allowed; }

.panel-foot {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 16px;
  border-top: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  background: var(--color-background-secondary, #f9fafb);
  gap: 12px;
}
.hint {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 11.5px;
  color: var(--color-text-tertiary, #9ca3af);
  line-height: 1.4;
}

/* ── 生成ボタン ── */
.btn-generate {
  display: inline-flex;
  align-items: center;
  padding: 9px 20px;
  border-radius: 9px;
  border: none;
  background: linear-gradient(135deg, #7c3aed, #2563eb);
  color: #fff;
  font-size: 13px;
  font-family: 'Noto Sans JP', sans-serif;
  font-weight: 500;
  cursor: pointer;
  white-space: nowrap;
  box-shadow: 0 0 16px rgba(124,58,237,.3);
  transition: opacity .18s, transform .15s;
  flex-shrink: 0;
}
.btn-generate:hover:not(:disabled) { opacity: .88; transform: translateY(-1px); }
.btn-generate:disabled { opacity: .4; cursor: not-allowed; box-shadow: none; }
.btn-inner { display: flex; align-items: center; gap: 7px; }

/* スピナー */
.spinner {
  width: 13px; height: 13px;
  border-radius: 50%;
  border: 2px solid rgba(255,255,255,.25);
  border-top-color: #fff;
  animation: spin .7s linear infinite;
}
@keyframes spin { to { transform: rotate(360deg); } }

/* ── エラー ── */
.error-box {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 16px;
  border-radius: 10px;
  background: rgba(239,68,68,.06);
  border: 0.5px solid rgba(239,68,68,.2);
  color: #ef4444;
  font-size: 13px;
}

/* ── 履歴バー ── */
.history-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 14px;
  border-radius: 12px;
  background: var(--color-background-secondary, #f9fafb);
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  gap: 12px;
}

/* 矢印ナビグループ */
.nav-group {
  display: flex;
  align-items: center;
  gap: 6px;
}
.nav-btn {
  width: 30px; height: 30px;
  border-radius: 8px;
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  background: var(--color-background-primary, #fff);
  color: var(--color-text-secondary, #6b7280);
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: background .15s, opacity .15s;
  padding: 0;
}
.nav-btn:hover:not(:disabled) {
  background: var(--color-background-tertiary, #f3f4f6);
}
.nav-btn:disabled { opacity: .35; cursor: not-allowed; }

.nav-label {
  font-size: 12px;
  font-variant-numeric: tabular-nums;
  color: var(--color-text-tertiary, #9ca3af);
  min-width: 36px;
  text-align: center;
}

/* アクションボタングループ */
.bar-actions {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-left: auto;
}


.btn-save {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 7px 16px;
  border-radius: 8px;
  border: none;
  background: linear-gradient(135deg, #7c3aed, #2563eb);
  color: #fff;
  font-size: 12.5px;
  font-family: 'Noto Sans JP', sans-serif;
  font-weight: 500;
  cursor: pointer;
  box-shadow: 0 0 12px rgba(124,58,237,.25);
  transition: opacity .18s, transform .15s;
  white-space: nowrap;
}
.btn-save:hover:not(:disabled) { opacity: .88; transform: translateY(-1px); }
.btn-save:disabled { opacity: .4; cursor: not-allowed; box-shadow: none; }

@media (max-width: 640px) {
  .page { padding: 20px 16px 40px; }
  .panel-foot { flex-direction: column; align-items: flex-start; }
  .btn-generate { width: 100%; justify-content: center; }
  .history-bar { flex-wrap: wrap; }
  .bar-actions { width: 100%; }
  .btn-save { flex: 1; justify-content: center; }
}
</style>
