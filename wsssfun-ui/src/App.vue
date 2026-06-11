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

html, body {
  margin: 0;
  padding: 0;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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
