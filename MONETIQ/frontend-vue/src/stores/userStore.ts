import { defineStore } from 'pinia'
import api from '@/api/axios'
import { ref } from 'vue'

export type UserRole = 'FREE' | 'PREMIUM' | 'ULTRA'

export interface PremiumTask { day: number; text: string }
export interface UserPlan { tier: UserRole; items: PremiumTask[] }
export interface Suggestion { text: string }
export interface UserProfile { analysisDate: string; suggestions: Suggestion[] }

export const useUserStore = defineStore('user', () => {
    const username = ref<string | null>(null)
    const role = ref<UserRole | null>(null)
    const profile = ref<UserProfile | null>(null)
    const plan = ref<UserPlan | null>(null)
    const isLoaded = ref(false)
    const me = ref<any>(null)
    const error = ref<string>('')

    const fetchMe = async () => {
        const token = localStorage.getItem('token')

        if (!token) {
            console.warn('[fetchMe] no token, skipping /me')
            reset()
            return
        }

        try {
            const res = await api.get('/me')
            me.value = res.data
            username.value = res.data.username
            role.value = res.data.role
            profile.value = res.data.profile
            plan.value = res.data.plan
        } catch (err: any) {
            console.warn('[fetchMe] user not authenticated or not found', err)
            reset()
            localStorage.removeItem('token')
        } finally {
            isLoaded.value = true
        }
    }

    const fetchPremiumPlan = async () => {
        try {
            const res = await api.get('/plans/premium')
            // ApiResponseDTO je tipa { success: boolean, data: ..., error: string | null }
            // Dakle, pravi plan je u res.data.data
            plan.value = res.data.data
        } catch (e) {
            console.error('fetchPremiumPlan failed', e)
        }
    }
    const fetchGeneratedPlan = async (username: string) => {
        try {
            const res = await api.get(`/premium/generate-plan?username=${username}`)
            // res.data.data je string JSON-a, parsiramo ga u objekt
            const parsed = JSON.parse(res.data.data)
            // backend vraća: { username, generatedDate, plan: [ { day, task } ] }
            plan.value = {
                tier: 'PREMIUM',
                items: parsed.plan.map((p: any) => ({ day: p.day.replace('Day ', ''), text: p.task }))
            }
        } catch (e) {
            console.error('fetchGeneratedPlan failed', e)
        }
    }



    const setProfile = (analysis: UserProfile) => {
        profile.value = analysis
    }

    const reset = () => {
        username.value = null
        role.value = null
        profile.value = null
        plan.value = null
        isLoaded.value = true
        me.value = null
        error.value = ''
    }

    return { username, role, profile, plan, isLoaded, me, error, fetchMe, fetchPremiumPlan,fetchGeneratedPlan, setProfile, reset }
})
