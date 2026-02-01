<template>
  <section class="how-it-works">
    <div class="hiw-head">
      <div class="eyebrow">HOW IT WORKS</div>
      <h2>From profile to strategy in minutes</h2>
      <p>
        A simple, repeatable workflow used by creators to turn insights into growth.
      </p>
    </div>

    <div class="steps">
      <GlassCard
          v-for="(step, index) in steps"
          :key="step.title"
          class="step-card"
      >
        <div class="step-top">
          <div class="step-index">{{ index + 1 }}</div>

          <img
              class="step-icon"
              :src="step.icon"
              :alt="step.title"
          />
        </div>

        <h3>{{ step.title }}</h3>
        <p>{{ step.description }}</p>
      </GlassCard>
    </div>
  </section>
</template>

<script setup lang="ts">
import GlassCard from '@/components/ui/GlassCard.vue'

import analyzeIcon from '@/assets/analyze.png'
import optimizeIcon from '@/assets/optimize.png'
import monetizeIcon from '@/assets/monetize.png'

const steps = [
  {
    title: 'Analyze',
    description:
        'We scan public Instagram signals and surface the metrics that actually matter.',
    icon: analyzeIcon,
  },
  {
    title: 'Optimize',
    description:
        'You get clear recommendations on what to improve, remove, and double-down on.',
    icon: optimizeIcon,
  },
  {
    title: 'Monetize',
    description:
        'Turn engagement into revenue with smarter positioning and creator-friendly guidance.',
    icon: monetizeIcon,
  },
]
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';
@import '@/assets/styles/animations';


.how-it-works {
  max-width: 1100px;
  margin: 0 auto;
  padding: $space-xl $space-md;
  text-align: center;
}


.hiw-head {
  max-width: 720px;
  margin: 0 auto $space-xl;

  .eyebrow {
    font-size: 0.75rem;
    letter-spacing: 0.18em;
    text-transform: uppercase;
    opacity: 0.72;
    margin-bottom: $space-sm;
  }

  h2 {
    font-family: $font-heading;
    font-weight: 800;
    font-size: clamp(2rem, 3.5vw, 2.7rem);
    line-height: 1.12;
    margin-bottom: $space-md;
    @include gradient-text($primary, $secondary);
  }

  p {
    font-family: $font-heading;
    font-weight: 200;
    color: $text-muted;
    font-size: 1.05rem;
    line-height: 1.7;
  }
}


.steps {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: $space-lg;

  @media (max-width: 980px) {
    grid-template-columns: 1fr;
  }
}

.step-card {
  padding: 1.6rem 1.4rem;
  text-align: center;
  transition: transform 0.25s ease;
  @include hover-glow;

  &:hover {
    transform: translateY(-6px);
  }
}


.step-top {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1rem;
}

.step-index {
  width: 42px;
  height: 42px;
  border-radius: 999px;
  display: grid;
  place-items: center;

  font-family: $font-heading;
  font-weight: 700;
  font-size: 1rem;

  background: rgba(255, 255, 255, 0.06);
  border: 1px solid rgba(255, 255, 255, 0.14);
  color: rgba(255, 255, 255, 0.9);
}

.step-icon {
  width: 108px;
  height: 108px;
  margin-bottom: 12px;
  object-fit: contain;
}

.step-card h3 {


    font-family: $font-heading;
    font-weight: 700;
    font-size: 1.3rem;
    margin-bottom: $space-xs;
    @include gradient-text($primary, $secondary);


}

.step-card p {
  font-family: $font-heading;
  font-weight: 200;
  color: $text-muted;
  line-height: 1.65;
  font-size: 1.02rem;
}
</style>
