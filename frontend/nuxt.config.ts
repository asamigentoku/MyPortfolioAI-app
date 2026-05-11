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
      googleClientId: '',   // NUXT_PUBLIC_GOOGLE_CLIENT_ID で上書きされる
      apiBaseUrl: 'http://localhost:8080', // NUXT_PUBLIC_API_BASE_URL で上書きされる
    },
  }
})