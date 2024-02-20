<template>
  <div>
    <!--用户管理工具栏-->
    <el-form ref="searchForm" :model="searchForm" :inline="true">
      <el-form-item>
        <el-input v-model="searchForm.name" placeholder="请输入要搜索的用户名称" style="padding-left: 10px;"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="loadUserList">搜索</el-button>
        <el-button type="primary" @click="dialogFormVisible=true">新建</el-button>
        <el-popconfirm title="确定要批量删除这些用户信息吗" style="padding-left: 10px;" @confirm="deleteUser()">
          <el-button type="danger" slot="reference" :disabled="deleteAllBtnStu">批量删除</el-button>
        </el-popconfirm>
      </el-form-item>
    </el-form>

    <!--分配角色对话框-->
    <el-dialog title="权限信息" :visible.sync="dialogUserRoleVisible" width="550px" @close="resetRoleForm('UserRoleForm')">
      <el-form :model="UserRoleForm" ref="UserRoleForm" label-width="100px">
        <el-tree show-checkbox :data="roleTreeData" ref="roleTree" node-key="id" :props="defaultProps" :default-expand-all="true" :check-strictly="true">     
        </el-tree>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitRoleForm('UserRoleForm')">保存分配角色</el-button>
        <el-button @click="resetRoleForm('UserRoleForm')">重置</el-button>
      </div>
    </el-dialog>

    <!--新建或编辑用户对话框-->
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="600px" @close="resetUserForm('UserForm')">
      <el-form :model="UserForm" ref="UserForm" label-width="100px" :rules="UserRules">
        <el-form-item label="用户头像" label-width="100px">
          <!--将本地图片 上次阿里文件服务器上-->
          <el-upload class="avatar-uploader" :action="'/sys/oss/upload'" :show-file-list="false" :on-success="handleAvatarSuccess" :headers="headers">
            <img v-if="UserForm.avatar" :src="UserForm.avatar" class="avatar" style="width: 100%;height: 100%;object-fit:cover;">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>     
        </el-form-item>
        <el-form-item label="用户名" label-width="100px" prop="username">
          <el-input v-model="UserForm.username"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" label-width="100px" prop="email">
          <el-input v-model="UserForm.email"></el-input>
        </el-form-item>
        <el-form-item label="手机号" label-width="100px" prop="tel">
          <el-input v-model="UserForm.tel"></el-input>
        </el-form-item>
        <el-form-item label="用户状态" label-width="100px" prop="statu">
          <el-radio-group v-model="UserForm.statu">
            <el-radio :label="0">禁用</el-radio>
            <el-radio :label="1">正常</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitUserForm('UserForm')">保存用户</el-button>
          <el-button @click="resetUserForm('UserForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!--用户表格数据显示-->
    <el-table v-loading="loading" :data="tableData" style="width: 100%;margin-bottom: 20px;" border :cell-style="{padding:'2px'}" @selection-change="selectionChange">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column label="头像" width="55">
        <template slot-scope="scope">
          <el-avatar size="small" :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名" width="120"></el-table-column>
      <el-table-column label="角色">
        <template slot-scope="scope">
          <el-tag v-for="(role,index) in scope.row.roles" :key="role.id" style="margin-left: 2px;">
            {{ role.name }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="email" label="邮箱" width="200"></el-table-column>
      <el-table-column prop="tel" label="电话" width="150"></el-table-column>
      <el-table-column prop="type" label="用户状态" width="90">
        <template slot-scope="scope">
          <el-tag size="mini" v-if="scope.row.statu===0" type="danger">禁用</el-tag>
          <el-tag size="mini" v-else-if="scope.row.statu===1" type="success">正常</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间">
        <template slot-scope="scope">
          {{ scope.row.created | fmtDate }}
        </template> 
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="280px">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="editRoleMethod(scope.row.id)">分配角色</el-button>   
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" size="small" @click="editUser(scope.row.id)">编辑</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-popconfirm title="确定要重置该用户密码吗" @confirm="resetPassword(scope.row.id,scope.row.username)" style="padding-left: 10px;">
            <el-button type="text" size="small" slot="reference">重置密码</el-button>  
          </el-popconfirm>
          <el-divider direction="vertical"></el-divider>    
          <el-popconfirm title="确定要删除该用户数据吗" @confirm="deleteUser(scope.row.id)" style="padding-left: 10px;">
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
      loading:false,
      tableData:[],//用户表格数据
      //编辑菜单表单 验证
      UserRules:{
        avatar:[{required: true, message:'请添加用户头像', trigger: 'blur'}], 
        username:[{required: true, message:'请输入用户名称', trigger: 'blur'}], 
        email:[{required: true, message:'请输入用户邮箱', trigger: 'blur'}],
        tel:[{required: true, message:'请填写用户手机', trigger: 'blur'}],
        statu:[{required: true, message:'请选择用户状态', trigger: 'blur'}],
      },
      //新建或编辑用户 对话框表单对象
      UserForm:{
        id:'',
        avatar:'',//用户头像图片地址（上传成功之后，会记录上传头像图片在服务器地址）
        username:'',  //用户姓名
        email:'', //用户邮箱
        tel:'', //用户手机
        statu:1,//用户状态
      },
      deleteAllBtnStu:true, //默认 批量删除按钮  是否被禁用
      dialogFormVisible:false,  //新建或编辑用户对话框是否显示
      dialogUserRoleVisible:false,
      searchForm:{
        name:'',  //可以进行用户名 搜索关键词
      },
      current:1,//当前的页码
      size: 5,//一页显示记录数
      total:0,  //总共的记录数
      selection:[], //复选框选中的角色对象
      UserRoleForm:{},
      roleTreeData:{},
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      headers:{
        token:JSON.parse(localStorage.getItem('TOKEN'))
      }
    }
  },
  created(){
    this.loadUserList()
    this.$axios.get('/role/tree').then(response=>{
      this.roleTreeData = response.data.resultdata
    }).catch(e=>{console.log(e)})
  },
  methods:{
    editRoleMethod(id){
      this.$axios.get('/user/info/'+id).then(response=>{
        let user = response.data.resultdata;
        this.UserRoleForm = user; 
        let roleIds = [];
        user.roles.forEach( role =>{
          roleIds.push(role.id)
        });
        this.$refs.roleTree.setCheckedKeys(roleIds); 
      }).catch(e=>{ console.log(e); })

      this.dialogUserRoleVisible = true
    },
    //重置密码
    resetPassword(id,username){
      this.$confirm("重置用户"+username+"的密码，是否继续执行该操作","重置密码提示",{
        confirmButtonText:"执行",
        cancelButtonText:"取消",
        type:'warning'
      }).then(()=>{
        this.$axios.post('/user/repass/'+id).then(response=>{
          this.$message({
            showClose:true,
            message:response.data.resultdata,
            type:'success', 
          });
        }).catch(e=>{console.log(e)})
      })
    },
    //点击页面的切换
    currentChangeHandler(val){
      this.current = val;
      this.loadUserList()
    },
    //修改一页数据记录数
    sizeChangeHandler(val){
      this.size = val;
      this.loadUserList()
    },
    //编辑用户
    editUser(id){
      this.$axios.get('/user/info/'+id).then(response=>{
        this.UserForm = response.data.resultdata
        this.dialogFormVisible = true
      }).catch(e=>{console.log(e)})
    },
    //删除用户
    deleteUser(id){
      var userIds = [];
      id?userIds.push(id):this.selection.forEach(user => {
        userIds.push(user.id)
      })
      this.$axios.post("/user/delete",userIds).then(response => {
        this.loadUserList();
        this.$message({
          showClose:true,
          message:response.data.resultdata,
          type:'success',
        });            
        //将对话框重新隐藏
        this.dialogFormVisible=false;
      }).catch(e=>{console.log(e);})
    },
    //加载用户表格数据
    loadUserList(){
      this.loading=true
      this.$axios.get('/user/list',{
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
      }).catch(e=>{console.log(error)})
    },
    //上传头像成功之后回调
    handleAvatarSuccess(response,file){
      //上传成功，从响应对象response中可以获得上传图片在服务器的地址
      this.UserForm.avatar = response.resultdata
    },
    //新建或编辑用户表单提交
    submitUserForm(UserForm){
      this.$refs[UserForm].validate((valid)=>{
        if(valid){
          this.$axios.post('/user/'+(this.UserForm.id === ''|| this.UserForm.id === null || typeof this.UserForm.id === "undefined"?"save":"update"),this.UserForm).then(response=>{
            this.loadUserList();
            this.$message({
              showClose:true,
              message:response.data.resultdata,
              type:'success',
              onClose:()=>{
                this.resetUserForm(UserForm);
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

    //保存分配角色表单
    submitRoleForm(UserRoleForm){
      //获得tree控件中设置 角色id。
      let roleIds = this.$refs.roleTree.getCheckedKeys();
      this.$axios.post('/user/role/'+this.UserRoleForm.id,roleIds).then(response=>{
        this.$message({
          showClose: true,
          message: response.data.resultdata,
          type:'success',
        });
        this.loadUserList();
        this.dialogUserRoleVisible = false
      }).catch(e=>{ console.log(e); })
    },

    
    //重置新建或编辑用户表单
    resetUserForm(UserForm){
      this.UserForm = {};
      this.$refs[UserForm].resetFields();
    },
    resetRoleForm(UserRoleForm){
      this.UserRoleForm={};
      this.$refs[UserRoleForm].resetFields();
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
  },
  components: {
  }
}
</script>

<style scoped>
.avatar-uploader{
  border: 1px solid #aae9d2;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 178px;
  height: 200px;
}
.avatar-uploader-icon{
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  line-height: 200px;
  text-align: center;
}
</style>
