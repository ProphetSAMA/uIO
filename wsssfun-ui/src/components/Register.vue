<template>
  <div class="registration-form">
    <el-form :model="formData" label-width="100px">
      <el-form-item label="用户名">
        <el-input v-model="formData.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="formData.password" placeholder="请输入密码" type="password"></el-input>
      </el-form-item>
      <el-form-item label="选择房间">
        <el-cascader
            v-model="formData.selectedRoom"
            :options="roomStore.rooms"
            :props="{ expandTrigger: 'hover' }"
            placeholder="请选择房间"
        ></el-cascader>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交注册</el-button>
        <p>
          <el-link class="login-text" type="primary" @click="$router.push('/login')">返回登陆</el-link>
        </p>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {ref, onMounted} from 'vue';
import {useRoomStore} from '../store/roomStore.js';
import axios from 'axios';
import {ElNotification} from 'element-plus';
import {useRouter} from "vue-router";

export default {
  setup() {
    const router = useRouter(); // 在 setup 中使用 useRouter 获取 router 实例
    const roomStore = useRoomStore();
    const formData = ref({
      username: '',
      password: '',
      selectedRoom: [] // 用于存储用户选择的楼栋、楼层、房间
    });

    /**
     * 加载房间信息
     *
     * @author Wsssfun
     */
    const loadRooms = async () => {

      // 从本地存储中加载房间信息
      const storedRooms = localStorage.getItem('rooms');
      if (storedRooms) {
        // 从本地存储中加载房间信息
        roomStore.rooms = JSON.parse(storedRooms);
        // 设置房间信息加载完成标志
        roomStore.isLoaded = true;
      } else {
        // 从服务端加载房间信息
        await roomStore.fetchRooms();
        // 加载成功后将房间信息存储到本地
        localStorage.setItem('rooms', JSON.stringify(roomStore.rooms));
      }
    };

    onMounted(loadRooms);

    /**
     * 注册逻辑
     *
     * @author Wsssfun
     */
    const submitForm = async () => {
      if (!formData.value.username || !formData.value.password || formData.value.selectedRoom.length === 0) {
        ElNotification({
          title: '注册失败',
          message: '请填写完整的注册信息！',
          type: 'warning',
          position: 'bottom-right',
          showClose: false,
          customClass: 'small-notification'
        });
        return;
      }

      // 提交请求到服务端
      try {
        const response = await axios.post('http://117.72.10.41:8080/api/users/register', formData.value);
        // 注册成功(服务端响应200)
        if (response.status === 200) {
          ElNotification({
            title: '注册成功',
            message: '即将为您登录',
            type: 'success',
            position: 'bottom-right',
            showClose: false,
            customClass: 'small-notification'
          });

          // 注册成功后清空表单数据
          formData.value.username = '';
          formData.value.password = '';
          formData.value.selectedRoom = [];

          // 两秒后跳转到登录页面
          setTimeout(() => {
            router.push('/');
          }, 2000);
        }
      } catch (error) {
        ElNotification({
          title: '注册失败',
          message: error.response.data,
          type: 'error',
          position: 'bottom-right',
          showClose: false,
          customClass: 'small-notification'
        });
      }
    };

    return {roomStore, formData, submitForm};
  }
};
</script>

<style scoped>
.registration-form {
  width: 400px;
  margin: 50px auto;
}

.login-text {
  text-align: right;
  margin-left: 20px;
}
</style>
