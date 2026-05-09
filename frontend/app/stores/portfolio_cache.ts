import { defineStore } from 'pinia'
import type { components } from '../pages/types/openapi'

type ProfileStructureResponse = components['schemas']['ProfileStructureResponse']

export const useProfileStructureStore = defineStore('profileStructure', {
  state: () => ({
    cacheKeys: [] as string[],
    currentIndex: -1,
    result: null as ProfileStructureResponse | null,
  }),

  getters: {
    hasPrev: (state) => state.currentIndex > 0,
    hasNext: (state) => state.currentIndex < state.cacheKeys.length - 1,
    historyLabel: (state) =>
      state.cacheKeys.length === 0 ? '' : `${state.currentIndex + 1} / ${state.cacheKeys.length}`,
  },

  actions: {
    addCacheKey(cacheKey: string) {
      if (!this.cacheKeys.includes(cacheKey)) {
        this.cacheKeys.push(cacheKey)
        this.currentIndex = this.cacheKeys.length - 1
      }
    },
    setIndex(index: number) {
      if (index >= 0 && index < this.cacheKeys.length) {
        this.currentIndex = index
      }
    },
    setResult(result: ProfileStructureResponse | null) {
      this.result = result
    },
    reset() {
      this.cacheKeys = []
      this.currentIndex = -1
      this.result = null
    },
  },
})