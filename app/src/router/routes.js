export default [
    {
        path: "/login",
        name: "login",
        component:()=> import('../views/Login.vue')
    },
    {
        path: "/register",
        name: "register",
        component:()=> import('../views/Register.vue')
    },
]