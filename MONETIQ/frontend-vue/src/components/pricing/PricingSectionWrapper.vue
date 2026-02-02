<template>
  <div class="pricing-grid">
    <slot />
  </div>
</template>

<style scoped lang="scss">
@import '@/assets/styles/variables';

.pricing-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: $space-lg;
}
</style>
