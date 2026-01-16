<template>
  <SectionWrapper>
    <section class="tier-page">
      <h2>Ultra Premium: Digital Product Request</h2>
      <form @submit.prevent="submitRequest">
        <label>
          Your Name:
          <input type="text" v-model="name" required />
        </label>
        <label>
          Email:
          <input type="email" v-model="email" required />
        </label>
        <label>
          Idea Description:
          <textarea v-model="idea" required></textarea>
        </label>
        <PrimaryButton type="submit">Submit Request</PrimaryButton>
      </form>
      <p v-if="submitted" class="submitted-msg">Our team will review your request.</p>
    </section>
  </SectionWrapper>
</template>

<script setup lang="ts">
import SectionWrapper from '@/components/layout/SectionWrapper.vue'
import PrimaryButton from '@/components/ui/PrimaryButton.vue'
import { ref } from 'vue'

const name = ref('')
const email = ref('')
const idea = ref('')
const submitted = ref(false)

function submitRequest() {
  submitted.value = true

}
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';

.tier-page {
  padding: $space-xl;
  max-width: 600px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;

  h2 {
    font-family: $font-heading;
    font-weight: 700;
    font-size: 2.5rem;
    margin-bottom: $space-lg;
    background: linear-gradient(90deg, $primary, $secondary);
    -webkit-background-clip: text;
    color: transparent;
    text-align: center;
  }

  form label {
    font-family: $font-heading;
    font-weight: 200;
    color: $text-muted;
    font-size: 1rem;
    line-height: 1.5;

    input, textarea {
      font-family: $font-heading;
      font-weight: 200;
      font-size: 1rem;
      line-height: 1.5;
    }
  }

  .submitted-msg {
    font-family: $font-heading;
    font-weight: 200;
    font-size: 1rem;
    color: $accent;
    margin-top: $space-md;
    text-align: center;
    line-height: 1.5;
  }

  button {
    @include hover-glow;
  }
}

</style>
