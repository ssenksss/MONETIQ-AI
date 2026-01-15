<template>
  <section class="landing">
    <div class="hero">
      <img src="@/assets/logo-icon.svg" class="hero-logo" />
      <h1>MONETIQ <span>AI</span></h1>
      <div class="coming-soon">COMING SOON</div>
    </div>

    <PricingSection />
  </section>
</template>

<script setup lang="ts">
import PricingSection from '@/components/pricing/PricingSection.vue'
</script>

<style lang="scss" scoped>
@import "../assets/styles/variables.scss";

.landing {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: $spacing-xl 1rem;
  gap: $spacing-xl;

  .hero {
    text-align: center;

    .hero-logo {
      width: 140px;
      height: 140px;
      margin-bottom: $spacing-md;
    }

    h1 {
      font-family: $font-heading;
      font-size: 5rem;
      font-weight: 700;
      background: linear-gradient(to right, $primary, $secondary);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;

      span {
        font-size: 2rem;
        color: $accent;
        vertical-align: super;
      }
    }

    .coming-soon {
      margin-top: $spacing-md;
      font-size: 3rem;
      font-weight: 200;
      color: $text-muted;
      letter-spacing: 0.2em;
    }
  }
}
</style>
