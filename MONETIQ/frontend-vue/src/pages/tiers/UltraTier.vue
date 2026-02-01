<template>
  <SectionWrapper>
    <section class="tier-page">
      <h2>Ultra Premium: Digital Product Request</h2>

      <form v-if="!submitted" @submit.prevent="submitRequest">
        <label>
          Creator username / handle:
          <input
              type="text"
              v-model="name"
              required
              placeholder="@mark.art"
          />
        </label>

        <label>
          Email:
          <input
              type="email"
              v-model="email"
              required
              placeholder="mark@gmail.com"
          />
        </label>

        <label>
          Idea Description:
          <textarea
              v-model="idea"
              required
              placeholder="Describe your digital product idea..."
          ></textarea>
        </label>

        <p v-if="error" class="error-msg">{{ error }}</p>

        <PrimaryButton type="submit" :disabled="loading">
          {{ loading ? 'Sending...' : 'Submit Request' }}
        </PrimaryButton>
      </form>

      <div v-else class="submitted-msg">
        Our team will review your request and contact you soon.

        <PrimaryButton class="mt-btn" type="button" @click="resetForm">
          Send another request
        </PrimaryButton>
      </div>
    </section>
  </SectionWrapper>
</template>

<script setup lang="ts">
import SectionWrapper from '@/components/layout/SectionWrapper.vue'
import PrimaryButton from '@/components/ui/PrimaryButton.vue'
import { ref } from 'vue'
import api from '@/api/axios'

const name = ref('')
const email = ref('')
const idea = ref('')
const submitted = ref(false)

const loading = ref(false)
const error = ref('')

function validate() {
  error.value = ''

  if (name.value.trim().length < 3) {
    error.value = 'Username is too short.'
    return false
  }
  if (!email.value.includes('@')) {
    error.value = 'Invalid email.'
    return false
  }
  if (idea.value.trim().length < 5) {
    error.value = 'Idea is too short.'
    return false
  }

  return true
}

async function submitRequest() {
  if (loading.value) return
  if (!validate()) return

  loading.value = true
  try {
    await api.post('/ultra/request', {
      username: name.value.trim(),
      description: `Email: ${email.value.trim()}\n\nIdea: ${idea.value.trim()}`,
    })

    submitted.value = true
  } catch (e) {
    console.error(e)
    error.value = 'Neuspešno slanje zahteva.'
  } finally {
    loading.value = false
  }
}

function resetForm() {
  name.value = ''
  email.value = ''
  idea.value = ''
  submitted.value = false
  error.value = ''
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
    display: flex;
    flex-direction: column;
    font-family: $font-heading;
    font-weight: 200;
    color: $text-muted;
    font-size: 1rem;
    line-height: 1.5;
    width: 100%;
    margin-bottom: $space-md;

    input, textarea {
      margin-top: $space-xs;
      padding: $space-sm;
      border-radius: 0.5rem;
      border: 1px solid rgba($text-main, 0.3);
      font-family: $font-heading;
      font-weight: 200;
      font-size: 1rem;
    }

    textarea {
      min-height: 110px;
      resize: vertical;
    }
  }

  .error-msg {
    width: 100%;
    font-family: $font-heading;
    font-weight: 200;
    font-size: 0.95rem;
    color: #ff6b6b;
    margin-bottom: $space-sm;
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

  .mt-btn {
    margin-top: $space-md;
  }

  button {
    @include hover-glow;
  }
}
</style>
