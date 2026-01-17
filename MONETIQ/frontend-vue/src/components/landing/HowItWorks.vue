<template>
  <router-link to="/how-it-works"></router-link>

  <section class="how-it-works">
    <h2>How It Works</h2>
    <div class="steps">
      <GlassCard v-for="(step, index) in steps" :key="index" class="step-card">
        <h3>{{ step.title }}</h3>
        <p>{{ step.description }}</p>
      </GlassCard>
    </div>
  </section>
</template>

<script setup lang="ts">
import GlassCard from '@/components/ui/GlassCard.vue'

const steps = [
  { title : 'Analyze', description: 'Get deep insights into your Instagram profile performance.' },
  { title: 'Optimize', description: 'Receive actionable strategies to improve your reach & engagement.' },
  { title: 'Monetize', description: 'Turn your audience into revenue with expert guidance.' }
]
</script>

<style lang="scss">
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';
@import '@/assets/styles/animations';

.how-it-works {
  text-align: center;

  h2 {
    font-family: $font-heading;
    font-size: 2.5rem;
    font-weight: 700;
    margin-bottom: $space-lg;
    line-height: 1.2;

    background: linear-gradient(70deg, $primary 0%, $primary 40%, $secondary 70%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;


    animation: fadeIn 1s ease forwards;
  }

  .steps {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: $space-lg;

    .step-card {
      min-width: 220px;
      animation: fadeIn 1s ease forwards;
      transition: transform 0.3s ease;

      @include hover-glow;

      &:hover {
        transform: translateY(-5px);
      }



    h3 {
        font-family: $font-heading;
        font-weight: 700;
        font-size: 1.5rem;
        margin-bottom: $space-xs;
        color: $primary; // zelena, primary
        animation: fadeIn 1s 0.2s ease forwards;
      }

      p, li {
        font-family: $font-heading;
        font-weight: 200;
        color: $text-muted;
        font-size: 1rem;
        line-height: 1.5;
      }
    }
  }
}
</style>
