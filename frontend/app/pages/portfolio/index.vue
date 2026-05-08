<script setup lang="ts">
const authStore = useAuthStore()
const { user, isLoggedIn } = storeToRefs(authStore)
interface Skill {
  name: string
  level: number
  description: string
  isFramework: boolean
  category: 'Backend' | 'Frontend' | 'Language' | 'Database' | 'DevOps' | 'ML' | 'Other'
}
interface Career   { careerName: string; time: string }
interface License  { licenseName: string; createdAt: string }
interface Project  { title: string; explanation: string; url: string | null; githubUrl: string | null; isGithub: boolean; skills: Skill[] }
interface Profile  { careers: Career[]; licenses: License[]; projects: Project[]; skills: Skill[] }

const profile: Profile = {
  careers: [
    { careerName: '株式会社テックイノベーション — シニアバックエンドエンジニア', time: '2022-04-01T00:00:00' },
    { careerName: '合同会社クリエイティブラボ — フルスタックエンジニア',         time: '2019-07-01T00:00:00' },
    { careerName: '○○大学 情報工学科 卒業',                                      time: '2019-03-01T00:00:00' },
  ],
  licenses: [
    { licenseName: '応用情報技術者試験',                              createdAt: '2021-11-01' },
    { licenseName: 'AWS Certified Solutions Architect – Associate', createdAt: '2022-08-15' },
    { licenseName: 'Google Cloud Professional Data Engineer',       createdAt: '2023-05-20' },
  ],
  projects: [
    {
      title: 'AIポートフォリオ生成サービス',
      explanation: 'Groq LLMを活用し、自然言語のプロフィール文から構造化されたポートフォリオJSONを自動生成するWebサービス。Spring Boot + Nuxt 3で構築。',
      url: 'https://portfolio-ai.example.com', githubUrl: 'https://github.com/example/portfolio-ai', isGithub: true,
      skills: [
        { name: 'Spring Boot', level: 4, description: 'REST API', isFramework: true,  category: 'Backend'  },
        { name: 'Nuxt 3',      level: 4, description: 'SSR',      isFramework: true,  category: 'Frontend' },
        { name: 'Groq API',    level: 3, description: 'LLM呼出', isFramework: false, category: 'ML'       },
      ],
    },
    {
      title: 'リアルタイム在庫管理ダッシュボード',
      explanation: 'WebSocketを用いたリアルタイム在庫追跡システム。複数倉庫の在庫状況を一元管理し、低在庫アラートを自動送信。',
      url: null, githubUrl: 'https://github.com/example/inventory-dash', isGithub: true,
      skills: [
        { name: 'Vue 3',      level: 5, description: 'Composition API', isFramework: true,  category: 'Frontend'  },
        { name: 'PostgreSQL', level: 4, description: 'クエリ最適化',    isFramework: false, category: 'Database' },
        { name: 'Docker',     level: 3, description: 'コンテナ化',      isFramework: false, category: 'DevOps'   },
      ],
    },
    {
      title: 'MLモデル監視プラットフォーム',
      explanation: '本番MLモデルのドリフト検知・精度モニタリングを自動化するプラットフォーム。Pythonバックエンドと可視化ダッシュボードで構成。',
      url: 'https://mlops.example.com', githubUrl: null, isGithub: false,
      skills: [
        { name: 'Python',       level: 5, description: 'データ処理',         isFramework: false, category: 'Language' },
        { name: 'FastAPI',      level: 4, description: '非同期APIサーバー',  isFramework: true,  category: 'Backend'  },
        { name: 'scikit-learn', level: 3, description: 'ドリフト検知',       isFramework: true,  category: 'ML'       },
      ],
    },
  ],
  skills: [
    { name: 'Java',         level: 5, description: '業務システム開発',   isFramework: false, category: 'Language' },
    { name: 'TypeScript',   level: 4, description: '型安全な開発',       isFramework: false, category: 'Language' },
    { name: 'Python',       level: 4, description: 'ML・API開発',        isFramework: false, category: 'Language' },
    { name: 'Vue 3 / Nuxt', level: 5, description: 'SPA/SSR開発',        isFramework: true,  category: 'Frontend' },
    { name: 'Spring Boot',  level: 4, description: 'REST API設計',       isFramework: true,  category: 'Backend'  },
    { name: 'FastAPI',      level: 3, description: '非同期APIサーバー',  isFramework: true,  category: 'Backend'  },
    { name: 'PostgreSQL',   level: 4, description: 'クエリ最適化',       isFramework: false, category: 'Database' },
    { name: 'Docker / K8s', level: 3, description: 'コンテナ化',         isFramework: false, category: 'DevOps'   },
    { name: 'AWS',          level: 3, description: 'EC2・RDS・Lambda',   isFramework: false, category: 'DevOps'   },
  ],
}

const catMeta: Record<string, { bg: string; text: string; bar: string }> = {
  Language: { bg: '#EEEDFE', text: '#534AB7', bar: '#7F77DD' },
  Frontend: { bg: '#E1F5EE', text: '#0F6E56', bar: '#1D9E75' },
  Backend:  { bg: '#FAEEDA', text: '#854F0B', bar: '#BA7517' },
  Database: { bg: '#FAECE7', text: '#993C1D', bar: '#D85A30' },
  DevOps:   { bg: '#F1EFE8', text: '#5F5E5A', bar: '#888780' },
  ML:       { bg: '#FBEAF0', text: '#993556', bar: '#D4537E' },
  Other:    { bg: '#E6F1FB', text: '#185FA5', bar: '#378ADD' },
}
function cm(cat: string) { return catMeta[cat] ?? catMeta.Other }

function fd(iso: string) {
  return new Date(iso).toLocaleDateString('ja-JP', { year: 'numeric', month: 'short' })
}

const levelLabels = ['', '初級', '初中級', '中級', '上級', 'エキスパート']
function levelLabel(n: number) { return levelLabels[n] ?? '' }

// 修正箇所：computed をインポートし、定義を正しく閉じます
import { computed } from 'vue'

const skillGroups = computed(() => {
  const map: Record<string, Skill[]> = {}
  for (const s of profile.skills) {
    (map[s.category] ??= []).push(s)
  }
  return map
})
definePageMeta({
  layout: "menu"
});
</script>

<template>
  <div class="pg">

    <!-- Hero -->
    <header class="hero">
      <p class="eyebrow">ポートフォリオ</p>
      <h1 class="h-name">{{user?.name}}</h1>
    </header>

    <!-- Careers -->
    <section class="sec">
      <div class="sec-label">経歴</div>
      <div class="tl">
        <div v-for="(c, i) in profile.careers" :key="i" class="tl-row">
          <time class="tl-time">{{ fd(c.time) }}</time>
          <span class="tl-text">{{ c.careerName }}</span>
        </div>
      </div>
    </section>

    <!-- Licenses -->
    <section class="sec">
      <div class="sec-label">資格・認定</div>
      <div class="lic-grid">
        <div v-for="(l, i) in profile.licenses" :key="i" class="lic">
          <div class="lic-name">{{ l.licenseName }}</div>
          <div class="lic-date">{{ fd(l.createdAt) }} 取得</div>
        </div>
      </div>
    </section>

    <!-- Skills -->
    <section class="sec">
      <div class="sec-label">スキル</div>
      <div class="sk-groups">
        <div v-for="(skills, cat) in skillGroups" :key="cat" class="sk-group">
          <div class="sk-cat">
            <span
                class="sk-cat-pill"
                :style="{ background: cm(String(cat)).bg, color: cm(String(cat)).text }"
            >{{ cat }}</span>
          </div>
          <div class="sk-rows">
            <div v-for="s in skills" :key="s.name" class="sk-row">
              <div class="sk-left">
                <span class="sk-name">{{ s.name }}</span>
                <span v-if="s.isFramework" class="fw-tag">FW</span>
              </div>
              <div class="meter-wrap">
                <div class="meter-track">
                  <div
                      class="meter-fill"
                      :style="{ width: s.level * 20 + '%', background: cm(String(cat)).bar }"
                  />
                </div>
                <div class="meter-ticks">
                  <div v-for="n in 5" :key="n" class="meter-tick" />
                </div>
              </div>
              <span class="sk-level-label">{{ levelLabel(s.level) }}</span>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Projects -->
    <section class="sec">
      <div class="sec-label">プロジェクト</div>
      <div class="pj-list">
        <article v-for="(p, i) in profile.projects" :key="i" class="pj">
          <div class="pj-head">
            <h3 class="pj-title">{{ p.title }}</h3>
            <div class="pj-links">
              <a v-if="p.url" :href="p.url" target="_blank" class="pj-link" title="デモ">
                <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M18 13v6a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h6"/><polyline points="15 3 21 3 21 9"/><line x1="10" y1="14" x2="21" y2="3"/></svg>
              </a>
              <a v-if="p.githubUrl" :href="p.githubUrl" target="_blank" class="pj-link" title="GitHub">
                <svg width="12" height="12" viewBox="0 0 24 24" fill="currentColor"><path d="M12 .5C5.65.5.5 5.65.5 12c0 5.1 3.29 9.42 7.86 10.95.58.1.79-.25.79-.55v-2.1c-3.2.7-3.87-1.54-3.87-1.54-.52-1.33-1.28-1.69-1.28-1.69-1.05-.71.08-.7.08-.7 1.16.08 1.77 1.19 1.77 1.19 1.03 1.76 2.7 1.25 3.36.96.1-.75.4-1.26.73-1.55-2.55-.29-5.24-1.28-5.24-5.69 0-1.26.45-2.29 1.18-3.09-.12-.3-.51-1.46.11-3.05 0 0 .96-.31 3.15 1.18a10.9 10.9 0 0 1 2.87-.39c.97.01 1.95.13 2.87.39 2.18-1.49 3.14-1.18 3.14-1.18.63 1.59.23 2.75.11 3.05.74.8 1.18 1.83 1.18 3.09 0 4.42-2.7 5.4-5.27 5.68.41.36.78 1.06.78 2.13v3.16c0 .3.21.66.8.55A11.51 11.51 0 0 0 23.5 12C23.5 5.65 18.35.5 12 .5z"/></svg>
              </a>
            </div>
          </div>
          <p class="pj-desc">{{ p.explanation }}</p>
          <div class="pj-tags">
            <span
                v-for="s in p.skills"
                :key="s.name"
                class="pj-tag"
                :style="{ borderColor: cm(s.category).bar, color: cm(s.category).text }"
            >{{ s.name }}</span>
          </div>
        </article>
      </div>
    </section>

  </div>
</template>

<style scoped>
.pg { padding: 0 0 3rem; }

.hero {
  padding: 2rem 1.5rem 1.75rem;
  border-bottom: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  margin-bottom: 1.75rem;
}
.eyebrow {
  font-size: 11px;
  letter-spacing: .12em;
  text-transform: uppercase;
  color: var(--color-text-tertiary, #9ca3af);
  margin-bottom: .6rem;
}
.h-name  { font-size: 26px; font-weight: 500; margin-bottom: .3rem; }
.h-role  { font-size: 14px; color: var(--color-text-secondary, #6b7280); margin-bottom: 1rem; }
.chips   { display: flex; gap: 6px; flex-wrap: wrap; }
.chip {
  font-size: 11px;
  padding: 3px 10px;
  border-radius: 99px;
  background: var(--color-background-secondary, #f3f4f6);
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  color: var(--color-text-secondary, #6b7280);
}

.sec         { padding: 0 1.5rem; margin-bottom: 1.75rem; }
.sec-label {
  font-size: 11px;
  letter-spacing: .1em;
  text-transform: uppercase;
  color: var(--color-text-tertiary, #9ca3af);
  margin-bottom: 1rem;
  padding-bottom: .5rem;
  border-bottom: 0.5px solid var(--color-border-tertiary, #e5e7eb);
}

.tl        { display: flex; flex-direction: column; }
.tl-row    { display: flex; gap: 1rem; padding: .7rem 0; border-bottom: 0.5px solid var(--color-border-tertiary, #e5e7eb); }
.tl-row:last-child { border-bottom: none; }
.tl-time   { font-size: 11px; color: var(--color-text-tertiary, #9ca3af); min-width: 72px; flex-shrink: 0; padding-top: 2px; }
.tl-text   { font-size: 13px; line-height: 1.5; }

.lic-grid  { display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 8px; }
.lic {
  padding: .75rem 1rem;
  background: var(--color-background-secondary, #f9fafb);
  border-radius: 8px;
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
}
.lic-name  { font-size: 12px; font-weight: 500; line-height: 1.4; margin-bottom: 4px; }
.lic-date  { font-size: 11px; color: var(--color-text-tertiary, #9ca3af); }

.sk-groups { display: flex; flex-direction: column; gap: .75rem; }
.sk-cat    { margin-bottom: .5rem; }
.sk-cat-pill {
  font-size: 10px;
  font-weight: 500;
  padding: 2px 9px;
  border-radius: 99px;
}
.sk-rows   { display: flex; flex-direction: column; gap: 6px; }
.sk-row {
  display: grid;
  grid-template-columns: 140px 1fr 76px;
  align-items: center;
  gap: 12px;
  padding: 9px 12px;
  border-radius: 8px;
  background: var(--color-background-secondary, #f9fafb);
}
.sk-left { display: flex; align-items: center; gap: 6px; }
.sk-name { font-size: 12px; font-weight: 500; color: var(--color-text-primary); }
.fw-tag {
  font-size: 9px;
  padding: 1px 5px;
  border-radius: 3px;
  border: 0.5px solid var(--color-border-secondary, #d1d5db);
  color: var(--color-text-secondary, #6b7280);
  white-space: nowrap;
  flex-shrink: 0;
}
.meter-wrap {
  position: relative;
  height: 6px;
}
.meter-track {
  height: 100%;
  background: var(--color-border-tertiary, #e5e7eb);
  border-radius: 99px;
  overflow: hidden;
}
.meter-fill {
  height: 100%;
  border-radius: 99px;
  transition: width .6s cubic-bezier(.4,0,.2,1);
}
.meter-ticks {
  position: absolute;
  inset: 0;
  display: flex;
  pointer-events: none;
}
.meter-tick {
  flex: 1;
  border-right: 1.5px solid var(--color-background-primary, #fff);
  height: 100%;
}
.meter-tick:last-child { border-right: none; }
.sk-level-label {
  font-size: 11px;
  color: var(--color-text-tertiary, #9ca3af);
  text-align: right;
  white-space: nowrap;
}

.pj-list { display: flex; flex-direction: column; gap: 10px; }
.pj {
  padding: 1rem 1.25rem;
  border-radius: 12px;
  background: var(--color-background-primary, #fff);
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
}
.pj-head  { display: flex; justify-content: space-between; align-items: flex-start; gap: 12px; margin-bottom: .5rem; }
.pj-title { font-size: 14px; font-weight: 500; line-height: 1.3; }
.pj-links { display: flex; gap: 6px; flex-shrink: 0; }
.pj-link {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 24px;
  height: 24px;
  border-radius: 6px;
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  color: var(--color-text-tertiary, #9ca3af);
  text-decoration: none;
}
.pj-desc  { font-size: 12px; color: var(--color-text-secondary, #6b7280); line-height: 1.7; margin-bottom: .75rem; }
.pj-tags  { display: flex; gap: 6px; flex-wrap: wrap; }
.pj-tag {
  font-size: 10px;
  padding: 2px 8px;
  border-radius: 99px;
  border: 0.5px solid;
}
</style>