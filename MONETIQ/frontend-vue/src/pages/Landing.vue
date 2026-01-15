<template>
  <div class="landing-page">
    <div class="logo-container">
      <img src="@/assets/logo-icon.svg" alt="Logo" class="logo-icon" />
      <div class="logo-text">
        <h1>MONETIQ</h1>
        <span>AI</span>
      </div>
    </div>

    <div class="coming-soon">
      COMING SOON
    </div>


    <PricingSection />
  </div>
</template>

<script lang="ts" setup>
import PricingSection from '../components/pricing/PricingSection.vue'
</script>

<style scoped>
.landing-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
  padding: 3rem 1rem;
  background-color: #ffffff;
  gap: 2rem;
}


.logo-container {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 4rem;
}

.logo-icon {
  width: 120px;
  height: 120px;
  margin-right: 1.5rem;
}

.logo-text h1 {
  font-family: 'Montserrat', sans-serif;
  font-size: 5.5rem;
  font-weight: 700;
  background: linear-gradient(to right, #10B981, #1F3C88);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  margin: 0;
}

.logo-text span {
  font-size: 2rem;
  color: #F5B301;
  letter-spacing: 0.2em;
  margin-left: 0.5rem;
  vertical-align: super;
}


.coming-soon {
  font-family: 'Inter', sans-serif;
  font-weight: 200;
  font-size: 3rem;
  color: #B0B0B0;
  letter-spacing: 0.2em;
  text-align: center;
  margin-bottom: 3rem;
}
</style>
