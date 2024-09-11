<template>
  <el-form :model="form" ref="formRef" label-width="120px" @submit.native.prevent="handleSubmit">
    <el-form-item label="Username" prop="username" :rules="usernameRules">
      <el-input v-model="form.username" autocomplete="off" />
    </el-form-item>

    <el-form-item label="Password" prop="password" :rules="passwordRules">
      <el-input type="password" v-model="form.password" autocomplete="off" />
    </el-form-item>

    <el-form-item label="Confirm Password" prop="confirmPassword" :rules="confirmPasswordRules">
      <el-input type="password" v-model="form.confirmPassword" autocomplete="off" />
    </el-form-item>

    <!-- Building Selection -->
    <el-form-item label="Building" prop="buildingId">
      <el-select v-model="form.buildingId" placeholder="Select Building" @change="onBuildingChange">
        <el-option
            v-for="building in buildings"
            :key="building.id"
            :label="building.name"
            :value="building.id"
        />
      </el-select>
    </el-form-item>

    <!-- Floor Selection -->
    <el-form-item label="Floor" prop="floorId">
      <el-select v-model="form.floorId" placeholder="Select Floor" @change="onFloorChange" :disabled="!floors.length">
        <el-option
            v-for="floor in floors"
            :key="floor.id"
            :label="floor.name"
            :value="floor.id"
        />
      </el-select>
    </el-form-item>

    <!-- Room Selection -->
    <el-form-item label="Room" prop="roomId">
      <el-select v-model="form.roomId" placeholder="Select Room" :disabled="!rooms.length">
        <el-option
            v-for="room in rooms"
            :key="room.id"
            :label="room.name"
            :value="room.id"
        />
      </el-select>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="handleSubmit">Register</el-button>
      <el-button @click="handleReset">Reset</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { ElForm } from 'element-plus';

export default {
  components: {
    ElForm,
  },
  setup() {
    const form = ref({
      username: '',
      password: '',
      confirmPassword: '',
      buildingId: null,
      floorId: null,
      roomId: null,
    });

    const formRef = ref(null);
    const buildings = ref([]);
    const floors = ref([]);
    const rooms = ref([]);

    // Form validation rules
    const usernameRules = [
      { required: true, message: 'Please input your username', trigger: 'blur' },
    ];

    const passwordRules = [
      { required: true, message: 'Please input your password', trigger: 'blur' },
    ];

    const confirmPasswordRules = [
      { required: true, message: 'Please confirm your password', trigger: 'blur' },
      {
        validator: (rule, value, callback) => {
          if (value !== form.value.password) {
            callback(new Error('Passwords do not match'));
          } else {
            callback();
          }
        },
        trigger: 'blur',
      },
    ];

    // Fetch buildings on component mount
    onMounted(async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/buildings');
        buildings.value = response.data;
      } catch (error) {
        console.error('Error fetching buildings:', error);
      }
    });

    // Fetch floors when building changes
    const onBuildingChange = async (buildingId) => {
      if (buildingId) {
        try {
          // Clear previously selected floor and room
          form.value.floorId = null;
          form.value.roomId = null;
          floors.value = [];
          rooms.value = [];

          // Fetch floors based on selected building
          const response = await axios.get('http://localhost:8080/api/floors', {
            params: { buildingId },
          });
          floors.value = response.data;
        } catch (error) {
          console.error('Error fetching floors:', error);
        }
      }
    };

    // Fetch rooms when floor changes
    const onFloorChange = async (floorId) => {
      if (floorId && form.value.buildingId) {
        try {
          // Clear previously selected room
          form.value.roomId = null;
          rooms.value = [];

          // Fetch rooms based on selected building and floor
          const response = await axios.get('http://localhost:8080/api/rooms', {
            params: { buildingId: form.value.buildingId, floorId },
          });
          rooms.value = response.data;
        } catch (error) {
          console.error('Error fetching rooms:', error);
        }
      }
    };

    // Handle form submission
    const handleSubmit = async () => {
      try {
        // Validate form
        await formRef.value.validate();

        // Submit form data
        const response = await axios.post('http://localhost:8080/api/register', form.value);

        // Handle successful registration
        console.log('Registration successful:', response.data);
        // Redirect or show success message here
      } catch (error) {
        console.error('Registration error:', error);
        // Handle registration error here
      }
    };

    // Reset form fields
    const handleReset = () => {
      formRef.value.resetFields();
      buildings.value = [];
      floors.value = [];
      rooms.value = [];
    };

    return {
      form,
      formRef,
      buildings,
      floors,
      rooms,
      usernameRules,
      passwordRules,
      confirmPasswordRules,
      onBuildingChange,
      onFloorChange,
      handleSubmit,
      handleReset,
    };
  },
};
</script>

<style scoped>
/* Add any additional styling here */
</style>
