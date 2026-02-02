<template>
  <section class="hero">
    <img alt src="@/assets/logo-icon.svg" class="hero-logo" />
    <h1>
      MONETIQ <span>AI</span>
    </h1>
    <p class="subtitle">
      Instagram Profile Intelligence for Creators
    </p>
    <div class="cta-group">
      <PrimaryButton class="primary-btn" @click="goFree">
        Analyze Profile
      </PrimaryButton>

      <PrimaryButton class="secondary-btn" @click="goPricing">
        View Plans
      </PrimaryButton>

    </div>
  </section>
</template>

<script setup lang="ts">
import PrimaryButton from '@/components/ui/PrimaryButton.vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goFree = () => router.push('/free')
const goPricing = () => router.push('/pricing')

</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';
@import '@/assets/styles/animations';


.hero {
  padding-top: 55px;
  text-align: center;
  animation: fadeIn 1s ease forwards;

  .hero-logo { width: 160px; animation: float 4s ease-in-out infinite; margin-bottom: $space-md; }

  h1 {
    font-size: 5rem; margin: $space-sm 0;
    font-family: $font-heading;
    background: linear-gradient(80deg, #10B981, #1F3C88);
    -webkit-background-clip: text;
    color: transparent;
    span { font-size: 2rem; color: $accent;
      vertical-align: super; }

  }

  .subtitle {
    font-family: $font-heading;
    font-weight: 200;
    font-size: 1.5rem;
    color: $text-muted;
    letter-spacing: 0.15em;
    margin: $space-md 0 $space-lg;
    text-align: center;
    animation: fadeIn 1s 0.4s ease forwards;
  }

  .cta-group { display: flex; justify-content: center; gap: $space-md; margin-top: $space-md;
    .primary-btn { @include hover-glow; }
    .secondary-btn { background: $secondary; color: white; @include hover-glow; }
  }
}
</style>
