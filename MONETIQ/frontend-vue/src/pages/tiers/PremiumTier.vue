<template>
  <SectionWrapper>
    <section class="tier-page">
      <h2>Premium 30-Day Monetization Plan</h2>
      <ul class="plan-list">
        <li v-for="task in premiumPlan" :key="task.day">
          Day {{ task.day }}: {{ task.text }}
        </li>
      </ul>
    </section>
  </SectionWrapper>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useUserStore, PremiumTask } from '@/stores/userStore'
import SectionWrapper from '@/components/layout/SectionWrapper.vue'

const userStore = useUserStore()

const premiumPlan = computed<PremiumTask[]>(() => userStore.plan?.items || [])

onMounted(() => {
  userStore.fetchPremiumPlan()
})
</script>


<style lang="scss" scoped>
@import '@/assets/styles/variables';

.tier-page {
  padding: $space-xl;
  max-width: 800px;
  margin: 0 auto;

  h2 {
    font-family: $font-heading;
    font-weight: 700;
    font-size: 2.5rem;
    margin-bottom: $space-lg;
    background: linear-gradient(90deg, $primary, $secondary);
    -webkit-background-clip: text;
    color: transparent;
    text-align: center;
  }

  .plan-list li {
    font-family: $font-heading;
    font-weight: 200;
    color: $text-muted;
    font-size: 1.1rem;
    line-height: 1.5;
    padding: $space-sm 0;
    border-bottom: 1px solid $bg-soft;

    &:last-child {
      border-bottom: none;
    }
  }
}

</style>
