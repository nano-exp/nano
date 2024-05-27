import { createRouter, createWebHistory } from 'vue-router'

const Nano = () => import('./Nano.jsx')
const Vv = () => import('./Vv.jsx')
const NotFound = () => import('./common/NotFound.jsx')

export default createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: Nano, },
        { path: '/vv', component: Vv, },
        { path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound, },
    ],
})
