<template>
  <div id="app">
    <AppHeader />

    <main>
      <div v-if="!isReady">
        Loading...
      </div>

      <router-view v-else />
    </main>

    <AppFooter />
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue'
import { useUserStore } from '@/stores/userStore'
import { useAuthStore } from '@/stores/authStore'
import AppHeader from '@/components/layout/AppHeader.vue'
import AppFooter from '@/components/layout/AppFooter.vue'

const userStore = useUserStore()
const authStore = useAuthStore()
const isReady = ref(false)

onMounted(async () => {
  if (authStore.token) {
    try {
      await userStore.fetchMe()
    } catch (e) {
      console.error('fetchMe failed', e)
      userStore.reset()
      authStore.logout()
    }
  }
  isReady.value = true
})

</script>


<style lang="scss">
@import '@/assets/styles/main.scss';
</style>
