<template>
  <div class="login-bg">
    <div class="login-container">

      <el-form ref="loginForm" :model="loginForm" :rules="rules" class="demo-ruleForm" label-width="80px" status-icon>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名">
            <template #prepend>
              <el-icon>
                <User/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password"
                    placeholder="请输入密码"
                    show-password
          >
            <template #prepend>
              <el-icon>
                <Lock/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin">登录</el-button>
        </el-form-item>
        <p class="login-text">
          没有账号？
          <el-link type="primary" @click="$router.push('/register')">立即注册</el-link>
        </p>
      </el-form>
    </div>
  </div>
</template>

<script>
import axios from '../utils/axios';
import {useUserStore} from '../store/user.js';
import {ElMessage} from 'element-plus';
import {useRouter} from "vue-router";
import {Lock, User} from "@element-plus/icons-vue";

export default {
  components: {Lock, User},
  setup() {
    const router = useRouter(); // 在 setup 中使用 useRouter 获取 router 实例
    const userStore = useUserStore();
    return {userStore, router};
  },
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
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
          if (response.status === 200) {
            const token = response.data;

            // 将 Token 存储在 sessionStorage 中
            sessionStorage.setItem('token', token);

            // 更新全局状态，表示已登录
            this.userStore.login(this.loginForm.username);

            ElMessage.success('登录成功！');

            // 跳转到首页
            this.$router.push('/');
          }
        }
      } catch (error) {
        // 如果没有输入账号或密码，提示用户输入
        if (!this.loginForm.username || !this.loginForm.password) {
          ElMessage.error('请输入账号和密码');
          // 如果用户名或密码错误，提示用户
        } else if (error.response && error.response.data) {
          // 直接使用 error.response.data 作为错误信息
          ElMessage.error(error.response.data);
        }
      }
    }
  }
};
</script>

<style scoped>


.login-bg {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100vh;
  background: url(../assets/login-bg.jpg) center/cover no-repeat;
}

.login-container {
  width: 450px;
  border-radius: 5px;
  background: #fff;
  padding: 40px 50px 50px;
  box-sizing: border-box;
  background: rgba(0, 0, 0, 0.5); /* 毛玻璃效果 */
  backdrop-filter: blur(10px); /* 毛玻璃模糊效果 */
}

.login-tips {
  font-size: 12px;
  color: #999;
}

.login-text {
  display: flex;
  align-items: center;
  margin-top: 50px;
  margin-left: 120px;
  font-size: 14px;
  color: #787878;
}
</style>
