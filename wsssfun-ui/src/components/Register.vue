<template>
  <div class="registration-form">
    <el-form :model="formData" label-width="100px">
      <el-form-item label="用户名">
        <el-input v-model="formData.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="formData.password" type="password" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item label="选择房间">
        <el-cascader
            v-model="formData.selectedRoom"
            :options="rooms"
            placeholder="请选择房间"
            :props="{ expandTrigger: 'hover' }"
        ></el-cascader>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios';
import { ElMessage } from 'element-plus';

export default {
  data() {
    return {
      formData: {
        username: '',
        password: '',
        selectedRoom: [], // 用于存储用户选择的楼栋、楼层、房间
      },
      rooms: [], // 存储从后端获取的楼栋、楼层、房间数据
    };
  },
  mounted() {
    this.fetchRooms(); // 组件挂载时获取房间数据
  },
  methods: {
    // 获取楼栋、楼层、房间数据
    fetchRooms() {
      axios.get('http://localhost:8080/api/rooms')
          .then(response => {
            this.rooms = response.data; // 处理后端返回的房间数据
          })
          .catch(error => {
            ElMessage.error('获取房间信息失败！');
            console.error(error);
          });
    },
    // 提交表单数据
    submitForm() {
      if (!this.formData.username || !this.formData.password || this.formData.selectedRoom.length === 0) {
        ElMessage.warning('请填写完整的注册信息！');
        return;
      }

      // 向后端发送注册请求
      axios.post('http://localhost:8080/api/users/register', this.formData)
          .then(response => {
            ElMessage.success('注册成功！');
            console.log('注册成功：', response.data);
          })
          .catch(error => {
            ElMessage.error('注册失败，请重试！');
            console.error('注册失败：', error);
          });
    }
  }
};
</script>

<style scoped>
.registration-form {
  width: 400px;
  margin: 50px auto;
}
</style>
