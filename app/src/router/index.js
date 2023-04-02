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

import routes from "@/router/routes";

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  // 配置路由
  routes,
})

// 提供一个重置路由的方法
export const resetRouter = () => {
  router.matcher = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
  })
}

export const setRoutes = ()=>{
  let user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : null;
  if(user){
    const storeMenus = user.menus;
    if(storeMenus){
      // 获取当前路由对象名称的数组
      const currentNames = router.getRoutes().map(value => value.name);
      if(!currentNames.includes('Manage')){
        // 拼装动态路由
        const manageRoutes = {
          path:'/',
          name:'Manage',
          component:() => import('../views/Manage.vue'),
          redirect: "/home",
          meta:{
            name:"首页"
          },
          children:[
            {
              path: 'person',
              name: '个人信息',
              component:() => import('../views/Person.vue')
            }
          ]
        }
        const menus = storeMenus;
        menus.forEach(item => {
          if(item.path){ // 当且仅当path不为空的时候才去设置路由
            let itemMenu = {
              path:item.path.replace("/",""),
              name: item.name,
              component:()=> import('../views/'+item.pagePath+'.vue'),
              meta: {
                name:item.name
              }
            }
            manageRoutes.children.push(itemMenu)
          }else if(item.children.length){
            item.children.forEach( item => {
              if(item.path){
                let itemMenu = {
                  path:item.path.replace("/",""),
                  name: item.name,
                  component:()=> import('../views/'+item.pagePath+'.vue'),
                  meta: {
                    name:item.name
                  }
                }
                manageRoutes.children.push(itemMenu)
              }
            })
          }
        })
        router.addRoute(manageRoutes)
      }
    }
  }
}

// 重置我就再set一次路由
setRoutes()

// 配置路由前置守卫,既独享守卫
router.beforeEach(async (to,from,next) =>{
  let user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : null
  let token = null
  if(user != null){
    token = user.token;
  }
  if(token){
    //已经登录还想去登录
    if(to.path==='/login' || to.path==='/register'){
      next("/home")
    }else{
      // 已登录但是不是去登录页和注册页
      // setRoutes()
      next()
    }
  }else {
    // 未登录的情况
    let toPath = to.path
    if (toPath.includes('/login') || toPath.includes('/register')) {
      next()
    }else if(toPath.includes("/")){
      await router.push("/login")
    }
  }
})
// 配置动态路由的信息
// 将从

export default router
