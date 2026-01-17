<template>
  <section class="login-section">
    <img src="@/assets/logo-icon.svg" alt="MONETIQ AI" class="login-logo" />
    <h1>
      MONETIQ <span>AI</span>
    </h1>
    <p class="subtitle">Sign in to access your Instagram analysis dashboard</p>

    <form class="login-form" @submit.prevent="handleSubmit">

      <div class="form-group">
        <label for="email">Email</label>
        <input v-model="email" type="email" id="email" placeholder="you@example.com" required />
      </div>


      <div class="form-group">
        <label for="password">Password</label>
        <input v-model="password" type="password" id="password" placeholder="••••••••" required />
      </div>

      <div class="form-group checkbox-group">
        <label>
          <input type="checkbox" v-model="rememberMe" />
          Remember me
        </label>
      </div>

      <PrimaryButton type="submit" class="primary-btn" :disabled="loading">
        {{ loading ? 'Signing in...' : 'Sign In' }}
      </PrimaryButton>
    </form>

    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    <p class="signup-link">
      Don't have an account? <router-link to="/signup">Sign up</router-link>
    </p>
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
const rememberMe = ref(false)
const loading = ref(false)
const errorMessage = ref('')

const handleSubmit = async () => {
  errorMessage.value = ''
  loading.value = true
  try {
    await authStore.login({
      email: email.value,
      password: password.value,
      rememberMe: rememberMe.value
    })
    router.push('/analyze')
  } catch (err: any) {
    errorMessage.value = err.message || 'Login failed'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped lang="scss">
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';
@import '@/assets/styles/animations';

.login-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  padding: $space-xl 1rem;
  animation: fadeIn 1s ease forwards;

  .login-logo {
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

  .login-form {
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

  .signup-link {
    margin-top: $space-md;
    color: $text-muted;
    a {
      color: $primary;
      text-decoration: underline;
      &:hover { color: $secondary; }
    }
  }
}

@media (max-width: 480px) {
  .login-form { padding: $space-md; }
  h1 { font-size: 3rem; span { font-size: 1.5rem; } }
  .subtitle { font-size: 1.1rem; }
}
</style>
