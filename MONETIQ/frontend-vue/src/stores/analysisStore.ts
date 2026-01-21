import { defineStore } from 'pinia'
import { ref } from 'vue'
import axios from 'axios'

export interface AnalysisResult {
    username: string
    analysisDate: string
    suggestions: string[]
}

export const useAnalysisStore = defineStore('analysis', () => {
    const loading = ref(false)
    const result = ref<AnalysisResult | null>(null)
    const history = ref<AnalysisResult[]>([])
    const lastUser = ref<string | null>(null)

    const startAnalysis = async (username: string) => {
        loading.value = true
        result.value = null
        lastUser.value = username

        try {
            const response = await axios.get(
                `http://localhost:8080/api/profile/analyze?username=${username}`
            )

            const analysis: AnalysisResult = {
                username: response.data.username,
                analysisDate: response.data.analysisDate,
                suggestions: response.data.suggestions
            }

            result.value = { ...analysis }
            history.value.unshift({ ...analysis })
        } catch (err: any) {
            console.error(err)
        } finally {
            loading.value = false
        }
    }

    const reset = () => {
        result.value = null
        loading.value = false
        lastUser.value = null
    }

    return { loading, result, history, startAnalysis, reset, lastUser }
})
