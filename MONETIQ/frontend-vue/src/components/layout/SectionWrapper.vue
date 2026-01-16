<template>
  <section class="section">
    <slot />
  </section>
</template>

<style lang="scss" scoped>
@import '@/assets/styles/variables';

.section {
  max-width: 1200px;
  margin: 0 auto;
  padding: $space-xl $space-md;
}
</style>
