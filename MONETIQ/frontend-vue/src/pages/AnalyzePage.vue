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
import { useProfileStore } from '@/stores/profileStore'
import { useRouter } from 'vue-router'

import AnalysisInput from '@/components/analysis/AnalysisInput.vue'
import AnalysisProcessing from '@/components/analysis/AnalysisProcessing.vue'
import AnalysisResult from '@/components/analysis/AnalysisResult.vue'
import AnalysisHistory from '@/components/analysis/AnalysisHistory.vue'

const analysisStore = useAnalysisStore()
const profileStore = useProfileStore()
const router = useRouter()

const { result, history, loading } = storeToRefs(analysisStore)

const startAnalysis = async (username: string) => {
  await analysisStore.startAnalysis(username)

  await profileStore.fetchProfile(username)

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
}
</style>
