<script setup lang="ts">
import { computed, ref } from 'vue'
import type {components} from "~/pages/types/openapi";
import {useApi} from "~/composables/useApi";
import {useAuthStore} from "~/stores/auth";

const auth = useAuthStore()
const { user } = storeToRefs(auth)

type CareerDto  = components['schemas']['CareerDto']
type SkillDto   = components['schemas']['SkillDto']
type LicenseDto = components['schemas']['LicenseDto']
type ProjectDto = components['schemas']['ProjectDto']

const client = useApi()

interface Skill {
  name: string
  level: number
  description: string
  isFramework: boolean
  category: 'Backend' | 'Frontend' | 'Language' | 'Database' | 'DevOps' | 'ML' | 'Other'
}
interface Career  { careerName: string; time: string }
interface License { licenseName: string; createdAt: string }
interface Project { title: string; explanation: string; url: string | null; githubUrl: string | null; isGithub: boolean; skills: Skill[] }

const careers  = ref<CareerDto[]>([])
const skills   = ref<SkillDto[]>([])
const licenses = ref<LicenseDto[]>([])
const projects = ref<ProjectDto[]>([])

const loading = ref(true)

const userId = computed(() => auth.user?.userId ?? 0)

async function fetchAll() {
  loading.value = true
  const id = userId.value

  const [c, s, l, p] = await Promise.all([
    client.GET('/api/v1/careers/user/{userId}',  { params: { path: { userId: id } } }),
    client.GET('/api/v1/skills/user/{userId}',   { params: { path: { userId: id } } }),
    client.GET('/api/v1/licenses/user/{userId}', { params: { path: { userId: id } } }),
    client.GET('/api/v1/project/user/{userId}',  { params: { path: { userId: id } } }),
  ])
  careers.value  = (c.data  as CareerDto[]  ?? [])
  skills.value   = (s.data  as SkillDto[]   ?? [])
  licenses.value = (l.data  as LicenseDto[] ?? [])
  projects.value = (p.data  as ProjectDto[] ?? [])
  loading.value  = false
}
onMounted(fetchAll)

const catMeta: Record<string, { bg: string; text: string; bar: string }> = {
  Language: { bg: '#EEEDFE', text: '#3C3489', bar: '#7F77DD' },
  Frontend: { bg: '#E1F5EE', text: '#085041', bar: '#1D9E75' },
  Backend:  { bg: '#FAEEDA', text: '#633806', bar: '#BA7517' },
  Database: { bg: '#FAECE7', text: '#712B13', bar: '#D85A30' },
  DevOps:   { bg: '#F1EFE8', text: '#444441', bar: '#888780' },
  ML:       { bg: '#FBEAF0', text: '#72243E', bar: '#D4537E' },
  Other:    { bg: '#E6F1FB', text: '#0C447C', bar: '#378ADD' },
}
function cm(cat: string) { return catMeta[cat] ?? catMeta.Other }

const catLabel: Record<string, string> = {
  Language: '言語',
  Frontend: 'フロントエンド',
  Backend:  'バックエンド',
  Database: 'データベース',
  DevOps:   'DevOps / クラウド',
  ML:       'ML / AI',
  Other:    'その他',
}

function fd(iso: string) {
  return new Date(iso).toLocaleDateString('ja-JP', { year: 'numeric', month: 'short' })
}

const levelLabels = ['', '初級', '初中級', '中級', '上級', 'エキスパート']
function levelLabel(n: number) { return levelLabels[n] ?? '' }

const skillGroups = computed(() => {
  const map: Record<string, Skill[]> = {}
  for (const s of skills.value) {
    (map[s.category] ??= []).push(s)
  }
  return map
})

// クリックしたスキル名をキーに。同じスキルを再クリックで閉じる
const activeSkillName = ref<string | null>(null)
function toggleSkill(s: Skill) {
  activeSkillName.value = activeSkillName.value === s.name ? null : s.name
}

definePageMeta({ layout: 'menu' })
</script>

<template>
  <div class="pg">

    <!-- Hero -->
    <header class="hero">
      <div class="hero-inner">
        <div class="hero-avatar">
          <span class="hero-initials">{{ user?.name?.slice(0, 1) ?? '?' }}</span>
        </div>
        <div class="hero-body">
          <p class="eyebrow">Portfolio</p>
          <h1 class="h-name">{{ user?.name }}</h1>
          <p class="h-sub">Senior Backend Engineer &amp; Full-Stack Developer</p>
        </div>
      </div>
    </header>

    <div class="content">

      <!-- 経歴 + 資格 -->
      <div class="top-grid">

        <section class="card">
          <div class="card-header">
            <span class="section-icon">
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8"><rect x="2" y="7" width="20" height="14" rx="2"/><path d="M16 7V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v2"/></svg>
            </span>
            <h2 class="card-title">経歴</h2>
          </div>
          <ol class="timeline">
            <li v-for="(c, i) in careers" :key="i" class="tl-item">
              <div class="tl-dot" />
              <div class="tl-content">
                <time class="tl-time">{{ fd(c.time) }}</time>
                <p class="tl-text">{{ c.careerName }}</p>
              </div>
            </li>
          </ol>
        </section>

        <section class="card">
          <div class="card-header">
            <span class="section-icon">
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8"><circle cx="12" cy="8" r="6"/><path d="M8.21 13.89 7 23l5-3 5 3-1.21-9.12"/></svg>
            </span>
            <h2 class="card-title">資格・認定</h2>
          </div>
          <ul class="lic-list">
            <li v-for="(l, i) in licenses" :key="i" class="lic-item">
              <div class="lic-dot" />
              <div class="lic-body">
                <p class="lic-name">{{ l.licenseName }}</p>
                <time class="lic-date">{{ fd(l.createdAt) }} 取得</time>
              </div>
            </li>
          </ul>
        </section>

      </div>

      <!-- スキル -->
      <section class="card">
        <div class="card-header">
          <span class="section-icon">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8"><polyline points="22 12 18 12 15 21 9 3 6 12 2 12"/></svg>
          </span>
          <h2 class="card-title">スキル</h2>
          <span class="card-hint">各行をタップで詳細を表示</span>
        </div>

        <div class="sk-groups">
          <div v-for="(skills, cat) in skillGroups" :key="cat" class="sk-group">
            <div class="sk-cat-label">
              <span
                  class="cat-pill"
                  :style="{ background: cm(String(cat)).bg, color: cm(String(cat)).text }"
              >{{ catLabel[String(cat)] ?? cat }}</span>
            </div>

            <div class="sk-rows">
              <template v-for="s in skills" :key="s.name">

                <!-- スキル行 -->
                <button
                    class="sk-row"
                    :class="{ 'sk-row--open': activeSkillName === s.name }"
                    @click="toggleSkill(s)"
                >
                  <div class="sk-left">
                    <span class="sk-name">{{ s.name }}</span>
                    <span v-if="s.isFramework" class="fw-badge">FW</span>
                  </div>
                  <div class="sk-meter">
                    <div class="meter-track">
                      <div
                          class="meter-fill"
                          :style="{ width: s.level * 20 + '%', background: cm(String(cat)).bar }"
                      />
                    </div>
                    <div class="meter-ticks">
                      <span v-for="n in 5" :key="n" class="tick" />
                    </div>
                  </div>
                  <div class="sk-row-right">
                    <svg
                        class="sk-chevron"
                        :class="{ 'sk-chevron--open': activeSkillName === s.name }"
                        width="12" height="12" viewBox="0 0 24 24" fill="none"
                        stroke="currentColor" stroke-width="2.5"
                    ><polyline points="6 9 12 15 18 9"/></svg>
                  </div>
                </button>

                <!-- 行直下の詳細パネル -->
                <Transition name="panel">
                  <div
                      v-if="activeSkillName === s.name"
                      class="sk-detail"
                      :style="{ borderLeftColor: cm(String(cat)).bar }"
                  >
                    <p class="sk-detail-desc">{{ s.description }}</p>
                    <div class="sk-detail-lv">
                      <div class="meter-track" style="flex:1; max-width:100px">
                        <div
                            class="meter-fill"
                            :style="{ width: s.level * 20 + '%', background: cm(String(cat)).bar }"
                        />
                      </div>
                    </div>
                  </div>
                </Transition>

              </template>
            </div>
          </div>
        </div>
      </section>

      <!-- プロジェクト -->
      <section>
        <div class="sec-header">
          <span class="section-icon">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8"><path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"/></svg>
          </span>
          <h2 class="card-title">プロジェクト</h2>
        </div>

        <div class="pj-grid">
          <article v-for="(p, i) in projects" :key="i" class="pj-card">
            <div class="pj-card-top">
              <div class="pj-num">{{ String(i + 1).padStart(2, '0') }}</div>
              <div class="pj-links">
                <a v-if="p.url" :href="p.url" target="_blank" class="pj-link" title="デモを開く">
                  <svg width="11" height="11" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M18 13v6a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h6"/><polyline points="15 3 21 3 21 9"/><line x1="10" y1="14" x2="21" y2="3"/></svg>
                  <span>デモ</span>
                </a>
                <a v-if="p.githubUrl" :href="p.githubUrl" target="_blank" class="pj-link" title="GitHub">
                  <svg width="11" height="11" viewBox="0 0 24 24" fill="currentColor"><path d="M12 .5C5.65.5.5 5.65.5 12c0 5.1 3.29 9.42 7.86 10.95.58.1.79-.25.79-.55v-2.1c-3.2.7-3.87-1.54-3.87-1.54-.52-1.33-1.28-1.69-1.28-1.69-1.05-.71.08-.7.08-.7 1.16.08 1.77 1.19 1.77 1.19 1.03 1.76 2.7 1.25 3.36.96.1-.75.4-1.26.73-1.55-2.55-.29-5.24-1.28-5.24-5.69 0-1.26.45-2.29 1.18-3.09-.12-.3-.51-1.46.11-3.05 0 0 .96-.31 3.15 1.18a10.9 10.9 0 0 1 2.87-.39c.97.01 1.95.13 2.87.39 2.18-1.49 3.14-1.18 3.14-1.18.63 1.59.23 2.75.11 3.05.74.8 1.18 1.83 1.18 3.09 0 4.42-2.7 5.4-5.27 5.68.41.36.78 1.06.78 2.13v3.16c0 .3.21.66.8.55A11.51 11.51 0 0 0 23.5 12C23.5 5.65 18.35.5 12 .5z"/></svg>
                  <span>GitHub</span>
                </a>
              </div>
            </div>
            <h3 class="pj-title">{{ p.title }}</h3>
            <p class="pj-desc">{{ p.explanation }}</p>
            <div class="pj-skills">
              <span
                  v-for="s in p.skills"
                  :key="s.name"
                  class="pj-tag"
                  :style="{ background: cm(s.category).bg, color: cm(s.category).text }"
              >{{ s.name }}</span>
            </div>
          </article>
        </div>
      </section>

    </div>
  </div>
</template>

<style scoped>
.pg {
  font-family: -apple-system, 'Hiragino Sans', 'Hiragino Kaku Gothic ProN', 'Noto Sans JP', sans-serif;
  padding-bottom: 4rem;
}

/* ── Hero ── */
.hero {
  padding: 2rem 1.5rem 1.75rem;
  border-bottom: 1px solid var(--color-border-tertiary, #e5e7eb);
  margin-bottom: 1.5rem;
  background: var(--color-background-primary, #fff);
}
.hero-inner { display: flex; align-items: center; gap: 1.25rem; }
.hero-avatar {
  width: 52px; height: 52px; border-radius: 50%;
  background: #EEEDFE; border: 1.5px solid #AFA9EC;
  display: flex; align-items: center; justify-content: center; flex-shrink: 0;
}
.hero-initials { font-size: 20px; font-weight: 600; color: #3C3489; line-height: 1; }
.hero-body { flex: 1; min-width: 0; }
.eyebrow {
  font-size: 10px; letter-spacing: .14em; text-transform: uppercase;
  color: var(--color-text-tertiary, #9ca3af); margin-bottom: .35rem;
}
.h-name { font-size: 22px; font-weight: 600; letter-spacing: -.01em; line-height: 1.2; margin-bottom: .25rem; }
.h-sub  { font-size: 12px; color: var(--color-text-secondary, #6b7280); }

/* ── Layout ── */
.content { padding: 0 1.25rem; display: flex; flex-direction: column; gap: 1.25rem; }
.top-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 1.25rem; }
@media (max-width: 560px) { .top-grid { grid-template-columns: 1fr; } }

/* ── Card ── */
.card {
  background: var(--color-background-primary, #fff);
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  border-radius: 14px; padding: 1.25rem; overflow: hidden;
}
.card-header {
  display: flex; align-items: center; gap: 6px;
  margin-bottom: 1rem; padding-bottom: .75rem;
  border-bottom: 0.5px solid var(--color-border-tertiary, #e5e7eb);
}
.section-icon {
  display: flex; align-items: center; justify-content: center;
  width: 24px; height: 24px; border-radius: 6px;
  background: var(--color-background-secondary, #f3f4f6);
  color: var(--color-text-secondary, #6b7280); flex-shrink: 0;
}
.card-title { font-size: 13px; font-weight: 600; letter-spacing: .02em; flex: 1; }
.card-hint  { font-size: 10px; color: var(--color-text-tertiary, #9ca3af); }
.sec-header { display: flex; align-items: center; gap: 6px; margin-bottom: .75rem; }

/* ── Timeline ── */
.timeline { list-style: none; padding: 0; margin: 0; position: relative; }
.timeline::before {
  content: ''; position: absolute; left: 4px; top: 8px; bottom: 8px;
  width: 1px; background: var(--color-border-tertiary, #e5e7eb);
}
.tl-item { display: flex; gap: .75rem; padding: 0 0 1rem; position: relative; }
.tl-item:last-child { padding-bottom: 0; }
.tl-dot {
  width: 10px; height: 10px; border-radius: 50%;
  background: #7F77DD; border: 2px solid var(--color-background-primary, #fff);
  outline: 1.5px solid #7F77DD; flex-shrink: 0; margin-top: 4px; position: relative; z-index: 1;
}
.tl-content { flex: 1; min-width: 0; }
.tl-time { display: block; font-size: 10px; color: var(--color-text-tertiary, #9ca3af); margin-bottom: 2px; }
.tl-text { font-size: 12px; line-height: 1.5; }

/* ── Licenses ── */
.lic-list { list-style: none; padding: 0; margin: 0; display: flex; flex-direction: column; gap: .75rem; }
.lic-item {
  display: flex; align-items: flex-start; gap: .6rem;
  padding: .75rem; background: var(--color-background-secondary, #f9fafb); border-radius: 10px;
}
.lic-dot { width: 8px; height: 8px; border-radius: 50%; background: #1D9E75; flex-shrink: 0; margin-top: 5px; }
.lic-body { flex: 1; min-width: 0; }
.lic-name { font-size: 12px; font-weight: 500; line-height: 1.4; margin-bottom: 3px; }
.lic-date { font-size: 10px; color: var(--color-text-tertiary, #9ca3af); }

/* ── Skills ── */
.sk-groups { display: flex; flex-direction: column; gap: 1.25rem; }
.sk-cat-label { margin-bottom: .5rem; }
.cat-pill { font-size: 10px; font-weight: 600; letter-spacing: .04em; padding: 3px 10px; border-radius: 99px; }
.sk-rows { display: flex; flex-direction: column; gap: 3px; }

/* スキル行 */
.sk-row {
  display: grid;
  grid-template-columns: 130px 1fr auto;
  align-items: center;
  gap: 12px;
  padding: 8px 10px;
  border-radius: 8px;
  background: var(--color-background-secondary, #f9fafb);
  border: 0.5px solid transparent;
  cursor: pointer;
  text-align: left;
  width: 100%;
  transition: border-color .15s, background .15s, border-radius .15s;
}
.sk-row:hover { border-color: var(--color-border-tertiary, #e5e7eb); }
.sk-row--open {
  border-color: var(--color-border-secondary, #d1d5db);
  background: var(--color-background-primary, #fff);
  border-radius: 8px 8px 0 0;
}

.sk-left { display: flex; align-items: center; gap: 5px; overflow: hidden; }
.sk-name {
  font-size: 12px; font-weight: 500;
  white-space: nowrap; overflow: hidden; text-overflow: ellipsis;
}
.fw-badge {
  font-size: 9px; padding: 1px 5px; border-radius: 3px;
  border: 0.5px solid var(--color-border-secondary, #d1d5db);
  color: var(--color-text-tertiary, #9ca3af); white-space: nowrap; flex-shrink: 0; line-height: 1.4;
}
.sk-meter { position: relative; height: 6px; }
.meter-track { height: 100%; background: var(--color-border-tertiary, #e5e7eb); border-radius: 99px; overflow: hidden; }
.meter-fill { height: 100%; border-radius: 99px; transition: width .5s cubic-bezier(.4,0,.2,1); }
.meter-ticks { position: absolute; inset: 0; display: flex; pointer-events: none; }
.tick { flex: 1; border-right: 1.5px solid var(--color-background-primary, #fff); }
.tick:last-child { border-right: none; }

.sk-row-right { display: flex; align-items: center; gap: 6px; flex-shrink: 0; }
.sk-lv { font-size: 10px; font-weight: 500; white-space: nowrap; }
.sk-chevron { color: var(--color-text-tertiary, #9ca3af); transition: transform .2s; flex-shrink: 0; }
.sk-chevron--open { transform: rotate(180deg); }

/* 詳細パネル：行直下に接続 */
.sk-detail {
  padding: .75rem 1rem;
  background: var(--color-background-primary, #fff);
  border: 0.5px solid var(--color-border-secondary, #d1d5db);
  border-top: none;
  border-radius: 0 0 8px 8px;
  border-left-width: 3px;
  margin-bottom: 2px;
}
.sk-detail-desc {
  font-size: 12px; line-height: 1.75;
  color: var(--color-text-secondary, #6b7280);
  margin-bottom: .6rem;
}
.sk-detail-lv { display: flex; align-items: center; gap: .75rem; }
.sk-detail-lv-label { font-size: 10px; font-weight: 600; white-space: nowrap; }

/* トランジション */
.panel-enter-active { transition: opacity .18s ease, transform .18s ease; }
.panel-leave-active { transition: opacity .12s ease, transform .12s ease; }
.panel-enter-from, .panel-leave-to { opacity: 0; transform: translateY(-4px); }

/* ── Projects ── */
.pj-grid { display: grid; grid-template-columns: 1fr; gap: 1rem; }
.pj-card {
  background: var(--color-background-primary, #fff);
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  border-radius: 14px; padding: 1.25rem;
  transition: border-color .15s;
}
.pj-card:hover { border-color: var(--color-border-secondary, #d1d5db); }
.pj-card-top { display: flex; justify-content: space-between; align-items: center; margin-bottom: .75rem; }
.pj-num { font-size: 11px; font-weight: 700; letter-spacing: .08em; color: var(--color-text-tertiary, #9ca3af); }
.pj-links { display: flex; gap: 6px; }
.pj-link {
  display: flex; align-items: center; gap: 4px;
  padding: 4px 10px; border-radius: 6px;
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  color: var(--color-text-secondary, #6b7280);
  text-decoration: none; font-size: 11px;
  transition: background .12s, border-color .12s;
}
.pj-link:hover { background: var(--color-background-secondary, #f3f4f6); border-color: var(--color-border-secondary, #d1d5db); }
.pj-title { font-size: 15px; font-weight: 600; margin-bottom: .5rem; line-height: 1.35; }
.pj-desc  { font-size: 12px; line-height: 1.75; color: var(--color-text-secondary, #6b7280); margin-bottom: .75rem; }
.pj-skills { display: flex; flex-wrap: wrap; gap: 5px; }
.pj-tag { font-size: 10px; font-weight: 500; padding: 3px 10px; border-radius: 99px; }
</style>
