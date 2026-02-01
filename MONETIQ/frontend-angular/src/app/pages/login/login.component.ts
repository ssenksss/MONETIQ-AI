import { Component } from '@angular/core'
import { Router } from '@angular/router'
import { AuthService } from '../../core/auth.service'
import { CommonModule } from '@angular/common'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [FormsModule, CommonModule, ReactiveFormsModule],
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
    email = ''
    password = ''
    loading = false
    error = ''

    private readonly allowedEmails = new Set(['admin@monetiq.ai', 'manager@monetiq.ai'])

    constructor(private auth: AuthService, private router: Router) {}

    submit() {
        this.error = ''
        this.loading = true

        const email = this.email.trim().toLowerCase()

        if (!this.allowedEmails.has(email)) {
            this.error = 'Access denied. Only admin can log in.'
            this.loading = false
            return
        }

        this.auth.login(email, this.password).subscribe({
            next: (res) => {
                const token = res?.data?.token
                if (!token) {
                    this.error = 'Missing token in response'
                    this.loading = false
                    return
                }
                this.auth.saveToken(token)
                this.router.navigate(['/ultra'])
                this.loading = false
            },
            error: (e) => {
                this.error = e?.error?.error || 'Login failed'
                this.loading = false
            },
        })
    }
}
