<template>
  <div class="analyze-page">
    <AnalysisInput @start-analysis="startAnalysis" />
    <AnalysisProcessing v-if="loading" />
    <div v-if="result">
      <AnalysisResult />
      <button class="view-profile-btn" @click="goToProfile">
        View Profile & Plans
      </button>
    </div>
    <AnalysisHistory v-if="history.length" />
  </div>
</template>

<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { useAnalysisStore } from '@/stores/analysisStore'
import { useRouter } from 'vue-router'

import AnalysisInput from '@/components/analysis/AnalysisInput.vue'
import AnalysisProcessing from '@/components/analysis/AnalysisProcessing.vue'
import AnalysisResult from '@/components/analysis/AnalysisResult.vue'
import AnalysisHistory from '@/components/analysis/AnalysisHistory.vue'

const store = useAnalysisStore()
const { result, history, loading } = storeToRefs(store)
const router = useRouter()

const startAnalysis = (username: string) => {
  store.startAnalysis(username)
}

const goToProfile = () => {
  router.push('/profile')
}
</script>

<style scoped lang="scss">
.analyze-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1.5rem;
  padding: 2rem;

  .view-profile-btn {
    margin-top: 1rem;
    padding: 0.5rem 1rem;
    border: none;
    border-radius: 0.5rem;
    background-color: #4f46e5;
    color: #fff;
    cursor: pointer;
    transition: 0.2s;

    &:hover {
      background-color: #3730a3;
    }
  }
}
</style>
