import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// 重写编程式路由的push和replace方法
// 先把VueRouter原型对象的push,先保存一份
let originPush = VueRouter.prototype.push
let originReplace = VueRouter.prototype.replace
// 重写push|replace
// 第一个参数(location)：告诉原来的push方法,你往哪里跳转(传递那些参数)
// 第二个参数(resolve):成功的回调
// 第三个参数(reject):失败的回调
VueRouter.prototype.push = function(location,resolve,reject){
  if(resolve && reject){
    // this为当前上下文信息，this为VueRouter实例
    // call||apply
    // 相同点：都可以调用函数一次,都可以篡改函数上下文一次
    // 不同点：call与apply传递参数：call传递参数用逗号隔开，apply方法执行，传递数组
    originPush.call(this,location,resolve,reject);
  }else{
    originPush.call(this,location,()=>{}, ()=>{});
  }
}
VueRouter.prototype.replace = function(location,resolve,reject){
  if(resolve && reject){
    originReplace.call(this,location,resolve,reject);
  }else{
    originReplace.call(this,location,()=>{}, ()=>{});
  }
}

import routers from "@/router/routers";

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  // 配置路由
  routes:routers,
})

export default router
