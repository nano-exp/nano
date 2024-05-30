import { createRouter, createWebHistory } from 'vue-router'

const Nano = () => import('./pages/Nano.jsx')
const Vv = () => import('./pages/vv/Vv.jsx')
const AdminVv = () => import('./pages/admin-vv/AdminVv.jsx')
const NotFound = () => import('./common/NotFound.jsx')

export default createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: Nano, },
        { path: '/vv', component: Vv, },
        { path: '/admin-vv', component: AdminVv, },
        { path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound, },
    ],
})
