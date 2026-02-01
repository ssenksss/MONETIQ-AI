<template>
  <SectionWrapper>
    <GlassCard class="hero">
      <div class="hero-top">
        <div class="hero-copy">
          <div class="eyebrow">RESOURCES</div>
          <h1>Everything you need to build a smarter creator workflow</h1>
          <p>
            Guides, playbooks, templates and product updates — designed to help you turn insights into action.
          </p>
        </div>

        <img
            class="hero-logo"
            :src="logoIcon"
            alt="Monetiq"
        />
      </div>

      <div class="hero-actions">
        <div class="search" :class="{ filled: !!query }">
          <span class="icon">⌕</span>
          <input
              v-model="query"
              class="search-input"
              type="text"
              placeholder="Search resources (e.g. bio, engagement, pricing, reels)…"
          />
          <button v-if="query" class="clear" @click="query = ''" type="button">Clear</button>
        </div>

        <div class="chips">
          <button
              v-for="c in categories"
              :key="c"
              class="chip"
              :class="{ active: activeCategory === c }"
              @click="activeCategory = c"
              type="button"
          >
            {{ c }}
          </button>
        </div>
      </div>

      <div class="hero-stats">
        <div class="stat">
          <div class="k">Topics</div>
          <div class="v">{{ categories.length - 1 }}</div>
        </div>
        <div class="stat">
          <div class="k">Resources</div>
          <div class="v">{{ resources.length }}</div>
        </div>
        <div class="stat">
          <div class="k">Result</div>
          <div class="v">{{ filtered.length }}</div>
        </div>
      </div>
    </GlassCard>
  </SectionWrapper>

  <SectionWrapper>
    <div class="section-head">
      <div>
        <h2>Featured</h2>
        <p>Hand-picked resources that deliver the biggest impact.</p>
      </div>
    </div>

    <div class="featured-grid">
      <GlassCard class="featured-card">
        <div class="badge">PLAYBOOK</div>
        <h3>Creator Growth Playbook</h3>
        <p>Practical steps to improve consistency, retention and engagement — without burnout.</p>

        <div class="meta">
          <span>8 min read</span><span class="dot">•</span><span>Updated weekly</span>
        </div>

        <div class="row-actions">
          <PrimaryButton class="small" @click="openExternal('https://help.instagram.com/')">Open</PrimaryButton>
          <button class="ghost" @click="copyText('Creator Growth Playbook')" type="button">Copy title</button>
        </div>
      </GlassCard>

      <GlassCard class="featured-card">
        <div class="badge">TEMPLATE</div>
        <h3>Content Audit Checklist</h3>
        <p>A clean checklist to review what works, what to cut, and what to double-down on.</p>

        <div class="meta">
          <span>Template</span><span class="dot">•</span><span>Coming soon</span>
        </div>

        <div class="row-actions">
          <PrimaryButton class="small" @click="downloadTemplate()">Download</PrimaryButton>
          <button class="ghost" @click="copyText('Content Audit Checklist')" type="button">Copy title</button>
        </div>
      </GlassCard>

      <GlassCard class="featured-card">
        <div class="badge">UPDATES</div>
        <h3>Product & Data Notes</h3>
        <p>What changed, why it matters, and how to interpret metrics for better decisions.</p>

        <div class="meta">
          <span>Changelog</span><span class="dot">•</span><span>Transparent by default</span>
        </div>

        <div class="row-actions">
          <PrimaryButton class="small" @click="scrollTo('#updates')">View</PrimaryButton>
          <button class="ghost" @click="copyText('Product & Data Notes')" type="button">Copy title</button>
        </div>
      </GlassCard>
    </div>
  </SectionWrapper>

  <SectionWrapper>
    <div class="section-head">
      <div>
        <h2>Browse</h2>
        <p>Explore by category — built like a modern docs hub.</p>
      </div>
    </div>

    <div class="grid">
      <GlassCard
          v-for="r in filtered"
          :key="r.id"
          class="card"
          @click="handleOpen(r)"
          @keydown.enter="handleOpen(r)"
          role="button"
          tabindex="0"
      >
        <div class="card-top">
          <div class="pill">{{ r.category }}</div>
          <div class="time">{{ r.meta }}</div>
        </div>

        <h3>{{ r.title }}</h3>
        <p>{{ r.description }}</p>

        <div class="card-bottom">
          <div class="tags">
            <span v-for="t in r.tags" :key="t" class="tag">{{ t }}</span>
          </div>

          <div class="open">
            <span>Open</span>
            <span class="arrow">→</span>
          </div>
        </div>
      </GlassCard>
    </div>

    <div v-if="filtered.length === 0" class="empty">
      No results. Try another keyword or category.
    </div>
  </SectionWrapper>

  <SectionWrapper>
    <div id="updates" class="section-head">
      <div>
        <h2>Product & Data Notes</h2>
        <p>Small updates that help you interpret what you see in the app.</p>
      </div>
    </div>

    <div class="notes">
      <GlassCard class="note">
        <h3>Why numbers may differ from Instagram</h3>
        <p>
          Metrics can vary based on timing, caching and what is publicly visible. Compare trends over time — not a single snapshot.
        </p>
      </GlassCard>

      <GlassCard class="note">
        <h3>Consistency beats spikes</h3>
        <p>
          The biggest improvement usually comes from a repeatable cadence. Focus on clarity, repetition and feedback loops.
        </p>
      </GlassCard>

      <GlassCard class="note">
        <h3>Privacy-first by design</h3>
        <p>
          You should never share your password. The platform focuses on safe flows and transparent data handling.
        </p>
      </GlassCard>
    </div>
  </SectionWrapper>

  <SectionWrapper>
    <GlassCard class="support">
      <div class="support-head">
        <span class="badge">SUPPORT</span>
        <h2>We’re here if you need help</h2>
        <p>
          Get direct help from the MONETIQ team.<br>
          Share context and screenshots —
          we’ll guide you through the issue.
        </p>
      </div>

      <div class="support-grid">
        <div class="support-option">
          <h3>Email support</h3>
          <p>
            Best for account questions, bugs, or anything that needs explanation.
          </p>

          <div class="meta">
            <span class="label">Email</span>
            <span class="value mono">support@monetiq.ai</span>
          </div>

          <div class="meta">
            <span class="label">Typical response</span>
            <span class="value">24–48 hours</span>
          </div>
        </div>

        <div class="support-cta">
          <PrimaryButton class="primary" @click="mailSupport">
            Contact support
          </PrimaryButton>

          <button class="ghost" @click="copyEmail" type="button">
            Copy support email
          </button>

          <div v-if="copied" class="toast">Copied!</div>
        </div>
      </div>
    </GlassCard>
  </SectionWrapper>


  <transition name="toast">
    <div v-if="toast" class="global-toast" role="status" aria-live="polite">
      {{ toast }}
    </div>
  </transition>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import SectionWrapper from '@/components/layout/SectionWrapper.vue'
import GlassCard from '@/components/ui/GlassCard.vue'
import PrimaryButton from '@/components/ui/PrimaryButton.vue'
import logoIcon from '@/assets/logo-icon.png'



type Resource = {
  id: string
  title: string
  description: string
  category: string
  tags: string[]
  meta: string
  url?: string
  kind: 'external' | 'download' | 'internal'
  internalAction?: 'updates'
}

const query = ref('')
const categories = ['All', 'Guides', 'Playbooks', 'Templates', 'Pricing', 'Growth', 'Product']
const activeCategory = ref('All')

const resources: Resource[] = [
  {
    id: 'g-1',
    title: 'Bio & CTA: quick wins',
    description: 'Simple improvements to communicate value and drive clicks without looking spammy.',
    category: 'Guides',
    tags: ['bio', 'cta', 'profile'],
    meta: '6 min read',
    kind: 'external',
    url: 'https://help.instagram.com/',
  },
  {
    id: 'g-2',
    title: 'Engagement: what actually moves the needle',
    description: 'A practical view of saves, shares, comments — and what to do with them.',
    category: 'Growth',
    tags: ['engagement', 'signals', 'strategy'],
    meta: '7 min read',
    kind: 'external',
    url: 'https://help.instagram.com/',
  },
  {
    id: 'p-1',
    title: 'Pricing your offers (creator-friendly)',
    description: 'A simple way to think about packaging, pricing, and value communication.',
    category: 'Pricing',
    tags: ['pricing', 'offers', 'value'],
    meta: '9 min read',
    kind: 'external',
    url: 'https://help.instagram.com/',
  },
  {
    id: 't-1',
    title: 'Content Audit Checklist (template)',
    description: 'Review what’s working, what to remove, and what to double-down on.',
    category: 'Templates',
    tags: ['audit', 'template', 'workflow'],
    meta: 'Template',
    kind: 'download',
  },
  {
    id: 'pl-1',
    title: 'Creator Growth Playbook',
    description: 'Repeatable habits for consistent growth — built for real schedules.',
    category: 'Playbooks',
    tags: ['playbook', 'cadence', 'systems'],
    meta: '8 min read',
    kind: 'external',
    url: 'https://help.instagram.com/',
  },
  {
    id: 'u-1',
    title: 'Product & Data Notes',
    description: 'What changed and how to interpret metrics inside the app.',
    category: 'Product',
    tags: ['updates', 'metrics', 'notes'],
    meta: 'Changelog',
    kind: 'internal',
    internalAction: 'updates',
  },
]

const filtered = computed(() => {
  const q = query.value.trim().toLowerCase()
  const cat = activeCategory.value

  return resources.filter((r) => {
    const catOk = cat === 'All' || r.category === cat
    if (!catOk) return false
    if (!q) return true

    const hay = `${r.title} ${r.description} ${r.category} ${r.tags.join(' ')}`.toLowerCase()
    return hay.includes(q)
  })
})

const openExternal = (url: string) => window.open(url, '_blank', 'noopener,noreferrer')

const scrollTo = (hash: string) => {
  const el = document.querySelector(hash)
  if (el) el.scrollIntoView({ behavior: 'smooth', block: 'start' })
}

const handleOpen = (r: Resource) => {
  if (r.kind === 'external' && r.url) openExternal(r.url)
  else if (r.kind === 'download') downloadTemplate()
  else if (r.kind === 'internal' && r.internalAction === 'updates') scrollTo('#updates')
}

const toast = ref<string | null>(null)
const showToast = (msg: string) => {
  toast.value = msg
  setTimeout(() => (toast.value = null), 1700)
}

const downloadTemplate = () => {
  showToast('Template is coming soon ')
}

const mailSupport = () => {
  const subject = encodeURIComponent('Support')
  const body = encodeURIComponent('Hi team,\n\nI need help with...\n\nThanks!')
  window.location.href = `mailto:support@monetiq.ai?subject=${subject}&body=${body}`
}

const copied = ref(false)
const copyEmail = async () => {
  try {
    await navigator.clipboard.writeText('support@monetiq.ai')
    copied.value = true
    showToast('Copied!')
    setTimeout(() => (copied.value = false), 900)
  } catch {
    window.prompt('Copy email:', 'support@monetiq.ai')
  }
}

const copyText = async (text: string) => {
  try {
    await navigator.clipboard.writeText(text)
    showToast('Copied!')
  } catch {
  }
}
</script>

<style scoped lang="scss">
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';


:deep(.glass-card) {

}

.hero,
.featured-card,
.card,
.note,
.support {
  position: relative;
  overflow: hidden;
}

.hero::before,
.featured-card::before,
.card::before,
.note::before,
.support::before {
  content: '';
  position: absolute;
  inset: -1px;
  background: radial-gradient(600px 260px at 12% 10%, rgba(255,255,255,0.09), transparent 60%),
  radial-gradient(420px 220px at 90% 0%, rgba(255,255,255,0.06), transparent 55%);
  pointer-events: none;
  opacity: 0.9;
}

.card:focus-visible,
.chip:focus-visible,
.clear:focus-visible,
.ghost:focus-visible {
  outline: 2px solid rgba(255, 255, 255, 0.35);
  outline-offset: 3px;
}


.hero {
  max-width: 1100px;
  margin: 2rem auto;
  padding: clamp(1.2rem, 2.2vw, 1.8rem);
  border-radius: $radius-lg;
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(14px);
  box-shadow: $shadow-soft;
  border: 1px solid rgba(255, 255, 255, 0.08);

  .hero-top {
    display: grid;
    grid-template-columns: 1fr auto;
    align-items: center;
    gap: 1.2rem;

    @media (max-width: 900px) {
      grid-template-columns: 1fr;
      text-align: center;
    }
  }

  .hero-copy {
    max-width: 860px;
  }

  .hero-logo {
    width: 200px;
    height: 200px;
    object-fit: contain;
    opacity: 0.48;
    filter: saturate(1.3) contrast(1);
    pointer-events: none;

    @media (max-width: 900px) {
      margin: 0 auto;
    }
  }

  .eyebrow {
    font-size: 0.75rem;
    letter-spacing: 0.18em;
    text-transform: uppercase;
    opacity: 0.72;
    margin-bottom: 0.65rem;
  }

  h1 {
    font-family: $font-heading;
    font-weight: 800;
    font-size: clamp(1.9rem, 3.2vw, 2.55rem);
    line-height: 1.12;
    margin: 0 0 0.65rem;
    @include gradient-text($primary, $secondary);
    text-shadow: 0 0 24px rgba(255, 255, 255, 0.08);
  }

  p {
    margin: 0;
    color: $text-muted;
    font-weight: 200;
    line-height: 1.75;
    font-size: 1.03rem;
  }

  .hero-actions {
    margin-top: 1.15rem;
    display: grid;
    grid-template-columns: 1.25fr 1fr;
    gap: 0.9rem;

    @media (max-width: 900px) {
      grid-template-columns: 1fr;
    }
  }

  .hero-stats {
    margin-top: 1rem;
    display: grid;
    grid-template-columns: repeat(3, minmax(0, 1fr));
    gap: 0.75rem;

    @media (max-width: 520px) {
      grid-template-columns: 1fr;
    }

    .stat {
      border-radius: 16px;
      padding: 0.9rem 1rem;
      background: rgba(255, 255, 255, 0.04);
      border: 1px solid rgba(255, 255, 255, 0.07);

      .k {
        font-size: 0.8rem;
        letter-spacing: 0.12em;
        text-transform: uppercase;
        opacity: 0.7;
        margin-bottom: 0.25rem;
      }
      .v {
        font-family: $font-heading;
        font-weight: 600;
        font-size: 1.3rem;
        color:$secondary;
      }
    }
  }
}

.search {
  display: flex;
  align-items: center;
  gap: 0.7rem;
  padding: 0.85rem 1rem;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.06);
  border: 1px solid rgba(255, 255, 255, 0.10);
  transition: border-color 0.15s ease, background 0.15s ease, transform 0.15s ease;

  &:hover { transform: translateY(-1px); }
  &.filled { border-color: rgba(255,255,255,0.18); }

  .icon {
    opacity: 0.75;
    font-size: 0.95rem;
    user-select: none;
  }

  .search-input {
    flex: 1;
    background: transparent;
    border: none;
    outline: none;
    color: white;
    font-weight: 200;
    font-size: 1rem;

    &::placeholder {
      opacity: 0.55;
    }
  }

  .clear {
    background: transparent;
    border: 1px solid rgba(255, 255, 255, 0.14);
    color: rgba(255, 255, 255, 0.9);
    border-radius: 999px;
    padding: 0.35rem 0.7rem;
    cursor: pointer;
    font-weight: 200;
    transition: background 0.15s ease;

    &:hover {
      background: rgba(255, 255, 255, 0.06);
    }
  }
}

.chips {
  display: flex;
  flex-wrap: wrap;
  gap: 0.55rem;
  justify-content: flex-end;

  @media (max-width: 900px) { justify-content: flex-start; }

  .chip {
    border: 1px solid rgba(255, 255, 255, 0.12);
    background: rgba(255, 255, 255, 0.03);
    color: rgba(255, 255, 255, 0.92);
    border-radius: 999px;
    padding: 0.55rem 0.85rem;
    font-weight: 200;
    cursor: pointer;
    transition: transform 0.15s ease, background 0.15s ease, border-color 0.15s ease;

    &:hover {
      transform: translateY(-1px);
      background: rgba(255, 255, 255, 0.06);
    }

    &.active {
      border-color: rgba(255, 255, 255, 0.26);
      background: rgba(255, 255, 255, 0.10);
      box-shadow: 0 0 0 6px rgba(255, 255, 255, 0.03);
    }
  }
}

.section-head {
  max-width: 1100px;
  margin: 0 auto 0.9rem;

  h2 {
    background: linear-gradient(90deg, $secondary, $secondary);
    -webkit-background-clip: text;
    color: transparent;
    font-family: $font-heading;
    font-size: 1.65rem;
    margin: 0 0 0.25rem;
  }

  p {
    margin: 0;
    color: $text-muted;
    font-weight: 200;
    line-height: 1.6;
  }
}


.featured-grid {
  max-width: 1100px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 0.9rem;

  @media (max-width: 1100px) {
    grid-template-columns: 1fr;
  }
}

.featured-card {
  padding: 1.25rem 1.25rem 1.15rem;
  border-radius: $radius-lg;
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(12px);
  box-shadow: $shadow-soft;
  border: 1px solid rgba(255, 255, 255, 0.08);

  transition: transform 0.18s ease, background 0.18s ease, border-color 0.18s ease;

  &:hover {
    transform: translateY(-2px);
    background: rgba(255, 255, 255, 0.07);
    border-color: rgba(255, 255, 255, 0.12);
  }

  .badge {
    display: inline-flex;
    align-items: center;
    gap: 0.5rem;
    font-size: 0.75rem;
    letter-spacing: 0.14em;
    text-transform: uppercase;
    opacity: 0.75;
    margin-bottom: 0.55rem;
  }

  h3 {
    @include gradient-text( $primary, $secondary);

    font-family: $font-heading;
    margin: 0 0 0.5rem;
    font-size: 1.35rem;
    letter-spacing: 0.01em;
  }

  p {
    margin: 0 0 0.9rem;
    color: $text-muted;
    font-weight: 200;
    line-height: 1.75;
  }

  .meta {
    display: flex;
    gap: 0.55rem;
    align-items: center;
    opacity: 0.72;
    font-weight: 200;
    margin-bottom: 0.95rem;

    .dot { opacity: 0.6; }
  }

  .row-actions {
    display: flex;
    gap: 0.6rem;
    align-items: center;
    flex-wrap: wrap;

    .small {
      padding: 0.62rem 1rem;
    }

    .ghost {
      background: transparent;
      border: 1px solid rgba(255, 255, 255, 0.14);
      border-radius: 999px;
      padding: 0.55rem 0.9rem;
      color: rgba(255, 255, 255, 0.92);
      cursor: pointer;
      font-weight: 200;
      transition: background 0.15s ease, transform 0.15s ease;

      &:hover {
        background: rgba(255, 255, 255, 0.06);
        transform: translateY(-1px);
      }
    }
  }
}


.grid {
  max-width: 1100px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 0.9rem;

  @media (max-width: 1100px) {
    grid-template-columns: 1fr;
  }
}

.card {
  padding: 1.2rem;
  border-radius: $radius-lg;
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(12px);
  box-shadow: $shadow-soft;
  border: 1px solid rgba(255, 255, 255, 0.08);
  transition: transform 0.18s ease, background 0.18s ease, border-color 0.18s ease;
  cursor: pointer;
  outline: none;

  &:hover {
    transform: translateY(-2px);
    background: rgba(255, 255, 255, 0.07);
    border-color: rgba(255, 255, 255, 0.12);
  }

  .card-top {
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 0.75rem;
    margin-bottom: 0.7rem;

    .pill {
      font-size: 0.75rem;
      letter-spacing: 0.12em;
      text-transform: uppercase;
      opacity: 0.78;
    }

    .time {
      opacity: 0.60;
      font-weight: 200;
      font-size: 0.95rem;
    }
  }

  h3 {
    @include gradient-text( $primary, $secondary);

    font-family: $font-heading;
    margin: 0 0 0.55rem;
    font-size: 1.22rem;
    letter-spacing: 0.01em;
  }

  p {
    margin: 0 0 0.95rem;
    color: $text-muted;
    font-weight: 200;
    line-height: 1.75;
  }

  .card-bottom {
    display: flex;
    align-items: flex-end;
    justify-content: space-between;
    gap: 0.9rem;

    .tags {
      display: flex;
      flex-wrap: wrap;
      gap: 0.35rem;
      min-height: 1.9rem;
      align-content: flex-start;
    }

    .tag {
      border: 1px solid rgba(255, 255, 255, 0.12);
      border-radius: 999px;
      padding: 0.34rem 0.6rem;
      opacity: 0.82;
      font-size: 0.85rem;
      font-weight: 200;
      background: rgba(255, 255, 255, 0.03);
    }

    .open {
      display: flex;
      gap: 0.4rem;
      align-items: center;
      opacity: 0.86;
      font-weight: 200;
      white-space: nowrap;

      .arrow { opacity: 0.7; }
    }
  }
}


.notes {
  max-width: 1100px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 0.9rem;

  @media (max-width: 1100px) {
    grid-template-columns: 1fr;
  }
}

.note {
  padding: 1.25rem;
  border-radius: $radius-lg;
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(12px);
  box-shadow: $shadow-soft;
  border: 1px solid rgba(255, 255, 255, 0.08);

  h3 {
    @include gradient-text( $primary, $secondary);

    font-family: $font-heading;
    margin: 0 0 0.55rem;
    font-size: 1.15rem;
  }

  p {
    margin: 0;
    color: $text-muted;
    font-weight: 200;
    line-height: 1.75;
  }
}


.support {
  max-width: 1100px;
  margin: 0 auto;
  padding: 1.35rem;
  border-radius: $radius-lg;
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(12px);
  box-shadow: $shadow-soft;
  border: 1px solid rgba(255, 255, 255, 0.08);

  display: grid;
  grid-template-columns: 1.35fr 0.65fr;
  gap: 1rem;

  @media (max-width: 900px) {
    grid-template-columns: 1fr;
  }

  .support-left {
    h2 {
      font-family: $font-heading;
      margin: 0 0 0.55rem;
      font-size: 1.65rem;
    }

    p {
      margin: 0 0 0.95rem;
      color: $text-muted;
      font-weight: 200;
      line-height: 1.75;
    }

    .support-meta {
      display: grid;
      gap: 0.55rem;

      .row {
        display: flex;
        justify-content: space-between;
        gap: 0.9rem;
        padding: 0.55rem 0.65rem;
        border-radius: 14px;
        border: 1px solid rgba(255, 255, 255, 0.07);
        background: rgba(255, 255, 255, 0.03);
      }

      .k { opacity: 0.7; }
      .v { opacity: 0.92; font-weight: 200; }
      .mono { font-family: $font-heading; font-weight: 300; }
    }
  }

  .support-right {
    display: flex;
    flex-direction: column;
    justify-content: center;
    gap: 0.65rem;

    .ghost {
      background: transparent;
      border: 1px solid rgba(255, 255, 255, 0.14);
      border-radius: 999px;
      padding: 0.72rem 1rem;
      color: rgba(255, 255, 255, 0.92);
      cursor: pointer;
      font-weight: 200;
      transition: background 0.15s ease, transform 0.15s ease;

      &:hover {
        background: rgba(255, 255, 255, 0.06);
        transform: translateY(-1px);
      }

      &.big {
        padding: 0.78rem 1rem;
      }
    }

    .toast {
      opacity: 0.75;
      font-size: 0.9rem;
      text-align: center;
      margin-top: 0.15rem;
    }
  }
}


.global-toast {
  position: fixed;
  left: 50%;
  bottom: 22px;
  transform: translateX(-50%);
  z-index: 50;

  padding: 0.7rem 1rem;
  border-radius: 999px;
  background: rgba(10, 10, 14, 0.75);
  border: 1px solid rgba(255, 255, 255, 0.16);
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.35);
  backdrop-filter: blur(14px);

  color: rgba(255, 255, 255, 0.92);
  font-weight: 200;
}

.toast-enter-active,
.toast-leave-active {
  transition: opacity 0.18s ease, transform 0.18s ease;
}
.toast-enter-from,
.toast-leave-to {
  opacity: 0;
  transform: translateX(-50%) translateY(10px);
}

.empty {
  max-width: 1100px;
  margin: 0.9rem auto 0;
  opacity: 0.75;
  font-weight: 200;
}



.support {
  max-width: 1100px;
  margin: 0 auto;
  padding: 2rem;
  border-radius: $radius-lg;

}

.support-head {
  max-width: 540px;
  margin-bottom: 1.6rem;

  .badge {
    display: inline-block;
    font-size: 0.75rem;
    letter-spacing: 0.14em;
    text-transform: uppercase;
    opacity: 0.8;
    margin-bottom: 0.4rem;
  }

  h2 {
    font-family: $font-heading;
    font-size: 1.9rem;
    margin: 0 0 0.5rem;
    @include gradient-text($primary, $secondary);
  }

  p {
    font-weight: 200;
    line-height: 1.7;
  }
}

.support-grid {
  display: grid;
  grid-template-columns: 1fr 0.6fr;
  gap: 1.5rem;

  @media (max-width: 900px) {
    grid-template-columns: 1fr;
  }
}

.support-option {
  padding: 1.4rem;
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.10);

  h3 {
    @include gradient-text($primary, $secondary);

    font-family: $font-heading;
    margin: 0 0 0.4rem;
    font-size: 1.2rem;
  }

  p {
    margin: 0 0 1rem;
    font-weight: 200;
    line-height: 1.6;
  }

  .meta {
    display: flex;
    justify-content: space-between;
    padding: 0.55rem 0;
    border-bottom: 1px solid rgba(255, 255, 255, 0.08);

    &:last-child {
      border-bottom: none;
    }

    .label {
      opacity: 0.65;
      font-size: 0.9rem;
      padding-top: 2px;
    }

    .value {
      font-weight: 300;
      color: $text-muted;

    }

    .mono {
      padding-left: 20px;
      font-family: $font-heading;
      color: $secondary;

    }
  }
}

.support-cta {
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 0.75rem;

  .primary {
    width: 100%;
  }

  .ghost {
    background: transparent;
    color: $text-muted;

    border: 1px solid rgba(255, 255, 255, 0.16);
    border-radius: 999px;
    padding: 0.8rem 1rem;
    cursor: pointer;
    transition: background 0.15s ease;

    &:hover {
      background: rgba(255, 255, 255, 0.08);
    }
  }

  .toast {
    text-align: center;
    font-size: 0.9rem;
    opacity: 0.8;
  }
}

</style>
