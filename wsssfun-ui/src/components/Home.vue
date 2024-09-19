<template>
  <div class="common-layout">
    <el-container class="full-height">
      <el-header class="el-header">
        <h1 class="h1">首页</h1>
        <el-button type="danger" @click="logout">退出登录</el-button>
        <p v-if="username" class="inline-text">用户名: {{ username }}</p>
        <p v-if="roomDisplay" class="inline-text">房间信息: {{ roomDisplay }}</p>
      </el-header>
      <el-container class="full-height">
        <el-aside class="el-aside" width="200px">
          <div class="sidebar">
            <el-button
                :plain="currentView !== 'All'"
                class="sidebar-button"
                type="primary"
                @click="setView('All')"
            >
              总览
            </el-button>
            <el-button
                :plain="currentView !== 'InfoPower'"
                class="sidebar-button"
                type="primary"
                @click="setView('InfoPower')"
            >
              详细用量
            </el-button>
          </div>
        </el-aside>
        <el-main class="el-main">
          <!-- 动态组件渲染 -->
          <component :is="currentView"/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import {ref,onMounted} from 'vue';
import {useUserStore} from '../store/user.js';
import {useRouter} from 'vue-router';
import axios from 'axios';
import All from '@/components/menu/HelloWorld.vue'; // 示例组件1
import latestTable from '@/components/menu/table/latestTable.vue'; // 示例组件2
import InfoPower  from "@/components/menu/table/InfoPower.vue";

export default {
  components: {All, latestTable, InfoPower},
  setup() {
    const userStore = useUserStore();
    const router = useRouter();
    const currentView = ref('All'); // 默认视图组件1
    const roomDisplay = ref('');

    // 根据用户ID获取房间信息
    const fetchRoomInfo = async (userId) => {
      try {
        const response = await axios.get(`http://localhost:8080/api/users/profile?userId=${userId}`);

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

    const logout = () => {
      sessionStorage.removeItem('token');
      userStore.logout();
      router.push('/login');
    };

    const setView = (view) => {
      currentView.value = view;
    };

    return {
      logout,
      currentView,
      setView,
      roomDisplay,
      username
    };
  }
};
</script>

<style scoped>
.full-height {
  height: 100vh;
}

.el-header {
  text-align: center;
  background-color: #4000FF;
  color: white;
  display: flex;
  align-items: center;
  //justify-content: center;
  gap: 10px; /* 调整h1和p之间的间距 */
}

.h1 {
  text-align: right;
  //display: inline-block;
  margin: 0 auto;
}

.inline-text {
  //float: right;
  //display: inline-block;
  margin: 0;
  text-align: right;
}

.el-aside {
  background-color: #ECEEF1;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  padding-top: 20px;
}

.el-main {
  background-color: #ffffff;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  flex-grow: 1;
}

.sidebar {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 10px;
}

.sidebar-button {
  width: 100%;
}

.common-layout {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

</style>

<!--            <p>用户名: {{ username }}</p>-->
<!--            <p v-if="roomDisplay">房间信息: {{ roomDisplay }}</p>-->
<!--            <el-button type="danger" @click="logout">退出登录</el-button>-->

<!--<script>-->
<!--import { ref, onMounted } from 'vue';-->
<!--import {useUserStore} from '../store/user.js';-->
<!--import {useRouter} from 'vue-router';-->
<!--import axios from 'axios';-->
<!--import LatestTable from "./table/latestTable.vue";-->

<!--export default {-->
<!--  components: {LatestTable},-->
<!--  setup() {-->
<!--    // 使用 useUserStore 和 useRouter 获取全局状态和路由对象-->
<!--    const userStore = useUserStore();-->
<!--    const router = useRouter();-->
<!--    const roomDisplay = ref('');-->

<!--    // 根据用户ID获取房间信息-->
<!--    const fetchRoomInfo = async (userId) => {-->
<!--      try {-->
<!--        const response = await axios.get(`http://localhost:8080/api/users/profile?userId=${userId}`);-->
<!--        //-->
<!--        roomDisplay.value = response.data.roomDisplay;-->
<!--      } catch (error) {-->
<!--        console.error('获取房间信息失败:', error);-->
<!--      }-->
<!--    };-->

<!--    const username = sessionStorage.getItem('username');-->

<!--    // 初始化，确保登录后获取用户ID并请求房间信息-->
<!--    onMounted(() => {-->
<!--      // 从 sessionStorage 获取用户ID-->
<!--      const userId = sessionStorage.getItem('userId');-->
<!--      if (userId) {-->
<!--        fetchRoomInfo(userId);-->
<!--      } else {-->
<!--        console.error('用户ID未找到');-->
<!--      }-->
<!--    });-->

<!--    return {-->
<!--      logout, // 将 logout 返回到模板中-->
<!--      roomDisplay,-->
<!--      username-->


