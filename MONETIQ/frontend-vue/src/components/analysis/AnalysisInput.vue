<template>
  <section class="analysis-input">
    <h2>Enter Instagram Profile</h2>
    <form @submit.prevent="submit">
      <input
          v-model="username"
          type="text"
          placeholder="Instagram username"
          :disabled="disabled"
          required
      />
      <button type="submit" :disabled="disabled">
        {{ disabled ? 'Analyzing...' : 'Analyze' }}
      </button>
    </form>
  </section>
</template>

<script lang="ts" setup>
import { ref, computed } from 'vue'
import { useAnalysisStore } from '@/stores/analysisStore'

const username = ref('')
const analysisStore = useAnalysisStore()

const disabled = computed(() => analysisStore.loading)

const submit = () => {
  if (!username.value) return
  analysisStore.startAnalysis(username.value)
  username.value = ''
}
</script>

<style scoped lang="scss">
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';
@import '@/assets/styles/animations';
@import '@/assets/styles/base';

.analysis-input {
  width: 100%;
  max-width: 400px;
  margin-bottom: $space-lg;
  @include glass;
  padding: $space-md;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: $space-md;
  animation: fadeIn 0.5s ease forwards;

  h2 {
    font-family: $font-heading;
    font-size: 1.75rem;
    text-align: center;
    @include gradient-text($primary, $secondary);
  }

  form {
    display: flex;
    width: 100%;
    gap: $space-sm;

    input {
      flex: 1;
      padding: $space-sm;
      border-radius: $radius-sm;
      border: 1px solid rgba(255,255,255,0.2);
      background: rgba(255,255,255,0.05);
      color: $text-main;
      font-family: $font-base;
      transition: 0.2s;

      &:focus {
        outline: none;
        border-color: $primary;
        box-shadow: 0 0 10px rgba($primary, 0.5);
      }

      &:disabled {
        opacity: 0.6;
        cursor: not-allowed;
      }
    }

    button {
      padding: $space-sm $space-md;
      border: none;
      border-radius: $radius-sm;
      background-color: $primary;
      color: #fff;
      cursor: pointer;
      font-weight: 500;
      transition: 0.2s;

      &:hover {
        background-color: $secondary;
      }

      &:disabled {
        opacity: 0.6;
        cursor: not-allowed;
      }
    }
  }
}
</style>
