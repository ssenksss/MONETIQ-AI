<template>
  <section class="analysis-history" v-if="history.length">
    <h2>Previous Analyses</h2>
    <ul>
      <li v-for="item in history" :key="item.createdAt">
        <strong>@{{ item.username }}</strong>
        <span>{{ formatDate(item.createdAt) }}</span>
      </li>
    </ul>
  </section>
</template>

<script lang="ts" setup>
import { computed } from 'vue'
import { useAnalysisStore } from '@/stores/analysisStore'

const analysisStore = useAnalysisStore()
const history = computed(() => analysisStore.history)

const formatDate = (dateStr: string) => new Date(dateStr).toLocaleString()
</script>

<style scoped lang="scss">
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';
@import '@/assets/styles/animations';
@import '@/assets/styles/base';

.analysis-history {
  width: 100%;
  max-width: 650px;
  margin: $space-lg auto;
  @include glass;                // glass effect
  padding: $space-md;
  border-radius: $radius-lg;
  box-shadow: $shadow-soft;
  animation: fadeIn 0.5s ease forwards;

  h2 {
    font-family: $font-heading;
    font-size: 1.5rem;
    margin-bottom: $space-md;
    text-align: center;
    @include gradient-text($primary, $secondary);
  }

  ul {
    list-style: none;
    padding: 0;
    display: flex;
    flex-direction: column;
    gap: $space-sm;
    li {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: $space-sm $space-md;
      border-radius: $radius-md;
      background: rgba(255,255,255,0.05);
      box-shadow: $shadow-soft;
      font-family: $font-base;
      color: $text-main;
      transition: transform 0.2s, background 0.2s;
      @include hover-glow;
      cursor: pointer;

      &:hover {
        background: rgba(255,255,255,0.1);
      }

      strong {
        font-weight: 600;
        color: $primary;
      }

      span {
        font-size: 0.85rem;
        color: $text-muted;
      }
    }

  }

  @media (max-width: 480px) {
    padding: $space-sm;

    h2 {
      font-size: 1.25rem;
    }

    li {
      flex-direction: column;
      align-items: flex-start;
      gap: 0.25rem;
      font-size: 0.9rem;

      span {
        font-size: 0.8rem;
      }
    }
  }
}
</style>
