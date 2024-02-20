<template>
  <div>
    <!-- 食物管理 工具栏 -->
    <el-form ref="searchForm" :model="searchForm" :inline="true">
      <el-form-item>
        <el-input v-model="searchForm.foodname" placeholder="请输入要搜索的食物名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchForm.businessId" placeholder="请输入商家编号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="loadFoodList()">搜索</el-button>
        <el-button @click="dialogFormVisible = true" type="primary">新建</el-button>&nbsp;
        <el-popconfirm title="确定要删除这些食物信息吗？" @confirm="deleteFood()">
          <el-button type="danger" slot="reference" :disabled="deleteAllBtnStu">批量删除</el-button>
        </el-popconfirm>
      </el-form-item>
    </el-form>
    <!-- 食物信息对话框 -->
    <el-dialog
      title="食物信息"
      :visible.sync="dialogFormVisible"
      width="550px"
      @close="resetFoodForm('FoodForm')"
    >
      <el-form :model="FoodForm" ref="FoodForm" label-width="100px" :rules="FoodRules">
        <el-form-item label="食物名称" label-width="100px" prop="foodName">
          <el-input v-model="FoodForm.foodName"></el-input>
        </el-form-item>
        <el-form-item label="食物描述" label-width="100px" prop="foodExplain">
          <el-input v-model="FoodForm.foodExplain"></el-input>
        </el-form-item>
        <el-form-item label="食物图片" label-width="100px" prop="foodImg">
            <el-upload class="avatar-uploader" action="/sys/oss/upload" :show-file-list="false" :on-success="handleAvatarSuccess" :headers="headers">
            <img v-if="FoodForm.foodImg" :src="FoodForm.foodImg" class="avatar" style="width: 100%; height: 100%; object-fit: cover" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="食物价格" label-width="100px" prop="foodPrice">
          <el-input v-model="FoodForm.foodPrice"></el-input>
        </el-form-item>
        <el-form-item label="商家编号" label-width="100px" prop="businessId">
          <el-input v-model="FoodForm.businessId"></el-input>
        </el-form-item>
        <!-- <el-form-item label="备注" label-width="100px" prop="remarks">
          <el-input v-model="FoodForm.remarks"></el-input>
        </el-form-item> -->
        <el-form-item>
          <el-button type="primary" @click="submitFoodForm('FoodForm')">保存食物</el-button>
          <el-button @click="resetFoodForm('FoodForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 食物分页的表格数据显示 -->
    <el-table
      :data="tableData"
      style="width: 100%"
      margin-bottom="20px"
      border
      :cell-style="{ padding: '2px' }"
      @selection-change="selectionChange"
    >
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column label="食物名称" prop="foodName" width="200"></el-table-column>
      <el-table-column label="食物描述" prop="foodExplain" width="300"></el-table-column>

      <el-table-column label="食物图片" width="120">
        <template slot-scope="scope">
          <img
            v-if="scope.row.foodImg"
            :src="decodeBase64(scope.row.foodImg)"
            style="max-width: 100%; max-height: 100px"
          />
        </template>
      </el-table-column>
      <el-table-column label="食物价格" prop="foodPrice" width="80"></el-table-column>
      <el-table-column label="商家编号" prop="businessId" width="80"></el-table-column>
      <!-- <el-table-column label="备注" prop="remarks" width="280"></el-table-column> -->
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="editFood(scope.row.foodId)">编辑</el-button
          >&nbsp;&nbsp;
          <el-popconfirm title="确定要该食物信息吗？" @confirm="deleteFood(scope.row.foodId)">
            <el-button type="text" size="small" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页的页码 -->
    <el-pagination
      background
      layout="prev, pager, next,total,jumper,sizes"
      :total="total"
      :page-size="size"
      :page-sizes="[5, 10, 15, 20, 25]"
      :current-page="current"
      @size-change="sizeChangeHander"
      @current-change="currentChangeHandler"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      deleteAllBtnStu: true, //true不可用，false可用
      tableData: [
        {
          foodImg: '',
        },
      ],

      // 新建/编辑食物的对话框是否显示
      dialogFormVisible: false,
      // 工具栏搜索框的表单对象
      searchForm: {
        foodname: '', //可以按照角色的名字进行搜索
      },
      FoodRules: {
        foodName: [{ required: true, message: '请输入食物的名称', trigger: 'blur' }],
        foodExplain: [{ required: true, message: '请输入食物描述', trigger: 'blur' }],
        foodImg:[{ required: true, message: '请存入图片', trigger: 'blur' }],
        foodPrice: [{ required: true, message: '请输入食物价格', trigger: 'blur' }],
        businessId: [{ required: true, message: '请输入商家编号', trigger: 'blur' }],
        // remarks: [{ required: true, message: '请输入备注', trigger: 'blur' }],
      },
      FoodForm: {
        foodName: '',
        foodExplain: '',
        foodImg: '',
        // food_id: 0,
        // remarks: '',
        base64String: '',
        foodId: 0,
      },
      headers: {
        token: JSON.parse(localStorage.getItem('TOKEN')),
      },
      current: 1, //页码
      size: 5,
      total: 0,
      selection: [], //表格复选框选中的食物对象
    }
  },
  created() {
    this.loadFoodList()
  },
  methods: {
    decodeBase64(base64String) {
      return base64String
    },
    // 点击页面
    currentChangeHandler(val) {
      this.current = val
      this.loadFoodList()
    },
    // 修改一页显示的记录数
    sizeChangeHander(val) {
      this.size = val
      this.loadFoodList()
    },
    handleAvatarSuccess(response, file) {
      this.FoodForm.foodImg = response.resultdata
    },
    deleteFood(foodId) {
      var foodIds = [] // 存储要删除的所有的食物 id，单个删除数组存储就是一个id
      if (foodId) {
        foodIds.push(foodId)
      } else {
        this.selection.forEach((food) => {
          foodIds.push(food.foodId)
        })
      }

      this.$axios
        .post('/food/delete/', foodIds)
        .then((response) => {
          this.$message({
            showClose: true,
            message: response.data.resultdata,
            type: 'success',
            onClose: () => {
              this.loadFoodList()
            },
          })
        })
        .catch((e) => {
          console.log(e)
        })
    },

    editFood(foodId) {
      console.log(foodId)
      this.$axios
        .get('/food/info/' + foodId)
        .then((response) => {
          this.FoodForm = response.data.resultdata
          this.dialogFormVisible = true
        })
        .catch((e) => {
          console.log(e)
        })
    },
    // 请求java服务器端获得所有的食物信息
    loadFoodList() {
      this.$axios
        .get('/food/page', {
          params: {
            name: this.searchForm.foodname, //搜索关键词
            businessId: this.searchForm.businessId,
            current: this.current, //页码
            size: this.size, //一页显示多少条
          },
        })
        .then((response) => {
          this.tableData = response.data.resultdata.records
          this.total = response.data.resultdata.total
          this.size = response.data.resultdata.size
          this.current = response.data.resultdata.current
        })
        .catch((e) => {
          console.log(e)
        })
    },
    selectionChange(value) {
      // value参数，就是选中的复选框对应的这一行的食物对象
      this.selection = value
      if (this.selection) {
        this.deleteAllBtnStu = this.selection.length === 0
      }
    },

    submitFoodForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios
            .post('/food/' + (this.FoodForm.foodId ? 'update' : 'save'), this.FoodForm)
            .then((response) => {
              this.$message({
                showClose: true,
                message: response.data.resultdata,
                type: 'success',
                onClose: () => {
                  this.loadFoodList() //重新加载食物信息
                  this.resetFoodForm(formName) //重置食物添加表单
                },
              })
              this.dialogFormVisible = false
            })
            .catch((e) => {
              console.log(e)
            })
        } else {
          return false
        }
      })
    },

    resetFoodForm(forName) {
      this.FoodForm = {}
      this.$refs[forName].resetFields()
    },
    selectionChange(value) {
      // value参数，就是选中的复选框对应的这一行的食物对象
      this.selection = value
      if (this.selection) {
        this.deleteAllBtnStu = this.selection.length === 0
      }
    },
  },
  components: {},
}
</script>

<style scoped>
.avatar-uploader {
  border: 1px solid #aeaeae;
  border: 1px solid black;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 150px;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #aeaeae;
  width: 150px;
  height: 150px;
  line-height: 150px;
  text-align: center;
}
</style>
