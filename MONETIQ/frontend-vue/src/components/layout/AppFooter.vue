
<template>
  <footer class="footer">
    <div class="footer-inner">
      <!-- Logo + pitch -->
      <div class="footer-logo">
        <img src="@/assets/logo-icon.svg" alt="MONETIQ AI" />
        <span>MONETIQ <em>AI</em></span>
        <p>AI-powered Instagram growth intelligence for serious creators and brands.</p>
      </div>


      <div class="footer-links">
        <div>
          <h4>Product</h4>
          <ul>
            <li><a href="#">How it works</a></li>
            <li><a href="#">Pricing</a></li>
            <li><a href="#">Free Plan</a></li>
            <li><a href="#">Premium</a></li>
          </ul>
        </div>

        <div>
          <h4>Company</h4>
          <ul>
            <li><a href="#">About</a></li>
            <li><a href="#">Careers</a></li>
            <li><a href="#">Contact</a></li>
          </ul>
        </div>

        <div>
          <h4>Legal</h4>
          <ul>
            <li><a href="#">Privacy</a></li>
            <li><a href="#">Terms</a></li>
            <li><a href="#">Security</a></li>
          </ul>
        </div>
      </div>

      <div class="footer-bottom">
        <div class="social">
          <a href="#"><i class="fab fa-twitter"></i></a>
          <a href="#"><i class="fab fa-linkedin"></i></a>
          <a href="#"><i class="fab fa-instagram"></i></a>
          <a href="mailto:hello@monetiq.ai"><i class="fas fa-envelope"></i></a>
        </div>
        <p>© 2026 MONETIQ AI — Trusted by serious creators worldwide</p>
      </div>
    </div>
  </footer>
</template>

<script setup lang="ts"></script>

<style lang="scss" scoped>
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';

.footer {
  @include glass;
  padding: $space-xl $space-lg;
  font-family: $font-heading;
  color: $text-main;

  .footer-inner { max-width: 1200px; margin: 0 auto; }

  .footer-logo {
    text-align: center;
    margin-bottom: $space-lg;

    img { width: 48px; margin-bottom: $space-xs; animation: float 4s ease-in-out infinite; }
    span {
      font-size: 1.8rem;
      font-weight: 700;
      @include gradient-text($primary, $secondary);

      em { font-style: normal; font-size: 0.6em; color: $accent; margin-left: 0.2rem; }
    }
    p { font-weight: 200; font-size: 0.8rem; color: $text-muted; margin-top: $space-xs; letter-spacing: 0.1em; }
  }

  .footer-links {
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: $space-lg;
    margin-bottom: $space-lg;

    h4 { font-size: 1.2rem; margin-bottom: $space-sm; color: $secondary; }

    ul { list-style: none; padding: 0; margin: 0; }

    li a {
      font-weight: 200;
      font-size: 0.75rem;
      text-decoration: none;
      color: $text-muted;
      display: block;
      margin-bottom: $space-xs;
      letter-spacing: 0.1em;
      transition: $transition;

      &:hover { color: $primary; transform: scale(1.05); }
    }
  }

  .footer-bottom {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    gap: $space-sm;
    border-top: 1px solid rgba($text-muted, 0.2);
    padding-top: $space-md;

    .social {
      display: flex;
      gap: $space-sm;

      a {
        font-size: 1.2rem;
        color: $text-muted;
        transition: $transition;

        &:hover { color: $primary; transform: scale(1.2); }
      }
    }

    p { font-size: 0.85rem; color: $text-muted; text-align: center; margin-top: $space-xs; }
  }
}
</style>
