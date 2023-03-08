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
                path: '/person',
                name:'person',
                component:() => import('../views/Person.vue'),
                meta: {
                    name:'个人信息'
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
        path: '/register',
        name: "register",
        component:()=> import('../views/Register.vue')
    },

]