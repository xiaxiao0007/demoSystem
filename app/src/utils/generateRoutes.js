// 递归数据菜单
// export function generateDynamicRoutes(menus,dynamicRoute){
//     menus.forEach(menu =>{
//         let route = {}
//         // 存在子路由,则递归遍历，并返回数据作为children保存,
//         // 对应有子路由的路由使用递归来管理
//         if(menu.children && menu.children.length > 0){
//             route = menuToRoute(menu);
//             route.children = generateDynamicRoutes(menu.children)
//         }else{
//
//         }
//     })
// }

// 根据从数据取出的路由菜单形式,生成路由的基本格式
export function menuToRoute(menu){
    if(!menu.component){
        return null;
    }else {
        let route ={
            name:menu.name,
            path:menu.path,
            meta:{
                name:menu.description
            }
        }
        // 路由所在的组件
        route.component =() => import('@/views/'+menu.pagePath+'.vue');
        // 路由的子路由
        return route;
    }
}
