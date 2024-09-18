<template>
  <div>
    <div ref="listTableRef" style="width: 1280px; height: 400px"></div>

    <!-- Loading and error message display -->
    <div v-if="loading">加载中...</div>
    <div v-if="errorMessages">{{ errorMessages }}</div>

    <!-- Show calculated usage -->
    <div>总使用量: {{ Usage }}</div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watchEffect } from 'vue';
import { usePowerStore } from '../store/powerStore.js'; // Import Pinia store
import { ListTable } from '@visactor/vtable'; // Import VTable component

// Setup table DOM reference
const listTableRef = ref();
const listTable = ref(null);

// Get data from Pinia store
const dataStore = usePowerStore();

// Fetch data on component mount
onMounted(() => {
  dataStore.fetchData();
  dataStore.fetchApiValue();
});

// Define computed property for sorted table data
const sortedTableData = computed(() => {
  return dataStore.tableData.slice().sort((a, b) => new Date(b.queryTime) - new Date(a.queryTime));
});

// Compute table data with usage values
const tableDataWithUsage = computed(() => {
  let previousValue = null;
  return sortedTableData.value.map(row => {
    const newRow = { ...row };
    if (previousValue !== null) {
      newRow.usevaule = (previousValue - parseFloat(newRow.powerValue)).toFixed(2);
    } else {
      newRow.usevaule = 0;
    }
    previousValue = parseFloat(newRow.powerValue);
    return newRow;
  });
});

// Calculate total usage
const Usage = computed(() => {
  return tableDataWithUsage.value.reduce((acc, row) => {
    const useValue = parseFloat(row.usevaule);
    return useValue > 0 ? acc + useValue : acc;
  }, 0);
});

// Define table columns
const columns = [
  { field: 'queryTime', title: '查询时间' },
  { field: 'powerValue', title: '电量值' },
  { field: 'usevaule', title: '使用量' },
  // Add more columns if necessary
];

// Watch for data updates and initialize VTable
watchEffect(() => {
  if (tableDataWithUsage.value.length > 0 && !listTable.value) {
    listTable.value = new ListTable(listTableRef.value, {
      records: tableDataWithUsage.value,
      columns: columns,
      widthMode: 'standard', // Adjust table width mode as needed
    });

    // Handle table cell click event
    listTable.value.on('click_cell', (params) => {
      console.log(params);
    });
  }
});

// Map store states to computed properties for loading and error messages
const loading = computed(() => dataStore.loading);
const errorMessages = computed(() => dataStore.errorMessages);
</script>

<style scoped>
/* Add custom styling for the table container if needed */
</style>
