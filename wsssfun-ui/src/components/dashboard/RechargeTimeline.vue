<template>
  <div>
    <h3 class="card-title">近一个月充值记录</h3>
    <div v-if="filteredData.length > 0" class="timeline-wrapper">
      <el-timeline>
        <el-timeline-item
          v-for="(event, index) in filteredData"
          :key="index"
          :timestamp="formatTime(event.querytime)"
          placement="top"
          color="#667eea"
          :hollow="true"
        >
          <div class="timeline-content">
            <span class="recharge-amount">+{{ event.changeValue }} kWh</span>
          </div>
        </el-timeline-item>
      </el-timeline>
    </div>
    <div v-else class="empty-state">
      <el-icon :size="28" color="#cbd5e0"><Calendar /></el-icon>
      <span>暂无充值记录</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import dayjs from 'dayjs'
import http from '@/utils/axios'
import { API_ROUTES } from '@/config/api'
import { Calendar } from '@element-plus/icons-vue'

const filteredData = ref<any[]>([])

const formatTime = (time: string) => {
  return dayjs(time).format('MM/DD HH:mm')
}

const fetchData = async () => {
  try {
    const userId = sessionStorage.getItem('userId')
    const response = await http.get(API_ROUTES.allPower, { params: { userId } })
    const allPowerData = response.data
    const now = dayjs()

    filteredData.value = allPowerData
      .filter((item: any) => item.changeValue > 0)
      .filter((item: any) => dayjs(item.querytime).isAfter(now.subtract(1, 'month')))
      .slice(0, -3)
      .reverse()
  } catch (error) {
    console.error('获取充值数据失败', error)
  }
}

onMounted(fetchData)
</script>

<style scoped>
.card-title {
  margin: 0 0 20px;
  font-size: 16px;
  font-weight: 600;
  color: #2d3748;
}

.timeline-wrapper {
  max-height: 400px;
  overflow-y: auto;
  padding-right: 8px;
}

.timeline-wrapper::-webkit-scrollbar {
  width: 4px;
}

.timeline-wrapper::-webkit-scrollbar-track {
  background: transparent;
}

.timeline-wrapper::-webkit-scrollbar-thumb {
  background: #e2e8f0;
  border-radius: 4px;
}

.timeline-content {
  display: flex;
  align-items: center;
  gap: 8px;
}

.recharge-amount {
  font-size: 15px;
  font-weight: 700;
  color: #43e97b;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  padding: 40px 0;
  color: #a0aec0;
  font-size: 14px;
}

:deep(.el-timeline-item__timestamp) {
  color: #718096;
  font-size: 12px;
}

:deep(.el-timeline-item__wrapper) {
  padding-left: 20px;
}
</style>
