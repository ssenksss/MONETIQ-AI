import { defineStore } from 'pinia'
import { ref } from 'vue'

interface Suggestion {
    text: string
    day?: number
}

export interface Profile {
    username: string
    analysisDate: string
    suggestions: Suggestion[]
}

export const useProfileStore = defineStore('profile', () => {
    const profile = ref<Profile>({
        username: 'TeodoraGaric',
        analysisDate: '2026-01-16',
        suggestions: [
            { text: 'Post more consistently' },
            { text: 'Use more hashtags' },
            { text: 'Engage with comments' },
        ],
    })

    const premiumPlan = ref<Suggestion[]>(Array.from({ length: 30 }, (_, i) => ({
        day: i + 1,
        text: `Actionable tip for day ${i + 1}`,
    })))

    const ultraPlan = ref<Suggestion[]>([
        { text: 'Exclusive insights' },
        { text: '1-on-1 strategy session' },
        { text: 'Priority support' },
    ])

    return { profile, premiumPlan, ultraPlan }
})
