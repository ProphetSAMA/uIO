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
                const response = await fetch('http://117.72.10.41:8080/api/all-power');
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
                // Fetch API value
                const response = await fetch('http://117.72.10.41:8080/api/latest-power');
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