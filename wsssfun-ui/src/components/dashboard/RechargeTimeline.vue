<template>
  <div>
    <h3 class="card-title">近一个月充值记录</h3>
    <el-skeleton :loading="loading" animated :rows="6">
      <template #template>
        <div style="padding: 8px 0;">
          <div v-for="i in 6" :key="i" style="display: flex; align-items: center; gap: 12px; margin-bottom: 20px;">
            <el-skeleton-item variant="circle" style="width: 12px; height: 12px; flex-shrink: 0;" />
            <div style="flex: 1;">
              <el-skeleton-item variant="text" style="width: 60%; height: 14px; margin-bottom: 6px;" />
              <el-skeleton-item variant="text" style="width: 40%; height: 18px;" />
            </div>
          </div>
        </div>
      </template>
      <template #default>
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
      </template>
    </el-skeleton>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import dayjs from 'dayjs'
import http from '@/utils/axios'
import { API_ROUTES } from '@/config/api'
import { Calendar } from '@element-plus/icons-vue'

const filteredData = ref<any[]>([])
const loading = ref(true)

const formatTime = (time: string) => {
  return dayjs(time).format('MM/DD HH:mm')
}

const fetchData = async () => {
  try {
    loading.value = true
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
  } finally {
    loading.value = false
  }
}

onMounted(fetchData)
</script>

<style scoped>
.card-title {
  margin: 0 0 20px;
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
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
  color: var(--text-muted);
  font-size: 14px;
}

:deep(.el-timeline-item__timestamp) {
  color: var(--text-secondary);
  font-size: 12px;
}

:deep(.el-timeline-item__wrapper) {
  padding-left: 20px;
}

:deep(.el-timeline-item__tail) {
  border-left-color: var(--card-border);
}
</style>
