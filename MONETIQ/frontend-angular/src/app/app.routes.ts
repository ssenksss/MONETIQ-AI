import { Routes } from '@angular/router'
import { AuthGuard } from './core/auth.guard'
import { ShellComponent } from './layout/shell/shell.component'
import { LoginComponent } from './pages/login/login.component'
import { UltraDashboardComponent } from './pages/ultra-dashboard/ultra-dashboard.component'
import { QueueComponent } from './pages/queue/queue.component'

export const routes: Routes = [
  { path: '', redirectTo: 'ultra', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },

  {
    path: '',
    component: ShellComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'ultra', component: UltraDashboardComponent },
      { path: 'queue', component: QueueComponent },
    ],
  },

  { path: '**', redirectTo: 'ultra' },
]
