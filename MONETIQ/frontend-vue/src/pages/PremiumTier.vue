<template>
  <section class="tier-page">
    <h2>Premium 30-Day Monetization Plan</h2>
    <ul class="plan-list">
      <li v-for="(task, idx) in premiumPlan" :key="idx">
        Day {{ task.day }}: {{ task.text }}
      </li>
    </ul>
  </section>
</template>

<script setup lang="ts">
import { useProfileStore } from '@/stores/profileStore.ts'

const store = useProfileStore()
const premiumPlan = store.premiumPlan
</script>

<style lang="scss" scoped>
@import "../assets/styles/variables.scss";

.tier-page {
  padding: $spacing-xl;
  max-width: 800px;
  margin: 0 auto;

  h2 {
    font-family: $font-heading;
    font-size: 2.5rem;
    font-weight: 700;
    margin-bottom: $spacing-lg;
    background: linear-gradient(90deg, $primary, $secondary);
    -webkit-background-clip: text;
    color: transparent;
    text-align: center;
  }

  .plan-list {
    list-style: none;
    padding: 0;
    margin: 0 auto;
    max-width: 600px;

    li {
      padding: $spacing-sm 0;
      border-bottom: 1px solid $border-soft;
      font-size: 1.1rem;
      color: $text-main;

      &:last-child {
        border-bottom: none;
      }
    }
  }
}
</style>
