import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from '@/store'
import * as API from '@/api'

// 引入饿了么UI
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './style/reset.css'
import request from "@/api/request";

Vue.config.productionTip = false

Vue.use(ElementUI,{size:'small'})
Vue.prototype.request = request

new Vue({
  beforeCreate() {
  //   注册全局事件总线 实现兄弟间的通信（既数据的传递）
    Vue.prototype.$bus= this
    // 使API能在全局使用
    Vue.prototype.$API = API
  },
  router,
  store,
  render: h => h(App)
}).$mount('#app')
