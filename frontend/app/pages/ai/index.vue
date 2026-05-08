<script setup lang="ts">

definePageMeta({ layout: 'menu' })

const client = useApi()

const profileText = ref('')
const loading = ref(false)
const error = ref('')
const result = ref<{
  cacheKey?: string
  careers?: unknown
  licenses?: unknown
  projects?: unknown
  skills?: unknown
} | null>(null)

const charCount = computed(() => profileText.value.length)
const canSubmit = computed(() => profileText.value.trim().length > 0 && !loading.value)

async function generate() {
  if (!canSubmit.value) return
  loading.value = true
  error.value = ''
  result.value = null

  try {
    const { data, error: apiError } = await client.POST('/api/v1/make_portfolio/profile-structure', {
      body: { profileInfo: profileText.value },
    })
    if (apiError) throw new Error('APIエラーが発生しました')
    result.value = data ?? null
  } catch (e: any) {
    error.value = e.message || '生成に失敗しました。もう一度お試しください。'
  } finally {
    loading.value = false
  }
}

function reset() {
  result.value = null
  error.value = ''
}
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
      <div class="panel" :class="{ dimmed: !!result }">
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
      <div v-if="result" class="result">
        <div class="result-head">
          <div class="result-title-wrap">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="#34d399" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <polyline points="20 6 9 17 4 12"/>
            </svg>
            <span class="result-title">生成完了</span>
          </div>
          <button class="btn-reset" @click="reset">やり直す</button>
        </div>

        <!-- 各セクション -->
        <div class="result-sections">
          <div v-if="result.careers" class="result-sec">
            <div class="result-sec-label">
              <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="#a78bfa" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <rect x="2" y="7" width="20" height="14" rx="2"/><path d="M16 7V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v2"/>
              </svg>
              経歴
            </div>
            <pre class="result-json">{{ JSON.stringify(result.careers, null, 2) }}</pre>
          </div>

          <div v-if="result.skills" class="result-sec">
            <div class="result-sec-label">
              <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="#60a5fa" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <polyline points="16 18 22 12 16 6"/><polyline points="8 6 2 12 8 18"/>
              </svg>
              スキル
            </div>
            <pre class="result-json">{{ JSON.stringify(result.skills, null, 2) }}</pre>
          </div>

          <div v-if="result.licenses" class="result-sec">
            <div class="result-sec-label">
              <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="#fb923c" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="12" cy="8" r="6"/><path d="M15.477 12.89 17 22l-5-3-5 3 1.523-9.11"/>
              </svg>
              資格
            </div>
            <pre class="result-json">{{ JSON.stringify(result.licenses, null, 2) }}</pre>
          </div>

          <div v-if="result.projects" class="result-sec">
            <div class="result-sec-label">
              <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="#34d399" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"/><path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"/>
              </svg>
              プロジェクト
            </div>
            <pre class="result-json">{{ JSON.stringify(result.projects, null, 2) }}</pre>
          </div>
        </div>
      </div>

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
.panel.dimmed { opacity: .5; pointer-events: none; }

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

/* ── 結果 ── */
.result {
  background: var(--color-background-primary, #fff);
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  border-radius: 14px;
  overflow: hidden;
}
.result-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 16px;
  border-bottom: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  background: var(--color-background-secondary, #f9fafb);
}
.result-title-wrap {
  display: flex;
  align-items: center;
  gap: 7px;
}
.result-title {
  font-size: 12px;
  font-weight: 500;
  color: #059669;
}
.btn-reset {
  font-size: 12px;
  color: var(--color-text-tertiary, #9ca3af);
  background: none;
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  border-radius: 7px;
  padding: 4px 12px;
  cursor: pointer;
  font-family: 'Noto Sans JP', sans-serif;
  transition: color .15s, background .15s;
}
.btn-reset:hover {
  color: var(--color-text-primary, #111);
  background: var(--color-background-secondary, #f3f4f6);
}

.result-sections { padding: 16px; display: flex; flex-direction: column; gap: 16px; }
.result-sec {}
.result-sec-label {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 11px;
  font-weight: 500;
  letter-spacing: .08em;
  text-transform: uppercase;
  color: var(--color-text-tertiary, #9ca3af);
  margin-bottom: 8px;
}
.result-json {
  font-size: 12px;
  line-height: 1.7;
  background: var(--color-background-secondary, #f9fafb);
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  border-radius: 8px;
  padding: 12px 14px;
  overflow-x: auto;
  color: var(--color-text-primary, #374151);
  font-family: 'SF Mono', 'Fira Code', 'Menlo', monospace;
  white-space: pre-wrap;
  word-break: break-word;
  margin: 0;
}

@media (max-width: 640px) {
  .page { padding: 20px 16px 40px; }
  .panel-foot { flex-direction: column; align-items: flex-start; }
  .btn-generate { width: 100%; justify-content: center; }
}
</style>