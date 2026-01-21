<template>
  <section class="signup-section">
    <img src="@/assets/logo-icon.svg" alt="MONETIQ AI" class="signup-logo" />
    <h1>
      MONETIQ <span>AI</span>
    </h1>
    <p class="subtitle">Create your account to start analyzing Instagram profiles</p>

    <form class="signup-form" @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="name">Full Name</label>
        <input v-model="name" type="text" id="name" placeholder="John Doe" required />
      </div>

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

      <div class="form-group checkbox-group">
        <label>
          <input type="checkbox" v-model="acceptedTerms" required />
          I agree to the <router-link to="/terms">Terms of Service</router-link> and <router-link to="/privacy">Privacy Policy</router-link>.
        </label>
      </div>

      <div class="form-group checkbox-group">
        <label>
          <input type="checkbox" v-model="marketingOptIn" />
          I want to receive occasional product updates and emails.
        </label>
      </div>

      <PrimaryButton type="submit" class="primary-btn" :disabled="loading">
        {{ loading ? 'Signing up...' : 'Sign Up' }}
      </PrimaryButton>
    </form>

    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </section>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'
import { useUserStore } from '@/stores/userStore'
import PrimaryButton from '@/components/ui/PrimaryButton.vue'

interface SignupPayload {
  name: string
  email: string
  password: string
  marketingOptIn: boolean
}

const authStore = useAuthStore()
const userStore = useUserStore()
const router = useRouter()

const name = ref('')
const email = ref('')
const password = ref('')
const confirmPassword = ref('')
const acceptedTerms = ref(false)
const marketingOptIn = ref(false)
const loading = ref(false)
const errorMessage = ref('')

const handleSubmit = async () => {
  errorMessage.value = ''

  if (password.value !== confirmPassword.value) {
    errorMessage.value = 'Passwords do not match'
    return
  }

  if (!acceptedTerms.value) {
    errorMessage.value = 'You must accept the terms and privacy policy'
    return
  }

  loading.value = true
  try {
    // Signup
    const payload: SignupPayload = {
      name: name.value,
      email: email.value,
      password: password.value,
      marketingOptIn: marketingOptIn.value
    }
    await authStore.signup(payload)

    await userStore.fetchMe()

    await router.push('/analyze')
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
    max-width: 480px;
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

        a {
          color: $primary;
          text-decoration: underline;
          &:hover { color: $secondary; }
        }
      }

      input[type="text"],
      input[type="email"],
      input[type="password"] {
        padding: $space-sm $space-md;
        border-radius: 0.5rem;
        border: 1px solid rgba($text-main, 0.3);
        background: rgba(255,255,255,0.05);
        color: $text-main;
        font-size: 1rem;

        &::placeholder { color: rgba($text-muted, 0.7); }

        &:focus {
          outline: none;
          border-color: $primary;
          background: rgba(255,255,255,0.1);
        }
      }

      &.checkbox-group {
        flex-direction: row;
        align-items: center;
        gap: $space-xs;

        input[type="checkbox"] {
          width: 1rem;
          height: 1rem;
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

@media (max-width: 480px) {
  .signup-form { padding: $space-md; }

  h1 { font-size: 3rem; span { font-size: 1.5rem; } }
  .subtitle { font-size: 1.1rem; }
}
</style>
