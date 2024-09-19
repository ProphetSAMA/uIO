<template>
  <div id="barchart-container" class="barchart-container"></div>
</template>

<script lang="ts">
import {onBeforeUnmount, onMounted} from "vue";
import {usePowerStore} from '@/store/powerStore.js';
import {IBarChartSpec, IVChart, VChart} from "@visactor/vchart";

export default {

  setup() {
    onMounted(() => {
      const container = document.getElementById("barchart-container");
      if (container) {
        chart = new VChart(parseSpec({data: powerStore.tableData}), {dom: container});
        chart.renderSync();  // 渲染图表
      } else {
        console.error('图表容器不存在，请检查容器ID是否正确！');
      }
    });
    const powerStore = usePowerStore();
    let chart: IVChart | null = null;

    const parseSpec = (chartProps: { data: any[] }) => {
      return {
        type: 'line',
        data: {
          values: chartProps.data.map(d => ({
            x: d.time,
            y: d.value
          }))
        },
        xField: 'x',
        yField: 'y',
        line: {
          style: {
            curveType: 'monotone'
          }
        }
      } as unknown as IBarChartSpec;
    };

    onBeforeUnmount(() => {
      if (chart) {
        chart.release(); // 释放图表资源
        chart = null;
      }
    });

  },
}
const spec = {
  type: 'line',
  data: {
    values: [
      {
        time: '2:00',
        value: 38
      },
      {
        time: '4:00',
        value: 56
      },
      {
        time: '6:00',
        value: 10
      },
      {
        time: '8:00',
        value: 70
      },
      {
        time: '10:00',
        value: 36
      },
      {
        time: '12:00',
        value: 94
      },
      {
        time: '14:00',
        value: 24
      },
      {
        time: '16:00',
        value: 44
      },
      {
        time: '18:00',
        value: 36
      },
      {
        time: '20:00',
        value: 68
      },
      {
        time: '22:00',
        value: 22
      }
    ]
  },
  xField: 'time',
  yField: 'value',
  line: {
    style: {
      curveType: 'monotone'
    }
  }
}


// const CONTAINER_ID = document.getElementById("barchart-container");
// const vchart = new VChart(spec, { dom: CONTAINER_ID });
// vchart.renderSync();

//
// interface BarChartProps {
//   theme?: string; // 主题
//   colors?: string[]; // 颜色数组
//   data?: any[]; // 数据数组
// }
//
// export default defineComponent({
//   props: {
//     colors: Array as PropType<BarChartProps["colors"]>, // 颜色属性
//   },
//
//   setup(props) {
//     const dataStore = usePowerStore();
//     const data = computed(() => {
//       return dataStore.tableData.map((item: { queryTime: string; powerValue: string; }) => ({
//         time: item.queryTime,
//         value: parseFloat(item.powerValue)
//       }));
//     });
//
//     let chart: IVChart | null = null;
//
//     const parseSpec = (chartProps: BarChartProps) => {
//       const colors = chartProps.colors;
//       return {
//         type: "area", // 图表类型
//         dataId: 'bar',
//         seriesField: 'Symbol',
//         dataZoom: [
//           {
//             orient: 'bottom',
//             backgroundChart: {
//               area: {
//                 style: {
//                   lineWidth: 1,
//                   fill: '#D1DBEE'
//                 }
//               },
//               line: {
//                 style: {
//                   stroke: '#D1DBEE',
//                   lineWidth: 1
//                 }
//               }
//             },
//             selectedBackgroundChart: {
//               area: {
//                 style: {
//                   lineWidth: 1,
//                   fill: '#fbb934'
//                 }
//               },
//               line: {
//                 style: {
//                   stroke: '#fbb934',
//                   lineWidth: 1
//                 }
//               }
//             }
//           }
//         ],
//         legends: {
//           visible: true,
//           orient: 'top'
//         },
//         title: {
//           // text: '',
//           textStyle: {
//             height: 50,
//             lineWidth: 3,
//             fill: '#333',
//             fontSize: 25,
//             fontFamily: 'Times New Roman'
//           }
//         },
//         data: [
//           {
//             id: "id0",
//             values: chartProps.data.map(d => ({
//               x: d.time,
//               y: d.value
//             })),
//           },
//         ],
//         xField: ['x'], // x轴字段
//         yField: 'y', // y轴字段
//         area: {
//           style: {
//             curveType: 'monotone',
//             fill: {
//               gradient: 'linear',
//               x0: 0,
//               y0: 0.5,
//               x1: 1,
//               y1: 0.5,
//               stops: [
//                 {
//                   offset: 0,
//                   color: '#009DB5',
//                   opacity: 0.3
//                 },
//                 {
//                   offset: 1,
//                   color: '#F0B71F',
//                   opacity: 0.3
//                 }
//               ]
//             }
//           }
//         },
//         line: {
//           style: {
//             curveType: 'monotone',
//             stroke: {
//               gradient: 'linear',
//               x0: 0,
//               y0: 0.5,
//               x1: 1,
//               y1: 0.5,
//               stops: [
//                 {
//                   offset: 0,
//                   color: '#009DB5'
//                 },
//                 {
//                   offset: 1,
//                   color: '#F0B71F'
//                 }
//               ]
//             }
//           }
//         },
//         point: {
//           style: {
//             fill: '#fff',
//             stroke: {
//               field: 'x',
//               scale: 'color'
//             }
//           }
//         },
//         color: {
//           type: 'ordinal',
//           domain: [
//             {
//               dataId: 'data',
//               fields: ['x']
//             }
//           ],
//           range: ['#009DB5', '#009DB5', '#009DB5', '#92be92', '#9fae52', '#F0B71F', '#F0B71F']
//         }
//       } as unknown as IBarChartSpec;
//     };
//
//     const createOrUpdateChart = (chartProps: BarChartProps) => {
//       const container = document.getElementById("barchart-container");
//       if (container && !chart) {
//         chart = new VChart(parseSpec(chartProps), {
//           dom: container,
//         });
//         chart.renderSync(); // 渲染图表
//       } else if (chart) {
//         chart.updateSpec(parseSpec(chartProps));
//         chart.renderSync();
//       }
//     };
//
//     const releaseChart = () => {
//       if (chart) {
//         chart.release(); // 释放图表资源
//         chart = null;
//       }
//     };
//
//     watch(data, (newData) => {
//       createOrUpdateChart({ colors: props.colors, data: newData });
//     }, { deep: true });
//
//     onMounted(() => {
//       createOrUpdateChart({ colors: props.colors, data: data.value });
//     });
//
//     onBeforeUnmount(() => {
//       releaseChart();
//     });
//
//     return {
//       data,
//       createOrUpdateChart,
//       releaseChart,
//     };
//   },
// });
</script>

<style scoped>
.barchart-container {
  width: 100%;
  height: 400px;
}
</style>
