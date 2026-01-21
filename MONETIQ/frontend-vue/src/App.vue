<template>
  <div id="app">
    <AppHeader />
    <router-view v-if="userStore.isLoaded" />
    <AppFooter />
  </div>
</template>

<script lang="ts" setup>

import { onMounted } from 'vue'
import { useUserStore } from '@/stores/userStore'
import AppHeader from '@/components/layout/AppHeader.vue'
import AppFooter from '@/components/layout/AppFooter.vue'

const userStore = useUserStore()

onMounted(() => {
  userStore.fetchMe()
})
</script>

<style lang="scss">
@import '@/assets/styles/main.scss';
</style>
