<template>
  <div id="login">
    <form @submit.prevent="login">
      <el-input
          v-model="state.username"
          placeholder="用户名"
          clearable
      ></el-input>

      <el-input
          v-model="state.password"
          :type="showPassword ? 'text' : 'password'"
          placeholder="密码"
          clearable
          :suffix-icon="showPassword ? 'el-icon-view' : 'el-icon-view-off'"
          @click-suffix="showPassword = !showPassword"
      ></el-input>

      <el-button type="primary" @click="login">登录</el-button>
      <el-button @click="clear">清空</el-button>
      <el-button type="text" @click="goToRegister">注册</el-button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';
import { useRouter } from 'vue-router';

export default {
  name: 'LoginForm',
  data() {
    return {
      state: {
        username: '',
        password: '',
      },
      showPassword: false,
    };
  },
  methods: {
    login() {
      axios.post('/api/login', {
        username: this.state.username,
        password: this.state.password,
      })
          .then(response => {
            console.log('登录成功:', response);
            // 处理登录成功后的逻辑
          })
          .catch(error => {
            console.error('登录失败:', error);
            // 处理登录失败的逻辑
          });
    },
    clear() {
      this.state.username = '';
      this.state.password = '';
    },
    goToRegister() {
      this.$router.push('/register');
    }
  }
};
</script>

<style>
#login {
  margin-top: 100px;
  max-width: 400px;
  margin-left: auto;
  margin-right: auto;
}
</style>