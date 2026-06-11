import {defineStore} from 'pinia';

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
                // Fetch data from API
                const response = await fetch('127.0.0.1/api/all-power');
                const data = await response.json();
                this.tableData = Array.isArray(data) ? data : []; // Ensure data is an array
            } catch (error) {
                this.errorMessages = error.message;
            } finally {
                this.loading = false;
            }
        },
        async fetchApiValue() {
            try {
                // 从 sessionStorage 获取 userId
                const userId = sessionStorage.getItem('userId');
                if (!userId) {
                    throw new Error('用户未登录');
                }
                // Fetch API value with userId
                const response = await fetch(`127.0.0.1/api/latest-power?userId=${userId}`);
                this.apiValue = await response.json();
            } catch (error) {
                this.errorMessages = error.message;
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
