<template>
  <div class="analysis-processing">
    <p>Analyzing Instagram profile…</p>
    <div class="loader"></div>
  </div>
</template>


<script lang="ts" setup>
import { computed } from 'vue'
import { useAnalysisStore } from '@/stores/analysisStore'

const analysisStore = useAnalysisStore()
const loading = computed(() => analysisStore.loading)
</script>

<style scoped lang="scss">
.analysis-processing {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 1rem 0;

  p {
    margin-bottom: 0.5rem;
    font-weight: 500;
  }

  .loader {
    width: 40px;
    height: 40px;
    border: 4px solid rgba(255, 255, 255, 0.2);
    border-top-color: #4f46e5;
    border-radius: 50%;
    animation: spin 1s linear infinite;
  }

  @keyframes spin {
    to { transform: rotate(360deg); }
  }
}
</style>
