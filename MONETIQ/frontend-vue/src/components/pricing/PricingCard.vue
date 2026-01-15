<template>
  <div class="pricing-card">
    <h3 class="tier">{{ tier }}</h3>
    <p class="price">{{ price }}</p>
    <ul class="features">
      <li v-for="(feature, index) in features" :key="index">{{ feature }}</li>
    </ul>
    <button class="buy-btn">Get Started</button>
  </div>
</template>

<script lang="ts" setup>
interface Props {
  tier: string
  price: string
  features: string[]
}

const props = defineProps<Props>()
</script>

<style scoped>
.pricing-card {
  border: 1px solid #e0e0e0;
  border-radius: 16px;
  padding: 2rem;
  width: 280px;
  text-align: center;
  background-color: #ffffff;
  box-shadow: 0 4px 12px rgba(0,0,0,0.05);
  transition: transform 0.2s ease;
}

.pricing-card:hover {
  transform: translateY(-6px);
}

.tier {
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
}

.price {
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 1rem;
  color: #10B981; /* tirkizno-zelena */
}

.features {
  text-align: left;
  margin-bottom: 1.5rem;
}

.features li {
  margin-bottom: 0.5rem;
}

.buy-btn {
  background: linear-gradient(90deg, #10B981, #1F3C88);
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
}

.buy-btn:hover {
  opacity: 0.9;
}
</style>
