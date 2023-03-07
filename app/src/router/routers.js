export default [
    {
        path:'/manage',
        name:'manage',
        redirect: '/manage/home',
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
    {
        path: '/login',
        name: "login",
        component:()=> import('../views/Login.vue')
    },
    {
        path:'/',
        redirect: '/login',
    }
]