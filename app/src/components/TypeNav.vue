<template>
  <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246); overflow-x: hidden; box-shadow: 2px 0 6px rgb(0 21 41 / 35%);">
    <el-menu style="min-height:100%; overflow-x: hidden"
             background-color="rgb(48, 65, 86)"
             text-color="#fff"
             active-text-color="#ffd04b"
             :collapse-transition="false"
<<<<<<< HEAD
             :collapse="isCollapse_TypeNav"
             router
=======
             :collapse="isCollapse_Type"
>>>>>>> 963f2ef91c127a140d986ca8d509bd3170206a78
    >
      <div style="height: 60px; line-height: 60px; text-align: center">
        <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; margin-right: 5px">
        <b style="color: white" v-show="logoTextShow">后台管理系统</b>
      </div>
<<<<<<< HEAD
      <el-menu-item index="/">
          <i class="el-icon-s-home"></i>
          <span slot="title">主页</span>
      </el-menu-item>
      <el-submenu index="">
=======
      <el-menu-item index="1-1">
        <template slot="title">
          <i class="el-icon-s-home"></i>
          <span slot="title">主页</span>
        </template>
      </el-menu-item>
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span slot="title">系统管理</span>
        </template>
          <el-menu-item index="1-1">
            <i class="el-icon-s-custom"></i>
            <span slot="title">用户管理</span>
          </el-menu-item>
          <el-menu-item index="1-2">选项2</el-menu-item>
      </el-submenu>
      <!-- <el-submenu index="2">
>>>>>>> 963f2ef91c127a140d986ca8d509bd3170206a78
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span slot="title">系统管理</span>
        </template>
<<<<<<< HEAD
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
=======
        <el-menu-item-group>
          <template slot="title">分组一</template>
          <el-menu-item index="2-1">选项1</el-menu-item>
          <el-menu-item index="2-2">选项2</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group title="分组2">
          <el-menu-item index="2-3">选项3</el-menu-item>
        </el-menu-item-group>
        <el-submenu index="2-4">
          <template slot="title">选项4</template>
          <el-menu-item index="2-4-1">选项4-1</el-menu-item>
        </el-submenu>
      </el-submenu> -->
>>>>>>> 963f2ef91c127a140d986ca8d509bd3170206a78
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
<<<<<<< HEAD
      isCollapse_TypeNav: this.isCollapse
=======
      isCollapse_Type: this.isCollapse,
>>>>>>> 963f2ef91c127a140d986ca8d509bd3170206a78
    }
  },
  props:['isCollapse'],
  mounted() {
    this.$bus.$on('show',(data) =>{
<<<<<<< HEAD
      this.isCollapse_TypeNav = data.isCollapse
=======
      this.isCollapse_Type = data.isCollapse
>>>>>>> 963f2ef91c127a140d986ca8d509bd3170206a78
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