/// <reference types=".vue-global-types/vue_3.5_false.d.ts" />
import { computed, onMounted, ref } from 'vue';
import { usePowerStore } from '@/store/powerStore.js'; // 引入Pinia store
import { ElTable, ElTableColumn } from 'element-plus'; // 引入Element Plus的分页组件
import infoVChart from '@/components/menu/infoVChart.vue';
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
    // @ts-ignore
    [InfoVChart,];
    // @ts-ignore
    const __VLS_0 = __VLS_asFunctionalComponent(infoVChart, new infoVChart({}));
    const __VLS_1 = __VLS_0({}, ...__VLS_functionalComponentArgsRest(__VLS_0));
    __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({});
    if (__VLS_ctx.paginatedData.length > 0) {
        __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({ ...{ class: ("table") }, });
        const __VLS_5 = __VLS_resolvedLocalAndGlobalComponents.ElTable;
        /** @type { [typeof __VLS_components.ElTable, typeof __VLS_components.elTable, typeof __VLS_components.ElTable, typeof __VLS_components.elTable, ] } */
        // @ts-ignore
        const __VLS_6 = __VLS_asFunctionalComponent(__VLS_5, new __VLS_5({ data: ((__VLS_ctx.paginatedData)), ...{ style: ({}) }, }));
        const __VLS_7 = __VLS_6({ data: ((__VLS_ctx.paginatedData)), ...{ style: ({}) }, }, ...__VLS_functionalComponentArgsRest(__VLS_6));
        const __VLS_11 = __VLS_resolvedLocalAndGlobalComponents.ElTableColumn;
        /** @type { [typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ] } */
        // @ts-ignore
        const __VLS_12 = __VLS_asFunctionalComponent(__VLS_11, new __VLS_11({ label: ("查询时间"), prop: ("querytime"), width: ("240"), }));
        const __VLS_13 = __VLS_12({ label: ("查询时间"), prop: ("querytime"), width: ("240"), }, ...__VLS_functionalComponentArgsRest(__VLS_12));
        const __VLS_17 = __VLS_resolvedLocalAndGlobalComponents.ElTableColumn;
        /** @type { [typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ] } */
        // @ts-ignore
        const __VLS_18 = __VLS_asFunctionalComponent(__VLS_17, new __VLS_17({ label: ("电量值"), prop: ("value"), width: ("240"), }));
        const __VLS_19 = __VLS_18({ label: ("电量值"), prop: ("value"), width: ("240"), }, ...__VLS_functionalComponentArgsRest(__VLS_18));
        const __VLS_23 = __VLS_resolvedLocalAndGlobalComponents.ElTableColumn;
        /** @type { [typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ] } */
        // @ts-ignore
        const __VLS_24 = __VLS_asFunctionalComponent(__VLS_23, new __VLS_23({ label: ("使用量"), prop: ("usevaule"), width: ("240"), }));
        const __VLS_25 = __VLS_24({ label: ("使用量"), prop: ("usevaule"), width: ("240"), }, ...__VLS_functionalComponentArgsRest(__VLS_24));
        __VLS_nonNullable(__VLS_10.slots).default;
        const __VLS_10 = __VLS_pickFunctionalComponentCtx(__VLS_5, __VLS_7);
    }
    if (__VLS_ctx.loading) {
        __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({});
    }
    if (__VLS_ctx.errorMessages) {
        __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({});
        (__VLS_ctx.errorMessages);
    }
    __VLS_styleScopedClasses['table'];
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
            infoVChart: infoVChart,
            paginatedData: paginatedData,
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