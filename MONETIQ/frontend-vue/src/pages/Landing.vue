<template>

  <HeroSection />

  <SectionWrapper>
    <HowItWorks />
  </SectionWrapper>

  <SectionWrapper>
    <FeatureGrid />
  </SectionWrapper>

  <SectionWrapper>
    <Timeline />
  </SectionWrapper>

  <SectionWrapper>
    <PricingSection />
  </SectionWrapper>

  <SectionWrapper>
    <CTASection />
  </SectionWrapper>
</template>

<script setup lang="ts">
import SectionWrapper from '@/components/layout/SectionWrapper.vue'
import HeroSection from '@/components/landing/HeroSection.vue'
import HowItWorks from '@/components/landing/HowItWorks.vue'
import FeatureGrid from '@/components/landing/FeatureGrid.vue'
import Timeline from '@/components/landing/Timeline.vue'
import CTASection from '@/components/landing/CTASection.vue'
import PricingSection from '@/components/pricing/PricingSection.vue'
</script>
