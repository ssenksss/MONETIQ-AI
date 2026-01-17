<template>
  <header class="header">
    <div class="header-inner">
      <div class="logo" @click="goHome">
        <img src="@/assets/logo-icon.svg" alt="MONETIQ AI" />
        <span>MONETIQ <em>AI</em></span>
      </div>

      <nav class="nav" v-if="!isMobile">
        <ul>
          <li><button @click="goProduct">Product</button></li>
          <li><button @click="goHowItWorks">How It Works</button></li>
          <li><button @click="goPricing">Pricing</button></li>
          <li><button @click="goResources">Resources</button></li>
        </ul>

        <div class="cta-buttons">
          <button class="sign-in" @click="goSignIn">Sign In</button>
          <PrimaryButton @click="goGetStarted">Get Started</PrimaryButton>
        </div>
      </nav>

      <div class="hamburger" v-if="isMobile" @click="toggleMobileMenu">
        <span></span>
        <span></span>
        <span></span>
      </div>
    </div>

    <transition name="slide-fade">
      <div class="mobile-menu" v-if="mobileMenuOpen">
        <ul>
          <li><button @click="goProduct">Product</button></li>
          <li><button @click="goHowItWorks">How It Works</button></li>
          <li><button @click="goPricing">Pricing</button></li>
          <li><button @click="goResources">Resources</button></li>
          <li><button class="sign-in" @click="goSignIn">Sign In</button></li>
          <li><button class="get-started" @click="goGetStarted">Get Started</button></li>
        </ul>
      </div>
    </transition>
  </header>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import PrimaryButton from '@/components/ui/PrimaryButton.vue'

const router = useRouter()
const isMobile = ref(false)
const mobileMenuOpen = ref(false)
const toggleMobileMenu = () => (mobileMenuOpen.value = !mobileMenuOpen.value)
const checkMobile = () => (isMobile.value = window.innerWidth < 768)

onMounted(() => {
  checkMobile()
  window.addEventListener('resize', checkMobile)
})


const goHome = () => router.push('/')
const goProduct = () => router.push('/product')
const goHowItWorks = () => router.push('/how-it-works')
const goPricing = () => router.push('/pricing')
const goResources = () => router.push('/resources')
const goSignIn = () => router.push('/login')
const goGetStarted = () => router.push('/signup')
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';

.header {
  @include glass;
  position: sticky;
  top: 0;
  z-index: 100;
  padding: $space-sm $space-xl;
  backdrop-filter: blur(25px);
  box-shadow: $shadow-glass;
  display: flex;
  flex-direction: column;
  animation: fadeIn 0.8s ease forwards;

  .header-inner {
    display: flex;
    justify-content: space-between;
    align-items: center;


    .logo {
      display: flex;
      align-items: center;
      gap: $space-sm;
      cursor: pointer;

      img { width: 44px; animation: float 4s ease-in-out infinite; }

      span {
        font-family: $font-heading;
        font-size: 1.6rem;
        font-weight: 700;
        @include gradient-text($primary, $secondary);

        em {
          font-style: normal;
          font-size: 0.6em;
          color: $accent;
          margin-left: 0.2rem;
        }
      }
    }


    .nav {
      display: flex;
      align-items: center;
      gap: $space-lg;

      ul {
        display: flex;
        gap: $space-lg;
        list-style: none;
        margin: 0;
        padding: 0;

        li button {
          background: transparent;
          border: none;
          font-family: $font-heading;
          font-weight: 200;          // lagani font
          font-size: 1rem;
          letter-spacing: 0.15em;    // isti kao hero subtitle
          color: $text-muted;
          cursor: pointer;
          position: relative;
          padding: $space-xs $space-sm;
          transition: $transition;

          &:after {
            content: '';
            position: absolute;
            bottom: -3px;
            left: 0;
            width: 0;
            height: 2px;
            background: $primary;
            border-radius: $radius-sm;
            transition: width 0.3s ease;
          }

          &:hover::after { width: 100%; }
          &:hover { color: $primary; transform: scale(1.05); }
        }
      }


      .cta-buttons {
        display: flex;
        gap: $space-sm;
        align-items: center;

        .sign-in {
          background: transparent;
          border: 1px solid $text-muted;
          border-radius: $radius-sm;
          padding: 0.3rem 0.8rem;
          font-family: $font-heading;
          font-weight: 600;
          font-size: 0.95rem;
          color: $text-main;
          transition: $transition;

          &:hover {
            border-color: $primary;
            color: $primary;
            transform: scale(1.05);
          }
        }

        .PrimaryButton {
          font-family: $font-heading;
          font-weight: 600;
          font-size: 0.95rem;
          padding: 0.5rem 1.2rem;
        }
      }
    }


    .hamburger {
      display: flex;
      flex-direction: column;
      gap: 5px;
      cursor: pointer;

      span { display: block; width: 25px; height: 3px; background: $text-main; border-radius: $radius-sm; }
    }
  }


  .mobile-menu {
    background: $bg-glass;
    backdrop-filter: blur(20px);
    padding: $space-lg;
    display: flex;
    flex-direction: column;
    gap: $space-md;

    ul li button {
      font-family: $font-heading;
      font-weight: 600;
      font-size: 1.1rem;
      color: $text-main;
      text-align: left;
      width: 100%;
      padding: $space-sm 0;

      &.get-started { @include gradient-text($primary, $secondary); font-weight: 700; }
      &.sign-in { border-bottom: 1px solid $text-muted; margin-bottom: $space-sm; }
      &:hover { color: $primary; }
    }
  }


  .slide-fade-enter-active, .slide-fade-leave-active { transition: all 0.3s ease; }
  .slide-fade-enter-from { transform: translateY(-20px); opacity: 0; }
  .slide-fade-enter-to { transform: translateY(0); opacity: 1; }
  .slide-fade-leave-from { transform: translateY(0); opacity: 1; }
  .slide-fade-leave-to { transform: translateY(-20px); opacity: 0; }
}
</style>
