import {defineStore} from 'pinia';
import { API_ROUTES } from '@/config/api';
import http from '@/utils/axios';

export const usePowerStore = defineStore('powerStore', {
    state: () => ({
        tableData: [] as any[],
        loading: false,
        errorMessages: '',
    }),
    actions: {
        async fetchData() {
            try {
                this.loading = true;
                const userId = sessionStorage.getItem('userId');
                const response = await http.get(API_ROUTES.allPower, { params: { userId } });
                this.tableData = Array.isArray(response.data) ? response.data : [];
            } catch (error: any) {
                this.errorMessages = error.message;
            } finally {
                this.loading = false;
            }
        },

        async fetchRecentWeekPower() {
            try {
                const userId = sessionStorage.getItem('userId');
                const response = await http.get(API_ROUTES.recentWeekPower, { params: { userId } });
                return response.data;
            } catch (error: any) {
                this.errorMessages = error.message;
                return [];
            }
        },
    },
});
