<template>
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
      <button type="submit">Submit Request</button>
    </form>
    <p v-if="submitted" class="submitted-msg">Our team will review your request.</p>
  </section>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const name = ref('')
const email = ref('')
const idea = ref('')
const submitted = ref(false)

function submitRequest() {
  submitted.value = true
  // later: call backend API
}
</script>

<style lang="scss" scoped>
@import "../assets/styles/variables.scss";

.tier-page {
  padding: $spacing-xl;
  max-width: 600px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;

  h2 {
    font-family: $font-heading;
    font-size: 2.5rem;
    font-weight: 700;
    margin-bottom: $spacing-lg;
    background: linear-gradient(90deg, $primary, $secondary);
    -webkit-background-clip: text;
    color: transparent;
    text-align: center;
  }

  form {
    display: flex;
    flex-direction: column;
    width: 100%;
    gap: $spacing-md;

    label {
      display: flex;
      flex-direction: column;
      font-weight: 500;
      color: $text-main;

      input, textarea {
        margin-top: $spacing-xs;
        padding: $spacing-sm;
        border-radius: 0.25rem;
        border: 1px solid $border-soft;
        font-size: 1rem;
      }

      textarea {
        resize: vertical;
        min-height: 100px;
      }
    }

    button {
      padding: 0.75rem;
      border-radius: 0.5rem;
      border: none;
      font-weight: 600;
      background: linear-gradient(90deg, $primary, $secondary);
      color: white;
      cursor: pointer;

      &:hover {
        opacity: 0.9;
      }
    }
  }

  .submitted-msg {
    margin-top: $spacing-md;
    color: $accent;
    font-weight: 500;
  }
}
</style>
