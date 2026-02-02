import { defineStore } from 'pinia'
import api from '@/api/axios'
import { ref } from 'vue'

export type UserRole = 'FREE' | 'PREMIUM' | 'ULTRA'

export interface PremiumItem { day: number; text: string }

export interface PremiumDay {
    day: number
    title: string
    contentType: string
    tasks: string[]
}

export interface PremiumPlan {
    username: string
    tier: UserRole
    planText: string
    days: PremiumDay[]
    items: PremiumItem[]
    createdAt: string
}

export interface Suggestion { text: string }
export interface UserProfile { analysisDate: string | null; suggestions: Suggestion[] | null }

const mapRole = (r: string): UserRole => {
    const s = (r || '').toUpperCase()
    if (s.includes('PREMIUM')) return 'PREMIUM'
    if (s.includes('ULTRA')) return 'ULTRA'
    return 'FREE'
}

export const useUserStore = defineStore('user', () => {
    const username = ref<string | null>(null)
    const role = ref<UserRole | null>(null)
    const profile = ref<UserProfile | null>(null)

    const plan = ref<PremiumPlan | null>(null)

    const isLoaded = ref(false)
    const me = ref<any>(null)
    const error = ref<string>('')

    const fetchMe = async () => {
        try {
            const res = await api.get('/me')

            const data = res.data?.data
            if (!data) {
                throw new Error('Invalid /me response: missing data')
            }

            me.value = data
            username.value = data.username ?? null
            role.value = mapRole(data.role ?? '')
            profile.value = data.profile ?? null

            return data
        } catch (err: any) {
            console.warn('[fetchMe] /me failed', err?.response?.status, err?.response?.data)
            reset()
            throw err
        } finally {
            isLoaded.value = true
        }
    }

    const fetchPremiumPlan = async (u: string) => {
        const res = await api.get('/premium/plan', { params: { username: u } })
        plan.value = res.data?.data ?? null
        return plan.value
    }
    const upgradeToPremium = async () => {
        const token = localStorage.getItem('token')
        if (!token) throw new Error('Not authenticated')

        await api.post(
            '/user/upgrade/premium',
            null,
            {
                headers: { Authorization: `Bearer ${token}` }
            }
        )

        await fetchMe()
    }
    const upgradeToUltra = async () => {
        const token = localStorage.getItem('token')
        if (!token) throw new Error('Not authenticated')

        await api.post(
            '/user/upgrade/ultra',
            null,
            { headers: { Authorization: `Bearer ${token}` } }
        )

        await fetchMe()
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

    return {
        username,
        role,
        profile,
        plan,
        isLoaded,
        me,
        error,
        fetchMe,
        fetchPremiumPlan,
        setProfile,
        reset,
        upgradeToPremium,
        upgradeToUltra
    }
})
