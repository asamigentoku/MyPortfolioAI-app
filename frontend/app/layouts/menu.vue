<script setup lang="ts">
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

type MenuItem = 'home' | 'browse' | 'create' | 'settings'

const menuItems: { id: MenuItem; label: string; to: string; icon: string }[] = [
  {
    id: 'create',
    label: 'AI作成',
    to: '/ai',
    icon: `<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
      <path d="M12 2L9.5 9.5 2 12l7.5 2.5L12 22l2.5-7.5L22 12l-7.5-2.5z"/>
    </svg>`,
  },
  {
    id: 'home',
    label: 'ホーム',
    to: '/home',
    icon: `<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
      <path d="M3 9.5L12 3l9 6.5V20a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V9.5z"/>
      <polyline points="9 21 9 12 15 12 15 21"/>
    </svg>`,
  },
  {
    id: 'browse',
    label: '閲覧',
    to: '/browse',
    icon: `<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
      <rect x="2" y="3" width="20" height="14" rx="2"/>
      <path d="M8 21h8M12 17v4"/>
    </svg>`,
  },
  {
    id: 'settings',
    label: '設定',
    to: '/setting',
    icon: `<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
      <circle cx="12" cy="12" r="3"/>
      <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1-2.83 2.83l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-4 0v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83-2.83l.06-.06A1.65 1.65 0 0 0 4.68 15a1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1 0-4h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 2.83-2.83l.06.06A1.65 1.65 0 0 0 9 4.68a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 4 0v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 2.83l-.06.06A1.65 1.65 0 0 0 19.4 9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 0 4h-.09a1.65 1.65 0 0 0-1.51 1z"/>
    </svg>`,
  },
]

function isActive(to: string): boolean {
  return route.path === to
}

function navigate(to: string) {
  router.push(to)
}
</script>

<template>
  <nav class="navbar">
    <!-- Logo -->
    <div class="logo">
      <span class="logo-mark">✦</span>
      <span class="logo-text">Aether</span>
    </div>

    <!-- Menu items — 均等配置 -->
    <ul class="menu-list">
      <li
          v-for="item in menuItems"
          :key="item.id"
          class="menu-item"
          :class="{ active: isActive(item.to) }"
          @click="navigate(item.to)"
      >
        <span class="icon" v-html="item.icon" />
        <span class="label">{{ item.label }}</span>
        <span v-if="item.id === 'create'" class="badge">AI</span>
      </li>
    </ul>

    <!-- Right side actions -->
    <div class="actions">
      <button class="action-btn" title="通知">
        <svg width="17" height="17" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
          <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/>
          <path d="M13.73 21a2 2 0 0 1-3.46 0"/>
        </svg>
        <span class="notif-dot" />
      </button>
      <div class="avatar">
        <span>田</span>
      </div>
    </div>
  </nav>

  <main>
    <slot />
  </main>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@300;400;500&family=DM+Serif+Display&display=swap');

.navbar {
  display: flex;
  align-items: center;
  width: 100%;
  height: 58px;
  padding: 0 28px;
  background: #0f0f11;
  border-bottom: 0.5px solid rgba(255, 255, 255, 0.08);
  box-sizing: border-box;
  font-family: 'Noto Sans JP', sans-serif;
  position: relative;
}

.navbar::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 1px;
  background: linear-gradient(90deg, transparent 0%, #a78bfa 30%, #60a5fa 60%, transparent 100%);
  opacity: 0.6;
}

/* ── Logo ── */
.logo {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-shrink: 0;
}

.logo-mark {
  font-size: 18px;
  color: #a78bfa;
  line-height: 1;
}

.logo-text {
  font-family: 'DM Serif Display', serif;
  font-size: 19px;
  color: #f0eeff;
  letter-spacing: 0.02em;
}

/* ── Menu list — 均等配置 ── */
.menu-list {
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  list-style: none;
  margin: 0;
  padding: 0;
  flex: 1;
}

.menu-item {
  display: flex;
  align-items: center;
  gap: 7px;
  padding: 6px 18px;
  border-radius: 8px;
  cursor: pointer;
  color: rgba(255, 255, 255, 0.45);
  font-size: 13.5px;
  font-weight: 400;
  letter-spacing: 0.01em;
  transition: color 0.18s ease, background 0.18s ease;
  white-space: nowrap;
  user-select: none;
}

.menu-item:hover {
  color: rgba(255, 255, 255, 0.82);
  background: rgba(255, 255, 255, 0.05);
}

.menu-item.active {
  color: #f0eeff;
  background: rgba(167, 139, 250, 0.12);
}

.menu-item.active .icon {
  color: #a78bfa;
}

.icon {
  display: flex;
  align-items: center;
  flex-shrink: 0;
  transition: color 0.18s ease;
}

.label {
  line-height: 1;
}

.badge {
  font-size: 10px;
  font-weight: 500;
  padding: 2px 5px;
  border-radius: 4px;
  background: rgba(167, 139, 250, 0.2);
  color: #c4b5fd;
  letter-spacing: 0.04em;
  margin-left: 1px;
}

.menu-item.active .badge {
  background: rgba(167, 139, 250, 0.3);
  color: #e0d9ff;
}

/* ── Right actions ── */
.actions {
  display: flex;
  align-items: center;
  gap: 12px;
  flex-shrink: 0;
}

.action-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 34px;
  height: 34px;
  border-radius: 8px;
  border: none;
  background: transparent;
  color: rgba(255, 255, 255, 0.4);
  cursor: pointer;
  position: relative;
  transition: color 0.18s ease, background 0.18s ease;
}

.action-btn:hover {
  color: rgba(255, 255, 255, 0.8);
  background: rgba(255, 255, 255, 0.06);
}

.notif-dot {
  position: absolute;
  top: 6px;
  right: 7px;
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: #a78bfa;
  border: 1.5px solid #0f0f11;
}

.avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: linear-gradient(135deg, #7c3aed, #2563eb);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  font-weight: 500;
  color: #fff;
  cursor: pointer;
  flex-shrink: 0;
  transition: opacity 0.18s ease;
}

.avatar:hover {
  opacity: 0.85;
}
</style>