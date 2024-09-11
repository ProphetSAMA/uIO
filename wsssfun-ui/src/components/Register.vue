<template>
  <div id="register">
    <form @submit.prevent="register">
      <el-input v-model="state.username" placeholder="用户名" clearable></el-input>
      <el-input v-model="state.password" :type="showPassword ? 'text' : 'password'" placeholder="密码" clearable :suffix-icon="showPassword ? 'el-icon-view' : 'el-icon-view-off'" @click-suffix="showPassword = !showPassword"></el-input>
      <el-input v-model="state.confirmPassword" :type="showPassword ? 'text' : 'password'" placeholder="确认密码" clearable :suffix-icon="showPassword ? 'el-icon-view' : 'el-icon-view-off'" @click-suffix="showPassword = !showPassword"></el-input>
      <el-cascader v-model="selectedValue" :options="cascaderOptions" placeholder="请选择"></el-cascader>
      <el-button type="primary" @click="register">注册</el-button>
      <el-button @click="clear">清空</el-button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'RegisterForm',
  data() {
    return {
      state: {
        username: '',
        password: '',
        confirmPassword: ''
      },
      showPassword: false,
      selectedValue: [],
      cascaderOptions: []
    };
  },
  methods: {
    async register() {
      if (this.state.password !== this.state.confirmPassword) {
        alert('密码不匹配');
        return;
      }

      try {
        const response = await axios.post('http://localhost:8080/api/auth/register', {
          username: this.state.username,
          password: this.state.password,
          roomId: this.selectedValue[this.selectedValue.length - 1]
        });
        console.log('注册成功', response.data);
      } catch (error) {
        console.error('注册失败', error);
      }
    },
    clear() {
      this.state.username = '';
      this.state.password = '';
      this.state.confirmPassword = '';
      this.selectedValue = [];
    },
    async fetchBuildings() {
      try {
        const buildings = await axios.get('http://localhost:8080/api/buildings');
        this.cascaderOptions = this.transformData(buildings.data);
      } catch (error) {
        console.error('获取楼号数据失败', error);
      }
    },
    async fetchFloors(buildingId) {
      try {
        const floors = await axios.get('http://localhost:8080/api/floors', {
          params: {buildingId}
        });
        return floors.data;
      } catch (error) {
        console.error('获取楼层数据失败', error);
        return [];
      }
    },
    async fetchRooms(floorId) {
      try {
        const rooms = await axios.get('http://localhost:8080/api/rooms', {
          params: {floorId}
        });
        return rooms.data;
      } catch (error) {
        console.error('获取房间数据失败', error);
        return [];
      }
    },
    async transformData(data) {
      const transformedData = await Promise.all(data.map(async (building) => {
        const floors = await this.fetchFloors(building.buildingId);
        return {
          value: building.buildingId,
          label: building.buildingName,
          children: await Promise.all(floors.map(async (floor) => {
            const rooms = await this.fetchRooms(floor.floorId);
            return {
              value: floor.floorId,
              label: `第${floor.floorNumber}层`,
              children: rooms.map((room) => ({
                value: room.roomId,
                label: `房间${room.roomNumber}`
              }))
            };
          }))
        };
      }));
      return transformedData;
    }
  },
  mounted() {
    this.fetchBuildings();
  }
};
</script>

<style>
#register {
  margin-top: 100px;
  max-width: 400px;
  margin-left: auto;
  margin-right: auto;
}
</style>