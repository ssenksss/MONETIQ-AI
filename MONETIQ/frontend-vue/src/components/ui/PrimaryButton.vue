<template>
  <button class="btn">
    <slot />
  </button>
</template>

<style lang="scss" scoped>
@import '@/assets/styles/variables';

.btn {
  padding: 0.9rem 2rem;
  border-radius: $radius-md;
  font-weight: 600;
  font-size: 1rem;
  border: none;
  cursor: pointer;
  background: linear-gradient(90deg, $primary, $secondary);
  color: white;
  transition: $transition;

  &:hover {
    transform: translateY(-2px);
    box-shadow: $shadow-soft;
  }
}
</style>
