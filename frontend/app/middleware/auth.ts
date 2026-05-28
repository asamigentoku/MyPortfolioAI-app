// middleware/auth.ts
import { useAuthStore } from '../stores/auth'

export default defineNuxtRouteMiddleware((to) => {
  const auth = useAuthStore()
  auth.restoreSession()

  if (!auth.isLoggedIn && to.path !== '/login' && to.path !== '/') {
    return navigateTo('/login')
  }
  if (auth.isLoggedIn && to.path === '/login') {
    return navigateTo('/ai')
  }
})
