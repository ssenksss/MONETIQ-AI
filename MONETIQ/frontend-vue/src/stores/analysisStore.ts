import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '@/api/axios'

export interface AnalysisResult {
    username: string
    analysisDate: string | null
    suggestions: string[]
}

type AnalysisResponseDTO = {
    username: string
    plan: string
    tier: string
    createdAt: string
    suggestions?: string[]
}

export const useAnalysisStore = defineStore('analysis', () => {
    const loading = ref(false)
    const result = ref<AnalysisResult | null>(null)
    const history = ref<AnalysisResult[]>([])
    const lastUser = ref<string | null>(null)
    const error = ref<string>('')

    const startAnalysis = async (username: string) => {
        loading.value = true
        result.value = null
        error.value = ''
        lastUser.value = username

        try {
            const response = await api.post('/analysis', { username })

            const analysisData: AnalysisResponseDTO = response.data.data

            const analysis: AnalysisResult = {
                username: analysisData.username,
                analysisDate: analysisData.createdAt ?? null,
                suggestions: analysisData.suggestions ?? []
            }

            result.value = { ...analysis }
            await fetchLatest()
            await fetchHistory()
            return analysis
        } catch (err: any) {
            console.error(err)
            error.value = err?.response?.data?.error || err?.message || 'Analysis failed'
            throw err
        } finally {
            loading.value = false
        }
    }
    const fetchLatest = async () => {
        const res = await api.get('/analysis/latest')
        const data = res.data?.data ?? null
        if (!data) { result.value = null; return null }

        result.value = {
            username: data.username,
            analysisDate: data.createdAt ?? null,
            suggestions: data.suggestions ?? []
        }
        return result.value
    }

    const fetchHistory = async () => {
        const res = await api.get('/analysis/history')
        const items = res.data?.data ?? []

        history.value = items.map((x: any) => ({
            username: x.username,
            analysisDate: x.createdAt ?? null,
            suggestions: x.suggestions ?? []
        }))

        return history.value
    }



    const reset = () => {
        result.value = null
        history.value = []
        loading.value = false
        lastUser.value = null
        error.value = ''
    }


    return { loading, result, history, startAnalysis, fetchLatest, fetchHistory, reset, lastUser, error }

})