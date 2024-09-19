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
            <p style="color: #52f152; font-size: 28px">成功登录</p>
            <br>
            <p>用户名: {{ username }}</p>
            <p v-if="roomDisplay">房间信息: {{ roomDisplay }}</p>
            <el-button type="danger" @click="logout">退出登录</el-button>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import {useUserStore} from '../store/user.js';
import {useRouter} from 'vue-router';
import axios from 'axios';

export default {
  setup() {
    // 使用 useUserStore 和 useRouter 获取全局状态和路由对象
    const userStore = useUserStore();
    const router = useRouter();
    const roomDisplay = ref('');

    const logout = () => {
      // 清除 sessionStorage 中的 token
      sessionStorage.removeItem('token');

      // 更新全局状态，恢复为未登录状态
      userStore.logout();

      // 使用 router.push 进行路由跳转
      router.push('/login');
    };

    // 根据用户ID获取房间信息
    const fetchRoomInfo = async (userId) => {
      try {
        const response = await axios.get(`http://localhost:8080/api/users/profile?userId=${userId}`);
        //
        roomDisplay.value = response.data.roomDisplay;
      } catch (error) {
        console.error('获取房间信息失败:', error);
      }
    };

    const username = sessionStorage.getItem('username');

    // 初始化，确保登录后获取用户ID并请求房间信息
    onMounted(() => {
      // 从 sessionStorage 获取用户ID
      const userId = sessionStorage.getItem('userId');
      if (userId) {
        fetchRoomInfo(userId);
      } else {
        console.error('用户ID未找到');
      }
    });

    return {
      logout, // 将 logout 返回到模板中
      roomDisplay,
      username
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
