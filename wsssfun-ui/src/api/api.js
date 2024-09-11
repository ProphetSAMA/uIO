import axios from 'axios';

export async function getBuildings() {
    try {
        const response = await axios.get('/api/buildings');
        return response.data;
    } catch (error) {
        console.error('获取楼号失败', error);
        return [];
    }
}

export async function getFloorsByBuildingId(buildingId) {
    try {
        const response = await axios.get('/api/floors', {
            params: { buildingId }
        });
        return response.data;
    } catch (error) {
        console.error('获取楼层失败', error);
        return [];
    }
}

export async function getRoomsByFloorId(floorId) {
    try {
        const response = await axios.get('/api/rooms', {
            params: { floorId }
        });
        return response.data;
    } catch (error) {
        console.error('获取房间失败', error);
        return [];
    }
}