<template>
  <el-container style="height: 100%;">
    <!-- 页头 -->
    <el-header>
      <el-row>
        <el-col :span="21">
          <h2>校园电费管理系统</h2>
        </el-col>
        <el-col :span="3" class="user-info" style="text-align: right;">
          <p class="inline-text">用户名: {{ username }}</p>
          <p class="inline-text room-text">房间信息: {{ roomDisplay }}</p>
        </el-col>
      </el-row>
    </el-header>

    <!-- 主体部分 -->
    <el-container>
      <!-- 侧边栏导航 -->
      <el-aside width="200px">
        <el-menu class="el-menu-vertical-demo" default-active="1">
          <el-menu-item index="1">首页</el-menu-item>
          <el-menu-item index="2">详细数据</el-menu-item>
          <el-menu-item index="3">用户管理</el-menu-item>
        </el-menu>
      </el-aside>

      <!-- 内容区 -->
      <el-main>
        <!-- 图表区域 -->
        <el-row :gutter="20">
          <!-- 柱状图 -->
          <el-col :span="12">
            <div ref="chartContainer" style="height: 400px;"></div>
          </el-col>

          <!-- 玫瑰图 -->
          <el-col :span="12">
            <div ref="roseChartContainer" style="height: 400px;"></div>
          </el-col>
        </el-row>

        <!-- 表格和时间轴 -->
        <el-row :gutter="20" style="margin-top: 20px;">
          <!-- 表格 -->
          <el-col :span="16">
            <el-table :data="paginatedData" style="width: 100%">
              <el-table-column label="时间" prop="querytime" width="180"/>
              <el-table-column label="电量" prop="value"/>
              <el-table-column label="电量变化" prop="changeValue"/>
            </el-table>
          </el-col>

          <!-- 时间轴 -->
          <el-col :span="8">
            <h3>近一个月充值记录</h3>
            <el-timeline>
              <el-timeline-item
                  v-for="(event, index) in filteredRechargeData"
                  :key="index"
                  :timestamp="event.querytime"
                  placement="top"
              >
                充值: {{ event.changeValue }} kWh
              </el-timeline-item>
            </el-timeline>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import {useUserStore} from '../store/user.js';
import axios from 'axios'; // 引入 Axios 库
import * as echarts from 'echarts'; // 引入 ECharts
import dayjs from 'dayjs';
import {useRouter} from "vue-router"; // 引入 dayjs 进行日期处理

const tableData = ref([]);
const paginatedData = ref([]);
const currentPage = ref(1);
const pageSize = 9;
const chartContainer = ref(null);
const roseChartContainer = ref(null);
const rechargeData = ref([]);
const filteredRechargeData = ref([]); // 筛选的近一个月的充值数据



// 用户信息
const userInfo = ref({
  username: '',
  roomDisplay: ''
});

// 获取用户名和房间信息
const fetchRoomInfo = async (userId) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/users/profile?userId=${userId}`);
    userInfo.value.roomDisplay = response.data.roomDisplay;
  } catch (error) {
    console.error('获取房间信息失败:', error);
  }
};

// 获取用户数据
const username = sessionStorage.getItem('username');
const userId = sessionStorage.getItem('userId');

// 初始化时获取数据
onMounted(() => {
  userInfo.value.username = username || '未登录';
  if (userId) {
    fetchRoomInfo(userId);
  } else {
    console.error('用户ID未找到');
  }
});


const paginateData = () => {
  const start = (currentPage.value - 1) * pageSize;
  const end = start + pageSize;
  paginatedData.value = tableData.value.slice(start, end);
};

// 处理后端数据，将同一天的数据合并
const processChartData = (data) => {
  const groupedData = {};
  const timeSlotData = {
    '00:00-05:59': 0,
    '06:00-11:59': 0,
    '12:00-17:59': 0,
    '18:00-23:59': 0,
  };

  data.forEach(item => {
    const day = item.querytime.split(' ')[0]; // 提取日期
    const hour = parseInt(item.querytime.split(' ')[1].split(':')[0], 10); // 提取小时
    const valueChange = parseFloat(item.changeValue.toFixed(2));

    // 汇总电量值，按天汇总
    if (!groupedData[day]) {
      groupedData[day] = {
        valueLast: parseFloat(item.value.toFixed(2)),
        rechargeSum: 0,
        consumptionSum: 0,
      };
    }
    groupedData[day].valueLast = parseFloat(item.value.toFixed(2));
    if (item.changeValue > 0) {
      groupedData[day].rechargeSum += valueChange;
    } else {
      groupedData[day].consumptionSum += Math.abs(valueChange);
    }

    // 根据小时划分时段并统计消耗值
    if (item.changeValue < 0) {
      if (hour >= 0 && hour < 6) {
        timeSlotData['00:00-05:59'] += Math.abs(valueChange);
      } else if (hour >= 6 && hour < 12) {
        timeSlotData['06:00-11:59'] += Math.abs(valueChange);
      } else if (hour >= 12 && hour < 18) {
        timeSlotData['12:00-17:59'] += Math.abs(valueChange);
      } else if (hour >= 18 && hour <= 23) {
        timeSlotData['18:00-23:59'] += Math.abs(valueChange);
      }
    }
  });

  return {
    chartData: Object.keys(groupedData).map(day => ({
      name: day,
      valueLast: groupedData[day].valueLast,
      rechargeSum: groupedData[day].rechargeSum,
      consumptionSum: groupedData[day].consumptionSum,
    })),
    roseChartData: Object.keys(timeSlotData).map(slot => ({
      timeSlot: slot,
      value: timeSlotData[slot].toFixed(2),
    })),
  };
};


// 处理时间轴数据，倒序显示并移除最后三个数据
const processTimelineData = (data) => {
  return data
      .filter(item => item.changeValue > 0)  // 只保留充值数据
      .slice(0, -3)                          // 移除最后三条数据
      .reverse();                            // 倒序显示
};

// 获取并处理充值时间轴的数据
const fetchRechargeData = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/all-power');
    const allPowerData = response.data;

    // 筛选出近一个月的充值数据
    const now = dayjs();
    filteredRechargeData.value = processTimelineData(allPowerData.filter(item => {
      const queryDate = dayjs(item.querytime);
      return queryDate.isAfter(now.subtract(1, 'month'));
    }));
  } catch (error) {
    console.error('获取充值数据失败', error);
  }
};

const fetchData = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/recent-week-power');
    const weekPowerData = response.data;

    tableData.value = weekPowerData;
    paginateData();

    const {chartData, roseChartData} = processChartData(weekPowerData);

    // 柱状图
    const chartInstance = echarts.init(chartContainer.value);
    const chartOptions = {
      title: {text: '近一周电量变化统计'},
      tooltip: {
        trigger: 'axis',
        axisPointer: {type: 'shadow'}
      },
      legend: {data: ['总电量', '充值', '消耗']},
      xAxis: {type: 'category', data: chartData.map(item => item.name)},
      yAxis: {type: 'value'},
      series: [
        {
          name: '总电量',
          type: 'bar',
          data: chartData.map(item => item.valueLast),
        },
        {
          name: '充值',
          type: 'bar',
          data: chartData.map(item => item.rechargeSum),
        },
        {
          name: '消耗',
          type: 'bar',
          data: chartData.map(item => item.consumptionSum),
        }
      ]
    };
    chartInstance.setOption(chartOptions);

    // 玫瑰图
    const roseChartInstance = echarts.init(roseChartContainer.value);
    const roseChartOptions = {
      title: {text: '一天中用量最多的时间段'},
      tooltip: {trigger: 'item'},
      series: [
        {
          name: '消耗量',
          type: 'pie',
          radius: [20, 100],
          roseType: 'area',
          data: roseChartData.map(item => ({
            value: item.value,
            name: item.timeSlot,
          })),
        },
      ],
    };
    roseChartInstance.setOption(roseChartOptions);
  } catch (error) {
    console.error('获取电量数据失败', error);
  }
};

onMounted(() => {
  fetchData();
  fetchRechargeData(); // 获取时间轴数据
});


</script>

<style scoped>
html, body {
  height: 100%;
  margin: 0;
  padding: 0;
  overflow: hidden;
}

.el-container {
  height: 100%;
  width: 100%;
  overflow: hidden;
  display: flex;
}

.el-main {
  flex-grow: 1;
  overflow: auto;
}

.el-aside {
  width: 200px;
}

[ref="chartContainer"], [ref="roseChartContainer"] {
  width: 100%;
  height: 100%; /* 让图表随容器自适应 */
}

h2 {
  margin: 0;
  font-size: 24px;
  text-align: center;
}

h3 {
  font-weight: bold;
  margin-bottom: 10px;
}

.el-header {
  background-color: #303133;
  color: white;
  text-align: center;
  line-height: 60px;
}

.el-menu {
  height: 100%;
  background-color: #3a3f47;
  color: #ffffff;
}

.el-menu-item {
  color: #ffffff;
}

.user-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.inline-text {
  margin: 0;
  padding: 0 10px 0 0;
}

.room-text {
  max-width: 100px; /* 根据实际情况调整宽度 */
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis; /* 显示省略号 */
  color: #409EFF; /* 调整文本颜色 */
  font-weight: bold;
}

</style>


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


