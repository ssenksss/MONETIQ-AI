
<template>
  <FreeTier v-if="userStore.role === 'FREE_USER'" />
  <PremiumTier v-else-if="userStore.role === 'PREMIUM_USER'" />
  <UltraTier v-else-if="userStore.role === 'ULTRA_USER'" />
</template>

<script setup lang="ts">
import { useUserStore } from '@/stores/userStore'
import FreeTier from "@/pages/tiers/FreeTier.vue";
import PremiumTier from "@/pages/tiers/PremiumTier.vue";
import UltraTier from "@/pages/tiers/UltraTier.vue";

const userStore = useUserStore()
</script>


