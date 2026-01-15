<template>
  <div class="card" :class="{ highlight }">
    <h3>{{ tier }}</h3>
    <p class="price">{{ price }}</p>
    <ul>
      <li v-for="(f, i) in features" :key="i">✓ {{ f }}</li>
    </ul>
    <button>{{ highlight ? 'Most Popular' : 'Get Started' }}</button>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  tier: string
  price: string
  features: string[]
  highlight?: boolean
}>()
</script>

<style lang="scss" scoped>
@import "../../assets/styles/variables.scss";



.card {
  background: $bg-light;
  border-radius: 20px;
  padding: 2.5rem;
  width: 300px;
  text-align: center;
  border: 1px solid $border-soft;
  box-shadow: $shadow-soft;
  transition: all 0.2s ease;

  &.highlight {
    border: 2px solid $primary;
    transform: translateY(-10px);
  }

  h3 {
    font-size: 1.6rem;
  }

  .price {
    font-size: 2rem;
    font-weight: 700;
    color: $primary;
    margin: 1rem 0;
  }

  ul {
    text-align: left;
    margin: 1.5rem 0;
    padding: 0;
    list-style: none;
    color: $text-muted;
  }

  li {
    margin-bottom: 0.5rem;
  }

  button {
    width: 100%;
    padding: 0.75rem;
    border-radius: 10px;
    border: none;
    font-weight: 600;
    background: linear-gradient(90deg, $primary, $secondary);
    color: white;
    cursor: pointer;

    &:hover {
      opacity: 0.9;
    }
  }
}
</style>
