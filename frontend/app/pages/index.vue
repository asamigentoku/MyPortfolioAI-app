<script setup lang="ts">
definePageMeta({ layout: false })

const auth = useAuthStore()
if (auth.isLoggedIn) {
  await navigateTo('/portfolio')
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
      <div class="nav-logo">
        <span class="logo-mark">✦</span>
        <span class="logo-text">Aether</span>
      </div>
      <NuxtLink to="/login" class="nav-cta">ログイン</NuxtLink>
    </header>

    <!-- ヒーロー -->
    <section class="hero">
      <div class="hero-badge">AI × ポートフォリオ</div>
      <h1 class="hero-h1">
        あなたの経歴を、<br />
        <span class="grad-text">AIが最高の形に。</span>
      </h1>
      <p class="hero-sub">
        職歴・スキル・プロジェクトを入力するだけで、<br class="br-pc" />
        AIが洗練されたポートフォリオを自動生成します。
      </p>
      <div class="hero-actions">
        <NuxtLink to="/login" class="btn-primary">
          無料で始める
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M5 12h14M12 5l7 7-7 7"/>
          </svg>
        </NuxtLink>
      </div>
    </section>

    <!-- 特徴 -->
    <section class="features">
      <p class="sec-eyebrow">FEATURES</p>
      <h2 class="sec-h2">Aether でできること</h2>

      <div class="feat-grid">
        <div class="feat-card">
          <div class="feat-icon" style="background:rgba(167,139,250,.12)">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#a78bfa" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
              <path d="M12 2L9.5 9.5 2 12l7.5 2.5L12 22l2.5-7.5L22 12l-7.5-2.5z"/>
            </svg>
          </div>
          <h3 class="feat-title">AIポートフォリオ自動生成</h3>
          <p class="feat-desc">Spring AI が職歴・スキルを解析し、採用担当者に刺さる構成を数秒で提案・生成します。</p>
        </div>

        <div class="feat-card">
          <div class="feat-icon" style="background:rgba(96,165,250,.12)">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#60a5fa" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
              <rect x="2" y="3" width="20" height="14" rx="2"/>
              <path d="M8 21h8M12 17v4"/>
            </svg>
          </div>
          <h3 class="feat-title">美しいポートフォリオ</h3>
          <p class="feat-desc">経歴・スキル・プロジェクトを洗練されたデザインで表示。URLを共有するだけで即公開できます。</p>
        </div>

        <div class="feat-card">
          <div class="feat-icon" style="background:rgba(52,211,153,.1)">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#34d399" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
              <path d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
              <path d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
            </svg>
          </div>
          <h3 class="feat-title">Google でかんたんログイン</h3>
          <p class="feat-desc">アカウント登録不要。Google OAuth 2.0 で即ログインして、すぐに使い始められます。</p>
        </div>

        <div class="feat-card">
          <div class="feat-icon" style="background:rgba(251,146,60,.1)">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#fb923c" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
              <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
              <polyline points="14 2 14 8 20 8"/>
              <line x1="12" y1="18" x2="12" y2="12"/>
              <line x1="9" y1="15" x2="15" y2="15"/>
            </svg>
          </div>
          <h3 class="feat-title">生成履歴管理</h3>
          <p class="feat-desc">過去に生成したポートフォリオを保存・編集・削除。いつでも見返してブラッシュアップできます。</p>
        </div>
      </div>
    </section>

    <!-- CTA -->
    <section class="cta-sec">
      <div class="cta-box">
        <div class="bg-glow glow-cta" />
        <h2 class="cta-h2">今すぐ始めましょう</h2>
        <p class="cta-sub">Googleアカウントだけで、すぐに使えます。</p>
        <NuxtLink to="/login" class="btn-primary large">
          Google でログイン
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M5 12h14M12 5l7 7-7 7"/>
          </svg>
        </NuxtLink>
      </div>
    </section>

    <!-- フッター -->
    <footer class="footer">
      <span class="logo-mark">✦</span>
      <span class="logo-text ft">Aether</span>
      <span class="footer-copy">© 2026 Aether. All rights reserved.</span>
    </footer>
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
  overflow-x: hidden;
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
  top: -120px; left: -100px;
  background: radial-gradient(circle, rgba(124,58,237,.22) 0%, transparent 70%);
}
.glow-blue {
  width: 420px; height: 420px;
  top: 60px; right: -80px;
  background: radial-gradient(circle, rgba(37,99,235,.18) 0%, transparent 70%);
}
.glow-cta {
  width: 600px; height: 300px;
  top: 50%; left: 50%;
  transform: translate(-50%, -50%);
  background: radial-gradient(circle, rgba(124,58,237,.2) 0%, transparent 70%);
}

/* ── ナビ ── */
.nav {
  position: relative;
  z-index: 10;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 40px;
  height: 60px;
  border-bottom: 0.5px solid rgba(255,255,255,.07);
}
.nav-logo { display: flex; align-items: center; gap: 8px; }
.logo-mark { font-size: 18px; color: #a78bfa; }
.logo-text {
  font-family: 'DM Serif Display', serif;
  font-size: 19px;
  color: #f0eeff;
  letter-spacing: .02em;
}
.logo-text.ft { font-size: 15px; }
.nav-cta {
  font-size: 13px;
  color: rgba(255,255,255,.6);
  text-decoration: none;
  padding: 6px 18px;
  border-radius: 8px;
  border: 0.5px solid rgba(255,255,255,.12);
  transition: color .18s, background .18s;
}
.nav-cta:hover { color: #f0eeff; background: rgba(255,255,255,.06); }

/* ── ヒーロー ── */
.hero {
  position: relative;
  z-index: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  padding: 80px 24px 0;
}
.hero-badge {
  font-size: 11px;
  letter-spacing: .14em;
  text-transform: uppercase;
  color: #a78bfa;
  background: rgba(167,139,250,.1);
  border: 0.5px solid rgba(167,139,250,.3);
  padding: 4px 14px;
  border-radius: 99px;
  margin-bottom: 28px;
}
.hero-h1 {
  font-family: 'DM Serif Display', serif;
  font-size: clamp(36px, 6vw, 60px);
  line-height: 1.2;
  margin-bottom: 20px;
}
.grad-text {
  background: linear-gradient(90deg, #a78bfa 0%, #60a5fa 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}
.hero-sub {
  font-size: 15px;
  color: rgba(255,255,255,.5);
  line-height: 1.8;
  margin-bottom: 36px;
  max-width: 480px;
}
.br-pc { display: block; }
.hero-actions {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
  justify-content: center;
  margin-bottom: 60px;
}

/* ── ボタン ── */
.btn-primary {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 11px 24px;
  border-radius: 10px;
  background: linear-gradient(135deg, #7c3aed, #2563eb);
  color: #fff;
  font-size: 13.5px;
  font-weight: 500;
  text-decoration: none;
  transition: opacity .18s, transform .18s;
  box-shadow: 0 0 24px rgba(124,58,237,.35);
}
.btn-primary:hover { opacity: .88; transform: translateY(-1px); }
.btn-primary.large { padding: 13px 30px; font-size: 14px; }
.btn-ghost {
  display: inline-flex;
  align-items: center;
  padding: 11px 24px;
  border-radius: 10px;
  border: 0.5px solid rgba(255,255,255,.14);
  color: rgba(255,255,255,.65);
  font-size: 13.5px;
  text-decoration: none;
  transition: color .18s, background .18s;
}
.btn-ghost:hover { color: #f0eeff; background: rgba(255,255,255,.06); }

/* ── ヒーローカード ── */
.hero-card-wrap { width: 100%; max-width: 560px; perspective: 1000px; }
.hero-card {
  background: rgba(255,255,255,.04);
  border: 0.5px solid rgba(255,255,255,.1);
  border-radius: 14px;
  overflow: hidden;
  transform: rotateX(4deg);
  box-shadow: 0 40px 80px rgba(0,0,0,.5), 0 0 0 0.5px rgba(167,139,250,.15);
}
.card-bar {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 10px 14px;
  border-bottom: 0.5px solid rgba(255,255,255,.07);
  background: rgba(255,255,255,.02);
}
.dot { width: 9px; height: 9px; border-radius: 50%; }
.dot.red    { background: #ff5f57; }
.dot.yellow { background: #febc2e; }
.dot.green  { background: #28c840; }
.card-body { padding: 20px; }
.card-row { display: flex; align-items: center; gap: 12px; margin-bottom: 14px; }
.card-avatar {
  width: 40px; height: 40px;
  border-radius: 50%;
  background: linear-gradient(135deg, #7c3aed, #2563eb);
  flex-shrink: 0;
}
.card-meta { flex: 1; display: flex; flex-direction: column; gap: 6px; }
.card-line {
  height: 8px;
  border-radius: 99px;
  background: rgba(255,255,255,.1);
}
.card-line.thin { height: 6px; background: rgba(255,255,255,.06); }
.card-line.w100 { width: 100%; }
.card-line.w90  { width: 90%; }
.card-line.w80  { width: 80%; }
.card-line.w60  { width: 60%; }
.card-line.w40  { width: 40%; }
.card-chips { display: flex; gap: 6px; flex-wrap: wrap; }
.card-chip {
  font-size: 10px;
  padding: 3px 10px;
  border-radius: 99px;
  background: rgba(167,139,250,.12);
  border: 0.5px solid rgba(167,139,250,.25);
  color: #c4b5fd;
}

/* ── 特徴 ── */
.features {
  position: relative;
  z-index: 1;
  padding: 100px 24px 80px;
  text-align: center;
}
.sec-eyebrow {
  font-size: 11px;
  letter-spacing: .14em;
  color: rgba(255,255,255,.3);
  margin-bottom: 12px;
}
.sec-h2 {
  font-family: 'DM Serif Display', serif;
  font-size: clamp(26px, 4vw, 38px);
  color: #f0eeff;
  margin-bottom: 48px;
}
.feat-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 14px;
  max-width: 900px;
  margin: 0 auto;
  text-align: left;
}
.feat-card {
  padding: 28px 22px;
  background: rgba(255,255,255,.03);
  border: 0.5px solid rgba(255,255,255,.08);
  border-radius: 14px;
  transition: border-color .2s, background .2s;
}
.feat-card:hover {
  background: rgba(255,255,255,.05);
  border-color: rgba(167,139,250,.25);
}
.feat-icon {
  width: 40px; height: 40px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 16px;
}
.feat-title { font-size: 14px; font-weight: 500; color: #f0eeff; margin-bottom: 8px; }
.feat-desc  { font-size: 13px; color: rgba(255,255,255,.45); line-height: 1.75; }

/* ── 技術スタック ── */
.tech-sec {
  position: relative;
  z-index: 1;
  padding: 0 24px 80px;
  text-align: center;
}
.tech-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 12px;
  max-width: 900px;
  margin: 0 auto;
  text-align: left;
}
.tech-card {
  padding: 20px;
  background: rgba(255,255,255,.03);
  border: 0.5px solid rgba(255,255,255,.08);
  border-radius: 12px;
}
.tech-label {
  font-size: 11px;
  letter-spacing: .1em;
  text-transform: uppercase;
  color: rgba(255,255,255,.3);
  margin-bottom: 12px;
}
.tech-tags { display: flex; flex-direction: column; gap: 6px; }
.tech-tag {
  font-size: 12.5px;
  color: rgba(255,255,255,.65);
  padding: 4px 0;
  border-bottom: 0.5px solid rgba(255,255,255,.05);
}
.tech-tag:last-child { border-bottom: none; }

/* ── CTA ── */
.cta-sec {
  position: relative;
  z-index: 1;
  padding: 40px 24px 100px;
  display: flex;
  justify-content: center;
}
.cta-box {
  position: relative;
  text-align: center;
  padding: 60px 40px;
  max-width: 520px;
  width: 100%;
  background: rgba(255,255,255,.03);
  border: 0.5px solid rgba(167,139,250,.2);
  border-radius: 20px;
  overflow: hidden;
}
.cta-h2 {
  font-family: 'DM Serif Display', serif;
  font-size: 30px;
  color: #f0eeff;
  margin-bottom: 10px;
  position: relative;
}
.cta-sub {
  font-size: 13.5px;
  color: rgba(255,255,255,.45);
  margin-bottom: 28px;
  position: relative;
}

/* ── フッター ── */
.footer {
  position: relative;
  z-index: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  padding: 24px;
  border-top: 0.5px solid rgba(255,255,255,.07);
  font-size: 12px;
  color: rgba(255,255,255,.25);
}
.footer-copy { margin-left: 6px; }

@media (max-width: 600px) {
  .nav { padding: 0 20px; }
  .br-pc { display: none; }
  .cta-box { padding: 40px 20px; }
}
</style>
