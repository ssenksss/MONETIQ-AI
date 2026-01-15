import { createRouter, createWebHistory } from 'vue-router'
// @ts-ignore
import Landing from '../pages/Landing.vue'
// @ts-ignore
import FreeTier from '../pages/FreeTier.vue'
// @ts-ignore
import PremiumTier from '../pages/PremiumTier.vue'
// @ts-ignore
import UltraPremium from '../pages/UltraPremium.vue'

const routes = [
  { path: '/', name: 'Landing', component: Landing },
  { path: '/free', name: 'FreeTier', component: FreeTier },
  { path: '/premium', name: 'PremiumTier', component: PremiumTier },
  { path: '/ultra', name: 'UltraPremium', component: UltraPremium },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
