// stores/auth.ts
import { defineStore } from 'pinia'

export interface AuthUser {
  userId: number
  name?: string
  email?: string
  token: string
}

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null as AuthUser | null,
  }),

  getters: {
    isLoggedIn: (state) => !!state.user,
  },

  actions: {
    login(user: AuthUser) {
      this.user = user
      if (import.meta.client) {
        sessionStorage.setItem('portfolio_user', JSON.stringify(user))
      }
    },

    logout() {
      this.user = null
      if (import.meta.client) {
        sessionStorage.removeItem('portfolio_user')
      }
      navigateTo('/')
    },

    restoreSession() {
      if (import.meta.client) {
        const stored = sessionStorage.getItem('portfolio_user')
        if (stored) {
          try {
            this.user = JSON.parse(stored)
          } catch {
            sessionStorage.removeItem('portfolio_user')
          }
        }
      }
    },
  },
})
