<template>
  <span class="badge">
    <slot />
  </span>
</template>

<style lang="scss" scoped>
@import '@/assets/styles/variables';

.badge {
  display: inline-block;
  padding: $space-xs $space-sm;
  border-radius: $radius-sm;
  background: $accent;
  color: white;
  font-size: 0.8rem;
  font-weight: 600;
}
</style>
