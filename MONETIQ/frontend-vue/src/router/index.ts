import { createRouter, createWebHistory } from 'vue-router'
import Landing from '@/pages/Landing.vue'
import FreePage from '@/pages/FreePage.vue'
import PremiumPage from '@/pages/PremiumPage.vue'
import UltraPage from '@/pages/UltraPage.vue'

const routes = [
    { path: '/', name: 'Landing', component: Landing },
    { path: '/free', name: 'Free', component: FreePage },
    { path: '/premium', name: 'Premium', component: PremiumPage },
    { path: '/ultra', name: 'Ultra', component: UltraPage },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
