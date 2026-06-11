<template>
  <div>
    <h3 class="card-title">一天中用量最多的时间段</h3>
    <div ref="chartContainer" style="height: 360px;"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue'
import * as echarts from 'echarts'
import http from '@/utils/axios'
import { API_ROUTES } from '@/config/api'

const chartContainer = ref<HTMLElement | null>(null)
let chartInstance: echarts.ECharts | null = null

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

const initChart = async () => {
  if (!chartContainer.value) return
  try {
    const userId = sessionStorage.getItem('userId')
    const response = await http.get(API_ROUTES.recentWeekPower, { params: { userId } })
    const roseData = processRoseData(response.data)

    chartInstance = echarts.init(chartContainer.value)
    chartInstance.setOption({
      tooltip: {
        trigger: 'item',
        backgroundColor: 'rgba(255, 255, 255, 0.95)',
        borderColor: '#e2e8f0',
        borderWidth: 1,
        textStyle: { color: '#2d3748' },
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
          borderColor: '#fff',
          borderWidth: 2,
        },
        label: {
          color: '#2d3748',
          fontSize: 13,
          fontWeight: 500,
        },
        labelLine: {
          lineStyle: { color: '#cbd5e0' },
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
    })
  } catch (error) {
    console.error('获取电量数据失败', error)
  }
}

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
  color: #2d3748;
}
</style>
