<template>
  <section class="tier-page">
    <h2>Free Tier Profile Suggestions</h2>
    <ProfileCard v-if="profile.username" :profile="profile" />
    <p v-else class="no-profile">No profile data available.</p>
  </section>
</template>

<script setup lang="ts">
import ProfileCard from '@/components/ProfileCard.vue'
import { useProfileStore } from '@/stores/profileStore.ts'

const store = useProfileStore()
const profile = store.profile
</script>

<style lang="scss" scoped>
@import "../assets/styles/variables.scss";

.tier-page {
  padding: $spacing-xl;
  max-width: 900px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;

  h2 {
    font-family: $font-heading;
    font-size: 2.5rem;
    font-weight: 700;
    margin-bottom: $spacing-lg;
    background: linear-gradient(90deg, $primary, $secondary);
    -webkit-background-clip: text;
    color: transparent;
  }

  .no-profile {
    color: $text-muted;
    font-size: 1.2rem;
    margin-top: $spacing-md;
  }
}
</style>
