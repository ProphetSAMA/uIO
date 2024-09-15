import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
    state: () => ({
        isLoggedIn: false,
        username: ''
    }),
    actions: {
        login(username) {
            this.isLoggedIn = true;
            this.username = username;
            localStorage.setItem('isLoggedIn', 'true');
            localStorage.setItem('username', username);
        },
        logout() {
            this.isLoggedIn = false;
            this.username = '';
            localStorage.removeItem('isLoggedIn');
            localStorage.removeItem('username');
        },
        initialize() {
            const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
            const username = localStorage.getItem('username') || '';
            this.isLoggedIn = isLoggedIn;
            this.username = username;
        }
    }
});
