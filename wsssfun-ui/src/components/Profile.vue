<template>
  <AppLayout>
    <div class="profile-page">
      <div class="profile-card">
        <div class="profile-header">
          <div class="profile-avatar">
            {{ userInfo.username?.charAt(0)?.toUpperCase() || 'U' }}
          </div>
          <h2 class="profile-username">{{ userInfo.username || '--' }}</h2>
          <span class="profile-room">
            <el-icon><Location /></el-icon>
            {{ userInfo.roomDisplay || '加载中...' }}
          </span>
        </div>

        <div class="profile-info">
          <div class="info-item">
            <div class="info-icon">
              <el-icon :size="20"><User /></el-icon>
            </div>
            <div class="info-content">
              <span class="info-label">用户名</span>
              <span class="info-value">{{ userInfo.username || '--' }}</span>
            </div>
          </div>
          <div class="info-item">
            <div class="info-icon location-icon">
              <el-icon :size="20"><OfficeBuilding /></el-icon>
            </div>
            <div class="info-content">
              <span class="info-label">所在房间</span>
              <span class="info-value">{{ userInfo.roomDisplay || '--' }}</span>
            </div>
          </div>
        </div>

        <div class="profile-actions">
          <el-button type="danger" plain class="logout-full-btn" @click="handleLogout">
            <el-icon><SwitchButton /></el-icon>
            退出登录
          </el-button>
        </div>
      </div>
    </div>
  </AppLayout>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import AppLayout from '@/components/layout/AppLayout.vue'
import http from '@/utils/axios'
import { API_ROUTES } from '@/config/api'
import { User, Location, OfficeBuilding, SwitchButton } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()

const userInfo = ref<{ username: string; roomDisplay: string }>({
  username: '',
  roomDisplay: '',
})

const fetchProfile = async () => {
  if (!userStore.userId) return
  try {
    const response = await http.get(API_ROUTES.profile(userStore.userId))
    userInfo.value = response.data
  } catch (error) {
    console.error('获取用户信息失败:', error)
  }
}

const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}

onMounted(fetchProfile)
</script>

<style scoped>
.profile-page {
  display: flex;
  justify-content: center;
  padding: 40px 20px;
}

.profile-card {
  background: var(--card-bg);
  backdrop-filter: blur(20px);
  border-radius: 24px;
  padding: 48px 40px;
  box-shadow: var(--card-shadow);
  width: 100%;
  max-width: 480px;
}

.profile-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 40px;
}

.profile-avatar {
  width: 88px;
  height: 88px;
  border-radius: 24px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-weight: 700;
  font-size: 36px;
  margin-bottom: 16px;
  box-shadow: 0 8px 24px rgba(102, 126, 234, 0.3);
}

.profile-username {
  margin: 0 0 8px;
  font-size: 24px;
  font-weight: 700;
  color: var(--text-primary);
}

.profile-room {
  display: flex;
  align-items: center;
  gap: 4px;
  color: var(--text-secondary);
  font-size: 14px;
}

.profile-info {
  display: flex;
  flex-direction: column;
  gap: 16px;
  margin-bottom: 32px;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px;
  background: linear-gradient(135deg, #667eea08 0%, #764ba208 100%);
  border-radius: 16px;
  border: 1px solid var(--card-border);
}

html.dark .info-item {
  background: rgba(255, 255, 255, 0.03);
}

.info-icon {
  width: 44px;
  height: 44px;
  border-radius: 12px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.info-icon.location-icon {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.info-content {
  display: flex;
  flex-direction: column;
}

.info-label {
  font-size: 12px;
  color: var(--text-muted);
  margin-bottom: 2px;
}

.info-value {
  font-size: 15px;
  font-weight: 600;
  color: var(--text-primary);
}

.profile-actions {
  display: flex;
  justify-content: center;
}

.logout-full-btn {
  width: 100%;
  height: 48px;
  border-radius: 14px;
  font-size: 15px;
  font-weight: 600;
}
</style>
