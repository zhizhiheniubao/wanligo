<template>
<div>
    <!-- 搜索栏 -->
    <el-form ref="searchForm" :model="searchForm" :inline="true">
        <el-form-item>
            <el-input v-model="searchForm.name" placeholder="请输入要搜索的用户名称"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button @click="loadAddressList()">搜索</el-button>
            <el-button type="primary" @click="dialogFormVisible = true">新建</el-button>&ensp;
            <el-popconfirm title="确认要批量删除这些地址信息吗?" @confirm="deleteAddressInformation()">
                <!-- disabled属性表示该标签是否为 不可用 -->
                <el-button slot="reference" type="danger" :disabled="deleteAllBtnStu">批量删除</el-button>
            </el-popconfirm>
        </el-form-item>
    </el-form>

    <!-- 新建地址 -->
    <el-dialog title="地址信息" :visible.sync="dialogFormVisible" width="550px" @closed="resetAddressForm('AddressForm')">
        <el-form :model="AddressForm" ref="AddressForm" label-width="100px" :rules="AddressRules">
            <!-- 下拉列表显示父菜单 -->
            <el-form-item label="所属用户" prop="accountId">
                <el-select v-model="AddressForm.accountId" placeholder="请选择所属用户">
                    <template>
                        <el-option v-for="item in accountList" :label="item.accountName" :key="item.accountId" :value="item.accountId" />
                    </template> 
                </el-select>
            </el-form-item>
            <el-form-item label="名称" label-width="100px" prop="contactTel">
                <el-input v-model="AddressForm.contactName"></el-input>
            </el-form-item>
            <el-form-item label="性别" label-width="100px" prop="contactSex">
                <el-radio-group v-model="AddressForm.contactSex">
                    <el-radio :label="1">男</el-radio>
                    <el-radio :label="0">女</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="电话" label-width="100px" prop="contactTel">
                <el-input v-model="AddressForm.contactTel"></el-input>
            </el-form-item>
            <el-form-item label="地址" label-width="100px" prop="address">
                <el-input v-model="AddressForm.address"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitAddressForm('AddressForm')">保存地址</el-button>
                <el-button @click="resetAddressForm('AddressForm')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>

    <!-- 地址数据显示 -->
    <el-card class="box-card">
        <el-table :data="tableData" style="width:100%;margin-bottom:20px;" border :cell-style="{ padding: '2px' }" @selection-change="selectionChange">
            <el-table-column type="selection" width="50"></el-table-column>
            <el-table-column prop="accountId" label="所属用户" width="150">
                <template slot-scope="scope">
                    <div v-for="item in accountList">
                        <el-tag v-if="item.accountId==scope.row.accountId">{{ item.accountName }}</el-tag>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="contactName" label="姓名" width="100"></el-table-column>
            <el-table-column prop="contactSex" label="性别" width="100">
                <template slot-scope="scope">
                    <span v-if="scope.row.contactSex === 1">男</span>
                    <span v-else-if="scope.row.contactSex === 0">女</span>
                </template>
            </el-table-column>
            <el-table-column prop="contactTel" label="电话" width="200"></el-table-column>
            <el-table-column prop="address" label="地址" width="413"></el-table-column>

            <el-table-column prop="" label="操作" width="400">
                <template slot-scope="scope">
                    <el-button type="info" @click="editAddressInformation(scope.row)">编辑</el-button>
                    <el-divider direction="vertical"></el-divider>
                    <el-popconfirm title="确定要删除该用户数据吗" @confirm="deleteAddressInformation(scope.row.daId)">
                        <el-button type="danger" slot="reference">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
    </el-card>

    <div class="block">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="current" :page-sizes="[1, 5, 10, 20]" :page-size="size" layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>
    </div>

</div>
</template>

<script>
export default {
    data() {
        return {
            AddressRules: {
                name: [{
                    required: true,
                    message: '请输入姓名',
                    trigger: 'blur'
                }],
                sex: [{
                    required: true,
                    message: '请选择性别',
                    trigger: 'blur'
                }],
                telephone: [{
                    required: true,
                    message: '请输入电话',
                    trigger: 'blur'
                }],
                address: [{
                    required: true,
                    message: '请输入地址',
                    trigger: 'blur'
                }]
            },
            AddressForm: {
                accountId:"",
                name: "",
                sex: 1,
                telephone: "",
                address: "",
                accountName: ""
            },
            searchForm: {
                name:  this.$route.query.name,
            },
            tableData: [],
            accountList: [],
            current: 1,
            size: 10,
            total: 0,
            selection: [],
            deleteAllBtnStu: true,
            dialogFormVisible: false
        }
    },
    created() {
        this.loadAddressList()
    },
    methods: {
        handleSizeChange(val) {
            this.size = val
            this.loadAddressList()
        },
        handleCurrentChange(val) {
            this.current = val
            this.loadAddressList()
        },
        //保存新建表单
        submitAddressForm(FormName) {
            //新建菜单： Java服务器控制器接口 /menu/save
            //编辑菜单： Java服务器控制器接口 /menu/update
            this.$refs[FormName].validate((valid) => {
                if (valid) {
                    const url = this.AddressForm.daId != undefined ? "update" : "save"
                    console.log(this.AddressForm, url)
                    this.$axios.post("/deliveryaddress/" + url, this.AddressForm).then(res => {
                        // let that = this
                        //显示菜单录入成功的消息框
                        this.$message({
                            showClose: true,
                            message: res.data.message,
                            type: 'success',
                        });
                        //添加新的菜单数据，重新加载表格菜单数据
                        this.loadAddressList();
                        //重置 新建菜单对话框
                        this.resetAddressForm(FormName);
                        //将对话框重新隐藏
                        this.dialogFormVisible = false;
                    }).catch(e => {
                        console.log(e);
                    })
                } else {
                    return false
                }
            })
        },
        //重置新建表单
        resetAddressForm(formName) {
            this.AddressForm = {};
            this.$refs[formName].resetFields();
        },
        //删除地址信息
        deleteAddressInformation(daId) {
            //批量删除没有传递id参数，单个删除id是有参数（要删除角色id）
            var daIds = []; //存储要删除的所有的角色id。单个删除数组存储就是一个id。
            daId ? daIds.push(daId) : this.selection.forEach(add => {
                daIds.push(add.daId)
            });
            this.$axios.post('/deliveryaddress/delete', daIds).then(response => {
                this.$message({
                    showClose: true,
                    message: response.data.resultdata,
                    type: 'success',
                });
                this.loadAddressList(); 
            }).catch(e => {
                console.log(e);
            })
        },
        //编辑地址信息
        editAddressInformation(item) {
            let id = item.daId
            let accountId = item.accountId
            console.log(this.AddressForm.accountName)
            this.$axios.get("/deliveryaddress/info/" + id).then(res => {
                this.AddressForm = res.data.resultdata
                for (let index = 0; index < this.accountList.length; index++) {
                    if (this.accountList[index].accountId == accountId) {
                        this.$set(this.AddressForm, "accountName", this.accountList[index].accountName)
                    }
                }
                this.dialogFormVisible = true
            }).catch(e => {
                console.log(e);
            })

        },
        //加载数据
        loadAddressList() {
            this.$axios.get('/deliveryaddress/page', {
                params: {
                    name: this.searchForm.name,
                    current: this.current,
                    size: this.size
                }
            }).then(response => {
                this.tableData = response.data.resultdata.DeliveryAddress.records
                this.accountList = response.data.resultdata.AccountList
                this.total = response.data.resultdata.DeliveryAddress.total
                this.size = response.data.resultdata.DeliveryAddress.size
                this.current = response.data.resultdata.DeliveryAddress.current
            }).catch(e => {
                console.log(e);
            })
        },
        //选中表格中 复选框触发的事件
        selectionChange(value) {
            this.selection = value;
            this.deleteAllBtnStu = (value.length === 0)
        },
    },
}
</script>

<style scoped></style>
