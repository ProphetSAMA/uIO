<template>
  <div class="app-layout">
    <!-- Header -->
    <header class="app-header">
      <div class="header-left">
        <el-icon :size="24" color="#fff"><Lightning /></el-icon>
        <h1 class="header-title">uIO</h1>
        <span class="header-subtitle">校园电费管理系统</span>
      </div>
      <div class="header-right">
        <div class="user-badge">
          <div class="user-avatar">{{ userStore.username?.charAt(0)?.toUpperCase() || 'U' }}</div>
          <div class="user-details">
            <span class="user-name">{{ userStore.username }}</span>
            <span class="user-room">{{ roomDisplay || '加载中...' }}</span>
          </div>
        </div>
        <el-button class="logout-btn" @click="handleLogout">
          <el-icon><SwitchButton /></el-icon>
        </el-button>
      </div>
    </header>

    <!-- Body -->
    <div class="app-body">
      <!-- Sidebar -->
      <aside class="app-sidebar">
        <nav class="sidebar-nav">
          <div
            class="nav-item"
            :class="{ active: activeMenu === '1' }"
            @click="router.push('/')"
          >
            <el-icon><DataLine /></el-icon>
            <span>数据概览</span>
          </div>
          <div
            class="nav-item"
            :class="{ active: activeMenu === '2' }"
            @click="router.push('/info-power')"
          >
            <el-icon><List /></el-icon>
            <span>详细数据</span>
          </div>
        </nav>
      </aside>

      <!-- Main content -->
      <main class="app-main">
        <slot />
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/store/user'
import http from '@/utils/axios'
import { API_ROUTES } from '@/config/api'
import { Lightning, SwitchButton, DataLine, List } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const roomDisplay = ref('')

const activeMenu = computed(() => {
  if (route.path === '/info-power') return '2'
  return '1'
})

const fetchRoomInfo = async () => {
  if (!userStore.userId) return
  try {
    const response = await http.get(API_ROUTES.profile(userStore.userId))
    roomDisplay.value = response.data.roomDisplay
  } catch (error) {
    console.error('获取房间信息失败:', error)
  }
}

const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}

onMounted(() => {
  fetchRoomInfo()
})
</script>

<style scoped>
.app-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

/* Header */
.app-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 32px;
  height: 64px;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(20px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.15);
}

.header-left {
  display: flex;
  align-items: center;
  gap: 12px;
}

.header-title {
  margin: 0;
  font-size: 22px;
  font-weight: 700;
  color: #fff;
  letter-spacing: 1px;
}

.header-subtitle {
  color: rgba(255, 255, 255, 0.6);
  font-size: 13px;
  margin-left: 4px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.user-badge {
  display: flex;
  align-items: center;
  gap: 10px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  padding: 6px 14px 6px 6px;
}

.user-avatar {
  width: 32px;
  height: 32px;
  border-radius: 10px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-weight: 700;
  font-size: 14px;
}

.user-details {
  display: flex;
  flex-direction: column;
}

.user-name {
  color: #fff;
  font-size: 13px;
  font-weight: 600;
  line-height: 1.2;
}

.user-room {
  color: rgba(255, 255, 255, 0.6);
  font-size: 11px;
  max-width: 120px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.logout-btn {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  color: #fff;
  border-radius: 10px;
  padding: 8px;
  height: auto;
}

.logout-btn:hover {
  background: rgba(255, 80, 80, 0.3);
  border-color: rgba(255, 80, 80, 0.4);
  color: #fff;
}

/* Body */
.app-body {
  flex: 1;
  display: flex;
  min-height: 0;
}

/* Sidebar */
.app-sidebar {
  width: 200px;
  padding: 20px 12px;
  background: rgba(255, 255, 255, 0.05);
  border-right: 1px solid rgba(255, 255, 255, 0.08);
}

.sidebar-nav {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px 16px;
  border-radius: 12px;
  color: rgba(255, 255, 255, 0.6);
  cursor: pointer;
  transition: all 0.2s;
  font-size: 14px;
  font-weight: 500;
}

.nav-item:hover {
  background: rgba(255, 255, 255, 0.1);
  color: rgba(255, 255, 255, 0.9);
}

.nav-item.active {
  background: rgba(255, 255, 255, 0.15);
  color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

/* Main */
.app-main {
  flex: 1;
  padding: 24px;
  overflow-y: auto;
  min-height: 0;
}
</style>
