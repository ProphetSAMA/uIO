import axios from 'axios';

const instance = axios.create({
    baseURL: 'https://api.uio.ink/api',
    headers: {
        'Content-Type': 'application/json',
    },
});

// 请求拦截器，添加 JWT 令牌
instance.interceptors.request.use((config) => {
    // 从 sessionStorage 中获取令牌
    const token = sessionStorage.getItem('token');
    if (token) {
        // 添加 JWT 令牌到请求头 用于访问其他页面
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

export default instance;
