import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

import routers from "@/router/routers";

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  // 配置路由
  routes:routers,
})

export default router
