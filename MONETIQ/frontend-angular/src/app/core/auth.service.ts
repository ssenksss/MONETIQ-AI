import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { environment } from '../../environments/environments'

type ApiResponse<T> = { success: boolean; data: T; error?: string }

type LoginResp = {
    status: string
    tier: string
    userId: number
    token: string
}

@Injectable({ providedIn: 'root' })
export class AuthService {
    private API = environment.apiBaseUrl

    constructor(private http: HttpClient) {}

    login(email: string, password: string) {
        return this.http.post<ApiResponse<LoginResp>>(`${this.API}/auth/login`, { email, password })
    }

    saveToken(token: string) {
        localStorage.setItem('monetiq_admin_token', token)
    }

    get token() {
        return localStorage.getItem('monetiq_admin_token')
    }

    logout() {
        localStorage.removeItem('monetiq_admin_token')
    }
}
