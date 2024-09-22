<template>
  <el-container style="height: 100vh;">
    <!-- 页头 -->
    <el-header>
      <el-row>
        <el-col :span="24">
          <h2>我的管理系统</h2>
        </el-col>
      </el-row>
    </el-header>

    <!-- 主体部分 -->
    <el-container>
      <!-- 侧边栏导航 -->
      <el-aside width="200px">
        <el-menu class="el-menu-vertical-demo" default-active="1">
          <el-menu-item index="1">首页</el-menu-item>
          <el-menu-item index="2">统计分析</el-menu-item>
          <el-menu-item index="3">用户管理</el-menu-item>
          <el-menu-item index="4">系统设置</el-menu-item>
        </el-menu>
      </el-aside>

      <!-- 内容区 -->
      <el-main>

        <!-- 卡片区域 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-card>
              <h3>卡片 1</h3>
              <p>内容描述</p>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card>
              <h3>卡片 2</h3>
              <p>内容描述</p>
            </el-card>
          </el-col>
        </el-row>

        <!-- 图表 -->
        <el-row :gutter="20">
          <el-col :span="24">
            <v-chart :option="chartOptions" style="height: 400px;"></v-chart>
          </el-col>
        </el-row>

        <!-- 表格 -->
        <el-row :gutter="20" style="margin-top: 20px;">
          <el-col :span="24">
            <el-table :data="tableData" style="width: 100%">
              <el-table-column label="日期" prop="date" width="180"/>
              <el-table-column label="名称" prop="name" width="180"/>
              <el-table-column label="数据值" prop="value"/>
            </el-table>
          </el-col>
        </el-row>

        <!-- 时间轴 -->
        <el-row :gutter="20" style="margin-top: 20px;">
          <el-col :span="24">
            <el-timeline>
              <el-timeline-item v-for="item in timelineData" :key="item.date" :timestamp="item.date">
                {{ item.content }}
              </el-timeline-item>
            </el-timeline>
          </el-col>
        </el-row>

      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import {ref} from 'vue';
import * as echarts from 'echarts/core'; // 引入核心模块
import {BarChart} from 'echarts/charts'; // 引入柱状图
import {GridComponent, TitleComponent, TooltipComponent} from 'echarts/components'; // 引入图表组件
import {CanvasRenderer} from 'echarts/renderers'; // 引入 Canvas 渲染器
import VChart from 'vue-echarts'; // 引入 VChart

// 注册 ECharts 所需组件和渲染器
echarts.use([TitleComponent, TooltipComponent, GridComponent, BarChart, CanvasRenderer]);
// 表格数据
const tableData = ref([
  {date: '2024-09-18', name: '周一', value: 5},
  {date: '2024-09-19', name: '周二', value: 20},
  {date: '2024-09-20', name: '周三', value: 36},
  {date: '2024-09-21', name: '周四', value: 10},
  {date: '2024-09-22', name: '周五', value: 10},
  {date: '2024-09-23', name: '周六', value: 20},
  {date: '2024-09-24', name: '周日', value: 30},
]);

// 图表配置
const chartOptions = ref({
  title: {text: '每周消费统计'},
  tooltip: {},
  xAxis: {data: tableData.value.map(item => item.name)}, // 使用表格中的name字段作为x轴
  yAxis: {},
  series: [{type: 'bar', data: tableData.value.map(item => item.value)}], // 使用表格中的value字段作为数据
});



// 时间轴数据
const timelineData = ref([
  { date: '2024-09-01', content: '事件1' },
  { date: '2024-09-05', content: '事件2' },
  { date: '2024-09-10', content: '事件3' },
]);
</script>

<style scoped>
.el-header {
  background-color: #303133;
  color: white;
  text-align: center;
  line-height: 60px;
}

.el-main {
  padding: 20px;
}

.el-menu {
  height: 100%;
}
</style>


<!--<template>-->
<!--  <div class="common-layout">-->
<!--    <el-container class="full-height">-->
<!--      <el-header class="el-header">-->
<!--        <h1 class="h1">首页</h1>-->
<!--        <el-button type="danger" @click="logout">退出登录</el-button>-->
<!--        <p v-if="username" class="inline-text">用户名: {{ username }}</p>-->
<!--        <p v-if="roomDisplay" class="inline-text">房间信息: {{ roomDisplay }}</p>-->
<!--      </el-header>-->
<!--      <el-container class="full-height">-->
<!--        <el-aside class="el-aside" width="200px">-->
<!--          <div class="sidebar">-->
<!--            <el-button-->
<!--                :plain="currentView !== 'All'"-->
<!--                class="sidebar-button"-->
<!--                type="primary"-->
<!--                @click="setView('All')"-->
<!--            >-->
<!--              总览-->
<!--            </el-button>-->
<!--            <el-button-->
<!--                :plain="currentView !== 'InfoPower'"-->
<!--                class="sidebar-button"-->
<!--                type="primary"-->
<!--                @click="setView('InfoPower')"-->
<!--            >-->
<!--              详细用量-->
<!--            </el-button>-->
<!--          </div>-->
<!--        </el-aside>-->
<!--        <el-main class="el-main">-->
<!--          &lt;!&ndash; 动态组件渲染 &ndash;&gt;-->
<!--          <component :is="currentView"/>-->
<!--        </el-main>-->
<!--      </el-container>-->
<!--    </el-container>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--import {ref,onMounted} from 'vue';-->
<!--import {useUserStore} from '../store/user.js';-->
<!--import {useRouter} from 'vue-router';-->
<!--import axios from 'axios';-->
<!--import All from '@/components/menu/HelloWorld.vue'; // 示例组件1-->
<!--import latestTable from '@/components/menu/table/latestTable.vue'; // 示例组件2-->
<!--import InfoPower  from "@/components/menu/table/InfoPower.vue";-->

<!--export default {-->
<!--  components: {All, latestTable, InfoPower},-->
<!--  setup() {-->
<!--    const userStore = useUserStore();-->
<!--    const router = useRouter();-->
<!--    const currentView = ref('All'); // 默认视图组件1-->
<!--    const roomDisplay = ref('');-->

<!--    // 根据用户ID获取房间信息-->
<!--    const fetchRoomInfo = async (userId) => {-->
<!--      try {-->
<!--        const response = await axios.get(`http://localhost:8080/api/users/profile?userId=${userId}`);-->

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

<!--    const logout = () => {-->
<!--      sessionStorage.removeItem('token');-->
<!--      userStore.logout();-->
<!--      router.push('/login');-->
<!--    };-->

<!--    const setView = (view) => {-->
<!--      currentView.value = view;-->
<!--    };-->

<!--    return {-->
<!--      logout,-->
<!--      currentView,-->
<!--      setView,-->
<!--      roomDisplay,-->
<!--      username-->
<!--    };-->
<!--  }-->
<!--};-->
<!--</script>-->

<!--<style scoped>-->
<!--.full-height {-->
<!--  height: 100vh;-->
<!--}-->

<!--.el-header {-->
<!--  text-align: center;-->
<!--  background-color: #4000FF;-->
<!--  color: white;-->
<!--  display: flex;-->
<!--  align-items: center;-->
<!--  //justify-content: center;-->
<!--  gap: 10px; /* 调整h1和p之间的间距 */-->
<!--}-->

<!--.h1 {-->
<!--  text-align: right;-->
<!--  //display: inline-block;-->
<!--  margin: 0 auto;-->
<!--}-->

<!--.inline-text {-->
<!--  //float: right;-->
<!--  //display: inline-block;-->
<!--  margin: 0;-->
<!--  text-align: right;-->
<!--}-->

<!--.el-aside {-->
<!--  background-color: #ECEEF1;-->
<!--  display: flex;-->
<!--  flex-direction: column;-->
<!--  justify-content: flex-start;-->
<!--  padding-top: 20px;-->
<!--}-->

<!--.el-main {-->
<!--  background-color: #ffffff;-->
<!--  color: white;-->
<!--  display: flex;-->
<!--  justify-content: center;-->
<!--  align-items: center;-->
<!--  padding: 20px;-->
<!--  flex-grow: 1;-->
<!--}-->

<!--.sidebar {-->
<!--  display: flex;-->
<!--  flex-direction: column;-->
<!--  gap: 10px;-->
<!--  padding: 10px;-->
<!--}-->

<!--.sidebar-button {-->
<!--  width: 100%;-->
<!--}-->

<!--.common-layout {-->
<!--  display: flex;-->
<!--  flex-direction: column;-->
<!--  height: 100vh;-->
<!--}-->

<!--</style>-->

<!--&lt;!&ndash;            <p>用户名: {{ username }}</p>&ndash;&gt;-->
<!--&lt;!&ndash;            <p v-if="roomDisplay">房间信息: {{ roomDisplay }}</p>&ndash;&gt;-->
<!--&lt;!&ndash;            <el-button type="danger" @click="logout">退出登录</el-button>&ndash;&gt;-->

<!--&lt;!&ndash;<script>&ndash;&gt;-->
<!--&lt;!&ndash;import { ref, onMounted } from 'vue';&ndash;&gt;-->
<!--&lt;!&ndash;import {useUserStore} from '../store/user.js';&ndash;&gt;-->
<!--&lt;!&ndash;import {useRouter} from 'vue-router';&ndash;&gt;-->
<!--&lt;!&ndash;import axios from 'axios';&ndash;&gt;-->
<!--&lt;!&ndash;import LatestTable from "./table/latestTable.vue";&ndash;&gt;-->

<!--&lt;!&ndash;export default {&ndash;&gt;-->
<!--&lt;!&ndash;  components: {LatestTable},&ndash;&gt;-->
<!--&lt;!&ndash;  setup() {&ndash;&gt;-->
<!--&lt;!&ndash;    // 使用 useUserStore 和 useRouter 获取全局状态和路由对象&ndash;&gt;-->
<!--&lt;!&ndash;    const userStore = useUserStore();&ndash;&gt;-->
<!--&lt;!&ndash;    const router = useRouter();&ndash;&gt;-->
<!--&lt;!&ndash;    const roomDisplay = ref('');&ndash;&gt;-->

<!--&lt;!&ndash;    // 根据用户ID获取房间信息&ndash;&gt;-->
<!--&lt;!&ndash;    const fetchRoomInfo = async (userId) => {&ndash;&gt;-->
<!--&lt;!&ndash;      try {&ndash;&gt;-->
<!--&lt;!&ndash;        const response = await axios.get(`http://localhost:8080/api/users/profile?userId=${userId}`);&ndash;&gt;-->
<!--&lt;!&ndash;        //&ndash;&gt;-->
<!--&lt;!&ndash;        roomDisplay.value = response.data.roomDisplay;&ndash;&gt;-->
<!--&lt;!&ndash;      } catch (error) {&ndash;&gt;-->
<!--&lt;!&ndash;        console.error('获取房间信息失败:', error);&ndash;&gt;-->
<!--&lt;!&ndash;      }&ndash;&gt;-->
<!--&lt;!&ndash;    };&ndash;&gt;-->

<!--&lt;!&ndash;    const username = sessionStorage.getItem('username');&ndash;&gt;-->

<!--&lt;!&ndash;    // 初始化，确保登录后获取用户ID并请求房间信息&ndash;&gt;-->
<!--&lt;!&ndash;    onMounted(() => {&ndash;&gt;-->
<!--&lt;!&ndash;      // 从 sessionStorage 获取用户ID&ndash;&gt;-->
<!--&lt;!&ndash;      const userId = sessionStorage.getItem('userId');&ndash;&gt;-->
<!--&lt;!&ndash;      if (userId) {&ndash;&gt;-->
<!--&lt;!&ndash;        fetchRoomInfo(userId);&ndash;&gt;-->
<!--&lt;!&ndash;      } else {&ndash;&gt;-->
<!--&lt;!&ndash;        console.error('用户ID未找到');&ndash;&gt;-->
<!--&lt;!&ndash;      }&ndash;&gt;-->
<!--&lt;!&ndash;    });&ndash;&gt;-->

<!--&lt;!&ndash;    return {&ndash;&gt;-->
<!--&lt;!&ndash;      logout, // 将 logout 返回到模板中&ndash;&gt;-->
<!--&lt;!&ndash;      roomDisplay,&ndash;&gt;-->
<!--&lt;!&ndash;      username&ndash;&gt;-->


