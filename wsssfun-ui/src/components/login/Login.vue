<template>
  <div class="login-container flx-center">
    <div class="login-box">
      <SwitchDark class="dark" />
      <div class="login-left">
        <img alt="login" class="login-left-img" src="../../assets/images/login_left.png" />
      </div>
      <div class="login-form">
        <div class="login-logo">
          <img alt="" class="login-icon" src="../../assets/images/login_bg.svg" />
          <h2 class="logo-text">uIO</h2>
        </div>
        <el-form ref="loginFormRef" :model="loginForm" :rules="loginRules" size="large">
          <el-form-item prop="username">
            <el-input v-model="loginForm.username" placeholder="请输入用户名">
              <template #prepend>
                <el-icon>
                  <User />
                </el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="loginForm.password" autocomplete="new-password" placeholder="请输入密码" show-password>
              <template #prepend>
                <el-icon>
                  <Lock />
                </el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-button type="primary" @click="handleLogin">登录</el-button>
          <p class="login-text">
            没有账号？
            <el-link type="primary" @click="$router.push('/register')">立即注册</el-link>
          </p>
        </el-form>
      </div>
    </div>
  </div>
</template>


<script lang="ts" setup>
import { useRouter } from 'vue-router';
import { useUserStore } from '@/store/user.js';
import { Lock, User } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import axios from 'axios';
import { ref, reactive } from 'vue';  // 导入 ref 和 reactive

const router = useRouter();
const userStore = useUserStore();

const loginFormRef = ref(null);  // 创建 ref
const loginForm = reactive({
  username: '',
  password: ''
});

const loginRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
};

const handleLogin = async () => {
  try {
    const valid = await loginFormRef.value?.validate();  // 通过 ref 获取表单实例并校验
    if (valid) {
      const response = await axios.post('https://117.72.10.41:8080/api/users/login', loginForm);
      if (response.status === 200) {
        const { token, userId, username } = response.data;
        if (token && userId && username) {
          sessionStorage.setItem('token', token);
          sessionStorage.setItem('userId', userId);
          userStore.login(username, userId, token);
          ElMessage.success('登录成功！');
          await router.push('/');
        }
      }
    }
  } catch (error) {
    if (!loginForm.username || !loginForm.password) {
      ElMessage.error('请输入账号和密码');
    } else if (error.response && error.response.data) {
      ElMessage.error(error.response.data);
    }
  }
};
</script>

<style lang="scss" scoped>
@import "./index.scss";

html, body {
  height: 100%;    // 确保 html 和 body 占满全屏
  margin: 0;
}

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 96.8vh;    // 设置容器高度为视口高度，确保填满屏幕
  width: 99.6vw;     // 设置宽度为视口宽度
  background-color: #f5f5f5; // 可选：添加背景颜色
}

.login-box {
  display: flex;
  width: 100%;
  max-width: 1200px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  background-color: white;
  border-radius: 10px;
}

.login-left {
  flex: 1;
  background-size: cover;
  background-position: center;
}


</style>

