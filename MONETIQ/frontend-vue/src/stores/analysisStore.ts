import { defineStore } from 'pinia'
import { ref } from 'vue'

export interface AnalysisResult {
    username: string
    plan: string
    createdAt: string
}

export const useAnalysisStore = defineStore('analysis', () => {
    const currentUsername = ref('')
    const loading = ref(false)
    const result = ref<AnalysisResult | null>(null)
    const history = ref<AnalysisResult[]>([])

    const startAnalysis = async (username: string) => {
        loading.value = true
        currentUsername.value = username
        result.value = null

        await new Promise(resolve => setTimeout(resolve, 1500))

        const generatedPlan = `
AI Monetization Plan for @${username}:

1. Focus on niche-specific content 3x/week.
2. Engage with followers daily.
3. Launch Free & Premium plan content.
4. Collaborate with micro-influencers in your niche.
5. Track metrics weekly and optimize.

Expected Outcome: Increase engagement by 30% in 3 months.
    `

        const analysis: AnalysisResult = {
            username,
            plan: generatedPlan,
            createdAt: new Date().toISOString()
        }

        result.value = analysis
        history.value.unshift(analysis)
        loading.value = false
    }

    return {
        currentUsername,
        loading,
        result,
        history,
        startAnalysis
    }
})
