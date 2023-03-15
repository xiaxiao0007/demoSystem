import {reqAllFileData} from "@/api";

const state ={
    fileData:{},
};
const mutations = {
    ALLFILEDATA(state,data){
        state.fileData = data
    }
};
const actions = {
    async getAllFileData(context,param){
        let result = await reqAllFileData(param);
        if (result.code == 200) {
            context.commit("ALLFILEDATA", result.data);
        }
    },
};
const getters ={
    fileTableData(state) {
        return  state.fileData.records || [];
    },
    fileTotal(state){
        return state. fileData.total;
    }
};

// 对外暴露
export default {
    state,
    mutations,
    actions,
    getters,
}