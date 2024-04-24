import { createRouter, createWebHistory } from 'vue-router'

const Nano = () => import('./Nano.jsx')
const NotFound = () => import('./common/NotFound.jsx')

export default createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: Nano, },
        { path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound, },
    ],
})
