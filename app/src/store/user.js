import { reqAllUserData } from '@/api'

const state ={
    userdata: {},
    // isAlterData:false,
};
const mutations = {
    ALLUSERDATA(state, userdata) {
        state.userdata = userdata;
    },
    // ADDUSER(state, data){
    //     state.isAlterData = data
    // }
};
const actions = {
    //获取购物车列表数据
    async getAllUserData(context,param){
        let result = await reqAllUserData(param);
        if (result.code == 200) {
            context.commit("ALLUSERDATA", result.data);
        }
    },

    // async getAddUser(context,data){
    //     let result = await reqAddUser(data);
    //     if(result.code == 200){
    //         context.commit("ADDUSER",result.data)
    //     }
    // }
};
const getters ={
    tableData(state) {
        return  state.userdata.records || [];
    },
    totals(state){
        return state.userdata.total;
    }
};
// 对外暴露
export default {
    state,
    mutations,
    actions,
    getters
}