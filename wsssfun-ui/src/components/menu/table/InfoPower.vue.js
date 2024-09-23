/// <reference types=".vue-global-types/vue_3.5_false.d.ts" />
import { computed, onMounted, ref } from 'vue';
import { usePowerStore } from '@/store/powerStore.js'; // 引入Pinia store
import { ElTable, ElTableColumn, ElPagination } from 'element-plus'; // 引入Element Plus的分页组件
const { defineProps, defineSlots, defineEmits, defineExpose, defineModel, defineOptions, withDefaults, } = await import('vue');
// 使用Pinia的store
const powerStore = usePowerStore();
// 定义分页相关的属性
const currentPage = ref(1); // 当前页
const pageSize = ref(10); // 每页显示的条数
// 获取所有数据时调用的方法
onMounted(() => {
    powerStore.fetchData(); // 默认加载所有数据
});
// 计算排序后的表格数据
const sortedTableData = computed(() => {
    return powerStore.tableData.slice().sort((a, b) => new Date(b.querytime) - new Date(a.querytime));
});
// 计算包含使用量的表格数据
const tableDataWithUsage = computed(() => {
    let previousValue = null;
    return sortedTableData.value.map(row => {
        const newRow = { ...row };
        if (previousValue !== null) {
            newRow.usevaule = (previousValue - parseFloat(newRow.value)).toFixed(2);
        }
        else {
            newRow.usevaule = 0;
        }
        previousValue = parseFloat(newRow.value);
        return newRow;
    });
});
// 前端分页计算
const paginatedData = computed(() => {
    const start = (currentPage.value - 1) * pageSize.value;
    const end = start + pageSize.value;
    return tableDataWithUsage.value.slice(start, end); // 根据当前页和每页条数进行数据切片
});
// 计算总使用量
const Usage = computed(() => {
    return tableDataWithUsage.value.reduce((acc, row) => {
        const useValue = parseFloat(row.usevaule);
        return useValue > 0 ? acc + useValue : acc;
    }, 0);
});
// 总数据条数
const totalItems = computed(() => powerStore.tableData.length);
// 处理分页的页面切换
const handlePageChange = (page) => {
    currentPage.value = page; // 更新当前页
};
// 处理加载和错误状态
const loading = computed(() => powerStore.loading);
const errorMessages = computed(() => powerStore.errorMessages);
const __VLS_fnComponent = (await import('vue')).defineComponent({});
;
let __VLS_functionalComponentProps;
function __VLS_template() {
    const __VLS_ctx = {};
    const __VLS_localComponents = {
        ...{},
        ...{},
        ...__VLS_ctx,
    };
    let __VLS_components;
    const __VLS_localDirectives = {
        ...{},
        ...__VLS_ctx,
    };
    let __VLS_directives;
    let __VLS_styleScopedClasses;
    // CSS variable injection 
    // CSS variable injection end 
    let __VLS_resolvedLocalAndGlobalComponents;
    __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({});
    if (__VLS_ctx.paginatedData.length > 0) {
        __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({ ...{ class: ("table") }, });
        const __VLS_0 = __VLS_resolvedLocalAndGlobalComponents.ElTable;
        /** @type { [typeof __VLS_components.ElTable, typeof __VLS_components.elTable, typeof __VLS_components.ElTable, typeof __VLS_components.elTable, ] } */
        // @ts-ignore
        const __VLS_1 = __VLS_asFunctionalComponent(__VLS_0, new __VLS_0({ data: ((__VLS_ctx.paginatedData)), ...{ style: ({}) }, }));
        const __VLS_2 = __VLS_1({ data: ((__VLS_ctx.paginatedData)), ...{ style: ({}) }, }, ...__VLS_functionalComponentArgsRest(__VLS_1));
        const __VLS_6 = __VLS_resolvedLocalAndGlobalComponents.ElTableColumn;
        /** @type { [typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ] } */
        // @ts-ignore
        const __VLS_7 = __VLS_asFunctionalComponent(__VLS_6, new __VLS_6({ label: ("时间"), prop: ("querytime"), width: ("240"), }));
        const __VLS_8 = __VLS_7({ label: ("时间"), prop: ("querytime"), width: ("240"), }, ...__VLS_functionalComponentArgsRest(__VLS_7));
        const __VLS_12 = __VLS_resolvedLocalAndGlobalComponents.ElTableColumn;
        /** @type { [typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ] } */
        // @ts-ignore
        const __VLS_13 = __VLS_asFunctionalComponent(__VLS_12, new __VLS_12({ label: ("剩余"), prop: ("value"), width: ("240"), }));
        const __VLS_14 = __VLS_13({ label: ("剩余"), prop: ("value"), width: ("240"), }, ...__VLS_functionalComponentArgsRest(__VLS_13));
        const __VLS_18 = __VLS_resolvedLocalAndGlobalComponents.ElTableColumn;
        /** @type { [typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ] } */
        // @ts-ignore
        const __VLS_19 = __VLS_asFunctionalComponent(__VLS_18, new __VLS_18({ label: ("变化"), prop: ("usevaule"), width: ("240"), }));
        const __VLS_20 = __VLS_19({ label: ("变化"), prop: ("usevaule"), width: ("240"), }, ...__VLS_functionalComponentArgsRest(__VLS_19));
        __VLS_nonNullable(__VLS_5.slots).default;
        const __VLS_5 = __VLS_pickFunctionalComponentCtx(__VLS_0, __VLS_2);
        if (__VLS_ctx.totalItems > 0) {
            const __VLS_24 = __VLS_resolvedLocalAndGlobalComponents.ElPagination;
            /** @type { [typeof __VLS_components.ElPagination, typeof __VLS_components.elPagination, ] } */
            // @ts-ignore
            const __VLS_25 = __VLS_asFunctionalComponent(__VLS_24, new __VLS_24({ ...{ 'onCurrentChange': {} }, currentPage: ((__VLS_ctx.currentPage)), pageSize: ((__VLS_ctx.pageSize)), total: ((__VLS_ctx.totalItems)), background: (true), ...{ class: ("pagination") }, layout: ("total, prev, pager, next, jumper"), }));
            const __VLS_26 = __VLS_25({ ...{ 'onCurrentChange': {} }, currentPage: ((__VLS_ctx.currentPage)), pageSize: ((__VLS_ctx.pageSize)), total: ((__VLS_ctx.totalItems)), background: (true), ...{ class: ("pagination") }, layout: ("total, prev, pager, next, jumper"), }, ...__VLS_functionalComponentArgsRest(__VLS_25));
            let __VLS_30;
            const __VLS_31 = {
                onCurrentChange: (__VLS_ctx.handlePageChange)
            };
            let __VLS_27;
            let __VLS_28;
            const __VLS_29 = __VLS_pickFunctionalComponentCtx(__VLS_24, __VLS_26);
        }
    }
    if (__VLS_ctx.loading) {
        __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({});
    }
    if (__VLS_ctx.errorMessages) {
        __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({});
        (__VLS_ctx.errorMessages);
    }
    __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({});
    (__VLS_ctx.Usage);
    __VLS_styleScopedClasses['table'];
    __VLS_styleScopedClasses['pagination'];
    var __VLS_slots;
    var __VLS_inheritedAttrs;
    const __VLS_refs = {};
    var $refs;
    return {
        slots: __VLS_slots,
        refs: $refs,
        attrs: {},
    };
}
;
const __VLS_self = (await import('vue')).defineComponent({
    setup() {
        return {
            ElTable: ElTable,
            ElTableColumn: ElTableColumn,
            ElPagination: ElPagination,
            currentPage: currentPage,
            pageSize: pageSize,
            paginatedData: paginatedData,
            Usage: Usage,
            totalItems: totalItems,
            handlePageChange: handlePageChange,
            loading: loading,
            errorMessages: errorMessages,
        };
    },
});
export default (await import('vue')).defineComponent({
    setup() {
        return {};
    },
});
;
