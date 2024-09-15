<template>
  <header>
    <h2>uIO</h2>
    <p v-if="isLoggedIn">已登录: {{ username }}</p>
    <p v-else>未登录</p>
  </header>
</template>

<script>
import {computed} from 'vue';
import {useUserStore} from '../store/user.js';

export default {
  setup() {
    const userStore = useUserStore();

    // 使用 computed 让 isLoggedIn 和 username 自动响应变化
    const isLoggedIn = computed(() => userStore.isLoggedIn);
    const username = computed(() => userStore.username);

    return {isLoggedIn, username};
  }
};
</script>

<style scoped>
header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
}
</style>
