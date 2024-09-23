import { createApp } from 'vue';
import { createPinia } from 'pinia';
import App from './App.vue';
import 'element-plus/dist/index.css';
import router from './router';
import ElementPlus from 'element-plus';
import zhCn from 'element-plus/es/locale/lang/zh-cn';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';
import 'element-plus/theme-chalk/dark/css-vars.css';
import { useUserStore } from "./store/user";
import './assets/styles/global.css'; // 引入全局样式
const app = createApp(App);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}
const pinia = createPinia();
app.use(pinia);
app.use(router);
const userStore = useUserStore();
userStore.initialize();
app.use(ElementPlus, {
    locale: zhCn,
});
app.mount('#app');
