import { defineStore } from 'pinia'
import axios from 'axios'
import { ref } from 'vue'

export type UserRole = 'FREE_USER' | 'PREMIUM_USER' | 'ULTRA_USER'

export interface PremiumTask {
    day: number
    text: string
}

export interface UserPlan {
    tier: 'FREE' | 'PREMIUM' | 'ULTRA'
    items: PremiumTask[]
}

export interface Suggestion {
    text: string
}

export interface UserProfile {
    analysisDate: string
    suggestions: Suggestion[]
}

export const useUserStore = defineStore('user', () => {
    const username = ref<string | null>(null)
    const role = ref<UserRole | null>(null)
    const profile = ref<UserProfile | null>(null)
    const plan = ref<UserPlan | null>(null)
    const isLoaded = ref(false)

    const fetchPremiumPlan = async () => {
        try {
            const { data } = await axios.get<UserPlan>('http://localhost:8080/api/plans/premium')
            plan.value = data as UserPlan
        } catch (e) {
            console.error('fetchPremiumPlan failed', e)
        }
    }

    const fetchMe = async () => {
        try {
            const { data } = await axios.get('http://localhost:8080/api/me')
            username.value = data.username
            role.value = data.role
            profile.value = data.profile
            plan.value = data.plan as UserPlan
        } catch (e) {
            console.error('fetchMe failed', e)
            reset()
        } finally {
            isLoaded.value = true
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
    }

    return {
        username,
        role,
        profile,
        plan,
        isLoaded,
        fetchMe,
        fetchPremiumPlan,
        reset,
        setProfile
    }
})
