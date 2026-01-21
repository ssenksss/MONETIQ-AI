<template>
  <SectionWrapper>
    <GlassCard class="free-tier-card">
      <Badge>Free Tier</Badge>

      <h2>Profile Snapshot</h2>
      <p class="subtitle">A quick overview of your Instagram performance.</p>

      <div v-if="!hasProfile">
        <AnalysisInput @analysis-complete="onAnalysisComplete" />
      </div>

      <div v-else class="profile-data">
        <p><strong>Username:</strong> {{ userStore.username }}</p>

        <ul>
          <li v-for="s in userStore.profile?.suggestions" :key="s.text">
            {{ s.text }}
          </li>
        </ul>


        <p class="analysis-date">
          Analysis Date: {{ formattedDate }}
        </p>
      </div>

      <div class="upgrade-hint">
        Want deeper insights?
        <span @click="goPremium">Upgrade to Premium</span>
      </div>
    </GlassCard>
  </SectionWrapper>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore'

import SectionWrapper from '@/components/layout/SectionWrapper.vue'
import GlassCard from '@/components/ui/GlassCard.vue'
import Badge from '@/components/ui/Badge.vue'
import AnalysisInput from '@/components/analysis/AnalysisInput.vue'

const userStore = useUserStore()
const router = useRouter()

const hasProfile = computed(() => userStore.profile !== null)

const formattedDate = computed(() => {
  if (!userStore.profile?.analysisDate) return ''
  return new Date(userStore.profile.analysisDate).toLocaleString()
})
const onAnalysisComplete = (payload: {
  analysisDate: string
  suggestions: { text: string }[]
}) => {
  userStore.setProfile(payload)
}

const goPremium = () => router.push('/premium')
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
