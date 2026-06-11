<template>
  <router-view />
  <p class="website">
    <a href="https://beian.miit.gov.cn/#/Integrated/index">豫ICP备2024093362号-1</a>
  </p>
</template>

<script setup lang="ts">
</script>

<style>
*,
*::before,
*::after {
  box-sizing: border-box;
}

:root {
  --bg-gradient: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  --text-primary: #2d3748;
  --text-secondary: #718096;
  --text-muted: #a0aec0;
  --card-bg: rgba(255, 255, 255, 0.95);
  --card-border: rgba(0, 0, 0, 0.05);
  --card-shadow: 0 8px 32px rgba(0, 0, 0, 0.08);
  --header-bg: rgba(255, 255, 255, 0.1);
  --header-border: rgba(255, 255, 255, 0.15);
  --sidebar-bg: rgba(255, 255, 255, 0.05);
  --sidebar-border: rgba(255, 255, 255, 0.08);
  --nav-hover-bg: rgba(255, 255, 255, 0.1);
  --nav-active-bg: rgba(255, 255, 255, 0.15);
  --badge-bg: rgba(255, 255, 255, 0.1);
  --btn-bg: rgba(255, 255, 255, 0.1);
  --btn-border: rgba(255, 255, 255, 0.2);
  --table-hover: #f8f0ff;
  --table-border: #f0f0f0;
  --skeleton-color: #f0f0f0;
  --skeleton-to: #e0e0e0;
}

html.dark {
  --bg-gradient: linear-gradient(135deg, #1a1a2e 0%, #16213e 100%);
  --text-primary: #e2e8f0;
  --text-secondary: #a0aec0;
  --text-muted: #718096;
  --card-bg: rgba(30, 30, 50, 0.9);
  --card-border: rgba(255, 255, 255, 0.08);
  --card-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
  --header-bg: rgba(255, 255, 255, 0.05);
  --header-border: rgba(255, 255, 255, 0.08);
  --sidebar-bg: rgba(0, 0, 0, 0.15);
  --sidebar-border: rgba(255, 255, 255, 0.05);
  --nav-hover-bg: rgba(255, 255, 255, 0.08);
  --nav-active-bg: rgba(255, 255, 255, 0.12);
  --badge-bg: rgba(255, 255, 255, 0.08);
  --btn-bg: rgba(255, 255, 255, 0.08);
  --btn-border: rgba(255, 255, 255, 0.12);
  --table-hover: rgba(102, 126, 234, 0.1);
  --table-border: rgba(255, 255, 255, 0.08);
  --skeleton-color: rgba(255, 255, 255, 0.06);
  --skeleton-to: rgba(255, 255, 255, 0.1);
}

html, body {
  margin: 0;
  padding: 0;
  min-height: 100vh;
  background: var(--bg-gradient);
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
}

#app {
  min-height: 100vh;
}

.website {
  text-align: center;
  padding: 16px 0;
}

.website a {
  color: rgba(255, 255, 255, 0.5);
  text-decoration: none;
  font-size: 12px;
  transition: color 0.2s;
}

.website a:hover {
  color: rgba(255, 255, 255, 0.8);
}
</style>
