import { createRouter, createWebHistory } from 'vue-router'

import Landing from '@/pages/Landing.vue'
import HowItWorksPage from '@/components/landing/HowItWorks.vue'
import AboutPage from '@/pages/About.vue'
import CareersPage from '@/pages/Careers.vue'
import ContactPage from '@/pages/Contact.vue'
import FreeTier from '@/pages/tiers/FreeTier.vue'
import PremiumPage from '@/pages/PremiumPage.vue'
import UltraPage from '@/pages/UltraPage.vue'
import AnalyzePage from '@/pages/AnalyzePage.vue'
import LoginPage from '@/pages/LoginPage.vue'
import SignupPage from '@/pages/SignupPage.vue'
import PrivacyPage from '@/pages/Privacy.vue'
import TermsPage from '@/pages/Terms.vue'
import SecurityPage from '@/pages/Security.vue'
import ProfilePage from '@/pages/ProfilePage.vue'
import ResourcesPage from '@/pages/Resources.vue'
import PricingSection from "@/components/pricing/PricingSection.vue";
import { useAuthStore } from '@/stores/authStore'
import { useUserStore } from '@/stores/userStore'

const routes = [
    { path: '/', name: 'Landing', component: Landing },
    { path: '/how-it-works', name: 'HowItWorks', component: HowItWorksPage },
    { path: '/about', name: 'About', component: AboutPage },
    { path: '/careers', name: 'Careers', component: CareersPage },
    { path: '/contact', name: 'Contact', component: ContactPage },
    { path: '/free', name: 'Free', component: FreeTier },

    { path: '/profile', component: ProfilePage, meta: { requiresAuth: true } },
    { path: '/premium', name: 'Premium', component: PremiumPage, meta: { requiresAuth: true, requiresPremium: true } },
    { path: '/ultra', name: 'Ultra', component: UltraPage, meta: { requiresAuth: true, requiresUltra: true } },
    { path: '/analyze', name: 'Analyze', component: AnalyzePage, meta: { requiresAuth: true } },

    { path: '/login', name: 'Login', component: LoginPage },
    { path: '/signup', name: 'Signup', component: SignupPage },
    { path: '/privacy', name: 'Privacy', component: PrivacyPage },
    { path: '/terms', name: 'Terms', component: TermsPage },
    { path: '/security', name: 'Security', component: SecurityPage },
    { path: '/resources', name: 'Resources', component: ResourcesPage },
    { path: '/pricing', name: 'Pricing', component: PricingSection },
    { path: '/premium-test', name: 'PremiumTest', component: PremiumPage },
    { path: '/ultra-test', name: 'UltraTest', component: UltraPage },


    { path: '/:pathMatch(.*)*', redirect: '/' }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach(async (to) => {
    const auth = useAuthStore()
    const user = useUserStore()


    if (to.meta.requiresAuth && !auth.token) return '/login'

    if (auth.token && !user.isLoaded) {
        try {
            await user.fetchMe()
        } catch {
            auth.logout()
            return '/login'
        }
    }

    if (to.meta.requiresPremium && user.role !== 'PREMIUM' && user.role !== 'ULTRA') return '/'
    if (to.meta.requiresUltra && user.role !== 'ULTRA') return '/'

})


export default router
