import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
    state: () => ({
        isLoggedIn: false,
        username: '',
        token: '',
        userId: ''
    }),
    actions: {
        // 登录
        login(username, userId, token) {
            this.isLoggedIn = true;
            this.username = username;
            this.token = token;
            this.userId = userId;

            sessionStorage.setItem('isLoggedIn', 'true');
            sessionStorage.setItem('userId', userId);
            sessionStorage.setItem('username', username);
            sessionStorage.setItem('token', token);
        },
        // 退出登录
        logout() {
            this.isLoggedIn = false;
            this.username = '';
            this.userId = '';
            sessionStorage.removeItem('isLoggedIn');
            sessionStorage.removeItem('username');
            sessionStorage.removeItem('userId');
            sessionStorage.removeItem('token');
            sessionStorage.clear();
        },
        // 初始化
        initialize() {
            const isLoggedIn = sessionStorage.getItem('isLoggedIn') === 'true';
            const username = sessionStorage.getItem('username') || '';
            const token = sessionStorage.getItem('token') || '';
            const userId = sessionStorage.getItem('userId') || '';
            this.isLoggedIn = isLoggedIn;
            this.username = username;
            this.token = token;
            this.userId = userId;
        }
    }
});
