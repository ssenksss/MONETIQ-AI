import { Component, OnDestroy, OnInit } from '@angular/core'
import { CommonModule } from '@angular/common'
import { FormsModule } from '@angular/forms'
import { UltraService, UltraRequest, UltraStatus } from '../../core/ultra.service'
import { Subject, Subscription, debounceTime, distinctUntilChanged } from 'rxjs'

@Component({
  selector: 'app-ultra-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ultra-dashboard.component.html',
  styleUrls: ['./ultra-dashboard.component.scss'],
})
export class UltraDashboardComponent implements OnInit, OnDestroy {
  loading = false
  search = ''
  status: UltraStatus | 'ALL' = 'ALL'
  rows: UltraRequest[] = []
  error = ''

  statuses: (UltraStatus | 'ALL')[] = ['ALL', 'PENDING', 'IN_REVIEW', 'APPROVED', 'REJECTED']

  private apply$ = new Subject<{ search: string; status: string }>()
  private sub?: Subscription

  constructor(private ultra: UltraService) {}

  ngOnInit() {
    this.sub = this.apply$
        .pipe(
            debounceTime(400),
            distinctUntilChanged((a, b) => a.search === b.search && a.status === b.status)
        )
        .subscribe(({ search, status }) => this.load(search, status))

    this.apply()
  }

  ngOnDestroy() {
    this.sub?.unsubscribe()
  }

  normStatus(s: any): UltraStatus {
    const v = String(s || 'PENDING').toUpperCase()
    if (v === 'IN_REVIEW') return 'IN_REVIEW'
    if (v === 'APPROVED') return 'APPROVED'
    if (v === 'REJECTED') return 'REJECTED'
    return 'PENDING'
  }

  private load(search: string, status: string) {
    this.error = ''
    this.loading = true

    this.ultra.getRequests(search, status).subscribe({
      next: (res) => {
        this.rows = (res || []).map((r) => ({
          ...r,
          status: this.normStatus(r.status),
        }))
      },
      error: (e) => {
        console.error(e)
        this.error = 'Failed to load requests.'
        this.rows = []
        this.loading = false
      },
      complete: () => (this.loading = false),
    })
  }

  apply() {
    const s = this.search.trim()
    const st = this.status
    this.apply$.next({ search: s, status: st })
  }

  trackById(_: number, r: UltraRequest) {
    return r.id
  }

  setStatus(id: number, s: UltraStatus) {
    this.error = ''

    const row = this.rows.find((x) => x.id === id)
    const prev = row?.status

    if (row) row.status = s

    this.ultra.updateStatus(id, s).subscribe({
      next: () => {
      },
      error: (e) => {
        console.error(e)
        this.error = 'Status update failed.'
        if (row && prev) row.status = prev as any
      },
    })
  }

  fmt(d: string | null | undefined): string {
    if (!d) return '-'
    const dt = new Date(d)
    if (Number.isNaN(dt.getTime())) return String(d)
    return dt.toLocaleString()
  }
}
