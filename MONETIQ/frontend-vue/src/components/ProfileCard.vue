<template>
  <div class="profile-card">
    <h3>{{ profile.username }}</h3>
    <p>Analysis Date: {{ profile.analysisDate }}</p>
    <ul>
      <li v-for="(s, idx) in profile.suggestions" :key="idx">{{ s.text }}</li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { PropType } from 'vue'

interface Suggestion {
  text: string
  day?: number
}

interface Profile {
  username: string
  suggestions: Suggestion[]
  analysisDate: string
}

const props = defineProps<{
  profile: Profile
}>()
</script>

<style scoped>
.profile-card {
  border: 1px solid #E5E5E5;
  padding: 1rem;
  border-radius: 0.5rem;
  margin: 1rem 0;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}
.profile-card h3 {
  color: #1E3A8A;
  margin-bottom: 0.5rem;
}
.profile-card ul {
  padding-left: 1.2rem;
  color: #555;
}
</style>
