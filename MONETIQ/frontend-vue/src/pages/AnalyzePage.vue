<template>
  <div class="analyze-page">
    <AnalysisInput @start-analysis="startAnalysis" />

    <AnalysisProcessing v-if="loading" />

    <AnalysisResult v-if="result" />

    <AnalysisHistory v-if="history.length" />
  </div>
</template>

<script lang="ts" setup>import { storeToRefs } from 'pinia'
import { useAnalysisStore } from '@/stores/analysisStore'
import { useUserStore } from '@/stores/userStore'
import { useAuthStore } from '@/stores/authStore'
import { useRouter } from 'vue-router'

import AnalysisInput from '@/components/analysis/AnalysisInput.vue'
import AnalysisProcessing from '@/components/analysis/AnalysisProcessing.vue'
import AnalysisResult from '@/components/analysis/AnalysisResult.vue'
import AnalysisHistory from '@/components/analysis/AnalysisHistory.vue'
import { onMounted } from 'vue'


const analysisStore = useAnalysisStore()
const userStore = useUserStore()
const authStore = useAuthStore()
const router = useRouter()

const { result, history, loading } = storeToRefs(analysisStore)
const { isAuthenticated } = storeToRefs(authStore)

const startAnalysis = async (username: string) => {
  try {
    await analysisStore.startAnalysis(username)

    if (isAuthenticated.value) {
      await userStore.fetchMe()
    }

    router.push('/profile')
  } catch (err: any) {
    console.error('[AnalyzePage] startAnalysis failed', err)
  }
}
onMounted(async () => {
  try {
    await analysisStore.fetchHistory()
  } catch (e) {
  }
})

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


<style scoped lang="scss">
.analyze-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1.5rem;
  padding: 2rem;
}
</style>
