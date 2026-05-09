<script setup lang="ts">
import type { components } from '../pages/types/openapi'

type ProfileStructureResponse = components['schemas']['ProfileStructureResponse']

interface Career   { careerName: string; time: string }
interface Skill    { name: string; level: number; description: string; isFramework: boolean; category: string }
interface License  { licenseName: string; createdAt: string }
interface Project  { title: string; explanation: string; url: string | null; githubUrl: string | null; isGithub: boolean; skills: Skill[] }

const props = defineProps<{ result: ProfileStructureResponse }>()

const careers  = computed(() => (props.result.careers  as unknown as Career[])  ?? [])
const skills   = computed(() => (props.result.skills   as unknown as Skill[])   ?? [])
const licenses = computed(() => (props.result.licenses as unknown as License[]) ?? [])
const projects = computed(() => (props.result.projects as unknown as Project[]) ?? [])

const skillsByCategory = computed(() => {
  const map: Record<string, Skill[]> = {}
  for (const s of skills.value) {
    ;(map[s.category] ??= []).push(s)
  }
  return map
})

function formatDate(iso: string) {
  return new Date(iso).toLocaleDateString('ja-JP', { year: 'numeric', month: 'long' })
}

const levelLabel = ['', '初級', '中級', '上級', '準上級', 'エキスパート']
</script>

<template>
  <div class="result">
    <!-- ヘッダー -->
    <div class="result-head">
      <div class="result-title-wrap">
        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="#34d399" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <polyline points="20 6 9 17 4 12"/>
        </svg>
        <span class="result-title">生成完了</span>
      </div>
    </div>

    <div class="sections">

      <!-- 経歴 -->
      <section v-if="careers.length" class="sec">
        <div class="sec-label">
          <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="#a78bfa" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <rect x="2" y="7" width="20" height="14" rx="2"/><path d="M16 7V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v2"/>
          </svg>
          経歴
        </div>
        <div class="timeline">
          <div v-for="(c, i) in careers" :key="i" class="timeline-item">
            <div class="timeline-dot" />
            <div class="timeline-body">
              <span class="timeline-name">{{ c.careerName }}</span>
              <span class="timeline-date">{{ formatDate(c.time) }}</span>
            </div>
          </div>
        </div>
      </section>

      <!-- スキル -->
      <section v-if="skills.length" class="sec">
        <div class="sec-label">
          <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="#60a5fa" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <polyline points="16 18 22 12 16 6"/><polyline points="8 6 2 12 8 18"/>
          </svg>
          スキル
        </div>
        <div v-for="(items, cat) in skillsByCategory" :key="cat" class="skill-group">
          <div class="skill-cat">{{ cat }}</div>
          <div class="skill-chips">
            <div v-for="s in items" :key="s.name" class="skill-chip">
              <span class="chip-name">{{ s.name }}</span>
              <span class="chip-level" :data-level="s.level">{{ levelLabel[s.level] ?? s.level }}</span>
            </div>
          </div>
        </div>
      </section>

      <!-- 資格 -->
      <section v-if="licenses.length" class="sec">
        <div class="sec-label">
          <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="#fb923c" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="12" cy="8" r="6"/><path d="M15.477 12.89 17 22l-5-3-5 3 1.523-9.11"/>
          </svg>
          資格
        </div>
        <div class="license-list">
          <div v-for="(l, i) in licenses" :key="i" class="license-card">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="#fb923c" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
              <circle cx="12" cy="8" r="6"/><path d="M15.477 12.89 17 22l-5-3-5 3 1.523-9.11"/>
            </svg>
            <div class="license-info">
              <span class="license-name">{{ l.licenseName }}</span>
              <span class="license-date">{{ formatDate(l.createdAt) }}</span>
            </div>
          </div>
        </div>
      </section>

      <!-- プロジェクト -->
      <section v-if="projects.length" class="sec">
        <div class="sec-label">
          <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="#34d399" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"/><path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"/>
          </svg>
          プロジェクト
        </div>
        <div class="project-grid">
          <div v-for="(p, i) in projects" :key="i" class="project-card">
            <div class="project-head">
              <span class="project-title">{{ p.title }}</span>
              <div class="project-links">
                <a v-if="p.url" :href="p.url" target="_blank" rel="noopener" class="proj-link">
                  <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M18 13v6a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h6"/><polyline points="15 3 21 3 21 9"/><line x1="10" y1="14" x2="21" y2="3"/>
                  </svg>
                  Demo
                </a>
                <a v-if="p.githubUrl" :href="p.githubUrl" target="_blank" rel="noopener" class="proj-link proj-link--gh">
                  <svg width="12" height="12" viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 2C6.477 2 2 6.477 2 12c0 4.418 2.865 8.167 6.839 9.49.5.092.682-.217.682-.482 0-.237-.009-.868-.013-1.703-2.782.604-3.369-1.342-3.369-1.342-.454-1.154-1.11-1.462-1.11-1.462-.908-.62.069-.608.069-.608 1.003.07 1.531 1.03 1.531 1.03.892 1.529 2.341 1.087 2.91.831.092-.646.35-1.086.636-1.336-2.22-.253-4.555-1.11-4.555-4.943 0-1.091.39-1.984 1.029-2.683-.103-.253-.446-1.27.098-2.647 0 0 .84-.269 2.75 1.025A9.578 9.578 0 0 1 12 6.836a9.59 9.59 0 0 1 2.504.337c1.909-1.294 2.747-1.025 2.747-1.025.546 1.377.202 2.394.1 2.647.64.699 1.028 1.592 1.028 2.683 0 3.842-2.339 4.687-4.566 4.935.359.309.678.919.678 1.852 0 1.336-.012 2.415-.012 2.742 0 .267.18.578.688.48C19.138 20.163 22 16.418 22 12c0-5.523-4.477-10-10-10z"/>
                  </svg>
                  GitHub
                </a>
              </div>
            </div>
            <p class="project-desc">{{ p.explanation }}</p>
            <div v-if="p.skills?.length" class="project-skills">
              <span v-for="s in p.skills" :key="s.name" class="proj-skill-tag">{{ s.name }}</span>
            </div>
          </div>
        </div>
      </section>

    </div>
  </div>
</template>

<style scoped>
.result {
  background: var(--color-background-primary, #fff);
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  border-radius: 14px;
  overflow: hidden;
  font-family: 'Noto Sans JP', sans-serif;
}

/* ── ヘッダー ── */
.result-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 16px;
  border-bottom: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  background: var(--color-background-secondary, #f9fafb);
}
.result-title-wrap { display: flex; align-items: center; gap: 7px; }
.result-title { font-size: 12px; font-weight: 500; color: #059669; }
.btn-reset {
  font-size: 12px;
  color: var(--color-text-tertiary, #9ca3af);
  background: none;
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  border-radius: 7px;
  padding: 4px 12px;
  cursor: pointer;
  font-family: inherit;
  transition: color .15s, background .15s;
}
.btn-reset:hover { color: var(--color-text-primary, #111); background: var(--color-background-secondary, #f3f4f6); }

/* ── セクション共通 ── */
.sections { padding: 20px; display: flex; flex-direction: column; gap: 28px; }
.sec {}
.sec-label {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 10.5px;
  font-weight: 600;
  letter-spacing: .1em;
  text-transform: uppercase;
  color: var(--color-text-tertiary, #9ca3af);
  margin-bottom: 12px;
}

/* ── 経歴タイムライン ── */
.timeline { display: flex; flex-direction: column; gap: 0; padding-left: 8px; }
.timeline-item {
  display: flex;
  align-items: flex-start;
  gap: 14px;
  position: relative;
  padding-bottom: 16px;
}
.timeline-item:last-child { padding-bottom: 0; }
.timeline-item::before {
  content: '';
  position: absolute;
  left: 5px;
  top: 18px;
  bottom: 0;
  width: 1px;
  background: var(--color-border-tertiary, #e5e7eb);
}
.timeline-item:last-child::before { display: none; }
.timeline-dot {
  width: 11px;
  height: 11px;
  border-radius: 50%;
  background: #a78bfa;
  border: 2px solid #fff;
  box-shadow: 0 0 0 1.5px #a78bfa;
  flex-shrink: 0;
  margin-top: 3px;
}
.timeline-body { display: flex; flex-direction: column; gap: 3px; }
.timeline-name { font-size: 13.5px; font-weight: 500; color: var(--color-text-primary, #111); }
.timeline-date { font-size: 11.5px; color: var(--color-text-tertiary, #9ca3af); }

/* ── スキル ── */
.skill-group { margin-bottom: 14px; }
.skill-group:last-child { margin-bottom: 0; }
.skill-cat {
  font-size: 10.5px;
  font-weight: 600;
  color: var(--color-text-tertiary, #9ca3af);
  letter-spacing: .06em;
  margin-bottom: 8px;
}
.skill-chips { display: flex; flex-wrap: wrap; gap: 8px; }
.skill-chip {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 5px 10px;
  background: var(--color-background-secondary, #f9fafb);
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  border-radius: 8px;
}
.chip-name { font-size: 12.5px; font-weight: 500; color: var(--color-text-primary, #111); }
.chip-level {
  font-size: 10px;
  padding: 1px 6px;
  border-radius: 20px;
  font-weight: 500;
}
.chip-level[data-level="1"] { background: rgba(167,139,250,.12); color: #7c3aed; }
.chip-level[data-level="2"] { background: rgba(96,165,250,.12); color: #2563eb; }
.chip-level[data-level="3"] { background: rgba(52,211,153,.12); color: #059669; }
.chip-level[data-level="4"] { background: rgba(251,146,60,.12); color: #d97706; }
.chip-level[data-level="5"] { background: rgba(239,68,68,.12); color: #dc2626; }

/* ── 資格 ── */
.license-list { display: flex; flex-direction: column; gap: 8px; }
.license-card {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 10px 14px;
  background: rgba(251,146,60,.04);
  border: 0.5px solid rgba(251,146,60,.2);
  border-radius: 10px;
}
.license-info { display: flex; flex-direction: column; gap: 2px; }
.license-name { font-size: 13px; font-weight: 500; color: var(--color-text-primary, #111); }
.license-date { font-size: 11px; color: var(--color-text-tertiary, #9ca3af); }

/* ── プロジェクト ── */
.project-grid { display: flex; flex-direction: column; gap: 12px; }
.project-card {
  padding: 14px 16px;
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  border-radius: 12px;
  background: var(--color-background-primary, #fff);
  transition: box-shadow .2s;
}
.project-card:hover { box-shadow: 0 2px 12px rgba(0,0,0,.06); }
.project-head {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 12px;
  margin-bottom: 8px;
}
.project-title { font-size: 13px; font-weight: 600; color: var(--color-text-primary, #111); line-height: 1.4; flex: 1; }
.project-links { display: flex; gap: 6px; flex-shrink: 0; }
.proj-link {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 11px;
  padding: 3px 8px;
  border-radius: 6px;
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  color: var(--color-text-tertiary, #6b7280);
  text-decoration: none;
  transition: background .15s, color .15s;
}
.proj-link:hover { background: var(--color-background-secondary, #f3f4f6); color: var(--color-text-primary, #111); }
.proj-link--gh { border-color: rgba(0,0,0,.12); }
.project-desc { font-size: 12.5px; color: var(--color-text-secondary, #6b7280); line-height: 1.6; margin: 0 0 10px; }
.project-skills { display: flex; flex-wrap: wrap; gap: 6px; }
.proj-skill-tag {
  font-size: 11px;
  padding: 2px 8px;
  background: rgba(96,165,250,.08);
  color: #2563eb;
  border-radius: 20px;
  border: 0.5px solid rgba(96,165,250,.2);
  font-weight: 500;
}

@media (max-width: 640px) {
  .sections { padding: 16px; }
  .project-head { flex-direction: column; }
  .project-links { align-self: flex-end; }
}
</style>