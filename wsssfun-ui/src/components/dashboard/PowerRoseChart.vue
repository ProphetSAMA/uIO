<template>
  <div>
    <h3 class="card-title">一天中用量最多的时间段</h3>
    <el-skeleton :loading="loading" animated :count="1">
      <template #template>
        <el-skeleton-item variant="rect" style="width: 100%; height: 360px; border-radius: 12px;" />
      </template>
      <template #default>
        <div ref="chartContainer" style="height: 360px;"></div>
      </template>
    </el-skeleton>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, watch } from 'vue'
import * as echarts from 'echarts'
import http from '@/utils/axios'
import { API_ROUTES } from '@/config/api'
import { useThemeStore } from '@/store/theme'

const chartContainer = ref<HTMLElement | null>(null)
const loading = ref(true)
let chartInstance: echarts.ECharts | null = null
const themeStore = useThemeStore()

const processRoseData = (data: any[]) => {
  const timeSlotData: Record<string, number> = {
    '凌晨 0-6h': 0,
    '上午 6-12h': 0,
    '下午 12-18h': 0,
    '晚间 18-24h': 0,
  }

  data.forEach(item => {
    if (item.changeValue < 0) {
      const hour = parseInt(item.querytime.split(' ')[1].split(':')[0], 10)
      const absChange = Math.abs(parseFloat(item.changeValue.toFixed(2)))
      if (hour >= 0 && hour < 6) timeSlotData['凌晨 0-6h'] += absChange
      else if (hour >= 6 && hour < 12) timeSlotData['上午 6-12h'] += absChange
      else if (hour >= 12 && hour < 18) timeSlotData['下午 12-18h'] += absChange
      else if (hour >= 18 && hour <= 23) timeSlotData['晚间 18-24h'] += absChange
    }
  })

  return Object.keys(timeSlotData).map(slot => ({
    name: slot,
    value: parseFloat(timeSlotData[slot].toFixed(2)),
  }))
}

const getChartOption = (roseData: { name: string; value: number }[]) => {
  const isDark = themeStore.isDark
  const textColor = isDark ? '#e2e8f0' : '#2d3748'
  const tooltipBg = isDark ? 'rgba(30, 30, 50, 0.95)' : 'rgba(255, 255, 255, 0.95)'
  const tooltipBorder = isDark ? 'rgba(255,255,255,0.1)' : '#e2e8f0'
  const borderColor = isDark ? '#1a1a2e' : '#fff'
  const labelLineColor = isDark ? 'rgba(255,255,255,0.2)' : '#cbd5e0'

  return {
    tooltip: {
      trigger: 'item',
      backgroundColor: tooltipBg,
      borderColor: tooltipBorder,
      borderWidth: 1,
      textStyle: { color: textColor },
      borderRadius: 12,
      padding: [12, 16],
      formatter: '{b}: {c} kWh ({d}%)',
    },
    series: [{
      name: '消耗量',
      type: 'pie',
      radius: ['20%', '70%'],
      roseType: 'area',
      itemStyle: {
        borderRadius: 8,
        borderColor: borderColor,
        borderWidth: 2,
      },
      label: {
        color: textColor,
        fontSize: 13,
        fontWeight: 500,
      },
      labelLine: {
        lineStyle: { color: labelLineColor },
      },
      data: roseData,
      color: [
        new echarts.graphic.LinearGradient(0, 0, 1, 1, [
          { offset: 0, color: '#667eea' },
          { offset: 1, color: '#764ba2' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 1, 1, [
          { offset: 0, color: '#4facfe' },
          { offset: 1, color: '#00f2fe' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 1, 1, [
          { offset: 0, color: '#f093fb' },
          { offset: 1, color: '#f5576c' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 1, 1, [
          { offset: 0, color: '#43e97b' },
          { offset: 1, color: '#38f9d7' },
        ]),
      ],
    }],
    animationDuration: 1000,
    animationEasing: 'cubicOut',
  }
}

let roseDataCache: { name: string; value: number }[] = []

const initChart = async () => {
  if (!chartContainer.value) return
  try {
    loading.value = true
    const userId = sessionStorage.getItem('userId')
    const response = await http.get(API_ROUTES.recentWeekPower, { params: { userId } })
    roseDataCache = processRoseData(response.data)
    loading.value = false

    chartInstance = echarts.init(chartContainer.value)
    chartInstance.setOption(getChartOption(roseDataCache))
  } catch (error) {
    loading.value = false
    console.error('获取电量数据失败', error)
  }
}

watch(() => themeStore.isDark, () => {
  chartInstance?.setOption(getChartOption(roseDataCache))
})

onMounted(initChart)
onBeforeUnmount(() => {
  chartInstance?.dispose()
})
</script>

<style scoped>
.card-title {
  margin: 0 0 16px;
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
}
</style>
