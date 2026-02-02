<template>
  <SectionWrapper class="tier-page">
    <div class="header">
      <h1 class="title">Monetization Plan</h1>
    </div>

    <GlassCard class="doc-card">
      <div class="doc-head">
        <div>
          <div class="kicker">MONETIQ PREMIUM</div>
          <p class="sub" v-if="username">
            Plan for <span>@{{ username }}</span>
          </p>
        </div>

        <div class="meta" v-if="createdAt">
          Generated: <span>{{ new Date(createdAt).toLocaleString() }}</span>
        </div>
      </div>

      <div class="divider"></div>

      <div v-if="planHtml" class="doc" v-html="planHtml"></div>

      <div v-else class="empty">
        No monetization plan yet. Generate a plan in Premium page.
      </div>
    </GlassCard>

    <div class="actions">
      <button class="btn secondary" @click="copyToClipboard" :disabled="!planText">
        Copy
      </button>

      <button class="btn" @click="downloadDocx" :disabled="!username">
        Download DOCX
      </button>

      <button class="btn" @click="downloadPdf" :disabled="!username">
        Download PDF
      </button>
    </div>
  </SectionWrapper>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import SectionWrapper from '@/components/layout/SectionWrapper.vue'
import GlassCard from '@/components/ui/GlassCard.vue'

const props = defineProps<{
  planText?: string
  username?: string
  createdAt?: string
}>()

const escapeHtml = (s: string) =>
    s.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;')

const isHeading = (line: string) => {
  const l = line.trim()
  return (
      /^#{1,3}\s+/.test(l) ||
      /^\d+\)\s+/.test(l) ||
      /^[A-Z0-9][A-Z0-9\s\-—:()]{6,}$/.test(l) ||
      /^[A-Za-z].+:\s*$/.test(l)
  )
}

const isBullet = (line: string) => /^[-•*]\s+/.test(line.trim())

const cleanHeading = (line: string) => line.trim().replace(/^#{1,3}\s+/, '')
const cleanBullet = (line: string) => line.trim().replace(/^[-•*]\s+/, '')

const planHtml = computed(() => {
  const raw = (props.planText || '').trim()
  if (!raw) return ''

  const lines = raw.split('\n').map(l => l.replace(/\r/g, ''))
  const out: string[] = []
  let listOpen = false

  const closeList = () => {
    if (listOpen) {
      out.push('</ul>')
      listOpen = false
    }
  }

  for (const line of lines) {
    const t = line.trim()
    if (!t) {
      closeList()
      continue
    }

    if (isHeading(t)) {
      closeList()
      out.push(`<h3>${escapeHtml(cleanHeading(t))}</h3>`)
      continue
    }

    if (isBullet(t)) {
      if (!listOpen) {
        out.push('<ul>')
        listOpen = true
      }
      out.push(`<li>${escapeHtml(cleanBullet(t))}</li>`)
      continue
    }

    closeList()
    out.push(`<p>${escapeHtml(t)}</p>`)
  }

  closeList()
  return out.join('\n')
})

const copyToClipboard = async () => {
  if (!props.planText) return
  await navigator.clipboard.writeText(props.planText)
}

const downloadDocx = () => {
  if (!props.username) return
  window.open(
      `http://localhost:8080/api/premium/download/docx?username=${encodeURIComponent(props.username)}`,
      '_blank'
  )
}

const downloadPdf = () => {
  if (!props.username) return
  window.open(
      `http://localhost:8080/api/premium/download/pdf?username=${encodeURIComponent(props.username)}`,
      '_blank'
  )
}
</script>

<style scoped lang="scss">
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';

.tier-page {
  padding-top: 0.5rem;
}

.header {
  max-width: 980px;
  margin: 0 auto $space-md;
  display: flex;
  justify-content: center;
}

.title {
  font-family: $font-heading;
  font-weight: 950;
  font-size: 3.1rem;
  line-height: 1.05;
  margin: $space-lg 0 0;
  text-align: center;
  @include gradient-text($primary, $secondary);
}
.doc-card {
  max-width: 980px;
  margin: $space-md auto 0;
  padding: 1.4rem 1.6rem;
  border-radius: 22px;
  animation: fadeIn 1s ease forwards;

}

.doc-card {
  max-width: 980px;
  margin: 0 auto;
  padding: 1.35rem 1.55rem;
  border-radius: 24px;


  background: rgba(255,255,255,0.055);
  border: 1px solid rgba(255,255,255,0.12);
  box-shadow: 0 18px 60px rgba(0,0,0,0.25);
}

.doc {
  max-width: 760px;
  margin: 0 auto;
  padding: 0.25rem 0.25rem 0.5rem;
}

.kicker {
  font-family: $font-mono;
  color: rgba($text-muted, 0.9);
  letter-spacing: 0.18em;
  font-size: 0.76rem;
  text-transform: uppercase;
}

.sub {
  margin: 0.35rem 0 0;
  font-family: $font-heading;
  font-weight: 250;
  font-size: 1.2rem;
  color: rgba($text-main, 0.92);

  span {
    font-weight: 800;
    @include gradient-text($primary, $secondary);
  }
}

.meta {
  font-family: $font-mono;
  color: rgba($text-muted, 0.92);
  font-size: 0.85rem;
  text-align: right;
  margin-top: 0.15rem;

  span {
    color: rgba($text-main, 0.92);
  }
}

.divider {
  height: 1px;
  width: 100%;
  margin: 1rem 0 1.15rem;
  background: linear-gradient(
          90deg,
          rgba(255,255,255,0.06),
          rgba(255,255,255,0.14),
          rgba(255,255,255,0.06)
  );
}

.doc {
  line-height: 1.8;
  font-family: $font-heading;
  color: rgba($text-main, 0.92);
}

.doc :deep(h3) {
  font-family: $font-heading;
  font-weight: 950;
  font-size: 1.28rem;
  margin: 1.25rem 0 0.6rem;
  color: rgba($text-main, 0.98);
}

.doc :deep(p) {
  margin: 0.6rem 0;
  font-family: $font-heading;
  font-weight: 250;
  font-size: 1.04rem;
  color: rgba($text-main, 0.88);
}



.doc :deep(ul) {
  margin: 0.65rem 0 1rem;
  padding: 0.85rem 1rem 0.85rem 1.15rem;
  border-radius: 16px;
  border: 1px solid rgba(255,255,255,0.08);
  background: rgba(255,255,255,0.04);
}

.doc :deep(li) {
  margin: 0.4rem 0;
  font-family: $font-mono;
  font-size: 0.95rem;
  color: rgba($text-main, 0.86);
}


.empty {
  font-family: $font-heading;
  font-weight: 200;
  color: $text-muted;
  text-align: center;
  padding: 1.6rem 0;
}


.actions {
  max-width: 980px;
  margin: $space-md auto 0;
  display: flex;
  justify-content: center;
  gap: $space-sm;
  flex-wrap: wrap;
}


.btn {
  padding: 0.85rem 1.15rem;
  border-radius: 16px;
  border: none;
  font-weight: 800;
  background: linear-gradient(90deg, $primary, $secondary);
  color: white;
  cursor: pointer;
  transition: $transition;
  box-shadow: 0 10px 30px rgba(0,0,0,0.18);

  &:disabled {
    opacity: 0.55;
    cursor: not-allowed;
    box-shadow: none;
  }

  &:hover:not(:disabled) {
    transform: translateY(-1px);
    opacity: 0.92;
  }
}

.btn.secondary {
  background: rgba(255,255,255,0.08);
  border: 1px solid rgba(255,255,255,0.12);
  box-shadow: none;
}
</style>
