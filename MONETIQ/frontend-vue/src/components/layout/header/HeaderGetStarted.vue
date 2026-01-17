<template>
  <button @click="goToSignup" class="cta-button">
    Get Started
  </button>
  <router-link to="/signup" class="btn-primary">Get Started</router-link>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goToSignup = () => {
  router.push('/signup')
}
</script>

<style scoped>
.cta-button {
  padding: 0.5rem 1rem;
  background-color: #4f46e5;
  color: white;
  border-radius: 0.5rem;
  cursor: pointer;
  transition: background-color 0.2s ease;
}
.cta-button:hover {
  background-color: #3730a3;
}
</style>
