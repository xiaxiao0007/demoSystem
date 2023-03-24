<template>
  <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246); overflow-x: hidden; box-shadow: 2px 0 6px rgb(0 21 41 / 35%);">
    <el-menu style="min-height:100%; overflow-x: hidden"
             background-color="rgb(48, 65, 86)"
             text-color="#fff"
             active-text-color="#ffd04b"
             :collapse-transition="false"
             :collapse="isCollapse_TypeNav"
             router
             :default-openeds="opens"
    >
      <div style="height: 60px; line-height: 60px; text-align: center">
        <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; margin-right: 5px">
        <b style="color: white" v-show="logoTextShow">后台管理系统</b>
      </div>

      <div v-for="item in menus" :key="item.id">
        <div v-if="item.path == '/manage/home'">
          <el-menu-item :index="item.path">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.name }}</span>
          </el-menu-item>
        </div>
        <div v-else>
          <el-submenu :index="item.id + ''">
            <template slot="title">
              <i :class="item.icon"></i>
              <span slot="title">{{ item.name }}</span>
            </template>
            <div  v-for="subItem in item.children" :key="subItem.id">
              <el-menu-item :index="subItem.path">
                <i :class="subItem.icon"></i>
                <span slot="title">{{ subItem.name }}</span>
              </el-menu-item>
            </div>
          </el-submenu>
        </div>
      </div>
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
      isCollapse_TypeNav: this.isCollapse,
      menus:[],
      opens:[]
    }
  },
  props:['isCollapse'],
  mounted() {
    this.$bus.$on('show',(data) =>{
      this.isCollapse_TypeNav = data.isCollapse
      this.sideWidth = data.sideWidth
      this.logoTextShow = data.logoTextShow
    })

    let user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : null
    this.menus = user.menus
    this.opens = user.menus.map(v => v.id + '')
  },
  beforeDestroy() {
    this.$bus.$off('show')
  },
}
</script>

<style scoped>

</style>
