<template>
  <AppLayout>
    <div class="glass-card">
      <h3 class="card-title">详细用电数据</h3>
      <PowerTable :show-pagination="true" :page-size="20" :show-total="true" />
    </div>
  </AppLayout>
</template>

<script setup lang="ts">
import AppLayout from '@/components/layout/AppLayout.vue'
import PowerTable from '@/components/dashboard/PowerTable.vue'
</script>

<style scoped>
.glass-card {
  background: var(--card-bg);
  backdrop-filter: blur(20px);
  border-radius: 20px;
  padding: 24px;
  box-shadow: var(--card-shadow);
}

.card-title {
  margin: 0 0 20px;
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
}
</style>
