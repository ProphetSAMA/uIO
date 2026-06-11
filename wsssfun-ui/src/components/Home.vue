<template>
  <AppLayout>
    <!-- Stat Cards -->
    <el-row :gutter="20" class="stat-row">
      <el-col :span="8">
        <div class="stat-card">
          <el-skeleton :loading="statsLoading" animated :rows="0">
            <template #template>
              <div style="display: flex; align-items: center; gap: 16px;">
                <el-skeleton-item variant="rect" style="width: 56px; height: 56px; border-radius: 16px; flex-shrink: 0;" />
                <div style="flex: 1;">
                  <el-skeleton-item variant="text" style="width: 60px; height: 13px; margin-bottom: 8px;" />
                  <el-skeleton-item variant="text" style="width: 100px; height: 28px;" />
                </div>
              </div>
            </template>
            <template #default>
              <div class="stat-icon balance-icon">
                <el-icon :size="28"><Lightning /></el-icon>
              </div>
              <div class="stat-content">
                <span class="stat-label">当前余额</span>
                <span class="stat-value">{{ currentBalance }} <small>kWh</small></span>
              </div>
            </template>
          </el-skeleton>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="stat-card">
          <el-skeleton :loading="statsLoading" animated :rows="0">
            <template #template>
              <div style="display: flex; align-items: center; gap: 16px;">
                <el-skeleton-item variant="rect" style="width: 56px; height: 56px; border-radius: 16px; flex-shrink: 0;" />
                <div style="flex: 1;">
                  <el-skeleton-item variant="text" style="width: 60px; height: 13px; margin-bottom: 8px;" />
                  <el-skeleton-item variant="text" style="width: 100px; height: 28px;" />
                </div>
              </div>
            </template>
            <template #default>
              <div class="stat-icon usage-icon">
                <el-icon :size="28"><TrendCharts /></el-icon>
              </div>
              <div class="stat-content">
                <span class="stat-label">今日消耗</span>
                <span class="stat-value">{{ todayUsage }} <small>kWh</small></span>
              </div>
            </template>
          </el-skeleton>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="stat-card">
          <el-skeleton :loading="statsLoading" animated :rows="0">
            <template #template>
              <div style="display: flex; align-items: center; gap: 16px;">
                <el-skeleton-item variant="rect" style="width: 56px; height: 56px; border-radius: 16px; flex-shrink: 0;" />
                <div style="flex: 1;">
                  <el-skeleton-item variant="text" style="width: 60px; height: 13px; margin-bottom: 8px;" />
                  <el-skeleton-item variant="text" style="width: 100px; height: 28px;" />
                </div>
              </div>
            </template>
            <template #default>
              <div class="stat-icon recharge-icon">
                <el-icon :size="28"><Wallet /></el-icon>
              </div>
              <div class="stat-content">
                <span class="stat-label">本周充值</span>
                <span class="stat-value">{{ weekRecharge }} <small>kWh</small></span>
              </div>
            </template>
          </el-skeleton>
        </div>
      </el-col>
    </el-row>

    <!-- Charts -->
    <el-row :gutter="20" class="chart-row">
      <el-col :span="12">
        <div class="glass-card">
          <PowerBarChart />
        </div>
      </el-col>
      <el-col :span="12">
        <div class="glass-card">
          <PowerRoseChart />
        </div>
      </el-col>
    </el-row>

    <!-- Table + Timeline -->
    <el-row :gutter="20" class="data-row">
      <el-col :span="16">
        <div class="glass-card">
          <h3 class="card-title">用电记录</h3>
          <PowerTable :page-size="8" :show-total="true" />
        </div>
      </el-col>
      <el-col :span="8">
        <div class="glass-card timeline-card">
          <RechargeTimeline />
        </div>
      </el-col>
    </el-row>
  </AppLayout>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import AppLayout from '@/components/layout/AppLayout.vue'
import PowerBarChart from '@/components/dashboard/PowerBarChart.vue'
import PowerRoseChart from '@/components/dashboard/PowerRoseChart.vue'
import PowerTable from '@/components/dashboard/PowerTable.vue'
import RechargeTimeline from '@/components/dashboard/RechargeTimeline.vue'
import { Lightning, TrendCharts, Wallet } from '@element-plus/icons-vue'
import http from '@/utils/axios'
import { API_ROUTES } from '@/config/api'

const currentBalance = ref('--')
const todayUsage = ref('--')
const weekRecharge = ref('--')
const statsLoading = ref(true)

const fetchStats = async () => {
  try {
    statsLoading.value = true
    const userId = sessionStorage.getItem('userId')
    const response = await http.get(API_ROUTES.recentWeekPower, { params: { userId } })
    const data = response.data
    if (!Array.isArray(data) || data.length === 0) return

    const sorted = data.slice().sort((a: any, b: any) => new Date(b.querytime).getTime() - new Date(a.querytime).getTime())
    currentBalance.value = sorted[0]?.value?.toFixed(2) || '--'

    const today = new Date().toISOString().split('T')[0]
    const todayRecords = data.filter((item: any) => item.querytime.startsWith(today))
    const todayConsumption = todayRecords
      .filter((item: any) => item.changeValue < 0)
      .reduce((sum: number, item: any) => sum + Math.abs(item.changeValue), 0)
    todayUsage.value = todayConsumption.toFixed(2)

    const weekRechargeVal = data
      .filter((item: any) => item.changeValue > 0)
      .reduce((sum: number, item: any) => sum + item.changeValue, 0)
    weekRecharge.value = weekRechargeVal.toFixed(2)
  } catch (error) {
    console.error('获取统计数据失败', error)
  } finally {
    statsLoading.value = false
  }
}

onMounted(fetchStats)
</script>

<style scoped>
.stat-row {
  margin-bottom: 20px;
}

.stat-card {
  background: var(--card-bg);
  backdrop-filter: blur(20px);
  border-radius: 20px;
  padding: 24px;
  display: flex;
  align-items: center;
  gap: 16px;
  box-shadow: var(--card-shadow);
  transition: transform 0.2s, box-shadow 0.2s;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 12px 40px rgba(0, 0, 0, 0.12);
}

html.dark .stat-card:hover {
  box-shadow: 0 12px 40px rgba(0, 0, 0, 0.4);
}

.stat-icon {
  width: 56px;
  height: 56px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  flex-shrink: 0;
}

.balance-icon {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.usage-icon {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.recharge-icon {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.stat-content {
  display: flex;
  flex-direction: column;
}

.stat-label {
  font-size: 13px;
  color: var(--text-secondary);
  margin-bottom: 4px;
}

.stat-value {
  font-size: 28px;
  font-weight: 700;
  color: var(--text-primary);
  line-height: 1;
}

.stat-value small {
  font-size: 14px;
  font-weight: 500;
  color: var(--text-secondary);
}

.chart-row {
  margin-bottom: 20px;
}

.data-row {
  margin-bottom: 20px;
}

.glass-card {
  background: var(--card-bg);
  backdrop-filter: blur(20px);
  border-radius: 20px;
  padding: 24px;
  box-shadow: var(--card-shadow);
}

.card-title {
  margin: 0 0 16px;
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
}

.timeline-card {
  height: 100%;
}
</style>
