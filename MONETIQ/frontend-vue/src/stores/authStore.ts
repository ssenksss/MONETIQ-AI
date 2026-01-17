import { defineStore } from 'pinia'

interface SignupPayload {
    name?: string
    email: string
    password: string
    marketingOptIn?: boolean
}

interface LoginPayload {
    email: string
    password: string
    rememberMe?: boolean
}

interface AuthState {
    user: any
    token: string | null
}

export const useAuthStore = defineStore('auth', {
    state: (): AuthState => ({
        user: null,
        token: null,
    }),
    actions: {
        async signup(payload: SignupPayload) {
            try {
                await new Promise(resolve => setTimeout(resolve, 1000))
                this.user = {
                    name: payload.name || '',
                    email: payload.email
                }
                this.token = 'mock-token'
            } catch (err) {
                throw new Error('Failed to sign up')
            }
        },

        async login(payload: LoginPayload) {
            try {
                await new Promise(resolve => setTimeout(resolve, 1000))
                this.user = { email: payload.email }
                this.token = 'mock-token'
            } catch (err) {
                throw new Error('Failed to login')
            }
        }
    }
})
