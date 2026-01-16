<template>
  <SectionWrapper>
    <h1>Premium 30-Day Strategy</h1>
    <div class="timeline">
      <GlassCard v-for="day in 30" :key="day" class="day-card">
        Day {{ day }}
      </GlassCard>
    </div>
    <p class="note">
      Each day contains actionable tips and personalized recommendations.
    </p>
  </SectionWrapper>
</template>

<script setup lang="ts">
import SectionWrapper from '@/components/layout/SectionWrapper.vue'
import GlassCard from '@/components/ui/GlassCard.vue'
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


.timeline {
  display: flex;
  gap: $space-sm;
  overflow-x: auto;
  padding-bottom: $space-sm;

  .day-card {
    font-family: $font-heading;
    font-weight: 200;
    color: $text-muted;
    text-align: center;
    @include hover-glow;
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

</style>
