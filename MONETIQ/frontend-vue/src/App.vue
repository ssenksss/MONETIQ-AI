<template>
  <div id="app">
    <Header />
    <router-view />
    <Footer />
  </div>
</template>

<script setup lang="ts">
import Header from './components/Header.vue'
import Footer from './components/Footer.vue'
</script>

<style>
#app {
  font-family: 'Inter', sans-serif;
  color: #1E3A8A;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}
</style>
