<template>
  <el-header style="border-bottom: 1px solid #ccc; line-height: 60px; display: flex" >
    <div style="flex: 1;">
      <span :class="collapseBtnClass" style="cursor: pointer;font-size: 18px" @click="collapse"></span>
      <el-breadcrumb separator="/" style="display: inline-block;margin-left: 10px;">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{$route.meta.name}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <el-dropdown style="width: 70px; cursor: pointer">
      <span>王小虎</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>个人信息</el-dropdown-item>
        <el-dropdown-item>退出</el-dropdown-item>
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
    }
  },
  props:['isCollapse'],
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
  },
}
</script>

<style scoped>

</style>