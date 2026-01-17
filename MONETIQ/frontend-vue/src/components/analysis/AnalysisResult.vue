<template>
  <section class="analysis-result" v-if="result">
    <h2>Monetization Plan for @{{ result.username }}</h2>

    <pre>{{ result.plan }}</pre>

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
  if (!r || !r.createdAt) return ''
  return new Date(r.createdAt).toLocaleString()
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

  pre {
    width: 100%;
    white-space: pre-wrap;
    padding: $space-md;
    border-radius: $radius-md;
    background: rgba(255,255,255,0.05);
    font-family: $font-mono;
    color: $text-main;
    box-shadow: $shadow-soft;
    overflow-x: auto;
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

    pre {
      font-size: 0.85rem;
    }
  }
}
</style>
