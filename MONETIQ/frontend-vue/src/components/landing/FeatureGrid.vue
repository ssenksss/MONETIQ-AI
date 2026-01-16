<template>
  <section class="feature-grid">
    <h2>Free Tier Insights</h2>
    <div class="grid">
      <InsightCard title="Engagement" description="Track likes, comments, and shares." icon="📊" />
      <InsightCard title="Growth" description="See follower trends and growth potential." icon="📈" />
      <InsightCard title="Content" description="Analyze which posts perform best." icon="🖼️" />
      <InsightCard title="Posting Times" description="Discover optimal posting schedule." icon="⏰" />
    </div>
  </section>
</template>

<script setup lang="ts">
import InsightCard from './InsightCard.vue'
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';
@import '@/assets/styles/animations';

.feature-grid {
  text-align: center;

  h2 {
    font-family: $font-heading;
    font-weight: 700;
    font-size: 2rem;
    margin-bottom: $space-lg;

    background: linear-gradient(
            70deg,
            $primary 0%,
            $primary 40%,
            $secondary 70%
    );
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;

    animation: fadeIn 1s ease forwards;
  }


  .grid {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: $space-md;
  }



}
</style>
