<template>
  <div class="analyze-page">

    <AnalysisInput @start-analysis="startAnalysis" />

    <AnalysisProcessing v-if="loading" />

    <AnalysisResult v-if="result" />

    <AnalysisHistory v-if="history.length" />
  </div>
</template>

<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { useAnalysisStore } from '@/stores/analysisStore'

import AnalysisInput from '@/components/analysis/AnalysisInput.vue'
import AnalysisProcessing from '@/components/analysis/AnalysisProcessing.vue'
import AnalysisResult from '@/components/analysis/AnalysisResult.vue'
import AnalysisHistory from '@/components/analysis/AnalysisHistory.vue'

const store = useAnalysisStore()
const { result, history, loading } = storeToRefs(store)

const startAnalysis = (username: string) => store.startAnalysis(username)
</script>

<style scoped lang="scss">
.analyze-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1.5rem;
  padding: 2rem;
}
</style>
