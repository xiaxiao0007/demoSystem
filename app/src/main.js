import Vue from 'vue'
import App from './App.vue'
import router from './router'

// 引入饿了么UI
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './style/reset.css'
import request from "@/api/request";

Vue.config.productionTip = false

Vue.use(ElementUI,{size:'small'})
Vue.prototype.request = request

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
