import axios, {
    type AxiosInstance,
    type InternalAxiosRequestConfig
} from 'axios'

import { useAuthStore } from '@/stores/authStore'

const api: AxiosInstance = axios.create({
    baseURL: 'http://localhost:8080/api',
    withCredentials: false,
})

api.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        const auth = useAuthStore()

        if (auth.token) {
            config.headers = config.headers ?? {}
            config.headers.Authorization = `Bearer ${auth.token}`
        }

        return config
    },
    (error) => Promise.reject(error)
)

export default api
