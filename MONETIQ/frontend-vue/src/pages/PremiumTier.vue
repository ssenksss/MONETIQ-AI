<template>
  <div class="premium-tier">
    <h2>Premium 30-Day Monetization Plan</h2>
    <ul>
      <li v-for="(task, idx) in premiumPlan" :key="idx">
        Day {{ task.day }}: {{ task.text }}
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { useProfileStore } from '../stores/profileStore.ts'
const store = useProfileStore()
const premiumPlan = store.premiumPlan
</script>

<style scoped>
.premium-tier {
  padding: 2rem;
}
.premium-tier li {
  margin-bottom: 0.5rem;
}
</style>
