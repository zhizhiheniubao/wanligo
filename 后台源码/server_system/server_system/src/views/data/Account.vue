<template>
  <div>
    <!-- 客户管理 工具栏 -->
    <el-form ref="searchForm" :model="searchForm" :inline="true">
      <el-form-item>
        <el-input v-model="searchForm.accountName" placeholder="请输入要搜索的客户名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="loadAccountList()">搜索</el-button>&nbsp;&nbsp;
        <el-popconfirm title="确定要删除这些食物信息吗？" @confirm="deleteAccounts()">
          <el-button type="danger" slot="reference" :disabled="deleteAllBtnStu">批量删除</el-button>
        </el-popconfirm>
      </el-form-item>
    </el-form>

    <el-dialog title="客户收货地址" :visible.sync="dialogTableVisible">
      <el-table :data="addressData" style="width:854px;margin-bottom:20px;" border :cell-style="{ padding: '2px' }" @selection-change="selectionChange">
        <el-table-column prop="contactName" label="姓名" width="120"></el-table-column>
        <el-table-column prop="contactSex" label="性别" width="120">
          <template slot-scope="scope">
            <span v-if="scope.row.contactSex === 1">男</span>
            <span v-else-if="scope.row.contactSex === 0">女</span>
          </template>
        </el-table-column>
        <el-table-column prop="contactTel" label="电话" width="200"></el-table-column>
        <el-table-column prop="address" label="地址" width="413"></el-table-column>
      </el-table>
    </el-dialog>


    <!-- 客户信息对话框 -->
    <el-dialog
      title="客户信息"
      :visible.sync="dialogFormVisible"
      width="550px"
      @close="resetAccountForm('AccountForm')"
    >
      <el-form :model="AccountForm" ref="AccountForm" label-width="100px" :rules="AccountRules">
        <el-form-item label="客户编号" label-width="100px" prop="accountId">
          <el-input v-model="AccountForm.accountId"></el-input>
        </el-form-item>
        <el-form-item label="客户头像" label-width="100px" prop="accountImg">
          <el-upload class="avatar-uploader" action="/sys/oss/upload" :show-file-list="false" :on-success="handleAvatarSuccess" :headers="headers">
            <img
              v-if="AccountForm.accountImg"
              :src="AccountForm.accountImg"
              class="avatar"
              style="width: 100%; height: 100%; object-fit: cover"
            />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="客户姓名" label-width="100px" prop="accountName">
          <el-input v-model="AccountForm.accountName"></el-input>
        </el-form-item>
        <el-form-item label="客户性别" label-width="100px" prop="accountSex">
          <el-radio-group v-model="AccountForm.accountSex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="禁用状态" label-width="100px" prop="statu">
          <el-radio-group v-model="AccountForm.statu">
            <el-radio :label="0">禁用</el-radio>
            <el-radio :label="1">正常</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitAccountForm('AccountForm')">保存客户</el-button>
          <el-button @click="resetAccountForm('AccountForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 客户分页的表格数据显示 -->
    <el-table
      :data="tableData"
      style="width: 100%"
      margin-bottom="20px"
      border
      :cell-style="{ padding: '2px' }"
      @selection-change="selectionChange"
    >
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column label="客户编号" prop="accountId" width="200"></el-table-column>
      <el-table-column label="客户头像" width="120">
        <template slot-scope="scope">
          <img
            v-if="scope.row.accountImg"
            :src="decodeBase64(scope.row.accountImg)"
            style="max-width: 100%; max-height: 100px"
          />
        </template>
      </el-table-column>
      <el-table-column label="客户名称" prop="accountName" width="80"></el-table-column>
      <el-table-column label="性别" prop="accountSex" width="80">
        <template slot-scope="scope">
          <el-tag size="mini" v-if="scope.row.statu === 0" type="danger">女</el-tag>
          <el-tag size="mini" v-else-if="scope.row.statu === 1" type="success">男</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="禁用状态" prop="statu" width="280">
        <template slot-scope="scope">
          <el-tag size="mini" v-if="scope.row.statu === 0" type="danger">禁用</el-tag>
          <el-tag size="mini" v-else-if="scope.row.statu === 1" type="success">正常</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="删除状态" prop="delTag" width="280">
        <template slot-scope="scope">
          <el-tag size="mini" v-if="scope.row.delTag === 1" type="success">正常</el-tag>
          <el-tag size="mini" v-else-if="scope.row.delTag === 0" type="danger">已删除</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <!-- <el-button type="text" size="small" @click="loadAddressList(scope.row.accountId);dialogTableVisible=true">收货地址</el-button> -->
          <el-button type="text" size="small" @click="checkAddress(scope.row.accountName)">收货地址</el-button>
          <el-button type="text" size="small" @click="editAccount(scope.row.accountId)">编辑</el-button>
          &nbsp;&nbsp;
          <el-popconfirm title="确定要删除这个账号信息吗？" @confirm="deleteAccount(scope.row)">
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
import router from '@/router'

export default {
  data() {
    return {
      row: {},
      addressData:{},
      deleteAllBtnStu: true,
      dialogFormVisible: false,
      searchForm: {
        accountName: '', //通过客户姓名来进行搜索
      },
      tableData: [
        {
          accountImg: '',
        },
      ],
      AccountRules: {
        accountId: [{ required: true, message: '请输入客户的编号', trigger: 'blur' }],
        accountName: [{ required: true, message: '请输入客户的名字', trigger: 'blur' }],
        accountSex: [{ required: true, message: '请选择客户的性别(1:男 0:女)', trigger: 'blur' }],
        statu: [{ required: true, message: '请选择禁用状态', trigger: 'blur' }],
      },
      AccountForm: {
        accountId: '', //用户编号
        accountImg: '', //用户头像
        accountName: '', //用户姓名
        accountSex: '', //用户性别
        statu: 1, //禁用状态
        delTag: 1, //删除状态
      },
      current: 1, //页码
      size: 5,
      total: 0,
      selection: [], //表格复选框选中的食物对象
      headers: {
        token: JSON.parse(localStorage.getItem('TOKEN')),
      },
      dialogTableVisible:false
    }
  },
  created() {
    this.loadAccountList()
  },
  methods: {
    loadAddressList(accountId) {
      this.$axios.get('/deliveryaddress/listByAccountId/'+accountId).then(response => {
        this.addressData = response.data.resultdata
      }).catch(e => {
        console.log(e);
      })
    },
    checkAddress(accountName){
      this.$router.push({
        path:"/data/deliveryaddress",
        query:{name:accountName}
      })
    },
    resetAccountForm(formName) {
      this.AccountForm = {}
      this.$refs[formName].resetFields()
    },
    decodeBase64(base64String) {
      return base64String
    },
    loadAccountList() {
      this.$axios
        .get('/account/page', {
          params: {
            name: this.searchForm.accountName, //搜索关键词
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
    sizeChangeHander(val) {
      this.size = val
      this.loadAccountList()
    },
    currentChangeHandler(val) {
      this.current = val
      this.loadAccountList()
    },
    deleteAccount(account) {
      this.$axios
        .delete(`/account/${account.accountId}`)
        .then((response) => {
          if (response.data.code === 200) {
            this.$message.success(response.data.message)
            // this.$message.success('删除成功')
            this.loadAccountList()
          }
        })
        .catch((error) => {
          console.log(error)
          this.$message.error('删除失败')
        })
      this.loadAccountList()
    },
    editAccount(accountId) {
      if (!accountId) return
      this.$axios
        .get('/account/info/' + accountId)
        .then((response) => {
          this.AccountForm = response.data.resultdata
          this.dialogFormVisible = true
        })
        .catch((e) => {
          console.log(e)
        })
    },

    deleteAccounts() {
      this.deleteAllBtnStu = false
      if (this.selection.length > 0) {
        this.selection.forEach((account) => {
          this.deleteAccount(account)
        })
      }
      this.loadAccountList()
    },
    handleAvatarSuccess(response, file) {
      this.AccountForm.accountImg = response.resultdata
    },
    submitAccountForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios
            .post('/account/update', this.AccountForm)
            .then((response) => {
              this.$message({
                showClose: true,
                message: response.data.resultdata,
                type: 'success',
                onClose: () => {
                  this.loadAccountList()
                  this.resetAccountForm(formName)
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
