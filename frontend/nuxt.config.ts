// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2025-07-15',
  devtools: { enabled: true },

  // ここを追加：Piniaモジュールを有効化する
  modules: [
    '@pinia/nuxt',
  ],

  vite: {
    optimizeDeps: {
      include: [
        '@vue/devtools-core',
        '@vue/devtools-kit',
        'axios',
      ]
    }
  },
  runtimeConfig: {
    public: {
      // プロセス環境変数を直接参照する設定
      googleClientId: process.env.NUXT_PUBLIC_GOOGLE_CLIENT_ID || '',
      apiBaseUrl: process.env.NUXT_PUBLIC_API_BASE_URL || 'http://localhost:8080',
    },
  },
})