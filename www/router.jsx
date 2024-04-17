import { createRouter, createWebHistory } from 'vue-router'

const Nano = () => import('./Nano.jsx')
const BarkMessage = () => import('./bark/message/App.jsx')
const NotFound = () => import('./common/NotFound.jsx')

export default createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: Nano, },
        { path: '/bark/message', component: BarkMessage, },
        { path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound, },
    ],
})
