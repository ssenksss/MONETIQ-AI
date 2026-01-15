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

<style lang="scss">
@import './assets/styles/variables.scss';

#app {
  font-family: $font-base;
  color: $text-main;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}
</style>
