<template>
  <el-header style="font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px; display: flex" >
    <div style="flex: 1; font-size: 20px">
      <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
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
      this.isCollapse_Header = !this.isCollapse_Header
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