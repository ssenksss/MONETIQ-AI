<template>
  <SectionWrapper>
    <div class="ultra-hero">
      <h1>Ultra Premium</h1>
      <p>Apply for the elite strategy plan for top creators.</p>
      <PrimaryButton>Apply Now</PrimaryButton>
    </div>

    <div class="features">
      <GlassCard>
        <div class="feature-content">
          <h3>Exclusive Insights</h3>
          <p>Get access to insights not available to other tiers.</p>
        </div>
      </GlassCard>

      <GlassCard>
        <div class="feature-content">
          <h3>1-on-1 Strategy</h3>
          <p>Work directly with our growth AI specialists.</p>
        </div>
      </GlassCard>

      <GlassCard>
        <div class="feature-content">
          <h3>Priority Support</h3>
          <p>Immediate help from our team whenever needed.</p>
        </div>
      </GlassCard>

    </div>
  </SectionWrapper>
</template>

<script setup lang="ts">
import SectionWrapper from '@/components/layout/SectionWrapper.vue'
import GlassCard from '@/components/ui/GlassCard.vue'
import PrimaryButton from '@/components/ui/PrimaryButton.vue'
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';

.ultra-hero {
  text-align: center;
  margin-bottom: $space-xl;
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


  p {
    font-family: $font-heading;
    font-weight: 200;
    font-size: 1.2rem;
    color: $text-muted;
    margin: $space-sm 0 $space-md;
    line-height: 1.5;
  }

  button {
    @include hover-glow;
  }
}
.features {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: $space-md;

  :deep(.feature-content) {
    text-align: center;

    h3 {
      font-family: $font-heading;
      font-weight: 700;
      font-size: 1.5rem;
      margin-bottom: $space-xs;
      color: $primary;
    }

    p {
      font-family: $font-heading;
      font-weight: 200;
      font-size: 1rem;
      color: $text-muted;
      line-height: 1.5;
    }
  }
}




</style>
