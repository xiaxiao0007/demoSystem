<template>
<div>
  {{user.nickname}}
</div>
</template>

<script>
export default {
  name: "Person",
  data(){
    return{
      user:sessionStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },

}
</script>

<style scoped>

</style>