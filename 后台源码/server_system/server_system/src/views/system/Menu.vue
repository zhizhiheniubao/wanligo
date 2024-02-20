<template>
  <div class="menu">
    <!--菜单管理的工具栏-->
    <el-form :index="true" style="padding-left: 10px;">
      <el-form-item>
        <el-button type="primary" @click="dialogFormVisible=true">新增菜单</el-button>
      </el-form-item>
    </el-form>

    <!--新增菜单的对话框-->
    <el-dialog title="新建菜单" :visible.sync="dialogFormVisible" width="550px" @close="resetMenuForm('MenuForm')">
      <el-form :model="MenuForm" ref="MenuForm" label-width="100px" :rules="MenuRules">
        <!--下拉列表显示父菜单-->
        <el-form-item label="上级菜单" prop="parentId">
          <el-select v-model="MenuForm.parentId" placeholder="请选择上级菜单">
            <template v-for="item in tableData">
              <el-option :label="item.name" :value="item.id"></el-option>
              <template v-for="child in item.children">
                <el-option :label="child.name" :value="child.id">
                  <span>{{ " - " + child.name }}</span>
                </el-option>
              </template>
            </template>
          </el-select>
        </el-form-item>

        <el-form-item label="菜单名称" label-width="100px" prop="name">
          <el-input v-model="MenuForm.name"></el-input>
        </el-form-item>

        <el-form-item label="权限编码" label-width="100px" prop="perms">
          <el-input v-model="MenuForm.perms"></el-input>
        </el-form-item>

        <el-form-item label="图    标" label-width="100px">
          <el-input v-model="MenuForm.icon"></el-input>
        </el-form-item>

        <el-form-item label="菜单URL" label-width="100px">
          <el-input v-model="MenuForm.path"></el-input>
        </el-form-item>

        <el-form-item label="菜单组件" label-width="100px">
          <el-input v-model="MenuForm.component"></el-input>
        </el-form-item>

        <el-form-item label="菜单类型" label-width="100px" prop="type">
          <el-radio-group v-model="MenuForm.type">
            <el-radio :label="0">目录</el-radio>
            <el-radio :label="1">菜单</el-radio>
            <el-radio :label="2">按钮</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="菜单状态" label-width="100px" prop="statu">
          <el-radio-group v-model="MenuForm.statu">
            <el-radio :label="0">禁用</el-radio>
            <el-radio :label="1">正常</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitMenuForm('MenuForm')">保存菜单</el-button>
          <el-button @click="resetMenuForm('MenuForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-table v-loading="loading" :data="tableData" style="width: 100%;margin-bottom: 20px;" row-key="id" border default-expand-all
    :tree-props="{children: 'children', hasChildren: 'hasChildren'}" :cell-style="{padding:'2px'}">
      <el-table-column prop="name" label="名称" width="180"></el-table-column>
      <el-table-column prop="perms" label="权限编码" width="180"></el-table-column>
      <el-table-column prop="icon" label="菜单图标" width="120"></el-table-column>
      <el-table-column prop="type" label="菜单类别" width="120">
        <!--scope.row对象取出就表格循环 每一个数据（每一个菜单对象）-->
        <template slot-scope="scope">
          <el-tag size="mini" v-if="scope.row.type===0" type="info">目录</el-tag>
          <el-tag size="mini" v-else-if="scope.row.type===1">菜单</el-tag>
          <el-tag size="mini" v-else-if="scope.row.type===2" type="warning">按钮</el-tag>
        </template> 
      </el-table-column>
      <el-table-column prop="path" label="菜单URL"></el-table-column>
      <el-table-column prop="component" label="菜单组件"></el-table-column>
      <el-table-column prop="type" label="菜单状态" width="120">
        <!--scope.row对象取出就表格循环 每一个数据（每一个菜单对象）-->
        <template slot-scope="scope">
          <el-tag size="mini" v-if="scope.row.statu===0" type="danger">禁用</el-tag>
          <el-tag size="mini" v-else-if="scope.row.type===1" type="success">正常</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="editMenu(scope.row.id)">编辑</el-button>        
          <el-popconfirm title="确定要删除这个菜单吗" @confirm="deleteMenu(scope.row.id)" style="padding-left: 10px;">
            <el-button size="small" slot="reference" type="text">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData:[],
      //编辑菜单表单 验证
      MenuRules:{
        parentId:[{required: true, message:'请选择上级菜单', trigger: 'blur'}], 
        name:[{required: true, message:'请输入菜单名称', trigger: 'blur'}], 
        perms:[{required: true, message:'请输入菜单权限编码', trigger: 'blur'}],
        type:[{required: true, message:'请选择菜单类型', trigger: 'blur'}],
        statu:[{required: true, message:'请选择菜单状态', trigger: 'blur'}],
      },
      loading:false,
      //控制新建菜单的表单是否显示
      dialogFormVisible:false,
      //新建菜单对象
      MenuForm:{
        id:0,
        parentId:"", //上级菜单的ID
        name:"",//菜单名称
        perms:'',//权限字符串
        icon:'',//图标
        path:'',//菜单路由路径
        component:'',//菜单组件
        type:0,//菜单类型  0 目录  1 菜单    2 按钮
        statu:0,//菜单状态 1 正常  0 禁用
      }
    }
  },
  created(){
    //请求获取页面管理
    this.loadMenuList()
  },
  methods:{
    //编辑菜单方法
    editMenu(id){
      //TODO:使用菜单编号id 请求Java服务器进行查询，获得要编辑的菜单对象，将数据回显到编辑菜单对话框中
      this.$axios.get("/menu/info/"+id)
      .then(response=>{
        this.MenuForm = response.data.resultdata
        this.dialogFormVisible = true
      }).catch(e=>{console.log(e)})
    },
    //删除菜单方法
    deleteMenu(id){
      //TODO:使用菜单编号id 请求Java服务器端,进行菜单数据的删除
      // console.log('编号为'+ id +'菜单删除')
      this.$axios.get("/menu/delete/"+id).then(response=>{
        this.loadMenuList();
      }).catch(e=>{console.log(e)})
    },
    //加载菜单数据
    loadMenuList(){
      this.loading=true
      this.$axios.get('/menu/list').then(response=>{
        this.tableData = response.data.resultdata
        this.loading=false
      }).catch(e=>{console.log(e)})
    },
    //提交 新建|编辑 菜单的表单
    submitMenuForm(FormName){
      this.$refs[FormName].validate((valid)=>{
        if(valid){
          this.$axios.post("/menu/"+(this.MenuForm.id != 0 ?"update":"save"),this.MenuForm)
          .then(response=>{
            this.loadMenuList();
            this.$message({
              showClose:true,
              message:response.data.resultdata,
              type:'success',
              onClose:()=>{
                //重置新建菜单对话框
                this.resetMenuForm(FormName);
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
    //重置 新建菜单表单
    resetMenuForm(FormName){
      this.MenuForm = {};
      this.$refs[FormName].resetFields();
    }
  },
  components: {
  },
}
</script>

<style scoped>
</style>
