<template>
  <GlassCard class="insight-card">
    <div class="icon">{{ icon }}</div>
    <h3>{{ title }}</h3>
    <p>{{ description }}</p>
  </GlassCard>
</template>

<script setup lang="ts">
import GlassCard from '@/components/ui/GlassCard.vue'

interface Props {
  title: string
  description: string
  icon: string
}
const props = defineProps<Props>()
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';
@import '@/assets/styles/animations';

.insight-card {
  width: 220px;
  text-align: center;
  animation: fadeIn 1s ease forwards;
  @include hover-glow;

  .icon {
    font-size: 2rem;
    margin-bottom: $space-sm;
  }

  h3 {
    font-family: $font-heading;
    font-weight: 700;
    font-size: 1.3rem;
    margin-bottom: $space-xs;
    color: $primary;
  }

  p {
    font-family: $font-heading;
    font-weight: 200;
    color: $text-muted;
    font-size: 1rem;
    line-height: 1.5;
  }
}

</style>
