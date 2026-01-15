<template>
  <div class="ultra-premium">
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
  </div>
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

<style scoped>
.ultra-premium {
  padding: 2rem;
}
form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  max-width: 400px;
}
input, textarea {
  padding: 0.5rem;
  border-radius: 0.25rem;
  border: 1px solid #ddd;
}
button {
  background: #10B981;
  color: white;
  border: none;
  padding: 0.7rem;
  border-radius: 0.25rem;
  cursor: pointer;
}
button:hover {
  background: #1E3A8A;
}
.submitted-msg {
  margin-top: 1rem;
  color: #F5B301;
}
</style>
