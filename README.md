# [エンジニア向けポートフォリオ作成アプリ]

### AIによる自動作成機能を搭載したフルスタックWebアプリケーション

Spring BootとNuxt.jsを組み合わせ、Googleログインによる認証と、AI（LLM）を活用した自動生成機能を統合したポートフォリオ作品です。

---

## 💎 サービス概要
ユーザーが入力したキーワードや条件に基づき、AIが最適な[生成するもの：例 記事、コード、レシピ等]を自動で作成します。作成されたデータはクラウド上で安全に保存・管理できます。

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
- **Package Manager**: pnpm

### バックエンド
- **Framework**: Spring Boot 3.x
- **Language**: Java 21
- **Security**: Spring Security (OAuth 2.0 Client)
- **AI Integration**: Spring AI
- **Database**: PostgreSQL (Supabase / Render)
- **Build Tool**: Gradle

### インフラ / デプロイ
- **Frontend**: Vercel
- **Backend**: Render
- **Database**: Supabase / PostgreSQL

## 🏗 システム構成
1. **Frontend (Nuxt.js)**:
    - ユーザーインターフェースと、API通信による非同期データ処理。
2. **Backend (Spring Boot)**:
    - REST APIの提供、Google認証のハンドリング、ビジネスロジックの実行。
3. **AI Service**:
    - Spring AIを介した生成AI APIとの連携。
4. **Database (PostgreSQL)**:
    - ユーザー情報および生成結果の永続化。

## 🔑 環境変数設定 (Environment Variables)

### Backend (`application.yml` または 環境変数)
- `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID`: GoogleクライアントID
- `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_SECRET`: Googleクライアントシークレット
- `SPRING_AI_OPENAI_API_KEY`: AIサービス用APIキー

### Frontend (`.env`)
- `NUXT_PUBLIC_API_BASE_URL`: バックエンドAPIのURL

## 🏃 起動方法

### バックエンド (Spring Boot)
```bash
cd backend
./gradlew bootRun