<template>
  <div>
    <!--商家管理工具栏-->
    <el-form ref="searchForm" :model="searchForm" :inline="true">
      <el-form-item>
        <el-input v-model="searchForm.data" placeholder="请输入要搜索的商家/餐品类别/地址" style="padding-left: 10px; width: 300px;"></el-input>
      </el-form-item>
      <el-form-item prop="delTag">
        <el-select v-model="searchForm.categoryId" placeholder="请选择餐品类别" style="padding-left: 10px;" clearable>
          <el-option v-for="item in categoryData" :key="item.categoryId" :label="item.categoryName" :value="item.categoryId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="delTag">
        <el-select v-model="searchForm.delTag" placeholder="删除状态" style="padding-left: 10px;" clearable>
          <el-option v-for="item in options" :key="item.statu" :label="item.label" :value="item.statu">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item> 
        <el-button @click="loadBusinessList">搜索</el-button>
        <el-button type="info" @click="resetSearchForm('searchForm')">清空</el-button>
        <el-button type="primary" @click="dialogFormVisible=true">新建</el-button>
        <el-popconfirm title="确定要批量删除这些用户信息吗" style="padding-left: 10px;" @confirm="deleteBusiness()">
          <!-- disabled属性表示该标签是否为 不可用 -->  
          <el-button type="danger" slot="reference" :disabled="deleteAllBtnStu">批量删除</el-button>
        </el-popconfirm>
      </el-form-item>
    </el-form>

    <!--新建或编辑商家对话框-->
    <el-dialog title="商家信息" :visible.sync="dialogFormVisible" width="600px" @close="resetBusinessForm('BusinessForm')">
      <el-form :model="BusinessForm" ref="BusinessForm" label-width="100px" :rules="BusinessRules">
        <el-form-item label="商家头像" label-width="100px">
          <!--将本地图片 上次阿里文件服务器上-->
          <el-upload class="avatar-uploader" action="/sys/oss/upload" :show-file-list="false" :on-success="handleAvatarSuccess" :headers="headers">
            <img v-if="BusinessForm.businessImg" :src="BusinessForm.businessImg" class="avatar" style="width: 100%;height: 100%;object-fit:cover;"> 
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>     
        </el-form-item>
        <el-form-item label="商家名称" label-width="100px" prop="businessName">
          <el-input v-model="BusinessForm.businessName"></el-input>
        </el-form-item>
        <el-form-item label="商家地址" label-width="100px" prop="businessAddress">
          <el-input v-model="BusinessForm.businessAddress"></el-input>
        </el-form-item>
        <el-form-item label="商家类型" label-width="100px" prop="businessExplain">
          <el-input v-model="BusinessForm.businessExplain"></el-input>
        </el-form-item>
        <el-form-item label="餐品类别" prop="categoryId" >
          <el-select v-model="BusinessForm.categoryId" placeholder="餐品类别" >
            <el-option v-for="item in categoryData" :key="item.categoryId" :label="item.categoryName" :value="item.categoryId"/>
          </el-select>
        </el-form-item>
        <el-form-item label="起送价格" label-width="100px" prop="starPrice">
          <el-input v-model="BusinessForm.starPrice"></el-input>
        </el-form-item>
        <el-form-item label="配送费" label-width="100px" prop="deliveryPrice">
          <el-input v-model="BusinessForm.deliveryPrice"></el-input>
        </el-form-item>
        
        <!-- <el-form-item label="删除状态" label-width="100px" prop="idDelete">
          <el-radio-group v-model="BusinessForm.delTag">
            <el-radio :label="0">删除</el-radio>
            <el-radio :label="1">正常</el-radio>
          </el-radio-group>
        </el-form-item> -->

        <el-form-item>
          <el-button type="primary" @click="submitBusinessForm('BusinessForm')">保存商家</el-button>
          <el-button @click="resetBusinessForm('BusinessForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!--商家相关餐品数据显示-->
    <el-drawer title="商家相关餐品" :visible.sync="foodTable" direction="rtl" size="40%">
      <el-table v-loading="foodData.loading" :data="foodData.foodList" border  style="margin: 0 20px; width: 85%;">
        <el-table-column label="餐品头像" width="100" prop="foodImg">
          <template slot-scope="scope">
            <el-avatar size="small" :src="scope.row.foodImg"></el-avatar>
          </template>
        </el-table-column>
        <el-table-column prop="foodName" label="餐品" width="150"></el-table-column>
        <el-table-column prop="foodExplain" label="餐品介绍" width="200"></el-table-column>
        <el-table-column prop="foodPrice" label="餐品价格"></el-table-column>
      </el-table>
    </el-drawer>

    <!--商家表格数据显示-->
    <el-table  v-loading="loading" :data="tableData" style="width: 100%;margin-bottom: 20px;" border :cell-style="{padding:'2px'}" @selection-change="selectionChange">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column label="商家头像" width="80" prop="businessImg">
        <template slot-scope="scope">
          <el-avatar size="small" :src="scope.row.businessImg"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column prop="businessName" label="商家名称" width="240px">
        <template slot-scope="scope">
          <el-button @click="businessFoodList(scope.row.businessId)" type="text" style="color:#696565 ;">{{ scope.row.businessName }}</el-button> 
        </template></el-table-column>
      <el-table-column prop="businessAddress" label="商家地址" width="320px"></el-table-column>
      <el-table-column prop="businessExplain" label="商家类型" width="180px"></el-table-column>
      <el-table-column prop="categoryId" label="餐品类别" width="120">
        <template slot-scope="scope">
          <template v-for="item in categoryData" >
            <template v-if="scope.row.categoryId == item.categoryId">{{ item.categoryName }}</template>
          </template>
        </template>
      </el-table-column>
      <el-table-column prop="starPrice" label="起送价格" width="120"></el-table-column>
      <el-table-column prop="deliveryPrice" label="配送费" width="120"></el-table-column>
      <el-table-column prop="type" label="删除状态" width="120">
        <template slot-scope="scope">
          <el-tag size="mini" v-if="scope.row.delTag===0" type="danger">已删除</el-tag>
          <el-tag size="mini" v-else-if="scope.row.delTag===1" type="success">正常</el-tag>
        </template>
      </el-table-column>
      <!-- <el-table-column prop="remarks" label="" width="150"></el-table-column> -->
      <el-table-column label="操作" width="280px" fixed="right">
        <template slot-scope="scope">
          <el-button size="small" type="primary"  @click="editBusiness(scope.row.businessId)" plain>编辑</el-button>
          <el-popconfirm title="确定要删除该商家数据吗" @confirm="deleteBusiness(scope.row.businessId)" style="padding-left: 10px;">
            <el-button v-if="scope.row.delTag === 0" size="small" slot="reference" type="danger" plain disabled>删除</el-button>
            <el-button v-else-if="scope.row.delTag===1" size="small" slot="reference" type="danger" plain>删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页的代码 -->
    <el-pagination background layout="prev,pager,next,sizes,total,jumper" :total="total" :page-size="size" :page-sizes="[5,10,15,20,25]"
    :current-page="current" @size-change="sizeChangeHandler" @current-change="currentChangeHandler">       
    </el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData:[],
      categoryData:{}, //餐品类别数据
      foodData:{
        foodList:[],
        loading:false,
      },
      loading:false,
      //编辑菜单表单 验证
      BusinessRules:{
        businessImg:[{required: true, message:'请添加店铺头像', trigger: 'blur'}], 
        businessName:[{required: true, message:'请输入店铺名称', trigger: 'blur'}], 
        businessAddress:[{required: true, message:'请输入店铺地址', trigger: 'blur'}],
        businessExplain:[{required: true, message:'请填写店铺类型', trigger: 'blur'}],
        categoryId:[{required: true, message:'请选择餐品类别', trigger: 'blur'}],
        // starPrice:[{required: true, message:'请输入起送价格', trigger: 'blur'}], 
        // deliveryPrice:[{required: true, message:'请输入配送费', trigger: 'blur'}],
        // remarks:[{required: true, message:'', trigger: 'blur'}],
      },
      BusinessForm:{
        businessId:'',
        businessImg:'',
        businessName:'',  
        businessAddress:'',
        businessExplain:'',
        categoryId:1, 
        starPrice:'',
        deliveryPrice:'',
        // delTag:1
        // remarks:''
      },
      deleteAllBtnStu:true, //默认 批量删除按钮  是否被禁用
      dialogFormVisible:false,  //新建或编辑用户对话框是否显示
      foodTable:false,
      searchForm:{
        data:'',  
        categoryId:'',
        delTag:'',
      },
      current:1,//当前的页码
      size: 5,//一页显示记录数
      total:0,  //总共的记录数
      selection:[], //复选框选中的角色对象
      headers:{
        token:JSON.parse(localStorage.getItem('TOKEN'))
      },
      //删除状态
      options:[
        {
          statu:0,
          label:'已删除',
        },
        {
          statu:1,
          label:'正常',
        },
      ],
    }
  },
  created(){
    this.loadBusinessList()
    this.$axios.get('/category/list').then(response=>{
        this.categoryData = response.data.resultdata
    }).catch(e=>{ console.log(e)})
  },
  methods:{
    currentChangeHandler(val){
      this.current = val;
      this.loadBusinessList()
    },
    sizeChangeHandler(val){
      this.size = val;
      this.loadBusinessList()
    },
    editBusiness(id){
      this.$axios.get('/business/info/'+id).then(response=>{
        this.BusinessForm = response.data.resultdata
        this.dialogFormVisible = true   
      }).catch(e=>{console.log(e)})
    },
    deleteBusiness(businessId){
      var businessIds = []; 
      businessId?businessIds.push(businessId):this.selection.forEach(business => {
        console.log(business.businessId);
        businessIds.push(business.businessId)
      })
      this.$axios.post("/business/delete",businessIds).then(response=>{
        this.$message({
          message:response.data.resultdata,
          type:'success',
        });         
        //将对话框重新隐藏
        this.dialogFormVisible=false;
        this.loadBusinessList();
      }).catch(e=>{console.log(e);})
    },
    //加载商家表格数据
    loadBusinessList(){
      this.loading=true,
      this.$axios.get('/business/page',{
        params:{
          data:this.searchForm.data,  //搜索关键词
          categoryId:this.searchForm.categoryId,
          delTag:this.searchForm.delTag,
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
    //相关商家餐品数据
    businessFoodList(businessId){
      this.foodData.loading=true,
      this.$axios.get('/food/listByBusinessId/'+businessId).then(response=>{
        this.foodData.foodList = response.data.resultdata
        this.foodData.loading=false
      }).catch(e=>{console.log(error)})
      this.foodTable = true
      
    },
    
    //上传头像成功之后回调
    handleAvatarSuccess(response,file){
      //上传成功，从响应对象response中可以获得上传图片在服务器的地址
      this.BusinessForm.businessImg = response.resultdata
    },
    //新建或编辑用户表单提交
    submitBusinessForm(BusinessForm){
      this.$refs[BusinessForm].validate((valid)=>{
        if(valid){
          this.$axios.post('/business/'+(this.BusinessForm.businessId === ''|| this.BusinessForm.businessId === null || typeof this.BusinessForm.businessId === "undefined"?"save":"update"),this.BusinessForm).then(response=>{
            this.loadBusinessList();
            this.$message({
              showClose:true, 
              message:response.data.resultdata,
              type:'success',
              onClose:()=>{
                this.resetBusinessForm(BusinessForm);
              }  
            });            
            //将对话框重新隐藏
            this.dialogFormVisible=false;
          })
        }else{
          return false
        }
      })
    },
    //重置新建或编辑用户表单
    resetBusinessForm(BusinessForm){
      this.BusinessForm = {};
      this.$refs[BusinessForm].resetFields();
      
    },
    //重置搜索框
    resetSearchForm(searchForm){
      this.searchForm = {}
      this.$refs[searchForm].resetFields();
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
