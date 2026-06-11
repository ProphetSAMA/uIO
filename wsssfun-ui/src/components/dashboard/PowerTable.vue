<template>
  <div>
    <div v-if="paginatedData.length > 0">
      <el-table
        :data="paginatedData"
        :header-cell-style="headerStyle"
        :row-style="rowStyle"
        style="width: 100%; border-radius: 12px; overflow: hidden;"
      >
        <el-table-column label="时间" prop="querytime" min-width="160" />
        <el-table-column label="剩余电量" prop="value" min-width="120">
          <template #default="{ row }">
            <span class="value-cell">{{ row.value }} kWh</span>
          </template>
        </el-table-column>
        <el-table-column label="变化" prop="usageValue" min-width="120">
          <template #default="{ row }">
            <span :class="Number(row.usageValue) > 0 ? 'change-positive' : 'change-negative'">
              {{ Number(row.usageValue) > 0 ? '+' : '' }}{{ row.usageValue }} kWh
            </span>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-if="showPagination && totalItems > pageSize"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="totalItems"
        background
        style="margin-top: 16px; justify-content: center;"
        layout="total, prev, pager, next"
        @current-change="handlePageChange"
      />
    </div>

    <div v-else-if="loading" class="empty-state">
      <el-icon :size="32" class="loading-icon"><Loading /></el-icon>
      <span>加载中...</span>
    </div>

    <div v-else-if="errorMessages" class="empty-state error">
      <span>{{ errorMessages }}</span>
    </div>

    <div v-else class="empty-state">
      <span>暂无数据</span>
    </div>

    <div v-if="showTotal && totalUsage > 0" class="total-badge">
      <span class="total-label">总使用量</span>
      <span class="total-value">{{ totalUsage.toFixed(2) }} kWh</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { usePowerStore } from '@/store/powerStore'
import { Loading } from '@element-plus/icons-vue'

const props = withDefaults(defineProps<{
  showPagination?: boolean
  showTotal?: boolean
  pageSize?: number
}>(), {
  showPagination: true,
  showTotal: true,
  pageSize: 10,
})

const powerStore = usePowerStore()
const currentPage = ref(1)

const headerStyle = {
  background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
  color: '#fff',
  fontWeight: '600',
  fontSize: '13px',
  border: 'none',
  padding: '14px 0',
}

const rowStyle = {
  transition: 'background-color 0.2s',
}

onMounted(() => {
  powerStore.fetchData()
})

const sortedTableData = computed(() => {
  return powerStore.tableData.slice().sort((a: any, b: any) => new Date(b.querytime).getTime() - new Date(a.querytime).getTime())
})

const tableDataWithUsage = computed(() => {
  let previousValue: number | null = null
  return sortedTableData.value.map((row: any) => {
    const newRow = { ...row }
    if (previousValue !== null) {
      newRow.usageValue = (previousValue - parseFloat(newRow.value)).toFixed(2)
    } else {
      newRow.usageValue = 0
    }
    previousValue = parseFloat(newRow.value)
    return newRow
  })
})

const paginatedData = computed(() => {
  if (!props.showPagination) return tableDataWithUsage.value
  const start = (currentPage.value - 1) * props.pageSize
  return tableDataWithUsage.value.slice(start, start + props.pageSize)
})

const totalItems = computed(() => powerStore.tableData.length)

const totalUsage = computed(() => {
  return tableDataWithUsage.value.reduce((acc: number, row: any) => {
    const val = parseFloat(row.usageValue)
    return val > 0 ? acc + val : acc
  }, 0)
})

const loading = computed(() => powerStore.loading)
const errorMessages = computed(() => powerStore.errorMessages)

const handlePageChange = (page: number) => {
  currentPage.value = page
}
</script>

<style scoped>
.value-cell {
  font-weight: 600;
  color: #2d3748;
}

.change-positive {
  color: #43e97b;
  font-weight: 600;
}

.change-negative {
  color: #f5576c;
  font-weight: 600;
}

.total-badge {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  margin-top: 16px;
  padding: 10px 20px;
  background: linear-gradient(135deg, #667eea15 0%, #764ba215 100%);
  border-radius: 12px;
  border: 1px solid rgba(102, 126, 234, 0.2);
}

.total-label {
  font-size: 13px;
  color: #718096;
}

.total-value {
  font-size: 16px;
  font-weight: 700;
  color: #667eea;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  padding: 40px;
  color: #718096;
}

.empty-state.error {
  color: #f5576c;
}

.loading-icon {
  animation: spin 1s linear infinite;
  color: #667eea;
}

@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

:deep(.el-table) {
  --el-table-border-color: #f0f0f0;
  --el-table-row-hover-bg-color: #f8f0ff;
}

:deep(.el-pagination) {
  --el-pagination-button-bg-color: rgba(255, 255, 255, 0.8);
  --el-pagination-hover-color: #667eea;
}
</style>
