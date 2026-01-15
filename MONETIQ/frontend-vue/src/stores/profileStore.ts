import { defineStore } from 'pinia'

interface Suggestion {
  text: string
  day?: number
}

interface Profile {
  username: string
  suggestions: Suggestion[]
  analysisDate: string
}

export const useProfileStore = defineStore('profile', {
  state: () => ({
    profile: {} as Profile,
    premiumPlan: [] as Suggestion[],
  }),
  actions: {
    setProfile(profile: Profile) {
      this.profile = profile
    },
    setPremiumPlan(plan: Suggestion[]) {
      this.premiumPlan = plan
    },
  },
})
