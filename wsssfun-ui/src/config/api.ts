// API 配置
const isDev = import.meta.env.DEV

export const API_BASE_URL = isDev
  ? 'http://localhost:8080'
  : 'https://api.uio.ink'

// API 路径（相对路径，配合 axios baseURL 使用）
export const API_ROUTES = {
  login: '/api/users/login',
  register: '/api/users/register',
  profile: (userId: string) => `/api/users/profile?userId=${userId}`,
  allUsers: '/api/users/all',
  rooms: '/api/rooms',
  allPower: '/api/all-power',
  recentWeekPower: '/api/recent-week-power',
  latestPower: (userId: string) => `/api/latest-power?userId=${userId}`,
} as const

export default API_ROUTES
