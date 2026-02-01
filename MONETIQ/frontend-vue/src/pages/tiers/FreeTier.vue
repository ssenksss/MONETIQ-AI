<template>
  <SectionWrapper>
    <GlassCard class="free-tier-card">
      <Badge>Free Tier</Badge>

      <h2>Profile Snapshot</h2>
      <p class="subtitle">A quick overview of your Instagram performance.</p>

      <div class="input-wrap">
        <AnalysisInput />
      </div>

      <div v-if="hasLatest" class="profile-data">
        <p><strong>Username:</strong> {{ analysisStore.result?.username }}</p>

        <ul>
          <li v-for="(s, i) in (analysisStore.result?.suggestions ?? [])" :key="i">
            {{ s }}
          </li>
        </ul>

        <p class="analysis-date">
          Analysis Date: {{ formattedDate }}
        </p>
      </div>

      <div v-else class="empty-state">
        No analysis yet. Enter a username above to run your first analysis.
      </div>

      <div v-if="analysisStore.history.length" class="history">
        <h3>History</h3>
        <ul>
          <li v-for="(h, idx) in analysisStore.history" :key="idx">
            <span class="h-user">{{ h.username }}</span>
            <span class="h-date">
              {{ h.analysisDate ? new Date(h.analysisDate).toLocaleString() : '' }}
            </span>
          </li>
        </ul>
      </div>

      <div class="upgrade-hint" v-if="userStore.role === 'FREE'">
        Want deeper insights?
        <span @click="goPremium">Upgrade to Premium</span>
      </div>
    </GlassCard>
  </SectionWrapper>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import { useAnalysisStore } from '@/stores/analysisStore'

import SectionWrapper from '@/components/layout/SectionWrapper.vue'
import GlassCard from '@/components/ui/GlassCard.vue'
import Badge from '@/components/ui/Badge.vue'
import AnalysisInput from '@/components/analysis/AnalysisInput.vue'

const userStore = useUserStore()
const analysisStore = useAnalysisStore()
const router = useRouter()

onMounted(async () => {
  try {
    await analysisStore.fetchLatest()
    await analysisStore.fetchHistory()
  } catch (e) {
    console.warn('[FreeTier] load latest/history failed', e)
  }
})

const hasLatest = computed(() => analysisStore.result !== null)

const formattedDate = computed(() => {
  if (!analysisStore.result?.analysisDate) return ''
  return new Date(analysisStore.result.analysisDate).toLocaleString()
})

const goPremium = () => router.push('/pricing')
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';

.free-tier-card {
  max-width: 720px;
  margin: 2rem auto;
  text-align: center;
  padding: $space-lg;

  h2 {
    font-family: $font-heading;
    font-weight: 700;
    font-size: 2.2rem;
    margin: $space-sm 0;
    @include gradient-text($primary, $secondary);
  }

  .subtitle {
    font-family: $font-heading;
    font-weight: 200;
    font-size: 1.1rem;
    color: $text-muted;
    line-height: 1.5;
    margin-bottom: $space-md;
    padding-bottom: 1rem;
  }

  .input-wrap {
    margin-bottom: $space-md;
  }

  .empty-state {
    margin-top: $space-md;
    font-family: $font-heading;
    font-weight: 200;
    color: $text-muted;
  }

  .profile-data {
    font-family: $font-heading;
    margin-top: $space-md;

    p {
      margin: $space-xs 0;
      font-size: 1rem;
      color: $text-main;
      @include gradient-text($primary, $secondary);

      &.analysis-date {
        font-size: 0.9rem;
        color: $text-muted;
        margin-top: $space-sm;
        text-align: right;
      }
    }

    ul {
      list-style-type: none;
      padding-left: 0;
      margin: $space-sm 0;
      display: flex;
      flex-direction: column;
      gap: $space-xs;

      li {
        position: relative;
        padding-left: 1.2rem;
        font-family: $font-mono;
        font-size: 0.95rem;
        color: $text-muted;

        &::before {
          content: '•';
          position: absolute;
          left: 0;
          color: $primary;
        }
      }
    }
  }

  .history {
    margin-top: $space-lg;
    text-align: left;

    h3 {
      font-family: $font-heading;
      font-weight: 600;
      margin-bottom: $space-sm;
    }

    ul {
      list-style: none;
      padding: 0;
      margin: 0;
      display: flex;
      flex-direction: column;
      gap: $space-xs;

      li {
        display: flex;
        justify-content: space-between;
        gap: $space-sm;
        padding: $space-xs $space-sm;
        border-radius: $radius-sm;
        background: rgba(255,255,255,0.04);
      }

      .h-user {
        font-family: $font-mono;
        color: $text-main;
      }

      .h-date {
        font-family: $font-mono;
        color: $text-muted;
        font-size: 0.85rem;
      }
    }
  }

  .upgrade-hint {
    font-family: $font-heading;
    font-weight: 200;
    font-size: 0.95rem;
    color: $text-muted;
    margin-top: $space-lg;

    span {
      margin-left: 0.25rem;
      color: $primary;
      font-weight: 600;
      cursor: pointer;
      transition: $transition;

      &:hover {
        text-decoration: underline;
      }
    }
  }
}
</style>
