import { createRouter, createWebHistory } from 'vue-router'

export default createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: () => import('./App.jsx') },
        { path: '/bark/message', component: () => import('./bark/message/App.jsx') },
        { path: '/:pathMatch(.*)*', name: 'NotFound', component: () => import('./common/NotFound.jsx') },

    ],
})