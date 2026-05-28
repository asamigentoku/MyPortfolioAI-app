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
        localStorage.setItem('portfolio_user', JSON.stringify(user))
      }
    },

    logout() {
      this.user = null
      if (import.meta.client) {
        localStorage.removeItem('portfolio_user')
      }
      navigateTo('/')
    },

    restoreSession() {
      if (import.meta.client) {
        const stored = localStorage.getItem('portfolio_user')
        if (stored) {
          try {
            this.user = JSON.parse(stored)
          } catch {
            localStorage.removeItem('portfolio_user')
          }
        }
      }
    },
  },
})
