<script setup lang="ts">
import { useAuthStore } from '~/stores/auth'
import { useApi } from '~/composables/useApi'
import type { components } from '~/types/openapi'

definePageMeta({ layout: 'menu' })

type CareerDto  = components['schemas']['CareerDto']
type SkillDto   = components['schemas']['SkillDto']
type LicenseDto = components['schemas']['LicenseDto']
type ProjectDto = components['schemas']['ProjectDto']

const auth   = useAuthStore()
const client = useApi()

const userId  = computed(() => auth.user?.userId ?? 0)
const loading = ref(true)
const tab     = ref<'career' | 'skill' | 'license' | 'project'>('career')

// ── データ ──────────────────────────────────────
const careers  = ref<CareerDto[]>([])
const skills   = ref<SkillDto[]>([])
const licenses = ref<LicenseDto[]>([])
const projects = ref<ProjectDto[]>([])

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

// ── 編集状態 ──────────────────────────────────────
const editingId  = ref<number | null>(null)
const editBuffer = ref<Record<string, unknown>>({})
const addingTab  = ref(false)
const addBuffer  = ref<Record<string, unknown>>({})

function startEdit(item: Record<string, unknown>) {
  editingId.value  = item.id as number
  editBuffer.value = { ...item }
  addingTab.value  = false
}
function cancelEdit() { editingId.value = null }

function startAdd() {
  addingTab.value  = true
  editingId.value  = null
  addBuffer.value  = defaultFor(tab.value)
}
function cancelAdd() { addingTab.value = false }

function defaultFor(t: string): Record<string, unknown> {
  if (t === 'career')  return { careerName: '', time: '' }
  if (t === 'skill')   return { name: '', level: 3, description: '', isFramework: false, category: 'Language' }
  if (t === 'license') return { licenseName: '', createdAt: '' }
  if (t === 'project') return { title: '', explanation: '', url: '', githubUrl: '', isGithub: false }
  return {}
}

// ── キャリア CRUD ──────────────────────────────────
async function saveCareer() {
  const b = editBuffer.value as CareerDto
  await client.PUT('/api/v1/careers/{id}', { params: { path: { id: b.id! } }, body: b })
  const idx = careers.value.findIndex(x => x.id === b.id)
  if (idx !== -1) careers.value[idx] = { ...b }
  cancelEdit()
}
async function addCareer() {
  const b = addBuffer.value as CareerDto
  const { data } = await client.POST('/api/v1/careers', { body: b })
  if (data) careers.value.push(data)
  cancelAdd()
}
async function deleteCareer(id: number) {
  if (!confirm('削除しますか？')) return
  await client.DELETE('/api/v1/careers/{id}', { params: { path: { id } } })
  careers.value = careers.value.filter(x => x.id !== id)
}

// ── スキル CRUD ──────────────────────────────────
async function saveSkill() {
  const b = editBuffer.value as SkillDto
  await client.PUT('/api/v1/skills/{id}', { params: { path: { id: b.id! } }, body: b })
  const idx = skills.value.findIndex(x => x.id === b.id)
  if (idx !== -1) skills.value[idx] = { ...b }
  cancelEdit()
}
async function addSkill() {
  const b = addBuffer.value as SkillDto
  const { data } = await client.POST('/api/v1/skills', { body: b })
  if (data) skills.value.push(data)
  cancelAdd()
}
async function deleteSkill(id: number) {
  if (!confirm('削除しますか？')) return
  await client.DELETE('/api/v1/skills/{id}', { params: { path: { id } } })
  skills.value = skills.value.filter(x => x.id !== id)
}

// ── 資格 CRUD ──────────────────────────────────
async function saveLicense() {
  const b = editBuffer.value as LicenseDto
  await client.PUT('/api/v1/licenses/{id}', { params: { path: { id: b.id! } }, body: b })
  const idx = licenses.value.findIndex(x => x.id === b.id)
  if (idx !== -1) licenses.value[idx] = { ...b }
  cancelEdit()
}
async function addLicense() {
  const b = addBuffer.value as LicenseDto
  const { data } = await client.POST('/api/v1/licenses', { body: b })
  if (data) licenses.value.push(data)
  cancelAdd()
}
async function deleteLicense(id: number) {
  if (!confirm('削除しますか？')) return
  await client.DELETE('/api/v1/licenses/{id}', { params: { path: { id } } })
  licenses.value = licenses.value.filter(x => x.id !== id)
}

// ── プロジェクト CRUD ──────────────────────────────────
async function saveProject() {
  const b = editBuffer.value as ProjectDto
  await client.PUT('/api/v1/project/{id}', { params: { path: { id: b.id! } }, body: b })
  const idx = projects.value.findIndex(x => x.id === b.id)
  if (idx !== -1) projects.value[idx] = { ...b }
  cancelEdit()
}
async function addProject() {
  const b = addBuffer.value as ProjectDto
  const { data } = await client.POST('/api/v1/project', { body: b })
  if (data) projects.value.push(data)
  cancelAdd()
}
async function deleteProject(id: number) {
  if (!confirm('削除しますか？')) return
  await client.DELETE('/api/v1/project/{id}', { params: { path: { id } } })
  projects.value = projects.value.filter(x => x.id !== id)
}

// ── タブ切り替え時にリセット ──────────────────────────────────
watch(tab, () => { cancelEdit(); cancelAdd() })

// ── ヘルパー ──────────────────────────────────────
const skillCategories = ['Language', 'Frontend', 'Backend', 'Database', 'DevOps', 'ML', 'Other']
const levelLabels     = ['', '初級', '初中級', '中級', '上級', 'エキスパート']
function fd(iso?: string) {
  if (!iso) return '—'
  return new Date(iso).toLocaleDateString('ja-JP', { year: 'numeric', month: 'short' })
}

const tabs = [
  { id: 'career',  label: '経歴',     count: computed(() => careers.value.length) },
  { id: 'skill',   label: 'スキル',   count: computed(() => skills.value.length) },
  { id: 'license', label: '資格',     count: computed(() => licenses.value.length) },
  { id: 'project', label: 'プロジェクト', count: computed(() => projects.value.length) },
] as const
</script>

<template>
  <div class="page">

    <!-- ページヘッダー -->
    <div class="page-header">
      <div class="header-icon">
        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="#a78bfa" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
          <rect x="2" y="3" width="20" height="14" rx="2"/><path d="M8 21h8M12 17v4"/>
        </svg>
      </div>
      <div>
        <h1 class="page-title">ポートフォリオ管理</h1>
        <p class="page-sub">経歴・スキル・資格・プロジェクトを編集できます</p>
      </div>
    </div>

    <!-- ローディング -->
    <div v-if="loading" class="loading">
      <span class="spinner-lg" /><span>読み込み中...</span>
    </div>

    <template v-else>
      <!-- タブ -->
      <div class="tabs">
        <button
          v-for="t in tabs" :key="t.id"
          class="tab-btn"
          :class="{ active: tab === t.id }"
          @click="tab = t.id"
        >
          {{ t.label }}
          <span class="tab-count">{{ t.count.value }}</span>
        </button>
      </div>

      <!-- ── 経歴タブ ── -->
      <div v-if="tab === 'career'" class="section">
        <div class="sec-head">
          <span class="sec-title">経歴一覧</span>
          <button class="btn-add" @click="startAdd">
            <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
            追加
          </button>
        </div>

        <!-- 追加フォーム -->
        <div v-if="addingTab" class="form-card add-card">
          <div class="form-grid">
            <div class="form-group full">
              <label class="form-label">会社名・役職</label>
              <input v-model="addBuffer.careerName as string" class="input" placeholder="株式会社〇〇 — エンジニア" />
            </div>
            <div class="form-group">
              <label class="form-label">年月</label>
              <input v-model="addBuffer.time as string" class="input" type="datetime-local" />
            </div>
          </div>
          <div class="form-actions">
            <button class="btn-cancel" @click="cancelAdd">キャンセル</button>
            <button class="btn-save" @click="addCareer">追加する</button>
          </div>
        </div>

        <!-- リスト -->
        <div class="list">
          <div v-if="careers.length === 0 && !addingTab" class="empty">まだ経歴がありません</div>
          <div v-for="c in careers" :key="c.id" class="list-item">
            <template v-if="editingId === c.id">
              <div class="form-grid">
                <div class="form-group full">
                  <label class="form-label">会社名・役職</label>
                  <input v-model="editBuffer.careerName as string" class="input" />
                </div>
                <div class="form-group">
                  <label class="form-label">年月</label>
                  <input v-model="editBuffer.time as string" class="input" type="datetime-local" />
                </div>
              </div>
              <div class="form-actions">
                <button class="btn-cancel" @click="cancelEdit">キャンセル</button>
                <button class="btn-save" @click="saveCareer">保存</button>
              </div>
            </template>
            <template v-else>
              <div class="item-body">
                <time class="item-time">{{ fd(c.time) }}</time>
                <span class="item-text">{{ c.careerName }}</span>
              </div>
              <div class="item-actions">
                <button class="btn-icon-sm" title="編集" @click="startEdit(c as Record<string,unknown>)">
                  <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/></svg>
                </button>
                <button class="btn-icon-sm danger" title="削除" @click="deleteCareer(c.id!)">
                  <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"/><path d="M19 6l-1 14H6L5 6"/><path d="M10 11v6M14 11v6"/><path d="M9 6V4h6v2"/></svg>
                </button>
              </div>
            </template>
          </div>
        </div>
      </div>

      <!-- ── スキルタブ ── -->
      <div v-if="tab === 'skill'" class="section">
        <div class="sec-head">
          <span class="sec-title">スキル一覧</span>
          <button class="btn-add" @click="startAdd">
            <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
            追加
          </button>
        </div>

        <div v-if="addingTab" class="form-card add-card">
          <div class="form-grid">
            <div class="form-group">
              <label class="form-label">スキル名</label>
              <input v-model="addBuffer.name as string" class="input" placeholder="Java" />
            </div>
            <div class="form-group">
              <label class="form-label">カテゴリ</label>
              <select v-model="addBuffer.category as string" class="input">
                <option v-for="cat in skillCategories" :key="cat" :value="cat">{{ cat }}</option>
              </select>
            </div>
            <div class="form-group">
              <label class="form-label">レベル ({{ levelLabels[(addBuffer.level as number) ?? 3] }})</label>
              <input v-model.number="addBuffer.level as number" class="input" type="range" min="1" max="5" />
            </div>
            <div class="form-group">
              <label class="form-label">説明</label>
              <input v-model="addBuffer.description as string" class="input" placeholder="REST API開発" />
            </div>
            <div class="form-group checkbox-group">
              <label class="checkbox-label">
                <input v-model="addBuffer.isFramework as boolean" type="checkbox" class="checkbox" />
                フレームワーク
              </label>
            </div>
          </div>
          <div class="form-actions">
            <button class="btn-cancel" @click="cancelAdd">キャンセル</button>
            <button class="btn-save" @click="addSkill">追加する</button>
          </div>
        </div>

        <div class="list">
          <div v-if="skills.length === 0 && !addingTab" class="empty">まだスキルがありません</div>
          <div v-for="s in skills" :key="s.id" class="list-item">
            <template v-if="editingId === s.id">
              <div class="form-grid">
                <div class="form-group">
                  <label class="form-label">スキル名</label>
                  <input v-model="editBuffer.name as string" class="input" />
                </div>
                <div class="form-group">
                  <label class="form-label">カテゴリ</label>
                  <select v-model="editBuffer.category as string" class="input">
                    <option v-for="cat in skillCategories" :key="cat" :value="cat">{{ cat }}</option>
                  </select>
                </div>
                <div class="form-group">
                  <label class="form-label">レベル ({{ levelLabels[(editBuffer.level as number) ?? 3] }})</label>
                  <input v-model.number="editBuffer.level as number" class="input" type="range" min="1" max="5" />
                </div>
                <div class="form-group">
                  <label class="form-label">説明</label>
                  <input v-model="editBuffer.description as string" class="input" />
                </div>
                <div class="form-group checkbox-group">
                  <label class="checkbox-label">
                    <input v-model="editBuffer.isFramework as boolean" type="checkbox" class="checkbox" />
                    フレームワーク
                  </label>
                </div>
              </div>
              <div class="form-actions">
                <button class="btn-cancel" @click="cancelEdit">キャンセル</button>
                <button class="btn-save" @click="saveSkill">保存</button>
              </div>
            </template>
            <template v-else>
              <div class="item-body">
                <div class="skill-left">
                  <span class="item-text">{{ s.name }}</span>
                  <span class="skill-cat">{{ s.category }}</span>
                  <span v-if="s.isFramework" class="fw-tag">FW</span>
                </div>
                <div class="skill-meter">
                  <div class="meter-track">
                    <div class="meter-fill" :style="{ width: (s.level ?? 0) * 20 + '%' }" />
                  </div>
                  <span class="meter-label">{{ levelLabels[s.level ?? 0] }}</span>
                </div>
              </div>
              <div class="item-actions">
                <button class="btn-icon-sm" @click="startEdit(s as Record<string,unknown>)">
                  <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/></svg>
                </button>
                <button class="btn-icon-sm danger" @click="deleteSkill(s.id!)">
                  <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"/><path d="M19 6l-1 14H6L5 6"/><path d="M10 11v6M14 11v6"/><path d="M9 6V4h6v2"/></svg>
                </button>
              </div>
            </template>
          </div>
        </div>
      </div>

      <!-- ── 資格タブ ── -->
      <div v-if="tab === 'license'" class="section">
        <div class="sec-head">
          <span class="sec-title">資格一覧</span>
          <button class="btn-add" @click="startAdd">
            <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
            追加
          </button>
        </div>

        <div v-if="addingTab" class="form-card add-card">
          <div class="form-grid">
            <div class="form-group full">
              <label class="form-label">資格名</label>
              <input v-model="addBuffer.licenseName as string" class="input" placeholder="応用情報技術者試験" />
            </div>
            <div class="form-group">
              <label class="form-label">取得日</label>
              <input v-model="addBuffer.createdAt as string" class="input" type="date" />
            </div>
          </div>
          <div class="form-actions">
            <button class="btn-cancel" @click="cancelAdd">キャンセル</button>
            <button class="btn-save" @click="addLicense">追加する</button>
          </div>
        </div>

        <div class="list">
          <div v-if="licenses.length === 0 && !addingTab" class="empty">まだ資格がありません</div>
          <div v-for="l in licenses" :key="l.id" class="list-item">
            <template v-if="editingId === l.id">
              <div class="form-grid">
                <div class="form-group full">
                  <label class="form-label">資格名</label>
                  <input v-model="editBuffer.licenseName as string" class="input" />
                </div>
                <div class="form-group">
                  <label class="form-label">取得日</label>
                  <input v-model="editBuffer.createdAt as string" class="input" type="date" />
                </div>
              </div>
              <div class="form-actions">
                <button class="btn-cancel" @click="cancelEdit">キャンセル</button>
                <button class="btn-save" @click="saveLicense">保存</button>
              </div>
            </template>
            <template v-else>
              <div class="item-body">
                <span class="item-text">{{ l.licenseName }}</span>
                <span class="item-time">{{ fd(l.createdAt) }} 取得</span>
              </div>
              <div class="item-actions">
                <button class="btn-icon-sm" @click="startEdit(l as Record<string,unknown>)">
                  <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/></svg>
                </button>
                <button class="btn-icon-sm danger" @click="deleteLicense(l.id!)">
                  <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"/><path d="M19 6l-1 14H6L5 6"/><path d="M10 11v6M14 11v6"/><path d="M9 6V4h6v2"/></svg>
                </button>
              </div>
            </template>
          </div>
        </div>
      </div>

      <!-- ── プロジェクトタブ ── -->
      <div v-if="tab === 'project'" class="section">
        <div class="sec-head">
          <span class="sec-title">プロジェクト一覧</span>
          <button class="btn-add" @click="startAdd">
            <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
            追加
          </button>
        </div>

        <div v-if="addingTab" class="form-card add-card">
          <div class="form-grid">
            <div class="form-group full">
              <label class="form-label">プロジェクト名</label>
              <input v-model="addBuffer.title as string" class="input" placeholder="在庫管理ダッシュボード" />
            </div>
            <div class="form-group full">
              <label class="form-label">説明</label>
              <textarea v-model="addBuffer.explanation as string" class="input textarea" rows="3" placeholder="プロジェクトの概要を入力..." />
            </div>
            <div class="form-group">
              <label class="form-label">デモURL</label>
              <input v-model="addBuffer.url as string" class="input" placeholder="https://..." />
            </div>
            <div class="form-group">
              <label class="form-label">GitHub URL</label>
              <input v-model="addBuffer.githubUrl as string" class="input" placeholder="https://github.com/..." />
            </div>
            <div class="form-group checkbox-group">
              <label class="checkbox-label">
                <input v-model="addBuffer.isGithub as boolean" type="checkbox" class="checkbox" />
                GitHubリンクあり
              </label>
            </div>
          </div>
          <div class="form-actions">
            <button class="btn-cancel" @click="cancelAdd">キャンセル</button>
            <button class="btn-save" @click="addProject">追加する</button>
          </div>
        </div>

        <div class="list">
          <div v-if="projects.length === 0 && !addingTab" class="empty">まだプロジェクトがありません</div>
          <div v-for="p in projects" :key="p.id" class="list-item project-item">
            <template v-if="editingId === p.id">
              <div class="form-grid">
                <div class="form-group full">
                  <label class="form-label">プロジェクト名</label>
                  <input v-model="editBuffer.title as string" class="input" />
                </div>
                <div class="form-group full">
                  <label class="form-label">説明</label>
                  <textarea v-model="editBuffer.explanation as string" class="input textarea" rows="3" />
                </div>
                <div class="form-group">
                  <label class="form-label">デモURL</label>
                  <input v-model="editBuffer.url as string" class="input" />
                </div>
                <div class="form-group">
                  <label class="form-label">GitHub URL</label>
                  <input v-model="editBuffer.githubUrl as string" class="input" />
                </div>
                <div class="form-group checkbox-group">
                  <label class="checkbox-label">
                    <input v-model="editBuffer.isGithub as boolean" type="checkbox" class="checkbox" />
                    GitHubリンクあり
                  </label>
                </div>
              </div>
              <div class="form-actions">
                <button class="btn-cancel" @click="cancelEdit">キャンセル</button>
                <button class="btn-save" @click="saveProject">保存</button>
              </div>
            </template>
            <template v-else>
              <div class="project-body">
                <div class="project-head">
                  <span class="item-text">{{ p.title }}</span>
                  <div class="project-links">
                    <a v-if="p.url" :href="p.url" target="_blank" class="pj-link" title="デモ">
                      <svg width="11" height="11" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M18 13v6a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h6"/><polyline points="15 3 21 3 21 9"/><line x1="10" y1="14" x2="21" y2="3"/></svg>
                    </a>
                    <a v-if="p.githubUrl" :href="p.githubUrl" target="_blank" class="pj-link" title="GitHub">
                      <svg width="11" height="11" viewBox="0 0 24 24" fill="currentColor"><path d="M12 .5C5.65.5.5 5.65.5 12c0 5.1 3.29 9.42 7.86 10.95.58.1.79-.25.79-.55v-2.1c-3.2.7-3.87-1.54-3.87-1.54-.52-1.33-1.28-1.69-1.28-1.69-1.05-.71.08-.7.08-.7 1.16.08 1.77 1.19 1.77 1.19 1.03 1.76 2.7 1.25 3.36.96.1-.75.4-1.26.73-1.55-2.55-.29-5.24-1.28-5.24-5.69 0-1.26.45-2.29 1.18-3.09-.12-.3-.51-1.46.11-3.05 0 0 .96-.31 3.15 1.18a10.9 10.9 0 0 1 2.87-.39c.97.01 1.95.13 2.87.39 2.18-1.49 3.14-1.18 3.14-1.18.63 1.59.23 2.75.11 3.05.74.8 1.18 1.83 1.18 3.09 0 4.42-2.7 5.4-5.27 5.68.41.36.78 1.06.78 2.13v3.16c0 .3.21.66.8.55A11.51 11.51 0 0 0 23.5 12C23.5 5.65 18.35.5 12 .5z"/></svg>
                    </a>
                  </div>
                </div>
                <p class="project-desc">{{ p.explanation }}</p>
              </div>
              <div class="item-actions">
                <button class="btn-icon-sm" @click="startEdit(p as Record<string,unknown>)">
                  <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/></svg>
                </button>
                <button class="btn-icon-sm danger" @click="deleteProject(p.id!)">
                  <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"/><path d="M19 6l-1 14H6L5 6"/><path d="M10 11v6M14 11v6"/><path d="M9 6V4h6v2"/></svg>
                </button>
              </div>
            </template>
          </div>
        </div>
      </div>
    </template>
  </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@300;400;500&family=DM+Serif+Display&display=swap');

.page {
  padding: 32px 32px 60px;
  max-width: 800px;
  margin: 0 auto;
  font-family: 'Noto Sans JP', sans-serif;
}

/* ── ページヘッダー ── */
.page-header {
  display: flex; align-items: flex-start; gap: 14px; margin-bottom: 24px;
}
.header-icon {
  width: 40px; height: 40px; border-radius: 10px;
  background: rgba(167,139,250,.12); border: 0.5px solid rgba(167,139,250,.25);
  display: flex; align-items: center; justify-content: center; flex-shrink: 0; margin-top: 2px;
}
.page-title { font-size: 18px; font-weight: 500; color: var(--color-text-primary, #111); margin-bottom: 4px; }
.page-sub   { font-size: 12.5px; color: var(--color-text-tertiary, #9ca3af); }

/* ── ローディング ── */
.loading {
  display: flex; align-items: center; gap: 10px;
  color: var(--color-text-tertiary, #9ca3af); font-size: 13px;
  padding: 60px 0; justify-content: center;
}

/* ── タブ ── */
.tabs {
  display: flex; gap: 4px;
  border-bottom: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  margin-bottom: 20px;
}
.tab-btn {
  display: inline-flex; align-items: center; gap: 6px;
  padding: 9px 16px; border: none; background: none; border-radius: 8px 8px 0 0;
  font-size: 13px; font-family: 'Noto Sans JP', sans-serif;
  color: var(--color-text-tertiary, #9ca3af); cursor: pointer;
  transition: color .15s, background .15s;
  margin-bottom: -0.5px;
  border-bottom: 2px solid transparent;
}
.tab-btn:hover { color: var(--color-text-primary, #111); }
.tab-btn.active {
  color: #7c3aed;
  border-bottom-color: #7c3aed;
  font-weight: 500;
}
.tab-count {
  font-size: 10px; padding: 1px 6px; border-radius: 99px;
  background: var(--color-background-secondary, #f3f4f6);
  color: var(--color-text-tertiary, #9ca3af);
}
.tab-btn.active .tab-count { background: rgba(124,58,237,.1); color: #7c3aed; }

/* ── セクション ── */
.section {}
.sec-head {
  display: flex; align-items: center; justify-content: space-between;
  margin-bottom: 12px;
}
.sec-title { font-size: 12px; font-weight: 500; letter-spacing: .08em; text-transform: uppercase; color: var(--color-text-tertiary, #9ca3af); }

/* ── 追加ボタン ── */
.btn-add {
  display: inline-flex; align-items: center; gap: 5px;
  padding: 6px 14px; border-radius: 8px;
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  background: var(--color-background-primary, #fff);
  color: var(--color-text-secondary, #374151);
  font-size: 12.5px; font-family: 'Noto Sans JP', sans-serif;
  cursor: pointer; transition: background .15s, border-color .15s;
}
.btn-add:hover { background: rgba(124,58,237,.06); border-color: rgba(124,58,237,.3); color: #7c3aed; }

/* ── フォームカード ── */
.form-card {
  background: var(--color-background-primary, #fff);
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  border-radius: 12px; padding: 16px; margin-bottom: 12px;
}
.add-card { border-color: rgba(124,58,237,.25); background: rgba(124,58,237,.02); }

.form-grid {
  display: grid; grid-template-columns: 1fr 1fr; gap: 10px; margin-bottom: 12px;
}
.form-group { display: flex; flex-direction: column; gap: 4px; }
.form-group.full { grid-column: 1 / -1; }
.form-label { font-size: 11px; color: var(--color-text-tertiary, #9ca3af); font-weight: 500; }

.input {
  padding: 8px 10px; border-radius: 8px;
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  background: var(--color-background-secondary, #f9fafb);
  font-size: 13px; font-family: 'Noto Sans JP', sans-serif;
  color: var(--color-text-primary, #111); outline: none;
  transition: border-color .15s;
}
.input:focus { border-color: rgba(124,58,237,.4); }
.input[type="range"] { padding: 4px 0; border: none; background: none; }
.input.textarea { resize: vertical; min-height: 80px; }

.checkbox-group { justify-content: flex-end; }
.checkbox-label { display: flex; align-items: center; gap: 6px; font-size: 13px; color: var(--color-text-secondary, #374151); cursor: pointer; }
.checkbox { width: 14px; height: 14px; cursor: pointer; accent-color: #7c3aed; }

.form-actions {
  display: flex; justify-content: flex-end; gap: 8px;
}
.btn-cancel {
  padding: 7px 14px; border-radius: 8px;
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  background: none; font-size: 12.5px; font-family: 'Noto Sans JP', sans-serif;
  color: var(--color-text-tertiary, #9ca3af); cursor: pointer;
}
.btn-save {
  padding: 7px 18px; border-radius: 8px; border: none;
  background: linear-gradient(135deg, #7c3aed, #2563eb);
  color: #fff; font-size: 12.5px; font-family: 'Noto Sans JP', sans-serif;
  font-weight: 500; cursor: pointer;
  box-shadow: 0 0 12px rgba(124,58,237,.25);
  transition: opacity .15s;
}
.btn-save:hover { opacity: .88; }

/* ── リスト ── */
.list { display: flex; flex-direction: column; gap: 6px; }
.empty { text-align: center; padding: 40px; font-size: 13px; color: var(--color-text-tertiary, #9ca3af); }

.list-item {
  background: var(--color-background-primary, #fff);
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  border-radius: 10px; padding: 14px 14px 14px 16px;
  display: flex; align-items: center; justify-content: space-between; gap: 12px;
  transition: border-color .15s;
}
.list-item:hover { border-color: rgba(167,139,250,.2); }

/* 編集中は縦並び */
.list-item:has(.form-grid) { flex-direction: column; align-items: stretch; }

.item-body { display: flex; align-items: center; gap: 12px; flex: 1; min-width: 0; }
.item-time { font-size: 11px; color: var(--color-text-tertiary, #9ca3af); white-space: nowrap; flex-shrink: 0; }
.item-text { font-size: 13.5px; color: var(--color-text-primary, #111); }

.item-actions { display: flex; gap: 4px; flex-shrink: 0; }
.btn-icon-sm {
  width: 28px; height: 28px; border-radius: 7px; border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  background: var(--color-background-secondary, #f9fafb);
  color: var(--color-text-tertiary, #9ca3af); cursor: pointer; display: flex; align-items: center; justify-content: center;
  transition: color .15s, background .15s;
}
.btn-icon-sm:hover { color: #7c3aed; background: rgba(124,58,237,.06); border-color: rgba(124,58,237,.2); }
.btn-icon-sm.danger:hover { color: #ef4444; background: rgba(239,68,68,.06); border-color: rgba(239,68,68,.2); }

/* ── スキル専用 ── */
.skill-left  { display: flex; align-items: center; gap: 8px; flex: 1; }
.skill-cat   { font-size: 10.5px; color: var(--color-text-tertiary, #9ca3af); }
.fw-tag {
  font-size: 9px; padding: 1px 5px; border-radius: 3px;
  border: 0.5px solid var(--color-border-secondary, #d1d5db);
  color: var(--color-text-tertiary, #9ca3af);
}
.skill-meter { display: flex; align-items: center; gap: 8px; width: 140px; flex-shrink: 0; }
.meter-track {
  flex: 1; height: 5px; border-radius: 99px;
  background: var(--color-border-tertiary, #e5e7eb); overflow: hidden;
}
.meter-fill  { height: 100%; border-radius: 99px; background: linear-gradient(90deg, #7c3aed, #2563eb); transition: width .4s; }
.meter-label { font-size: 11px; color: var(--color-text-tertiary, #9ca3af); white-space: nowrap; width: 52px; text-align: right; }

/* ── プロジェクト専用 ── */
.project-item { align-items: flex-start; }
.project-body { flex: 1; min-width: 0; }
.project-head { display: flex; align-items: center; gap: 10px; margin-bottom: 4px; }
.project-links { display: flex; gap: 5px; }
.pj-link {
  display: flex; align-items: center; justify-content: center;
  width: 20px; height: 20px; border-radius: 5px;
  border: 0.5px solid var(--color-border-tertiary, #e5e7eb);
  color: var(--color-text-tertiary, #9ca3af); text-decoration: none;
  transition: color .15s;
}
.pj-link:hover { color: var(--color-text-primary, #111); }
.project-desc { font-size: 12px; color: var(--color-text-tertiary, #9ca3af); line-height: 1.6; }

/* ── スピナー ── */
.spinner-lg {
  width: 18px; height: 18px; border-radius: 50%;
  border: 2px solid rgba(167,139,250,.2); border-top-color: #a78bfa;
  animation: spin .7s linear infinite;
}
@keyframes spin { to { transform: rotate(360deg); } }

@media (max-width: 640px) {
  .page { padding: 16px 16px 40px; }
  .form-grid { grid-template-columns: 1fr; }
  .skill-meter { display: none; }
}
</style>