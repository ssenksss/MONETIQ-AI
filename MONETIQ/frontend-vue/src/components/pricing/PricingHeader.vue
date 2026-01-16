<template>
  <div class="pricing-header">
    <h2>Pricing Plans</h2>
    <p>Choose the plan that scales with your growth</p>
  </div>
</template>

<script setup lang="ts"></script>

<style lang="scss" scoped>
@import '@/assets/styles/variables.scss';
@import '@/assets/styles/mixins.scss';


.pricing-header {
  text-align: center;
  margin-bottom: $space-xl;

  h2 {
    font-size: 3rem;

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
    font-size: 1rem;
    color: $text-muted;
    line-height: 1.5;
    margin-top: $space-sm;
  }

}
</style>
