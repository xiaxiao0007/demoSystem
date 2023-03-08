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
import TypeNav from "@/views/TypeNav.vue";
import Header from "@/views/Header.vue";
export default {
  name: "Manage",
  components:{TypeNav,Header}
}
</script>

<style scoped>

</style>