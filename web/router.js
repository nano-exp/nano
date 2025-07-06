import { createRouter, createWebHistory } from 'vue-router'

const Nano = () => import('./pages/Nano.vue')
const Vv = () => import('./pages/vv/Vv.vue')
const AdminVv = () => import('./pages/admin-vv/AdminVv.vue')
const NotFound = () => import('./common/NotFound.vue')

export default createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: Nano, },
        { path: '/vv', component: Vv, },
        { path: '/admin-vv', component: AdminVv, },
        { path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound, },
    ],
}) 