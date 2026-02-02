<template>
  <section class="analysis-result" v-if="result">
    <h2>Monetization Plan for @{{ result.username }}</h2>

    <ul>
      <li v-for="(suggestion, index) in result.suggestions" :key="index">
        {{ suggestion }}
      </li>
    </ul>

    <span class="timestamp">
      Generated: {{ formattedDate }}
    </span>
  </section>
</template>

<script lang="ts" setup>
import { computed } from 'vue'
import { useAnalysisStore } from '@/stores/analysisStore'
import type { AnalysisResult } from '@/stores/analysisStore'

const analysisStore = useAnalysisStore()
const result = computed<AnalysisResult | null>(() => analysisStore.result)

const formattedDate = computed(() => {
  const r = result.value
  if (!r || !r.analysisDate) return ''
  return new Date(r.analysisDate).toLocaleString()
})
</script>

<style scoped lang="scss">
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';
@import '@/assets/styles/animations';

.analysis-result {
  width: 100%;
  max-width: 650px;
  padding: $space-lg;
  margin: $space-lg 0;
  border-radius: $radius-lg;
  background: $bg-glass;
  backdrop-filter: blur(10px);
  box-shadow: $shadow-soft;
  display: flex;
  flex-direction: column;
  gap: $space-md;
  animation: fadeIn 0.5s ease forwards;

  h2 {
    font-family: $font-heading;
    font-size: 2rem;
    margin-bottom: $space-sm;
    @include gradient-text($primary, $secondary);
  }


  ul {
    list-style: disc;
    padding-left: 1.5rem;

    li {
      margin-bottom: $space-xs;
      color: $text-main;
      font-family: $font-mono;
      font-size: 1rem;
      line-height: 1.5;
    }
  }


  .timestamp {
    align-self: flex-end;
    font-size: 0.85rem;
    color: $text-muted;
    opacity: 0.7;
  }

  @media (max-width: 480px) {
    padding: $space-md;

    h2 {
      font-size: 1.5rem;
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
