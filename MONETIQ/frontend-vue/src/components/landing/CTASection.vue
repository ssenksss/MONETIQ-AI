<template>
  <section class="cta-section">
    <h2>Ultra Premium</h2>
    <p>Request a custom-built digital product based on your audience, positioning, and monetization goals.</p>

    <PrimaryButton @click="goToPricing">
      Apply
    </PrimaryButton>
  </section>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import PrimaryButton from '@/components/ui/PrimaryButton.vue'

const router = useRouter()

const goToPricing = () => {
  router.push('/pricing')
}
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';
@import '@/assets/styles/animations';

.cta-section {
  text-align: center;

  h2 {
    font-family: $font-heading;
    font-weight: 700;
    font-size: 2rem;
    margin-bottom: $space-sm;


    background: linear-gradient(70deg, $primary 0%, $primary 40%, $secondary 70%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;

    animation: fadeIn 1s ease forwards;
  }


  p {
    font-family: $font-heading;
    font-weight: 200;
    color: $text-muted;
    font-size: 1rem;
    line-height: 1.5;
    margin-bottom: $space-md;
    animation: fadeIn 1s 0.2s ease forwards;
  }

  button {
    @include hover-glow;
  }
}
</style>
