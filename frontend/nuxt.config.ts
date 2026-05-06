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
      googleClientId: process.env.GOOGLE_CLIENT_ID ?? '',
      apiBaseUrl: process.env.API_BASE_URL ?? 'http://localhost:8080', // 追加
    },
  },
})