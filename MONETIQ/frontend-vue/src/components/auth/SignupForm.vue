<template>
  <section class="signup-section">
    <img src="@/assets/logo-icon.svg" alt="MONETIQ AI" class="signup-logo" />
    <h1>
      MONETIQ <span>AI</span>
    </h1>
    <p class="subtitle">Create your account to start analyzing Instagram profiles</p>

    <form class="signup-form" @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="email">Email</label>
        <input v-model="email" type="email" id="email" placeholder="you@example.com" required />
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <input v-model="password" type="password" id="password" placeholder="••••••••" required />
      </div>

      <div class="form-group">
        <label for="confirmPassword">Confirm Password</label>
        <input v-model="confirmPassword" type="password" id="confirmPassword" placeholder="••••••••" required />
      </div>

      <PrimaryButton type="submit" class="primary-btn" :disabled="loading">
        {{ loading ? 'Signing up...' : 'Sign Up' }}
      </PrimaryButton>
    </form>

    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </section>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'
import PrimaryButton from '@/components/ui/PrimaryButton.vue'

const authStore = useAuthStore()
const router = useRouter()

const email = ref('')
const password = ref('')
const confirmPassword = ref('')
const loading = ref(false)
const errorMessage = ref('')

const handleSubmit = async () => {
  errorMessage.value = ''
  if (password.value !== confirmPassword.value) {
    errorMessage.value = 'Passwords do not match'
    return
  }
  loading.value = true
  try {
    await authStore.signup({ email: email.value, password: password.value })
    router.push('/analyze')
  } catch (err: any) {
    errorMessage.value = err.message || 'Signup failed'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped lang="scss">
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';
@import '@/assets/styles/animations';

.signup-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  padding: $space-xl 1rem;
  animation: fadeIn 1s ease forwards;

  .signup-logo {
    width: 160px;
    animation: float 4s ease-in-out infinite;
    margin-bottom: $space-md;
  }

  h1 {
    font-size: 4rem;
    margin: $space-sm 0;
    @include gradient-text($primary, $secondary);

    span {
      font-size: 2rem;
      color: $accent;
      vertical-align: super;
    }
  }

  .subtitle {
    font-family: $font-heading;
    font-weight: 200;
    font-size: 1.3rem;
    color: $text-muted;
    letter-spacing: 0.1em;
    margin: $space-md 0 $space-lg;
    animation: fadeIn 1s 0.4s ease forwards;
  }

  .signup-form {
    display: flex;
    flex-direction: column;
    gap: $space-md;
    width: 100%;
    max-width: 450px;
    background: rgba(255,255,255,0.05);
    padding: $space-lg;
    border-radius: 1rem;
    backdrop-filter: blur(10px);
    box-shadow: 0 8px 20px rgba(0,0,0,0.2);

    .form-group {
      display: flex;
      flex-direction: column;
      text-align: left;

      label {
        margin-bottom: $space-xs;
        font-weight: 500;
        font-size: 0.9rem;
        color: $text-main;
      }

      input {
        padding: $space-sm $space-md;
        border-radius: 0.5rem;
        border: 1px solid rgba($text-main, 0.3);
        background: rgba(255,255,255,0.05);
        color: $text-main;
        font-size: 1rem;

        &::placeholder {
          color: rgba($text-muted, 0.7);
        }

        &:focus {
          outline: none;
          border-color: $primary;
          background: rgba(255,255,255,0.1);
        }
      }
    }

    .primary-btn {
      @include hover-glow;
    }
  }

  .error {
    color: $accent;
    font-weight: 500;
    margin-top: $space-sm;
    text-align: center;
  }
}

/* Responsive tweaks */
@media (max-width: 480px) {
  .signup-form {
    padding: $space-md;
  }

  h1 {
    font-size: 3rem;

    span {
      font-size: 1.5rem;
    }
  }

  .subtitle {
    font-size: 1.1rem;
  }
}
</style>
