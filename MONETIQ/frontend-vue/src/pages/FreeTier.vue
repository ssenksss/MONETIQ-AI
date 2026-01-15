<template>
  <div class="free-tier">
    <h2>Free Tier Profile Suggestions</h2>
    <ProfileCard :profile="profile" />
  </div>
</template>

<script setup lang="ts">
import ProfileCard from '../components/ProfileCard.vue'
import { useProfileStore } from '../stores/profileStore.ts'

const store = useProfileStore()
const profile = store.profile
</script>

<style scoped>
.free-tier {
  padding: 2rem;
}
</style>
