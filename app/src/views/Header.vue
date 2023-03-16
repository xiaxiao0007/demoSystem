<template>
  <el-header style="border-bottom: 1px solid #ccc; line-height: 60px; display: flex" >
    <div style="flex: 1;">
      <span :class="collapseBtnClass" style="cursor: pointer;font-size: 18px" @click="collapse"></span>
      <el-breadcrumb separator="/" style="display: inline-block;margin-left: 10px;">
        <el-breadcrumb-item :to="{ path: '/manage/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{$route.meta.name}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <el-dropdown style="width: 100px; cursor: pointer">
      <div style="display: inline-block">
        <!-- 图片地址只需要取存放目录就行 -->
        <img :src="avatarUrl" alt=""
             style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
        <span style=" ">{{ user.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      </div>
      <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <router-link to="/manage/person" style="text-decoration: none">个人信息</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <span style="text-decoration: none" @click="logout">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </el-header>
</template>

<script>
export default {
  name: "Header",
  data(){
    return{
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse_Header:this.isCollapse,
      user: sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {},
    }
  },
  props:['isCollapse'],
  mounted() {
    this.$bus.$on('imageUrl',(data) =>{
      this.user.avatarUrl = data
    })
  },
  beforeDestroy() {
    this.$bus.$off('imageUrl')
  },
  computed:{
    avatarUrl(){
      let url = null;
      let headImageUrl = this.user.avatarUrl // 如何避免undefined造成的影响
      if( headImageUrl != undefined || headImageUrl != "" || headImageUrl != null ){
        url = this.request.defaults.baseURL + headImageUrl
      }
      return url
    }
  },
  methods:{
    collapse() {  // 点击收缩按钮触发
      this.isCollapse_Header= !this.isCollapse_Header
      if (this.isCollapse_Header) {  // 收缩
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.$bus.$emit('show',{isCollapse:this.isCollapse_Header,sideWidth:64,logoTextShow:false})
      } else {   // 展开
        this.collapseBtnClass = 'el-icon-s-fold'
        this.$bus.$emit('show',{isCollapse:this.isCollapse_Header,sideWidth:200,logoTextShow:true})
      }
    },
    logout(){
      sessionStorage.removeItem("user"); // 先push的话，token还在，所以走有token的那个分支，所以会出错，退出不了
      this.$router.push("/login")
      this.$message.success("退出成功")
    }
  },
}
</script>

<style scoped>

</style>
