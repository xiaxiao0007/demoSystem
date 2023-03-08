<template>
  <el-main style="width: 100%">
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="username"></el-input>
      <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-message" class="ml-5" v-model="email"></el-input>
      <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position" class="ml-5" v-model="address"></el-input>
      <el-button class="ml-5" type="primary"  @click="getData">搜索</el-button>
      <el-button class="ml-5" type="primary"  @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
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
      <el-upload action="http://localhost:9090/user/import"
                 :show-file-list="false"
                 accept="xlsx"
                 :on-success="handleSuccess"
                 style="display: inline-block" class="ml-5"
      >
        <el-button type="primary" >导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="id" label="id" width="50"></el-table-column>
      <el-table-column prop="username" label="姓名" width="140"></el-table-column>
      <el-table-column prop="nickname" label="昵称" width="120"></el-table-column>
      <el-table-column prop="phone" label="电话" width="120"></el-table-column>
      <el-table-column prop="email" label="邮箱" width="120"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column label="操作"  width="200" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click = "handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
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
        :total="totals">
      </el-pagination>
    </div>

    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="用户名">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </el-main>
</template>

<script>
import { mapGetters,mapState } from 'vuex';
export default {
  name: "Home",
  data() {
    return {
      pageNum:1,
      pageSize:5,
      username: '',
      email:'',
      address:'',
      form: {},
      dialogFormVisible:false,
      multipleSelection: [],
      headerBg: 'headerBg'
    }
  },
  mounted(){
    this.getData()
  },
  computed:{
      ...mapState({
        isSave:state => state.isAlterData,
      }),
      ...mapGetters(['tableData','totals'])
  },
  methods: {
    getData(){
      /*请求分页查询的数据
      fetch("http://localhost:9090/user/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize).then(res => res.json()).then(res => {
        // console.log(res)
        this.tableData = res.pageData
        this.total = res.total
      })
      this.request.get("/user/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username:this.username,
          email:this.email,
          address:this.address,
        }
      }).then(res => {
        // console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })*/
      this.$store.dispatch('getAllUserData',{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username:this.username,
          email:this.email,
          address:this.address,
        }
      )
    },
   async save() {
      /*this.request.post("/user", this.form).then(res => {
        if (res) {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.getData()
        } else {
           this.$message.error("保存失败")
        }
      })*/
       let result = await  this.$API.reqAddUser(this.form);
       if(result.code == 200){
         this.$message.success("保存成功")
         this.dialogFormVisible = false
         this.getData()
       }else{
         this.$message.error("保存失败")
       }
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    handleEdit(row) {
      this.form = row
      this.dialogFormVisible = true
    },
    async del(id){
      /*this.request.delete("/user/" + id).then(res => {
        if (res) {
          this.$message.success("删除成功")
          this.getData()
        } else {
          this.$message.error("删除失败")
        }
      })*/
      let result = await this.$API.reqDeleteOneUser(id)
        if (result.code == 200) {
          this.$message.success("删除成功")
          this.getData()
        } else {
          this.$message.error("删除失败")
        }
    },
    handleSelectionChange(val) {
       this.multipleSelection = val
    },
    delBatch() {
      // let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      // console.log(ids)
      // let result = await this.$API.reqDeleteBatchUser(ids);
      // if(result.code == 200){
      //   this.$message.success("批量删除成功")
      //   this.getData()
      // } else {
      //   this.$message.error("批量删除失败")
      // }
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      console.log()
      this.request.post("/user/del/batch", ids).then(res => {
        if (res) {
          this.$message.success("批量删除成功")
          this.getData()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    exp(){
      window.open("http://localhost:9090/user/export")
    },
    reset(){
      this.username = "";
      this.email ="";
      this.address = "";
      this.getData();
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.getData()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.getData()
    },
    handleSuccess(){
      this.$message.success("导入成功")
      this.getData()
    }
  }
};
</script>
<style>
  .headerBg {
  background: #eee!important;
}
</style>
