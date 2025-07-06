import { createRouter, createWebHistory } from 'vue-router'

const Nano = () => import('#@/pages/Nano.vue')
const Vv = () => import('#@/pages/vv/Vv.vue')
const VvAdmin = () => import('#@/pages/vv-admin/VvAdmin.vue')
const NotFound = () => import('#@/common/NotFound.vue')

export default createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', component: Nano },
    { path: '/vv', component: Vv },
    { path: '/vv-admin', component: VvAdmin },
    { path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound },
  ],
})
