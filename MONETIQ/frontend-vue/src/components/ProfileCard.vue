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

interface Suggestion { text: string; day?: number }
interface Profile { username: string; suggestions: Suggestion[]; analysisDate: string }

const props = defineProps<{ profile: Profile }>()
</script>

<style lang="scss" scoped>
@import "../assets/styles/variables.scss";

.profile-card {
  border: 1px solid $border-soft;
  border-radius: 0.5rem;
  padding: $spacing-md;
  margin: $spacing-md 0;
  background: $bg-light;
  box-shadow: $shadow-soft;

  h3 {
    color: $primary;
    margin-bottom: $spacing-sm;
  }

  ul {
    padding-left: 1rem;
    color: $text-muted;
  }
}
</style>
