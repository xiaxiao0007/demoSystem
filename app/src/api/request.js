import axios from 'axios'
import router from "@/router";

const request = axios.create({
    // 注意！！ 这里是全局统一加上了 '/api' 前缀，也就是说所有接口都会加上'/api'前缀在，页面里面写接口的时候就不要加 '/api'了，否则会出现2个'/api'，类似 '/api/api/user'这样的报错，切记！！！
    baseURL: 'http://localhost:9090',  // 注意！！ 这里是全局统一加上了 后端接口前缀 前缀，后端必须进行跨域配置！
    timeout: 5000
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';//设置数据传输为json格式
    let user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : null
    if(user){
        config.headers['token'] = user.token;  // 设置请求头
    }
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        // console.log(res)
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        return res;

        // 当权限验证不通过的时候给出提示
        if(res.code === 401){
            router.push('/login')
        }
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)


export default request