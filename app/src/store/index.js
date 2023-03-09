import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

import user from './user'
import person from './person'
export default new Vuex.Store({
    // 实现Vuex仓库模块式开发存储数据
    modules:{
        // 注册相应的小仓库
        user,
        person,
    }
})