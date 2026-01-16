<template>
  <section class="cta-section">
    <h2>Ultra Premium</h2>
    <p>Apply now for an elite strategy plan designed for top creators.</p>
    <PrimaryButton @click="$emit('applyUltra')">Apply</PrimaryButton>
  </section>
</template>

<script setup lang="ts">
import PrimaryButton from '@/components/ui/PrimaryButton.vue'
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
