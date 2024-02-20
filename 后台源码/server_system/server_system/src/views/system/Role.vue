<template>
  <div>
    <!--角色管理的工具栏-->
    <el-form ref="searchForm" :model="searchForm" :inline="true">
      <el-form-item>
        <el-input v-model="searchForm.name" placeholder="请输入要搜索的角色名称" style="padding-left: 10px;"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="loadRoleList">搜索</el-button>
        <el-button type="primary" @click="dialogFormVisible=true">新建</el-button>
        <el-popconfirm title="确定要批量删除这些角色信息吗" style="padding-left: 10px;" @confirm="deleteRole()">
          <!-- disabled属性表示该标签是否为 不可用 -->  
          <el-button type="danger" slot="reference" :disabled="deleteAllBtnStu">批量删除</el-button>
        </el-popconfirm>
      </el-form-item>
    </el-form>

    <!--角色对户框-->
    <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="550px" @close="resetRoleForm('RoleForm')">
      <el-form :model="RoleForm" ref="RoleForm" label-width="100px" :rules="RoleRules">
        <el-form-item label="角色名称" label-width="100px" prop="name">
          <el-input v-model="RoleForm.name"></el-input>
        </el-form-item>

        <el-form-item label="角色编码" label-width="100px" prop="code">
          <el-input v-model="RoleForm.code"></el-input>
        </el-form-item>

        <el-form-item label="描述" label-width="100px" prop="remark">
          <el-input v-model="RoleForm.remark"></el-input>
        </el-form-item>

        <el-form-item label="角色状态" label-width="100px" prop="statu">
          <el-radio-group v-model="RoleForm.statu">
            <el-radio :label="0">禁用</el-radio>
            <el-radio :label="1">正常</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitRoleForm('RoleForm')">保存角色</el-button>
          <el-button @click="resetRoleForm('RoleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!--权限对话框-->
    <el-dialog title="权限信息" :visible.sync="dialogPermVisible" width="550px" @close="resetPermForm('PermForm')">
      <el-form :model="PermForm" ref="PermForm" label-width="100px">
        <el-tree show-checkbox :data="permTreeData" ref="permTree" node-key="id" :props="defaultProps" :default-expand-all="true" :check-strictly="true">     
        </el-tree>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitPermForm('PermForm')">保存权限</el-button>
        <el-button @click="resetPermForm('PermForm')">重置</el-button>
      </div>
    </el-dialog>

    <!--角色表格数据的显示-->
    <el-table v-loading="loading" :data="tableData" style="width: 100%;margin-bottom: 20px;" border :cell-style="{padding:'2px'}" 
    @selection-change="selectionChange">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column prop="name" label="角色名称" width="200"></el-table-column>
      <el-table-column prop="code" label="角色编码" width="150"></el-table-column>
      <el-table-column prop="remark" label="角色描述" width="500"></el-table-column>
      <el-table-column prop="type" label="角色状态" width="90">
        <template slot-scope="scope">
          <el-tag size="mini" v-if="scope.row.statu===0" type="danger">禁用</el-tag>
          <el-tag size="mini" v-else-if="scope.row.statu===1" type="success">正常</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="editRole(scope.row.id)">编辑</el-button>   
          <el-button type="text" size="small" @click="permsHandler(scope.row.id)">分配权限</el-button>      
          <el-popconfirm title="确定要删除该角色数据吗" @confirm="deleteRole(scope.row.id)" style="padding-left: 10px;">
            <el-button size="small" slot="reference" type="text">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--分页的代码-->
    <el-pagination background layout="prev,pager,next,sizes,total,jumper" :total="total" :page-size="size" :page-sizes="[5,10,15,20,25]"
    :current-page="current" @size-change="sizeChangeHandler" @current-change="currentChangeHandler">

    </el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      deleteAllBtnStu:true, //true 不可用，false 可用
      tableData:[],
      //编辑菜单表单 验证
      RoleRules:{
        name:[{required: true, message:'请输入角色名称', trigger: 'blur'}], 
        code:[{required: true, message:'请输入角色编码', trigger: 'blur'}],
        remark:[{required: true, message:'请填写角色描述', trigger: 'blur'}],
        statu:[{required: true, message:'请选择角色状态', trigger: 'blur'}],
      },
      //新建/编辑角色对话框是否显示
      dialogFormVisible:false,
      dialogPermVisible:false,
      loading:false,
      //工具栏 搜索框的表单对象
      searchForm:{
        name:'', //可以按照角色名字进行所载
      },
      PermForm:{},
      permTreeData:{},
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      RoleForm:{  //新建/编辑角色对户口对象
          id:0,
          name:'',//角色名称
          code:'',//角色编码
          remark:'',//角色描述
          statu:1,//角色状态 1正常  0禁用
      },
      current:1,//当前的页码
      size: 5,//一页显示记录数
      total:0,  //总共的记录数
      selection:[], //复选框选中的角色对象
    }
  },
  created(){
    this.loadRoleList()
  },
  methods:{
    //点击页面的切换
    currentChangeHandler(val){
      this.current = val;
      this.loadRoleList()
    },
    //修改一页数据记录数
    sizeChangeHandler(val){
      this.size = val;
      this.loadRoleList()
    },
    //分配角色权限
    permsHandler(id){
      this.$axios.get("/role/info/"+id).then(response=>{
        console.log(id+"角色所能操作的菜单："+response.data.resultdata.menuIds);
        this.$refs.permTree.setCheckedKeys(response.data.resultdata.menuIds)
        this.PermForm = response.data.resultdata
      }).catch(e=>{console.log(e)})   
      this.dialogPermVisible = true
    },
    //删除角色信息
    deleteRole(id){
      var roleIds = []; 
      id?roleIds.push(id):this.selection.forEach(role => {
        roleIds.push(role.id)
      })
      this.$axios.post("/role/delete",roleIds).then(response=>{
        this.$message({
          showClose:true,
          message:response.data.resultdata,
          type:'success',
          onClose:()=>{
            this.loadRoleList();
          }  
        });            
        //将对话框重新隐藏
        this.dialogFormVisible=false;
      }).catch(e=>{console.log(e);})
    },
    //编辑角色信息
    editRole(id){
      //TODO: 请求服务器端，通过角色id 查询该角色的详细信息，在对户框中进行回显
      this.$axios.get("/role/info/"+id).then(response=>{
        this.RoleForm = response.data.resultdata
        this.dialogFormVisible = true
      }).catch(e=>{ console.log(e); })
    },
    //请求Java服务器端获得所有的角色
    loadRoleList(){
      this.loading=true
      this.$axios.get('/role/list',{
        params:{
          name:this.searchForm.name,  //搜索关键词
          current:this.current,   //页码
          size:this.size, //一页显示多少条
        }
      }).then(response=>{
        this.tableData = response.data.resultdata.records
        this.total = response.data.resultdata.total
        this.size = response.data.resultdata.size
        this.current = response.data.resultdata.current
        this.loading=false
      }).catch(e=>{console.log(e)})

      this.$axios.get('/menu/list').then(response=>{
        this.permTreeData = response.data.resultdata
      }).catch(e=>{console.log(e)})
    },
     //提交 新建表单  
    submitRoleForm(FormName){
      this.$refs[FormName].validate((valid)=>{
        if(valid){
          this.$axios.post('/role/'+(this.RoleForm.id!=0?"update":"save"),this.RoleForm).then(response=>{
            this.loadRoleList();
            this.$message({
              showClose:true,
              message:response.data.resultdata,
              type:'success',
              onClose:()=>{
                this.resetRoleForm(FormName);
              }  
            });            
            //将对话框重新隐藏
            this.dialogFormVisible=false;
          }).catch(e=>{console.log(e);})
        }else{
          return false
        }
      })
    },
    submitPermForm(FormName){
      let menuIds = this.$refs.permTree.getCheckedKeys();
        this.$axios.post('/role/perm/'+this.PermForm.id,menuIds).then(response=>{
          this.loadRoleList();
          this.$message({
            showClose:true,
            message:response.data.resultdata,
            type:'success',
          });            
          //将对话框重新隐藏
          this.dialogPermVisible=false;
        }).catch(e=>{console.log(e);})
    },
    //重置角色权限分配对话框表单
    resetPermForm(FormName){
      this.PermForm = {};
      this.$refs[FormName].resetFields();
    },
    //重置 新建角色表单
    resetRoleForm(FormName){
      this.RoleForm = {};
      this.$refs[FormName].resetFields();
    },
    //选中表格中 复选框触发的事件
    selectionChange(value){
      //value参数就是选中的复选框 对应的这一行角色对象
      this.selection = value
      if(this.selection){
        //this.selection.length === 0 如果选中，数组不为0，返回false
        //this.selection.length === 0 如果没有选中，数组长度为0，返回true
        this.deleteAllBtnStu = (this.selection.length === 0)
      }
    },
  } 
}
</script>

<style scoped>
</style>
