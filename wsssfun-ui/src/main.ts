import {createApp, ref} from 'vue';
import App from './App.vue';
import 'element-plus/dist/index.css';

import router from './router';
import ElementPlus from 'element-plus';

import zhCn from 'element-plus/es/locale/lang/zh-cn';

import 'element-plus/theme-chalk/dark/css-vars.css'

const app = createApp(App);

app.use(router);
app.use(ElementPlus, {
    locale: zhCn,
});

app.mount('#app');