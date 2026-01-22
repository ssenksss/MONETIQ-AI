import { defineStore } from 'pinia'
import api from '@/api/axios'
import { ref, computed } from 'vue'

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
        try {
            const res = await api.post('/auth/signup', payload)
            token.value = res.data.token
            localStorage.setItem('token', token.value!)
        } catch (err: any) {
            throw new Error(err.response?.data?.message || 'Signup failed')
        }
    }

    const login = async (email: string, password: string) => {
        try {
            const res = await api.post('/auth/login', { email, password })
            token.value = res.data.token
            localStorage.setItem('token', token.value!)
        } catch (err: any) {
            throw new Error(err.response?.data?.message || 'Login failed')
        }
    }

    const logout = () => {
        token.value = null
        localStorage.removeItem('token')
    }

    return { token, isAuthenticated, login, signup, logout }
})
