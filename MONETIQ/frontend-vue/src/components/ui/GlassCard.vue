<template>
  <div class="glass">
    <slot />
  </div>
</template>

<style lang="scss" scoped>
@import '@/assets/styles/variables';

.glass {
  background: $bg-glass;
  backdrop-filter: blur(20px);
  border-radius: $radius-lg;
  box-shadow: $shadow-glass;
  padding: $space-lg;
}
</style>
