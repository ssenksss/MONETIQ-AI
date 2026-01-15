<template>
  <header class="header">
    <div class="logo">
      <img src="../assets/logo-icon.svg" alt="Logo" />
      <span class="brand">MONETIQ <em>AI</em></span>
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

<style lang="scss" scoped>
@import "../assets/styles/variables.scss";


.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: $spacing-md $spacing-xl;
  background: $bg-light;
  border-bottom: 1px solid $border-soft;

  .logo {
    display: flex;
    align-items: center;
    gap: $spacing-sm;

    img {
      width: 50px;
      height: 50px;
    }

    .brand {
      font-family: $font-heading;
      font-weight: 700;
      font-size: 1.5rem;
      background: linear-gradient(90deg, $primary, $secondary);
      -webkit-background-clip: text;
      color: transparent;

      em {
        font-style: normal;
        font-size: 0.7em;
        color: $accent;
        margin-left: $spacing-xs;
      }
    }
  }

  nav a {
    margin-left: $spacing-lg;
    text-decoration: none;
    color: $text-main;
    font-weight: 500;

    &.router-link-active {
      color: $primary;
    }

    &:hover {
      color: $accent;
    }
  }
}
</style>
