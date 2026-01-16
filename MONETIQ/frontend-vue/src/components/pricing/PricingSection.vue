<template>
  <section class="pricing-section">
    <PricingHeader />

    <div class="pricing-grid">
      <PricingCard
          tier="Free"
          price="€0"
          :features="['Basic insights', 'Limited reports']"
      />
      <PricingCard
          tier="Premium"
          price="$49.99/mo"
          :features="['Detailed analytics', 'Custom tips', '30-day monetization plan']"
          highlight
      />
      <PricingCard
          tier="Ultra"
          price="$89.99/mo"
          :features="['All Premium features', 'Simulated digital product', 'Priority support', 'Dedicated analyst']"

      />
    </div>
  </section>
</template>

<script setup lang="ts">
import PricingHeader from './PricingHeader.vue'
import PricingCard from './PricingCard.vue'
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables';

.pricing-section {
  padding: $space-xl $space-md;
  text-align: center;

  .pricing-grid {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: $space-lg;
    margin-top: $space-lg;
    font-family: $font-heading;
    font-weight: 200;
    color: $text-muted;
    font-size: 1rem;
    line-height: 1.5;
  }



}
</style>
