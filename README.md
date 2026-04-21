# [エンジニア向けポートフォリオ作成アプリ]

### AIによる自動作成機能を搭載したフルスタックWebアプリケーション

Spring BootとNuxt.jsを組み合わせ、Googleログインによる認証と、AI（LLM）を活用した自動生成機能を統合したポートフォリオ作品です。

---

## 💎 サービス概要
エンジニアのポートフォリオ作成を支援するためのWebアプリケーションです。ユーザーはGoogleアカウントで簡単にログインし、AIが自動生成するコンテンツを活用して、魅力的なポートフォリオを作成できます。   

## 🚀 主な機能
* **Google OAuth 2.0 ログイン**: 面倒な会員登録なしで、Googleアカウントですぐに利用可能。
* **AI自動生成エンジン**: Spring AIを利用し、[OpenAI / Gemini / Claude] などの最新AIモデルと連携。
* **生成履歴管理**: 過去に生成したコンテンツの保存、編集、削除。
* **リアルタイム・プレビュー**: AIが生成する過程を直感的に確認できるUI。
* **レスポンシブ対応**: PC・スマホ両方で快適に利用可能。

## 🛠 技術スタック

### フロントエンド
- **Framework**: Nuxt 3 (Vue.js 3)
- **State Management**: Pinia
- **Styling**: Tailwind CSS / Nuxt UI
- **Package Manager**:npm

### バックエンド
- **Framework**: Spring Boot 3.x
- **Language**: Java 21
- **Security**: Spring Security (OAuth 2.0 Client)
- **Database**: PostgreSQL (Supabase / Render)
- **Build Tool**: Gradle
## 🏗 システム構成
1. **Frontend (Nuxt.js)**:
    - ユーザーインターフェースと、API通信による非同期データ処理。
2. **Backend (Spring Boot)**:
    - REST APIの提供、Google認証のハンドリング、ビジネスロジックの実行。
3. **AI Service**:
    - Spring AIを介した生成AI APIとの連携。
4. **Database (PostgreSQL)**:
    - ユーザー情報および生成結果の永続化。


## 🏃 起動方法

### バックエンド (Spring Boot)
```bash
cd backend
./gradlew bootRun