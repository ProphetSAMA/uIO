import axios from 'axios';

const instance = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL || 'https://api.uio.ink/api',
    headers: {
        'Content-Type': 'application/json',
    },
});

// Request interceptor to add JWT token
instance.interceptors.request.use((config) => {
    // Get token from sessionStorage
    const token = sessionStorage.getItem('token');
    if (token) {
        // Add JWT token to request header
        config.headers.Authorization = "Bearer " + token;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

// Response interceptor to handle 401 unauthorized errors
instance.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        if (error.response && error.response.status === 401) {
            // Clear stored token and user info
            sessionStorage.removeItem('token');
            sessionStorage.removeItem('userId');
            sessionStorage.removeItem('username');
            
            // Redirect to login page
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

export default instance;
