<template>
  <div class="card" :class="{ highlight }">
    <h3>{{ tier }}</h3>
    <p class="price">{{ price }}</p>

    <ul>
      <li v-for="(f, i) in features" :key="i">✓ {{ f }}</li>
    </ul>

    <button @click="goToTier(tier)">
      {{ highlight ? 'Most Popular' : 'Get Started' }}
    </button>

  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'
import { useUserStore } from '@/stores/userStore'

const { tier, price, features, highlight } = defineProps<{
  tier: string
  price: string
  features: string[]
  highlight?: boolean
}>()

const router = useRouter()
const authStore = useAuthStore()
const userStore = useUserStore()

const goToTier = async (tier: string) => {
  if (!authStore.token && (tier === 'Premium' || tier === 'Ultra')) {
    router.push('/login')
    return
  }

  if (tier === 'Free') {
    router.push('/free')
    return
  }

  if (tier === 'Premium') {
    await userStore.upgradeToPremium()
    await userStore.fetchMe()
    router.push('/premium')
    return
  }

  if (tier === 'Ultra') {
    await userStore.upgradeToUltra()
    router.push('/ultra')
    return
  }

}
</script>



<style lang="scss" scoped>
@import '@/assets/styles/variables.scss';
@import '@/assets/styles/mixins.scss';


.card {
  @include glass;
  @include hover-glow;

  width: 300px;
  text-align: center;
  transition: $transition;

  h3 {
    font-size: 1.5rem;
    margin-bottom: $space-sm;
    @include gradient-text($primary, $secondary);
  }

  .price {
    font-size: 2.2rem;
    color: $primary;
    margin: $space-md 0;
  }

  ul {
    text-align: left;
    margin: $space-md 0;
    padding: 0;
    list-style: none;
    color: $text-muted;

    li {
      margin-bottom: $space-sm;
    }
  }

  button {
    width: 100%;
    padding: 0.75rem;
    border-radius: $radius-md;
    border: none;
    font-weight: 600;
    background: linear-gradient(90deg, $primary, $secondary);
    color: white;
    cursor: pointer;
    transition: $transition;


    &:hover {
      opacity: 0.9;
      transform: scale(1.03);
    }
  }

  &:hover {
    transform: translateY(-3px);
  }

  &.highlight {
    border: 2px solid $primary;
    transform: translateY(-5px);
  }
}

</style>
