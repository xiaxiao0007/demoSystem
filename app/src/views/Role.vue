<template>
  <div>
    <div style="margin:10px 10px 0 5px;">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
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
    </div>
    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="id" label="id" width="50"></el-table-column>
      <el-table-column prop="name" label="姓名" width="200" align="center"></el-table-column>
      <el-table-column prop="flag" label="唯一标识"></el-table-column>
      <el-table-column prop="description" label="描述" align="center"></el-table-column>
      <el-table-column label="操作"  width="auto" align="center">
        <template slot-scope="scope">
          <el-button type="info" @click = "allocationPower(scope.row)">分配菜单权限<i class="el-icon-menu"></i></el-button>
          <el-button type="success" @click = "handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
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
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="唯一标识">
          <el-input v-model="form.flag" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="菜单分配" :visible.sync="menuVisible" width="30%" >
      <!-- check-strictly 取消父子关联 -->
      <el-tree
      :props="props"
      :data="menuData"
      show-checkbox
      node-key="id"
      ref="tree"
      check-strictly
      :default-expanded-keys="expends"
      :default-checked-keys="checks">
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span><i :class="data.icon"></i> {{ data.name }}</span>
      </span>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menuVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "role",
  data(){
    return {
      name:'',
      pageNum: 1,
      pageSize: 5,
      form:{},
      tableData:[],
      total:0,
      dialogFormVisible:false,
      menuVisible:false,
      multipleSelection:[],
      menuData: [],
      props: {
        label: 'name',
      },
      expends: [],
      checks: [],
      roleId: 0, // 角色管理的id
      roleFlag: '',
      ids: []
    }
  },
  mounted() {
    this.getData();
  },
  methods:{
    getData(){
      this.request.get("/role/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.name,
        }
      }).then(res => {
        this.tableData = res.records
        this.total = res.total
      })

      // 获取菜单管理中的所有的按钮
      this.request.get("/menu/ids").then(r => {
        this.ids = r.data
      })
    },
    reset(){
      this.name = '';
      this.getData();
    },
    handleEdit(row){
      this.form =  JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },
    del(id){
      this.request.delete("/role/" + id).then(res => {
        if (res) {
         this.$message.success("删除成功")
         this.getData()
       } else {
         this.$message.error("删除失败")
       }
     })
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/role/del/batch", ids).then(res => {
        if (res.code === '200') {
         this.$message.success("批量删除成功")
         this.getData()
        } else {
         this.$message.error("批量删除失败")
        }
     })
    },
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    save(){
      this.request.post("/role", this.form).then(res => {
       if (res) {
         this.$message.success("保存成功")
         this.dialogFormVisible = false
         this.getData()
       } else {
          this.$message.error("保存失败")
       }
     })
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize;
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
    },
    async allocationPower(role){
      this.roleId = role.id
      this.roleFlag = role.flag

      this.request.get("/menu").then(res => {
        this.menuData = res.data
        // 把后台返回的菜单数据处理成id数据
        this.expends = this.menuData.map(v => v.id)
      })

      this.request.get("/role/roleMenu/" + role.id).then(res =>{
        this.checks = res.data
       /* this.checks.forEach(id => {
          if(!this.checks.includes(id)){
            this.$nextTick(()=>{ // $nextTick 渲染未来的元素
              this.$refs.tree.setChecked( id,false, false)
            })
          }
        })*/
        this.menuVisible = true
      })
    },
    saveRoleMenu(){
      this.request.post("/role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys()).then(
        res => {
          /*console.log(this.roleId)
          console.log(this.$refs.tree.getCheckedKeys())*/
          if (res.code === '200') {
            this.$message.success("保存成功")
            this.menuVisible = false
          } else {
            this.$message.error(res.msg)
          }
        }
      )
    }
  },
}
</script>

<style scoped>
.headerBg {
  background: #eee!important;
}
</style>