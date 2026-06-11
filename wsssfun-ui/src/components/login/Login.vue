<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <div class="logo">
          <el-icon :size="48" color="#667eea"><Lightning /></el-icon>
        </div>
        <h1 class="title">uIO</h1>
        <p class="subtitle">校园电费查询平台</p>
      </div>

      <el-form ref="formRef" :model="form" :rules="rules" class="login-form" @submit.prevent="handleLogin">
        <el-form-item prop="username">
          <el-input
            v-model="form.username"
            placeholder="请输入用户名"
            size="large"
            :prefix-icon="User"
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input
            v-model="form.password"
            type="password"
            placeholder="请输入密码"
            size="large"
            :prefix-icon="Lock"
            show-password
            @keyup.enter="handleLogin"
          />
        </el-form-item>

        <div class="form-options">
          <el-checkbox v-model="rememberMe">记住我</el-checkbox>
        </div>

        <el-button
          type="primary"
          size="large"
          class="login-btn"
          :loading="loading"
          @click="handleLogin"
        >
          登 录
        </el-button>
      </el-form>

      <div class="login-footer">
        <span>还没有账号？</span>
        <router-link to="/register" class="register-link">立即注册</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user.js'
import { User, Lock, Lightning } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'

const router = useRouter()
const userStore = useUserStore()

const formRef = ref(null)
const loading = ref(false)
const rememberMe = ref(false)

const form = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  if (!formRef.value) return

  await formRef.value.validate(async (valid) => {
    if (!valid) return

    loading.value = true
    try {
      const response = await axios.post('http://localhost:8080/api/users/login', form)
      if (response.status === 200) {
        const { token, userId, username } = response.data
        if (token && userId && username) {
          sessionStorage.setItem('token', token)
          sessionStorage.setItem('userId', userId)
          userStore.login(username, userId, token)
          ElMessage.success('登录成功')
          await router.push('/')
        }
      }
    } catch (error) {
      if (error.response?.data) {
        ElMessage.error(error.response.data)
      } else {
        ElMessage.error('登录失败，请检查网络')
      }
    } finally {
      loading.value = false
    }
  })
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.login-card {
  width: 100%;
  max-width: 420px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  border-radius: 24px;
  padding: 48px 40px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
}

.login-header {
  text-align: center;
  margin-bottom: 40px;
}

.logo {
  margin-bottom: 16px;
}

.title {
  font-size: 36px;
  font-weight: 700;
  color: #2d3748;
  margin: 0 0 8px;
  letter-spacing: 2px;
}

.subtitle {
  font-size: 14px;
  color: #718096;
  margin: 0;
}

.login-form {
  margin-bottom: 24px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
  padding: 4px 12px;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 2px 12px rgba(102, 126, 234, 0.15);
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.2);
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.login-btn {
  width: 100%;
  height: 48px;
  border-radius: 12px;
  font-size: 16px;
  font-weight: 600;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  letter-spacing: 4px;
}

.login-btn:hover {
  background: linear-gradient(135deg, #5a6fd6 0%, #6a4190 100%);
  transform: translateY(-1px);
  box-shadow: 0 8px 20px rgba(102, 126, 234, 0.3);
}

.login-footer {
  text-align: center;
  color: #718096;
  font-size: 14px;
}

.register-link {
  color: #667eea;
  text-decoration: none;
  font-weight: 600;
  margin-left: 4px;
}

.register-link:hover {
  color: #764ba2;
}

@media (max-width: 480px) {
  .login-card {
    padding: 36px 24px;
    border-radius: 20px;
  }

  .title {
    font-size: 28px;
  }
}
</style>
