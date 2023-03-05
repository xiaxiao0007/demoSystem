<template>
  <el-container style="border: 1px solid #eee; min-height:100vh">
    <type-nav :isCollapse="false"></type-nav>
    <el-container style="display: flex;flex-direction: column">
      <Header :isCollapse="false"></Header>
      <router-view/>
    </el-container>
  </el-container>
</template>
<script>
import Header from "@/components/Header.vue"
import TypeNav from "@/components/TypeNav.vue";
export default {
  name:'App',
  components:{
    Header,TypeNav
  }
}
</script>
<style>
</style>
