import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
    state: () => ({
        isLoggedIn: false,
        username: '',
        token: '',
        userId: ''  // 添加 userId
    }),
    actions: {
        // 登录
        login(username, userId, token) {
            console.log('Login method called with:', username, userId, token);
            this.isLoggedIn = true;
            this.username = username;
            this.token = token;
            this.userId = userId;  // 这里使用传递过来的 userId
            sessionStorage.setItem('isLoggedIn', 'true');
            sessionStorage.setItem('userId', userId);  // 保存 userId
            sessionStorage.setItem('username', username);
            sessionStorage.setItem('token', token);
            console.log('Pinia state updated:', this.username, this.userId, this.token);
        },
        // 退出登录
        logout() {
            this.isLoggedIn = false;
            this.username = '';
            this.userId = '';  // 清除 userId
            sessionStorage.removeItem('isLoggedIn');
            sessionStorage.removeItem('username');
            sessionStorage.removeItem('userId'); // 移除 userId
            sessionStorage.removeItem('token');
            sessionStorage.clear();
        },
        // 初始化
        initialize() {
            const isLoggedIn = sessionStorage.getItem('isLoggedIn') === 'true';
            const username = sessionStorage.getItem('username') || '';
            const token = sessionStorage.getItem('token') || '';
            const userId = sessionStorage.getItem('userId') || '';  // 获取 userId
            this.isLoggedIn = isLoggedIn;
            this.username = username;
            this.token = token;
            this.userId = userId;
        }
    }
});
