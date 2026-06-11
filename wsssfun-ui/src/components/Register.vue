<template>
  <div class="register-container">
    <div class="register-card">
      <div class="register-header">
        <div class="logo">
          <el-icon :size="48" color="#667eea"><Lightning /></el-icon>
        </div>
        <h1 class="title">创建账号</h1>
        <p class="subtitle">加入 uIO 校园电费查询平台</p>
      </div>

      <el-form ref="formRef" :model="form" :rules="rules" class="register-form" @submit.prevent="handleRegister">
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
            placeholder="请输入密码（至少6位）"
            size="large"
            :prefix-icon="Lock"
            show-password
          />
        </el-form-item>

        <el-form-item prop="confirmPassword">
          <el-input
            v-model="form.confirmPassword"
            type="password"
            placeholder="请确认密码"
            size="large"
            :prefix-icon="Lock"
            show-password
          />
        </el-form-item>

        <!-- 房间选择区域 -->
        <div class="room-section">
          <div class="section-title">
            <el-icon><OfficeBuilding /></el-icon>
            <span>选择房间</span>
          </div>

          <div class="room-selects">
            <el-form-item prop="buildingId" class="room-item">
              <el-select
                v-model="form.buildingId"
                placeholder="选择楼栋"
                size="large"
                @change="onBuildingChange"
              >
                <el-option
                  v-for="building in buildings"
                  :key="building.value"
                  :label="building.label"
                  :value="building.value"
                />
              </el-select>
            </el-form-item>

            <el-form-item prop="floorId" class="room-item">
              <el-select
                v-model="form.floorId"
                placeholder="选择楼层"
                size="large"
                :disabled="!form.buildingId"
                @change="onFloorChange"
              >
                <el-option
                  v-for="floor in floors"
                  :key="floor.value"
                  :label="floor.label"
                  :value="floor.value"
                />
              </el-select>
            </el-form-item>

            <el-form-item prop="roomId" class="room-item">
              <el-select
                v-model="form.roomId"
                placeholder="选择房间"
                size="large"
                :disabled="!form.floorId"
              >
                <el-option
                  v-for="room in rooms"
                  :key="room.value"
                  :label="room.label"
                  :value="room.value"
                />
              </el-select>
            </el-form-item>
          </div>

          <!-- 已选择房间摘要 -->
          <div v-if="selectedRoomSummary" class="room-summary">
            <el-icon><Location /></el-icon>
            <span>{{ selectedRoomSummary }}</span>
          </div>
        </div>

        <el-button
          type="primary"
          size="large"
          class="register-btn"
          :loading="loading"
          @click="handleRegister"
        >
          注 册
        </el-button>
      </el-form>

      <div class="register-footer">
        <span>已有账号？</span>
        <router-link to="/login" class="login-link">返回登录</router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { User, Lock, Lightning, Location, OfficeBuilding } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import http from '@/utils/axios'
import { API_ROUTES } from '@/config/api'
import { useRoomStore } from '@/store/roomStore'

const router = useRouter()
const roomStore = useRoomStore()
const formRef = ref(null)
const loading = ref(false)

const buildings = ref<any[]>([])
const floors = ref<any[]>([])
const rooms = ref<any[]>([])

const form = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  buildingId: null,
  floorId: null,
  roomId: null
})

// 密码验证
const validateConfirmPassword = (rule, value, callback) => {
  if (value !== form.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于 6 位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ],
  buildingId: [{ required: true, message: '请选择楼栋', trigger: 'change' }],
  floorId: [{ required: true, message: '请选择楼层', trigger: 'change' }],
  roomId: [{ required: true, message: '请选择房间', trigger: 'change' }]
}

// 已选择房间摘要
const selectedRoomSummary = computed(() => {
  if (!form.buildingId || !form.floorId || !form.roomId) return ''

  const building = buildings.value.find(b => b.value === form.buildingId)
  const floor = floors.value.find(f => f.value === form.floorId)
  const room = rooms.value.find(r => r.value === form.roomId)

  if (building && floor && room) {
    return `${building.label} ${floor.label} ${room.label}`
  }
  return ''
})

// 加载房间数据
onMounted(async () => {
  try {
    await roomStore.fetchRooms()
    buildings.value = roomStore.rooms.map((b: any) => ({
      value: b.value,
      label: b.label
    }))
  } catch (error) {
    console.error('获取房间信息失败', error)
    ElMessage.error('获取房间信息失败，请刷新重试')
  }
})

// 楼栋变化
const onBuildingChange = (buildingId) => {
  form.floorId = null
  form.roomId = null
  floors.value = []
  rooms.value = []

  const building = roomStore.rooms.find((b: any) => b.value === buildingId)
  if (building?.children) {
    floors.value = building.children.map(f => ({
      value: f.value,
      label: f.label
    }))
  }
}

// 楼层变化
const onFloorChange = (floorId) => {
  form.roomId = null
  rooms.value = []

  const building = roomStore.rooms.find((b: any) => b.value === form.buildingId)
  const floor = building?.children?.find(f => f.value === floorId)
  if (floor?.children) {
    rooms.value = floor.children.map(r => ({
      value: r.value,
      label: r.label
    }))
  }
}

// 注册
const handleRegister = async () => {
  if (!formRef.value) return

  await formRef.value.validate(async (valid) => {
    if (!valid) return

    loading.value = true
    try {
      const response = await http.post(API_ROUTES.register, {
        username: form.username,
        password: form.password,
        selectedRoom: [form.buildingId, form.floorId, form.roomId]
      })

      if (response.status === 200) {
        ElMessage.success('注册成功，即将跳转到登录页面')
        setTimeout(() => router.push('/login'), 1500)
      }
    } catch (error) {
      if (error.response?.data) {
        ElMessage.error(error.response.data)
      } else {
        ElMessage.error('注册失败，请检查网络')
      }
    } finally {
      loading.value = false
    }
  })
}
</script>

<style scoped>
.register-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.register-card {
  width: 100%;
  max-width: 480px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  border-radius: 24px;
  padding: 40px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
}

.register-header {
  text-align: center;
  margin-bottom: 32px;
}

.logo {
  margin-bottom: 16px;
}

.title {
  font-size: 32px;
  font-weight: 700;
  color: #2d3748;
  margin: 0 0 8px;
}

.subtitle {
  font-size: 14px;
  color: #718096;
  margin: 0;
}

.register-form {
  margin-bottom: 24px;
}

.register-form :deep(.el-input__wrapper) {
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
  padding: 4px 12px;
}

.register-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 2px 12px rgba(102, 126, 234, 0.15);
}

.register-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.2);
}

/* 房间选择区域 */
.room-section {
  background: #f8fafc;
  border-radius: 16px;
  padding: 20px;
  margin-bottom: 24px;
}

.section-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 16px;
  font-weight: 600;
  color: #4a5568;
  margin-bottom: 16px;
}

.section-title .el-icon {
  color: #667eea;
}

.room-selects {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.room-item {
  margin-bottom: 0 !important;
}

.room-item :deep(.el-select) {
  width: 100%;
}

.room-summary {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-top: 16px;
  padding: 12px 16px;
  background: linear-gradient(135deg, #667eea15 0%, #764ba215 100%);
  border-radius: 10px;
  color: #667eea;
  font-weight: 500;
}

.room-summary .el-icon {
  color: #667eea;
}

.register-btn {
  width: 100%;
  height: 48px;
  border-radius: 12px;
  font-size: 16px;
  font-weight: 600;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  letter-spacing: 4px;
}

.register-btn:hover {
  background: linear-gradient(135deg, #5a6fd6 0%, #6a4190 100%);
  transform: translateY(-1px);
  box-shadow: 0 8px 20px rgba(102, 126, 234, 0.3);
}

.register-footer {
  text-align: center;
  color: #718096;
  font-size: 14px;
}

.login-link {
  color: #667eea;
  text-decoration: none;
  font-weight: 600;
  margin-left: 4px;
}

.login-link:hover {
  color: #764ba2;
}

@media (max-width: 480px) {
  .register-card {
    padding: 32px 24px;
    border-radius: 20px;
  }

  .title {
    font-size: 28px;
  }

  .room-selects {
    gap: 8px;
  }
}
</style>
