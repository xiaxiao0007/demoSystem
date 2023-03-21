export default [
    {
        path:'/',
        redirect: '/login',
    },
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
            {
                path: 'person',
                name:'person',
                component:() => import('../views/Person.vue'),
                meta: {
                    name:'个人信息'
                }
            },
            {
                path: 'file',
                name:'file',
                component:() => import('../views/File.vue'),
                meta: {
                    name:'文件管理'
                }
            },
            {
                path:'role',
                name:'role',
                component:()=>import('../views/Role.vue'),
                meta: {
                    name:'角色管理'
                }
            },
            {
                path:'menu',
                name:'menu',
                component:()=>import('../views/Menu.vue'),
                meta: {
                    name:'路径管理'
                }
            }
        ],
    },
    {
        path: '/login',
        name: "login",
        component:()=> import('../views/Login.vue')
    },
    {
        path: '/register',
        name: "register",
        component:()=> import('../views/Register.vue')
    },

]