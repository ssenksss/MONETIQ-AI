<template>
  <FreeTier />
  <SectionWrapper>
    <h1>Free Tier Insights</h1>
    <div class="insights-grid">
      <InsightCard
          v-for="(insight, i) in insights"
          :key="i"
          :title="insight.title"
          :description="insight.description"
          :icon="insight.icon"
      />
    </div>
  </SectionWrapper>
</template>


<script setup lang="ts">
import SectionWrapper from '@/components/layout/SectionWrapper.vue'
import InsightCard from '@/components/landing/InsightCard.vue'
import FreeTier from '@/pages/tiers/FreeTier.vue'


const insights = [
  { title: 'Engagement', description: 'Likes, comments, shares analysis.', icon: '📊' },
  { title: 'Growth', description: 'Follower trends & potential.', icon: '📈' },
  { title: 'Content', description: 'Top performing posts analysis.', icon: '🖼️' },
  { title: 'Posting Times', description: 'Optimal posting schedule.', icon: '⏰' }
]
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';


h1 {
  font-family: $font-heading;
  font-weight: 700;
  font-size: 2.5rem;
  text-align: center;
  margin-bottom: $space-lg;

  background: linear-gradient(
          70deg,
          $primary 0%,
          $primary 40%,
          $secondary 70%
  );
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}


.insights-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: $space-md;
}

</style>
