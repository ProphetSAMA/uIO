<template>
  <div class="not-found">
    <div class="not-found-card">
      <div class="error-code">404</div>
      <p class="error-message">页面不存在</p>
      <p class="error-desc">您访问的页面可能已被移除或地址有误</p>
      <el-button class="back-btn" @click="router.push('/')">
        <el-icon><HomeFilled /></el-icon>
        返回首页
      </el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { HomeFilled } from '@element-plus/icons-vue'
const router = useRouter()
</script>

<style scoped>
.not-found {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

.not-found-card {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  border-radius: 24px;
  padding: 60px 48px;
  text-align: center;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
}

.error-code {
  font-size: 96px;
  font-weight: 800;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  line-height: 1;
  margin: 0;
}

.error-message {
  font-size: 24px;
  font-weight: 600;
  color: #2d3748;
  margin: 16px 0 8px;
}

.error-desc {
  font-size: 14px;
  color: #718096;
  margin: 0 0 32px;
}

.back-btn {
  height: 48px;
  padding: 0 32px;
  border-radius: 12px;
  font-size: 15px;
  font-weight: 600;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  color: #fff;
  letter-spacing: 1px;
  transition: all 0.2s;
}

.back-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 8px 20px rgba(102, 126, 234, 0.3);
  background: linear-gradient(135deg, #5a6fd6 0%, #6a4190 100%);
  color: #fff;
}
</style>
