<template>
  <el-container class="home-container">
    <el-header>
      <h1>首页</h1>
    </el-header>
    <el-main>
      <p>成功登录</p>
      <el-button type="danger" @click="logout">退出登录</el-button>
    </el-main>
  </el-container>
</template>

<script>
import {useUserStore} from '../store/user.js';
import {useRouter} from 'vue-router';

export default {
  setup() {
    // 使用 useUserStore 和 useRouter 获取全局状态和路由对象
    const userStore = useUserStore();
    const router = useRouter();

    const logout = () => {
      // 清除 localStorage 中的 token
      localStorage.removeItem('token');

      // 更新全局状态，恢复为未登录状态
      userStore.logout();

      // 使用 router.push 进行路由跳转
      router.push('/login');
    };

    return {
      logout // 将 logout 返回到模板中
    };
  }
};
</script>

<style scoped>
.home-container {
  max-width: 600px;
  margin: 0 auto;
  text-align: center;
  padding: 50px 20px;
}

.el-header {
  background-color: #4000FF;
  color: white;
  padding: 20px 0;
}

.el-main {
  margin-top: 20px;
}

.el-button {
  margin-top: 20px;
}
</style>
