<template>
  <SectionWrapper>
    <GlassCard class="resources-page-card">
      <h1>Resources</h1>
      <p>Placeholder page for Resources section. Add links, guides, and tutorials here for your users.</p>
    </GlassCard>
  </SectionWrapper>
</template>

<script lang="ts" setup>
import SectionWrapper from '@/components/layout/SectionWrapper.vue'
import GlassCard from '@/components/ui/GlassCard.vue'
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';

.resources-page-card {
  max-width: 800px;
  margin: 2rem auto;
  padding: $space-lg;
  border-radius: $radius-lg;
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(12px);
  box-shadow: $shadow-soft;

  h1 {
    font-family: $font-heading;
    font-weight: 700;
    font-size: 2rem;
    margin-bottom: $space-md;
    @include gradient-text($primary, $secondary);
  }

  p {
    font-family: $font-base;
    font-weight: 200;
    font-size: 1rem;
    line-height: 1.6;
    color: $text-muted;
    margin-bottom: $space-md;
  }
}
</style>
