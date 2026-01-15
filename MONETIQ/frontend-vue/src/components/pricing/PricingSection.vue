<template>
  <section class="pricing-section">
    <PricingCard
      tier="Free"
      price="$0"
      :features="['3 profile suggestions', 'Basic analytics']"
    />
    <PricingCard
      tier="Premium"
      price="$19.99/mo"
      :features="['30-day monetization plan', 'Detailed analytics', 'Custom tips']"
      highlight
    />
    <PricingCard
      tier="Ultra Premium"
      price="$49.99/mo"
      :features="['All Premium features', 'Simulated digital product', 'Priority support']"
    />
  </section>
</template>

<script setup lang="ts">
import PricingCard from './PricingCard.vue'
</script>

<style lang="scss" scoped>
@import "../../assets/styles/variables.scss";


.pricing-section {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  gap: $spacing-lg;
  padding: $spacing-xl 1rem;
  flex-wrap: wrap;
}
</style>
