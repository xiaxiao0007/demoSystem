<template>
  <div id="app">

    <router-view/>
  </div>
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
