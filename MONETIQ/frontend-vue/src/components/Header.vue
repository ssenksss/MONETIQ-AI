<template>
  <header class="header">
    <div class="logo">
      <img src="../assets/logo-icon.svg" alt="MONETIQ logo" />
      <h1>
        MONETIQ <span class="ai">AI</span>
      </h1>
    </div>
    <nav>
      <router-link to="/">Landing</router-link>
      <router-link to="/free">Free</router-link>
      <router-link to="/premium">Premium</router-link>
      <router-link to="/ultra">Ultra</router-link>
    </nav>
  </header>
</template>

<script setup lang="ts"></script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background: white;
}
.logo {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}
.logo h1 {
  font-weight: 700;
  background: linear-gradient(90deg, #10B981, #1E3A8A);
  -webkit-background-clip: text;
  color: transparent;
}
.logo .ai {
  font-size: 0.6em;
  color: #F5B301;
  margin-left: 0.2rem;
  letter-spacing: 1px;
}
nav a {
  margin-left: 1rem;
  text-decoration: none;
  color: #1E3A8A;
  font-weight: 500;
}
nav a:hover {
  color: #10B981;
}
</style>
