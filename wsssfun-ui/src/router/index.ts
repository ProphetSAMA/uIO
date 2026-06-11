import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
    {
        path: '/',
        name: 'Home',
        component: () => import('@/components/Home.vue'),
        meta: { requiresAuth: true },
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/components/login/Login.vue'),
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('@/components/Register.vue'),
    },
    {
        path: '/info-power',
        name: 'InfoPower',
        component: () => import('@/components/InfoPowerPage.vue'),
        meta: { requiresAuth: true },
    },
    {
        path: '/profile',
        name: 'Profile',
        component: () => import('@/components/Profile.vue'),
        meta: { requiresAuth: true },
    },
    {
        path: '/:pathMatch(.*)*',
        name: 'NotFound',
        component: () => import('@/components/NotFound.vue'),
    },
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
})

router.beforeEach((to, _from, next) => {
    const token = sessionStorage.getItem('token')

    if (to.meta.requiresAuth && !token) {
        next('/login')
        return
    }

    if ((to.path === '/login' || to.path === '/register') && token) {
        next('/')
        return
    }

    next()
})

export default router
