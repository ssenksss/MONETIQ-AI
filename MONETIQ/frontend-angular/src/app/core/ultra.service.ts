import { Injectable } from '@angular/core'
import { HttpClient, HttpParams } from '@angular/common/http'
import { environment } from '../../environments/environments'
import { Observable, of } from 'rxjs'
import { catchError, timeout } from 'rxjs/operators'

export type UltraStatus = 'PENDING' | 'IN_REVIEW' | 'APPROVED' | 'REJECTED'

export interface UltraRequest {
  id: number
  username: string
  description: string
  requestDate: string
  status: UltraStatus | string
}

@Injectable({ providedIn: 'root' })
export class UltraService {
  private base = `${environment.apiBaseUrl}`

  constructor(private http: HttpClient) {}

  getRequests(search: string, status: string): Observable<UltraRequest[]> {
    let params = new HttpParams()
    if (search) params = params.set('search', search)
    if (status && status !== 'ALL') params = params.set('status', status)

    return this.http
        .get<UltraRequest[]>(`${this.base}/admin/ultra/requests`, { params })
        .pipe(timeout(8000))
  }

  updateStatus(id: number, status: UltraStatus): Observable<any> {
    return this.http
        .patch(`${this.base}/admin/ultra/requests/${id}/status`, { status })
        .pipe(timeout(8000))
  }

  getQueue(): Observable<any[]> {
    return this.http.get<any[]>(`${this.base}/admin/ultra/queue`).pipe(
        timeout(8000),
        catchError((e) => {
          console.error('getQueue failed', e)
          return of([])
        })
    )
  }
}
