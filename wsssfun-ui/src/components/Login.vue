<!-- src/views/Login.vue -->
<template>
  <el-container class="login-container">
    <el-header>
      <h2>登录</h2>
    </el-header>
    <el-main>
      <el-form :model="loginForm" status-icon :rules="rules" ref="loginForm" label-width="80px" class="demo-ruleForm">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" placeholder="请输入密码" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin">登录</el-button>
        </el-form-item>
        <el-alert v-if="error" title="登录失败，请重试" type="error" show-icon></el-alert>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
import axios from '../utils/axios';

export default {
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      error: '',
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    async handleLogin() {
      try {
        const valid = await this.$refs.loginForm.validate();
        if (valid) {
          const response = await axios.post('/users/login', this.loginForm);
          const token = response.data; // 获取 JWT 令牌
          localStorage.setItem('token', token); // 保存令牌到 localStorage
          this.$router.push('/home'); // 登录成功后跳转到首页
        }
      } catch (error) {
        this.error = '用户名或密码错误';
      }
    }
  }
};
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
}
</style>
