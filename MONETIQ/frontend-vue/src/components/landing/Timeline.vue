<template>
  <section class="timeline">
    <h2>Premium 30-Day Roadmap</h2>
    <div class="days">
      <GlassCard v-for="day in 30" :key="day" class="day-card">
        Day {{ day }}
      </GlassCard>
    </div>
  </section>
</template>

<script setup lang="ts">
import GlassCard from '@/components/ui/GlassCard.vue'
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';
@import '@/assets/styles/animations';

.timeline {
  text-align: center;

  .days {
    display: flex;
    overflow-x: auto;
    gap: $space-sm;
    padding-bottom: $space-sm;

    .day-card {
      min-width: 80px;
      text-align: center;
      font-family: $font-heading;
      font-weight: 200;
      color: $text-muted;
      animation: fadeIn 1s ease forwards;
      @include hover-glow;
    }
  }

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


}
</style>
