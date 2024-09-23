<template>
  <div>
    <!-- el-table 组件渲染表格数据 -->
    <div v-if="paginatedData.length > 0" class="table">
      <el-table :data="paginatedData" style="width: 100%">
        <el-table-column label="时间" prop="querytime" width="240"/>
        <el-table-column label="剩余" prop="value" width="240"/>
        <el-table-column label="变化" prop="usevaule" width="240"/>
      </el-table>


    <!-- 分页组件 -->
    <el-pagination v-if="totalItems > 0"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="totalItems"
        background
        class="pagination"
        layout="total, prev, pager, next, jumper"
        @current-change="handlePageChange"
    />
    </div>

    <!-- 加载和错误状态 -->
    <div v-if="loading">加载中...</div>
    <div v-if="errorMessages">{{ errorMessages }}</div>

    <!-- 显示总使用量 -->
    <div>总使用量: {{ Usage }}</div>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue';
import { usePowerStore } from '@/store/powerStore.js'; // 引入Pinia store
import { ElTable, ElTableColumn, ElPagination } from 'element-plus'; // 引入Element Plus的分页组件

// 使用Pinia的store
const powerStore = usePowerStore();

// 定义分页相关的属性
const currentPage = ref(1); // 当前页
const pageSize = ref(10); // 每页显示的条数

// 获取所有数据时调用的方法
onMounted(() => {
  powerStore.fetchData(); // 默认加载所有数据
});

// 计算排序后的表格数据
const sortedTableData = computed(() => {
  return powerStore.tableData.slice().sort((a, b) => new Date(b.querytime) - new Date(a.querytime));
});

// 计算包含使用量的表格数据
const tableDataWithUsage = computed(() => {
  let previousValue = null;
  return sortedTableData.value.map(row => {
    const newRow = {...row};
    if (previousValue !== null) {
      newRow.usevaule = (previousValue - parseFloat(newRow.value)).toFixed(2);
    } else {
      newRow.usevaule = 0;
    }
    previousValue = parseFloat(newRow.value);
    return newRow;
  });
});

// 前端分页计算
const paginatedData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return tableDataWithUsage.value.slice(start, end); // 根据当前页和每页条数进行数据切片
});

// 计算总使用量
const Usage = computed(() => {
  return tableDataWithUsage.value.reduce((acc, row) => {
    const useValue = parseFloat(row.usevaule);
    return useValue > 0 ? acc + useValue : acc;
  }, 0);
});

// 总数据条数
const totalItems = computed(() => powerStore.tableData.length);

// 处理分页的页面切换
const handlePageChange = (page) => {
  currentPage.value = page; // 更新当前页
};

// 处理加载和错误状态
const loading = computed(() => powerStore.loading);
const errorMessages = computed(() => powerStore.errorMessages);
</script>

<style scoped>
.table {
  margin-top: 20px;
  margin-left: 50px;
  width: 720px;
}
.pagination {
  margin-top: 20px;
  margin-left: 50px;
}
</style>
