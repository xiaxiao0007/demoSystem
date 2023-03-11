// 对当前这个模块的API进行统一管理
import requests from "./request";

// 用户Vue组件使用的数据api data为对象形式的参数
export const reqAllUserData = (data) =>requests({ url: "/user/page", params:data, method: "get" });

// 增加用户 data为表格数据，即也为对象,data触发js语法简写形式
export const reqAddUser = (data) =>requests({ url: "/user", data:data, method: "post" });

// 根据id删除一个用户
export const reqDeleteOneUser = (data) => requests({url:"/user/"+data, method:"delete"})

// 删除一批用户
export const reqDeleteBatchUser = (ids) => requests({url:"/user/del/batch",data:ids, method:"post"})

// 登录 验证数据
export const reqVerifyUserData = (userData) => requests({url:"/user/login",data:userData, method:"post"})

// 注册 数据传输到后台
export const reqRegisterUser = (user) => requests({method:"post",data:user,url:"/user/register"})

// 个人信息的获取
export const reqUserInfo = (data) => requests({url:"/user/getInfo/"+data, method:"get"})