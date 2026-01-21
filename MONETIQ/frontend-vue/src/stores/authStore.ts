import axios from 'axios'
import { useUserStore } from './userStore'
import { defineStore } from 'pinia'


interface LoginPayload {
    email: string
    password: string
}

interface SignupPayload {
    name?: string
    email: string
    password: string
}

export const useAuthStore = defineStore('auth', {
    actions: {
        async login(payload: LoginPayload) {
            await axios.post('/api/auth/login', payload)

            const userStore = useUserStore()
            await userStore.fetchMe()
        },

        async signup(payload: SignupPayload) {
            await axios.post('/api/auth/signup', payload)

            const userStore = useUserStore()
            await userStore.fetchMe()
        }
    }
})


