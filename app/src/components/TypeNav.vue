<template>
  <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246); overflow-x: hidden; box-shadow: 2px 0 6px rgb(0 21 41 / 35%);">
    <el-menu style="min-height:100%; overflow-x: hidden"
             background-color="rgb(48, 65, 86)"
             text-color="#fff"
             active-text-color="#ffd04b"
             :collapse-transition="false"
             :collapse="isCollapse_TypeNav"
             router
    >
      <div style="height: 60px; line-height: 60px; text-align: center">
        <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; margin-right: 5px">
        <b style="color: white" v-show="logoTextShow">后台管理系统</b>
      </div>
      <el-menu-item index="/">
          <i class="el-icon-s-home"></i>
          <span slot="title">主页</span>
      </el-menu-item>
      <el-submenu index="">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span slot="title">系统管理</span>
        </template>
        <el-menu-item index="/user">
          <i class="el-icon-s-custom"></i>
          <span slot="title">用户管理</span>
        </el-menu-item>
      </el-submenu>

<!--      <el-submenu index="3">-->
<!--        <template slot="title">-->
<!--          <i class="el-icon-setting"></i>-->
<!--          <span slot="title">导航三</span>-->
<!--        </template>-->
<!--        <el-menu-item-group>-->
<!--          <template slot="title">分组一</template>-->
<!--          <el-menu-item index="3-1">选项1</el-menu-item>-->
<!--          <el-menu-item index="3-2">选项2</el-menu-item>-->
<!--        </el-menu-item-group>-->
<!--        <el-menu-item-group title="分组2">-->
<!--          <el-menu-item index="3-3">选项3</el-menu-item>-->
<!--        </el-menu-item-group>-->
<!--        <el-submenu index="3-4">-->
<!--          <template slot="title">选项4</template>-->
<!--          <el-menu-item index="3-4-1">选项4-1</el-menu-item>-->
<!--        </el-submenu>-->
<!--      </el-submenu>-->
    </el-menu>
  </el-aside>
</template>

<script>
export default {
  name: "TypeNav",
  data(){
    return {
      sideWidth: 200,
      logoTextShow: true,
      isCollapse_TypeNav: this.isCollapse
    }
  },
  props:['isCollapse'],
  mounted() {
    this.$bus.$on('show',(data) =>{
      this.isCollapse_TypeNav = data.isCollapse
      this.sideWidth = data.sideWidth
      this.logoTextShow = data.logoTextShow
    })
  },
  beforeDestroy() {
    this.$bus.$off('show')
  },
}
</script>

<style scoped>

</style>