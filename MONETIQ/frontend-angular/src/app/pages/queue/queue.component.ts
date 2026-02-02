import { Component, OnInit, ChangeDetectorRef } from '@angular/core'
import { CommonModule } from '@angular/common'
import { UltraService } from '../../core/ultra.service'
import { finalize } from 'rxjs/operators'

@Component({
    selector: 'app-queue',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './queue.component.html',
    styleUrls: ['./queue.component.scss'],
})
export class QueueComponent implements OnInit {
    loading = false
    items: any[] = []
    error = ''

    constructor(private ultra: UltraService, private cdr: ChangeDetectorRef) {}

    ngOnInit() {
        this.refresh()
    }

    refresh() {
        this.error = ''
        this.loading = true

        this.ultra
            .getQueue()
            .pipe(
                finalize(() => {
                    this.loading = false
                    this.cdr.detectChanges()
                })
            )
            .subscribe({
                next: (x) => {
                    this.items = (x || []).slice().reverse()
                    this.cdr.detectChanges()
                },
                error: (e) => {
                    console.error(e)
                    this.error = 'Failed to load Redis queue'
                    this.items = []
                    this.cdr.detectChanges()
                },
            })
    }

    lines(item: any): string[] {
        return String(item ?? '')
            .split('\\n')
            .map((s) => s.trim())
            .filter((s) => s.length > 0)
    }

}
