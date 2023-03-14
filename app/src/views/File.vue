<template>
  <div>
    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="id" width="80"></el-table-column>
      <el-table-column prop="name" label="文件名称"></el-table-column>
      <el-table-column prop="type" label="文件类型"></el-table-column>
      <el-table-column prop="size" label="文件大小/b"></el-table-column>
    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
          :total="totals">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  name: "File",
  data() {
    return {
      pageNum:1,
      pageSize:5,
      name:'',
      multipleSelection: [],
    }
  },
  mounted() {
    this.getData()
  },
  computed:{
    ...mapGetters(['tableData','totals'])
  },
  methods:{
    getData(){
      this.$store.dispatch("getAllFileData",{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.name,
      })
    }
  }
}
</script>

<style scoped>

</style>