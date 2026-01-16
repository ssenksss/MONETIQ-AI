<template>
  <SectionWrapper>
    <GlassCard class="free-tier-card">
      <Badge>Free Tier</Badge>

      <h2>Profile Snapshot</h2>
      <p class="subtitle">
        A quick overview of your Instagram performance.
      </p>



      <div v-else class="no-profile">
        No profile data available yet.
      </div>

      <div class="upgrade-hint">
        Want deeper insights?
        <span @click="goPremium">Upgrade to Premium</span>
      </div>
    </GlassCard>
  </SectionWrapper>
</template>

<script setup lang="ts">
import SectionWrapper from '@/components/layout/SectionWrapper.vue'
import GlassCard from '@/components/ui/GlassCard.vue'
import Badge from '@/components/ui/Badge.vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goPremium = () => {
  router.push('/premium')
}
</script>


<style lang="scss" scoped>
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';

.free-tier-card {
  max-width: 720px;
  margin: 0 auto;
  text-align: center;

  h2 {
    font-family: $font-heading;   // heading font
    font-weight: 700;
    font-size: 2.2rem;
    margin: $space-sm 0;
    @include gradient-text($primary, $secondary);
  }

  .subtitle {
    font-family: $font-heading;   // lagan heading font
    font-weight: 200;
    font-size: 1.1rem;
    color: $text-muted;
    line-height: 1.5;
    margin-bottom: $space-lg;
  }

  .no-profile {
    font-family: $font-heading;
    font-weight: 200;
    color: $text-muted;
    font-size: 1.1rem;
    margin: $space-lg 0;
    line-height: 1.5;
  }

  .upgrade-hint {
    font-family: $font-heading;
    font-weight: 200;
    font-size: 0.95rem;
    color: $text-muted;
    margin-top: $space-lg;
    line-height: 1.5;

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
