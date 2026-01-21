import { defineStore } from 'pinia'
import axios from 'axios'

export const useProfileStore = defineStore('profile', () => {
    const fetchProfile = async (username: string, tier?: string) => {
        try {
            const response = await axios.get('/api/profile/analyze', {
                params: { username, tier }
            })
            return response.data

        } catch (err) {
            console.error(err)
        }
    }

    return { fetchProfile }
})
