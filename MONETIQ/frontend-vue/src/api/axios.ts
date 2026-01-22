import axios from 'axios'
import { useAuthStore } from '@/stores/authStore'

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    withCredentials: true,
})

api.interceptors.request.use((config) => {
    const auth = useAuthStore()
    if (auth.token && config.headers) {
        config.headers.Authorization = `Bearer ${auth.token}`
    }
    return config
}, (error) => {
    return Promise.reject(error)
})

export default api
