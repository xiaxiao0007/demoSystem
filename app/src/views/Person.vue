<template>
  <el-card class="div1">
    <el-form label-width="80px" size="small">
      <el-form-item label="用户名">
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item style="float: right">
        <el-button type="primary" @click="save">确 定</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  name: "Person",
  data(){
    return{
      user:this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {},
      form:{},
      dialogFormVisible:true
    }
  },
  created() {
    this.getUserInfo().then(res => {
      this.form = res
    })
  },
  methods:{
    async getUserInfo(){
      let result = await this.$API.reqUserInfo(this.user.id)
      if(result.code == 200){
        this.$message.success("信息取出成功");
        return result.data // 这个返回的是Promise封装的对象
      }else{
        this.$message.error("用户信息取出错误")
      }
    },
    save(){

    }
  }

}
</script>

<style scoped>
.div1{
  margin: auto;
  width: 800px;
  padding:120px 150px 120px 150px;
  /*border:2px solid #FC466B;
  background: aqua;*/
}
</style>