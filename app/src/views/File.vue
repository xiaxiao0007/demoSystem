<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary"  @click="getData">搜索</el-button>
      <el-button class="ml-5" type="primary"  @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-upload action="http://localhost:9090/file/upload" :show-file-list="false" :on-success="handleSuccess" style="display: inline-block" class="ml-5">
        <el-button type="primary" >文件上传<i class="el-icon-top"></i></el-button>
      </el-upload>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
    </div>

    <el-table :data="fileTableData" border stripe :header-cell-class-name="'headerBg'" >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="id" width="80"></el-table-column>
      <el-table-column prop="name" label="文件名称"></el-table-column>
      <el-table-column prop="type" label="文件类型"></el-table-column>
      <el-table-column prop="size" label="文件大小/b"></el-table-column>
      <el-table-column prop="url" label="下载">
        <template slot-scope="scope">
          <el-button type="primary" @click="download(scope.row.url)">下载</el-button>
        </template>
      </el-table-column>
      <el-table-column label="启用">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作"  width="200" align="center">
        <template slot-scope="scope">
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
          :total="fileTotal">
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
    ...mapGetters(['fileTableData','fileTotal'])
  },
  methods:{
    getData(){
      this.$store.dispatch("getAllFileData",{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.name,
      })
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize;
      this.getData()
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      this.getData()
    },
    handleSuccess(res){
      console.log(res)
      this.getData()
    },
    del(id){
      this.request.delete("/file/" + id).then(res => {
       if (res.code === '200') {
         this.$message.success("删除成功")
         this.getData()
       } else {
         this.$message.error("删除失败")
       }
     })
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/file/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.getData()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    reset(){
      this.name = ''
    },
    download(name){
      let url = this.request.defaults.baseURL + '/file'+name
      console.log(url)
      window.open(url)
    },
    changeEnable(row) {
      this.request.post("/file/update", row).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功")
        }
      })
    },
  },
}
</script>

<style scoped>

</style>