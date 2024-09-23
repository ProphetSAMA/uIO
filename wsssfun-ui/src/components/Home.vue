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
        <!-- 图表区域 -->
        <el-row :gutter="20">
          <el-col :span="24">
            <div ref="chartContainer" style="height: 400px;"></div>
          </el-col>
        </el-row>

        <!-- 表格 -->
        <el-row :gutter="20" style="margin-top: 20px;">
          <el-col :span="24">
            <el-table :data="tableData" style="width: 100%">
              <el-table-column label="时间" prop="querytime" width="180"/>
              <el-table-column label="电量" prop="value"/>
              <el-table-column label="电量变化" prop="changeValue"/>
            </el-table>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios'; // 引入 Axios 库
import * as echarts from 'echarts'; // 引入 ECharts

const tableData = ref([]);
const chartContainer = ref(null);

// 处理后端数据，将同一天的数据合并
const processChartData = (data) => {
  const groupedData = {};

  data.forEach(item => {
    const day = item.querytime.split(' ')[0]; // 提取日期部分
    if (!groupedData[day]) {
      groupedData[day] = {
        valueLast: parseFloat(item.value.toFixed(2)),   // 使用toFixed(2)确保精度并保留两位小数
        rechargeSum: 0,          // 初始化该日期的充值（正数）累加值
        consumptionSum: 0        // 初始化该日期的消耗（负数）累加值
      };
    }

    groupedData[day].valueLast = parseFloat(item.value.toFixed(2)); // 每次更新当天最新的 value
    if (item.changeValue > 0) {
      groupedData[day].rechargeSum += parseFloat(item.changeValue.toFixed(2));  // 累加充值，并保留精度
    } else {
      groupedData[day].consumptionSum += parseFloat(Math.abs(item.changeValue).toFixed(2)); // 累加消耗的绝对值
    }
  });

  return Object.keys(groupedData).map(day => ({
    name: day,
    valueLast: groupedData[day].valueLast,            // 当天的最新 value 值
    rechargeSum: groupedData[day].rechargeSum,        // 当天的充值总和
    consumptionSum: groupedData[day].consumptionSum   // 当天的消耗总和（正数显示）
  }));
};

const fetchData = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/recent-week-power');
    const weekPowerData = response.data;

    tableData.value = weekPowerData;

    const chartData = processChartData(weekPowerData);
    const chartInstance = echarts.init(chartContainer.value);

    const chartOptions = {
      title: { text: '近一周电量变化统计' },
      tooltip: {
        trigger: 'axis',
        axisPointer: { type: 'shadow' }
      },
      legend: { data: ['总电量', '充值', '消耗'] },
      xAxis: { type: 'category', data: chartData.map(item => item.name) },
      yAxis: { type: 'value' },
      series: [
        {
          name: '总电量',
          type: 'bar',
          data: chartData.map(item => item.valueLast), // 当天的最后一个 value
        },
        {
          name: '充值',
          type: 'bar',
          data: chartData.map(item => item.rechargeSum), // 当天的充值总和
        },
        {
          name: '消耗',
          type: 'bar',
          data: chartData.map(item => item.consumptionSum), // 当天的消耗总和（正数显示）
        }
      ]
    };

    chartInstance.setOption(chartOptions);
  } catch (error) {
    console.error('获取电量数据失败', error);
  }
};

onMounted(() => {
  fetchData();
});
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


