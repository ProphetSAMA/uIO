<template xmlns="">
  <div id="app">
    <AppHeader />
    <el-main>
      <router-view></router-view>
    </el-main>
  </div>
</template>

<script>
import { defineComponent } from 'vue';
import AppHeader from './components/AppHead.vue';
export default defineComponent({
  components: { AppHeader }
})
</script>

<style>

</style>