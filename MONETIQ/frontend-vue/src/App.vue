<template>
  <AppHeader />
  <router-view />
  <AppFooter />
</template>

<script setup lang="ts">
import AppHeader from '@/components/layout/AppHeader.vue'
import AppFooter from '@/components/layout/AppFooter.vue'
</script>

<style lang="scss">
@import '@/assets/styles/main.scss';
</style>
