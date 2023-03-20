<template>
  <el-card style="width: 500px;">
    <el-form label-width="80px" size="small">
      <el-upload
          class="avatar-uploader"
          action="http://localhost:9090/file/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
      >
        <img v-if="form.avatarUrl" :src="avatarUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>

      <el-form-item label="用户名">
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
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
      user: sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {},
      form:{},
      dialogFormVisible:true
    }
  },
  created() {
    this.getUserInfo().then(res => {
      this.form = res
    })
  },
  computed:{
    avatarUrl(){
      let url = null;
      let headImageUrl = this.form.avatarUrl  // 如何避免undefined造成的影响
      if( headImageUrl != undefined || headImageUrl != "" || headImageUrl != null ){
        url = this.request.defaults.baseURL + headImageUrl
      }
      return url
    }
  },
  methods:{
    async getUserInfo(){
      let result = await this.$API.reqUserInfo(this.user.id)
      if(result.code == 200){
        return result.data // 这个返回的是Promise封装的对象
      }else{
        this.$message.error("用户信息取出错误")
      }
    },
    save(){
      this.request.post("/user", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")

           /*this.$bus.$emit('imageUrl',this.form.avatarUrl)*/

          // 更新浏览器存储的用户信息
          this.getUserInfo().then(res => {
            res.token = JSON.parse(sessionStorage.getItem("user")).token
            sessionStorage.setItem("user", JSON.stringify(res))

            this.$bus.$emit('imageUrl',this.form.avatarUrl)
            // 通过定义的全局事件，将信息传递给兄弟组件
           /* this.$bus.$emit('imageUrl')*/
          })
          // 自定义事件
          /*this.$emit('refreshUser')*/

        } else {
          this.$message.error("保存失败")
        }
      })
    },
    handleAvatarSuccess(res){
      // 上传成功后返回的url的值，存入到用户表之中
      this.form.avatarUrl = res.data
    }
  }
}
</script>

<style scoped>
.avatar-uploader {
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
  text-align: center;
}
.avatar {
  width: 138px;
  height: 138px;
  display: block;
}
</style>