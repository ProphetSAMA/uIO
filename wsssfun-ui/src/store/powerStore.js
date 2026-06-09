import {defineStore} from 'pinia';
import axios from '../utils/axios';

export const usePowerStore = defineStore('powerStore', {
    state: () => ({
        tableData: [], // Initialize as an empty array
        apiValue: 0,
        loading: false,
        errorMessages: '',
    }),
    actions: {

        async fetchData() {
            try {
                this.loading = true;
                // Fetch data from API using user-scoped endpoint
                const response = await axios.get('/me/power/all');
                if (response.data && response.data.data) {
                    this.tableData = Array.isArray(response.data.data) ? response.data.data : [];
                } else if (response.data && response.data.code === 200) {
                    this.tableData = Array.isArray(response.data.data) ? response.data.data : [];
                } else {
                    this.tableData = [];
                    this.errorMessages = response.data?.message || '获取数据失败';
                }
            } catch (error) {
                this.errorMessages = error.message || '网络错误';
                this.tableData = [];
            } finally {
                this.loading = false;
            }
        },
        async fetchApiValue() {
            try {
                // Fetch API value using user-scoped endpoint
                const response = await axios.get('/me/power/latest');
                if (response.data && response.data.code === 200) {
                    this.apiValue = response.data.data || 0;
                } else if (response.status === 401) {
                    // Handle unauthorized - token might be expired
                    this.errorMessages = '登录已过期，请重新登录';
                    this.apiValue = 0;
                } else {
                    this.errorMessages = response.data?.message || '获取电量失败';
                    this.apiValue = 0;
                }
            } catch (error) {
                if (error.response && error.response.status === 401) {
                    this.errorMessages = '登录已过期，请重新登录';
                } else {
                    this.errorMessages = error.message || '网络错误';
                }
                this.apiValue = 0;
            }
        },
        async fetchRecentWeekData() {
            try {
                this.loading = true;
                // Fetch recent week data using user-scoped endpoint
                const response = await axios.get('/me/power/history');
                if (response.data && response.data.code === 200) {
                    this.tableData = Array.isArray(response.data.data) ? response.data.data : [];
                } else {
                    this.tableData = [];
                    this.errorMessages = response.data?.message || '获取数据失败';
                }
            } catch (error) {
                if (error.response && error.response.status === 401) {
                    this.errorMessages = '登录已过期，请重新登录';
                } else {
                    this.errorMessages = error.message || '网络错误';
                }
                this.tableData = [];
            } finally {
                this.loading = false;
            }
        },
        // mutations
        setTableData(state, data) {
            state.tableData = data;
        },
        setApiValue(state, value) {
            state.apiValue = value;
        },
        setLoading(state, value) {
            state.loading = value;
        },
    },
});