<template>
  <div>
    <h3 class="card-title">近一周电量变化统计</h3>
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

interface DayData {
  name: string
  valueLast: number
  rechargeSum: number
  consumptionSum: number
}

const processChartData = (data: any[]): DayData[] => {
  const groupedData: Record<string, { valueLast: number; rechargeSum: number; consumptionSum: number }> = {}

  data.forEach(item => {
    const day = item.querytime.split(' ')[0]
    const shortDay = day.substring(5) // MM-DD
    const valueChange = parseFloat(item.changeValue.toFixed(2))

    if (!groupedData[shortDay]) {
      groupedData[shortDay] = { valueLast: 0, rechargeSum: 0, consumptionSum: 0 }
    }
    groupedData[shortDay].valueLast = parseFloat(item.value.toFixed(2))
    if (item.changeValue > 0) {
      groupedData[shortDay].rechargeSum += valueChange
    } else {
      groupedData[shortDay].consumptionSum += Math.abs(valueChange)
    }
  })

  return Object.keys(groupedData).map(day => ({
    name: day,
    valueLast: groupedData[day].valueLast,
    rechargeSum: groupedData[day].rechargeSum,
    consumptionSum: groupedData[day].consumptionSum,
  }))
}

const initChart = async () => {
  if (!chartContainer.value) return
  try {
    const userId = sessionStorage.getItem('userId')
    const response = await http.get(API_ROUTES.recentWeekPower, { params: { userId } })
    const chartData = processChartData(response.data)

    chartInstance = echarts.init(chartContainer.value)
    chartInstance.setOption({
      tooltip: {
        trigger: 'axis',
        axisPointer: { type: 'shadow' },
        backgroundColor: 'rgba(255, 255, 255, 0.95)',
        borderColor: '#e2e8f0',
        borderWidth: 1,
        textStyle: { color: '#2d3748' },
        borderRadius: 12,
        padding: [12, 16],
      },
      legend: {
        data: ['总电量', '充值', '消耗'],
        bottom: 0,
        textStyle: { color: '#718096' },
        itemWidth: 12,
        itemHeight: 12,
        itemGap: 24,
      },
      grid: {
        top: 10,
        left: 10,
        right: 10,
        bottom: 40,
        containLabel: true,
      },
      xAxis: {
        type: 'category',
        data: chartData.map(item => item.name),
        axisLine: { lineStyle: { color: '#e2e8f0' } },
        axisLabel: { color: '#718096', fontSize: 12 },
        axisTick: { show: false },
      },
      yAxis: {
        type: 'value',
        axisLine: { show: false },
        axisLabel: { color: '#718096', fontSize: 12 },
        splitLine: { lineStyle: { color: '#f0f0f0', type: 'dashed' } },
      },
      series: [
        {
          name: '总电量',
          type: 'bar',
          barWidth: 14,
          itemStyle: {
            borderRadius: [6, 6, 0, 0],
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: '#667eea' },
              { offset: 1, color: '#764ba2' },
            ]),
          },
          data: chartData.map(item => item.valueLast),
        },
        {
          name: '充值',
          type: 'bar',
          barWidth: 14,
          itemStyle: {
            borderRadius: [6, 6, 0, 0],
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: '#4facfe' },
              { offset: 1, color: '#00f2fe' },
            ]),
          },
          data: chartData.map(item => item.rechargeSum),
        },
        {
          name: '消耗',
          type: 'bar',
          barWidth: 14,
          itemStyle: {
            borderRadius: [6, 6, 0, 0],
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: '#f093fb' },
              { offset: 1, color: '#f5576c' },
            ]),
          },
          data: chartData.map(item => item.consumptionSum),
        },
      ],
      animationDuration: 800,
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
