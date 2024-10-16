<template xmlns=''>
<!--  <AppHeader :is-logged-in="isLoggedIn"/>-->
      <router-view />
    <p class="website">
      <a href="https://beian.miit.gov.cn/#/Integrated/index">豫ICP备2024093362号-1</a>
    </p>
</template>

<script>
import {defineComponent} from 'vue';
import AppHeader from './components/AppHead.vue';

export default defineComponent({
  components: { AppHeader },
  data() {
    return {
      // 默认未登录
      isLoggedIn: false
    };
  },
  created() {
    // 检查是否已登录
    const token = localStorage.getItem('token');
    // 存在token则表示已登录
    if (token) {
      this.isLoggedIn = true;
    }
  }
})
</script>

<style>
.website {
  text-align: center;
  margin-top: -20px;
}
</style>