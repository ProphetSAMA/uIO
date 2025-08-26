import { defineStore } from 'pinia';
import axios from 'axios';

export const useRoomStore = defineStore('roomStore', {
    state: () => ({
        rooms: [],      // 存储房间数据
        isLoaded: false // 用于标记数据是否已加载
    }),

    actions: {
        /**
         * 获取房间列表逻辑
         *
         * @author Wsssfun
         */
        async fetchRooms() {
            if (this.isLoaded) {
                return; // 如果数据已加载，直接返回
            }
            try {
                // 优先从localStorage中加载数据
                const cachedRooms = localStorage.getItem('rooms');
                if (cachedRooms) {
                    this.rooms = JSON.parse(cachedRooms);
                    this.isLoaded = true;
                    return; // 若本地存在缓存 则直接使用
                }

                // 若本地没有缓存 则请求后端数据
                const response = await axios.get('localhost:8080/api/rooms');
                this.rooms = response.data;
                this.isLoaded = true;

                // 将房间数据存入localStorage
                localStorage.setItem('rooms', JSON.stringify(this.rooms));
            } catch (error) {
                console.error('获取房间信息失败！', error);
            }
        }
    }
});
