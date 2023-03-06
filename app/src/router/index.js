import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path:'/',
    name:'',
    redirect: '/home',
    component: () => import('../views/Manage.vue'),
    children: [
      {
        path:'home',
        name:'home',
        component:() => import('../views/Home.vue'),
        meta:{
          name:'首页',
        }
      },
      {
        path: 'user',
        name:'user',
        component: () => import('../views/User.vue'),
        meta:{
          name:'用户管理'
        }
      },
    ],
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
