<template>
  <div class="common-layout">
    <el-container>
      <el-header class="el-header">
        <h1 class="h1">首页</h1>
      </el-header>
      <el-container>
        <el-aside class="el-aside" width="200px">Aside</el-aside>
        <el-main class="el-main">
          <div class="logout">
            <p>成功登录</p>
            <el-button type="danger" @click="logout">退出登录</el-button>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
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
.el-header {
  text-align: center;
  background-color: #4000FF;
  color: white;
}
.h1 {
  text-align: left;
  margin-top: 15px;
  margin-left: 50px;
}

.el-aside {
  background-color: #ECEEF1;
}

.el-main {
  background-color: #000000;
  margin: 0 auto;
}

.logout {
  text-align: center;
  margin-top: 100px;
}

</style>
