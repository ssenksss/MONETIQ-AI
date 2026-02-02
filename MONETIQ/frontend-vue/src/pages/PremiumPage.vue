<template>



  <SectionWrapper>
    <h1 class="title"></h1>

    <div class="controls">
      <input
          v-model="usernameInput"
          class="username-input"
          placeholder="Enter Instagram username (e.g. nina.cooking)"
          @keyup.enter="loadPlan(usernameInput)"
      />

      <button class="btn" @click="loadPlan(usernameInput)" :disabled="loading || !usernameInput.trim()">
        {{ loading ? 'Loading...' : 'Generate' }}
      </button>

      <button class="btn secondary" @click="loadLatest" :disabled="loading">
        Use latest analysis
      </button>
    </div>

    <div class="grid">
      <div>
        <PremiumTier
            :username="plan?.username"
            :createdAt="plan?.createdAt"
            :planText="plan?.planText"
        />
      </div>

      <div class="side">
        <GlassCard class="side-card">
          <h2 class="side-title">30-Day Roadmap</h2>

          <div class="phase-board">
            <div class="phase p1">
              <div class="phase-head">PHASE 1</div>
              <div class="phase-sub">Warm up</div>
              <div class="phase-days">
                <span v-for="d in phaseDays(1)" :key="'p1'+d">Day {{ d }}</span>
              </div>
            </div>

            <div class="phase p2">
              <div class="phase-head">PHASE 2</div>
              <div class="phase-sub">Value delivery</div>
              <div class="phase-days">
                <span v-for="d in phaseDays(2)" :key="'p2'+d">Day {{ d }}</span>
              </div>
            </div>

            <div class="phase p3">
              <div class="phase-head">PHASE 3</div>
              <div class="phase-sub">Open cart</div>
              <div class="phase-days">
                <span v-for="d in phaseDays(3)" :key="'p3'+d">Day {{ d }}</span>
              </div>
            </div>
          </div>

          <h3 class="mini-title">Psychological Buy Buttons</h3>
          <div class="chips">
            <span v-for="b in buyButtons" :key="b" class="chip">{{ b }}</span>
          </div>
        </GlassCard>
      </div>
    </div>

    <SectionWrapper>
      <h1 class="h2">30-Day Execution Plan</h1>

      <p v-if="loading" class="note">
        Loading your plan<span class="dots">{{ dots }}</span>
      </p>

      <div v-else class="timeline">
        <GlassCard v-for="d in planDays" :key="d.day" class="day-card">
          <div class="day-head">
            <div class="left">
              <div class="day-kicker">DAY {{ d.day }}</div>
              <div class="day-title">{{ d.title }}</div>
            </div>
            <span class="badge">{{ d.contentType }}</span>
          </div>

          <ul class="tasks">
            <li v-for="(t, idx) in d.tasks" :key="idx">{{ t }}</li>
          </ul>
        </GlassCard>

        <div v-if="!planDays.length" class="note" style="grid-column: 1 / -1;">
          No plan found. Generate a plan above.
        </div>
      </div>
    </SectionWrapper>
  </SectionWrapper>
</template>

<script setup lang="ts">
import { computed, onMounted, onUnmounted, ref } from 'vue'
import SectionWrapper from '@/components/layout/SectionWrapper.vue'
import GlassCard from '@/components/ui/GlassCard.vue'
import PremiumTier from '@/pages/tiers/PremiumTier.vue'

import { useUserStore } from '@/stores/userStore'
import { useAnalysisStore } from '@/stores/analysisStore'

const userStore = useUserStore()
const analysisStore = useAnalysisStore()

const loading = ref(false)
const dots = ref('')
const usernameInput = ref('')
let interval: ReturnType<typeof setInterval> | null = null

const plan = computed(() => userStore.plan)
const planDays = computed(() => plan.value?.days ?? [])

const buyButtons = [
  'Curiosity', 'Anticipation', 'Authority',
  'Reciprocity', 'Social proof', 'Community',
  'Trust', 'Identification', 'Scarcity',
  'FOMO', 'Urgency'
]
const username = computed(() => plan.value?.username ?? '')
const createdAt = computed(() => plan.value?.createdAt ?? '')
const planText = computed(() => plan.value?.planText ?? '')
const resolveUsername = async (): Promise<string | null> => {
  if (analysisStore.result?.username) return analysisStore.result.username
  try {
    await analysisStore.fetchLatest()
    return analysisStore.result?.username ?? null
  } catch {
    return null
  }
}

const loadPlan = async (uRaw: string) => {
  const u = (uRaw || '').trim().replace(/^@/, '')
  if (!u) return

  loading.value = true
  try {
    await userStore.fetchPremiumPlan(u)
    usernameInput.value = u
  } finally {
    loading.value = false
  }
}

const loadLatest = async () => {
  const u = await resolveUsername()
  if (!u) return
  usernameInput.value = u
  await loadPlan(u)
}

const phaseDays = (phase: 1 | 2 | 3) => {
  if (phase === 1) return [1, 2, 3, 4, 5]
  if (phase === 2) return [6, 7, 8, 9, 10]
  return [11, 12, 13, 14]
}

onMounted(async () => {
  let count = 0
  interval = setInterval(() => {
    count = (count + 1) % 4
    dots.value = '.'.repeat(count)
  }, 450)

  const u = await resolveUsername()
  if (u) {
    usernameInput.value = u
    await loadPlan(u)
  }
})

onUnmounted(() => {
  if (interval) clearInterval(interval)
})
</script>

<style scoped lang="scss">
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';

.controls {
  max-width: 1100px;
  margin: 0 auto $space-lg;
  display: flex;
  gap: $space-sm;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;

  padding: 0.9rem;
  border-radius: 18px;
  border: 1px solid rgba(255,255,255,0.10);
  background: rgba(255,255,255,0.035);
  backdrop-filter: blur(10px);
}

.username-input {
  flex: 1;
  min-width: 320px;
  padding: 0.95rem 1.05rem;
  border-radius: 16px;
  border: 1px solid rgba($text-main, 0.18);
  background: rgba(255,255,255,0.045);
  color: $text-main;
  font-family: $font-mono;

  &:focus {
    outline: none;
    border-color: rgba($primary, 0.9);
    background: rgba(255,255,255,0.08);
  }
}

.btn {
  padding: 0.95rem 1.05rem;
  border-radius: 16px;
  border: none;
  font-weight: 800;
  background: linear-gradient(90deg, $primary, $secondary);
  color: white;
  cursor: pointer;
  transition: $transition;

  &:disabled { opacity: 0.6; cursor: not-allowed; }

  &:hover:not(:disabled) {
    opacity: 0.93;
    transform: translateY(-1px);
  }
}

.btn.secondary {
  background: rgba(255,255,255,0.07);
  border: 1px solid rgba(255,255,255,0.12);
}

.grid {
  max-width: 1100px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: 1.65fr 0.95fr;
  gap: $space-md;
  align-items: start;
}

@media (max-width: 980px) {
  .grid { grid-template-columns: 1fr; }
}

.side {
  position: sticky;
  top: 1.2rem;
}

.side-card {
  padding: 1.1rem 1.1rem 1rem;
  border-radius: 22px;
}

.side-title {
  font-family: $font-heading;
  font-weight: 900;
  margin-bottom: $space-sm;
  font-size: 1.25rem;
}

.phase-board {
  display: grid;
  gap: $space-sm;
  margin-bottom: $space-md;
}

.phase {
  border-radius: 18px;
  padding: 0.95rem 1rem;
  border: 1px solid rgba(255,255,255,0.10);
  background: rgba(255,255,255,0.04);
}

.phase-head {
  font-family: $font-heading;
  font-weight: 950;
  letter-spacing: 0.06em;
}

.phase-sub {
  font-family: $font-heading;
  color: $text-muted;
  font-weight: 200;
  margin-top: 0.15rem;
}

.phase-days {
  margin-top: 0.65rem;
  display: flex;
  flex-wrap: wrap;
  gap: 0.4rem;

  span {
    font-family: $font-mono;
    font-size: 0.78rem;
    color: rgba($text-main, 0.75);
    padding: 0.25rem 0.55rem;
    border-radius: 999px;
    border: 1px solid rgba(255,255,255,0.12);
    background: rgba(0,0,0,0.14);
  }
}

.mini-title {
  font-family: $font-heading;
  font-weight: 850;
  margin: 0.5rem 0 $space-sm;
}

.chips {
  display: flex;
  flex-wrap: wrap;
  gap: 0.45rem;
}

.chip {
  font-family: $font-mono;
  font-size: 0.82rem;
  padding: 0.32rem 0.58rem;
  border-radius: 999px;
  background: rgba(140, 90, 255, 0.12);
  border: 1px solid rgba(140, 90, 255, 0.22);
  color: rgba($text-main, 0.9);
}

.h2 {
  max-width: 1100px;
  margin: $space-xl auto $space-md;
  font-family: $font-heading;
  font-weight: 950;
  font-size: 2.2rem;
  text-align: center;
  @include gradient-text($primary, $secondary);
}

.timeline {
  max-width: 1100px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  gap: $space-md;
}

.day-card {
  padding: 1.05rem 1.1rem;
  border-radius: 22px;
  animation: fadeIn 1s ease forwards;
  @include hover-glow;
}
.day-card:hover {
  transform: translateY(-3px);
}
.side-card {
  padding: 1.1rem 1.1rem 1rem;
  border-radius: 22px;
  animation: fadeIn 1s ease forwards;
  @include hover-glow;
}
.phase {
  border-radius: 18px;
  padding: 0.95rem 1rem;
  border: 1px solid rgba(255,255,255,0.10);
  background: rgba(255,255,255,0.04);
  animation: fadeIn 1s ease forwards;
  @include hover-glow;
}
.day-head {
  display: flex;
  justify-content: space-between;
  align-items: start;
  gap: $space-sm;
  margin-bottom: 0.75rem;
}

.day-kicker {
  font-family: $font-mono;
  color: $text-muted;
  font-size: 0.78rem;
  letter-spacing: 0.14em;
}

.day-title {
  font-family: $font-heading;
  font-weight: 900;
  font-size: 1.05rem;
  margin-top: 0.2rem;
}

.badge {
  font-family: $font-mono;
  font-size: 0.78rem;
  color: rgba($text-main, 0.8);
  padding: 0.25rem 0.55rem;
  border-radius: 999px;
  border: 1px solid rgba(255,255,255,0.12);
  background: rgba(255,255,255,0.05);
}

.tasks {
  list-style: none;
  padding: 0;
  margin: 0;
  display: grid;
  gap: 0.5rem;

  li {
    font-family: $font-mono;
    color: rgba($text-main, 0.78);
    position: relative;
    padding-left: 1rem;

    &::before {
      content: '•';
      position: absolute;
      left: 0;
      color: $primary;
    }
  }
}

.note {
  font-family: $font-heading;
  font-weight: 200;
  color: $text-muted;
  text-align: center;
  margin-top: $space-md;
  font-size: 1rem;
  line-height: 1.5;
}

.dots { display: inline-block; width: 1.2rem; }

</style>
