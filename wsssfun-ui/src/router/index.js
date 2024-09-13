// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import Home from '../components/Home.vue';
import Register from "../components/Register.vue";
import Login from '../components/Login.vue';

const routes = [
    {
        path: '/home',
        name: 'Home',
        component: Home,
        meta: { requiresAuth: true },
    },
    {
        path: '/login',
        name: 'Login',
        component: Login,
    },
    {
        path: '/register',
        name: 'Register',
        component: Register,
    }
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL), // 使用 import.meta.env.BASE_URL
    routes,
});

// 路由守卫，检查用户是否有 JWT 令牌
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token');
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!token) {
            next('/login'); // 如果没有令牌，跳转到登录页面
        } else {
            next(); // 继续访问目标页面
        }
    } else {
        next(); // 如果目标页面不需要身份验证，直接访问
    }
});

export default router;
