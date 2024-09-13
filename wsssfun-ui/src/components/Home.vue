<!-- src/views/Home.vue -->
<template>
  <el-container class="home-container">
    <el-header>
      <h1>Welcome to Home</h1>
    </el-header>
    <el-main>
      <p>You are logged in!</p>
      <el-button type="danger" @click="logout">Logout</el-button>
    </el-main>
  </el-container>
</template>

<script>
export default {
  methods: {
    logout() {
      localStorage.removeItem('token'); // 删除 JWT 令牌
      this.$router.push('/login'); // 退出后重定向到登录页面
    }
  }
};
</script>

<style scoped>
.home-container {
  max-width: 600px;
  margin: 0 auto;
  text-align: center;
  padding: 50px 20px;
}

.el-header {
  background-color: #409EFF;
  color: white;
  padding: 20px 0;
}

.el-main {
  margin-top: 20px;
}

.el-button {
  margin-top: 20px;
}
</style>
