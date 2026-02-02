<template>
  <SectionWrapper>
    <GlassCard class="legal-page-card">
      <h1>Privacy Policy</h1>
      <p>
        This is a placeholder for the Privacy Policy. You can add your full privacy
        content here. Make sure to cover user data handling, cookies, and compliance.
      </p>
      <p>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor
        incididunt ut labore et dolore magna aliqua.
      </p>
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

.legal-page-card {
  max-width: 800px;
  margin: 2rem auto;
  padding: $space-lg;
  border-radius: $radius-lg;
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(12px);
  box-shadow: $shadow-soft;
  text-align: left;

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
