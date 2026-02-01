import { defineStore } from 'pinia'
import api from '@/api/axios'
import { ref, computed } from 'vue'
import { useAnalysisStore } from '@/stores/analysisStore'
import { useUserStore } from '@/stores/userStore'

interface SignupPayload {
    name: string
    email: string
    password: string
    marketingOptIn: boolean
}

export const useAuthStore = defineStore('auth', () => {
    const token = ref<string | null>(localStorage.getItem('token'))
    const isAuthenticated = computed(() => !!token.value)

    const signup = async (payload: SignupPayload) => {
        const res = await api.post('/auth/signup', payload)
        token.value = res.data.data.token
        localStorage.setItem('token', token.value!)
    }

    const login = async (email: string, password: string) => {
        const res = await api.post('/auth/login', { email, password })
        token.value = res.data.data.token
        localStorage.setItem('token', token.value!)
    }


    const logout = () => {
        token.value = null
        localStorage.removeItem('token')

        useAnalysisStore().reset()
        useUserStore().reset()
    }


    return { token, isAuthenticated, login, signup, logout }
})
