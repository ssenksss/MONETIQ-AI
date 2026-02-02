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
        try {
            const res = await api.get('/me')
            me.value = res.data
            username.value = res.data.username
            role.value = res.data.role
            profile.value = res.data.profile
            plan.value = res.data.plan
        } catch (err: any) {
            console.error('[Error] fetchMe failed', err)
            error.value = err.response?.data?.message || err.message
            reset()
        } finally {
            isLoaded.value = true
        }
    }

    const fetchPremiumPlan = async () => {
        try {
            const { data } = await api.get('/plans/premium')
            plan.value = data
        } catch (e) {
            console.error('fetchPremiumPlan failed', e)
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

    return { username, role, profile, plan, isLoaded, me, error, fetchMe, fetchPremiumPlan, setProfile, reset }
})
