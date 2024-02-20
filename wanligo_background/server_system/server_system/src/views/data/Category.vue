<template>
<div>
    <!-- 搜索栏 -->
    <el-form ref="searchForm" :model="searchForm" :inline="true">
        <el-form-item>
            <el-input v-model="searchForm.categoryName" placeholder="请输入类别名称"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button @click="loadCategoryList()">搜索</el-button>
            <el-button type="primary" @click="dialogFormVisible = true">新建</el-button>&ensp;
            <el-popconfirm title="确认要批量删除这些类别信息吗?" @confirm="deleteCategoryInformation()">
                <!-- disabled属性表示该标签是否为不可用 -->
                <el-button slot="reference" type="danger" :disabled="deleteAllBtnStu">批量删除</el-button>
            </el-popconfirm>
        </el-form-item>
    </el-form>
    <!-- 新建地址 -->
    <el-dialog title="类别信息" :visible.sync="dialogFormVisible" width="550px" @closed="resetCategoryForm('CategoryForm')">
        <el-form :model="CategoryForm" ref="CategoryForm" label-width="100px" :rules="CategoryRules">
            <el-form-item label="餐品类别" label-width="100px" prop="contactName">
                <el-input v-model="CategoryForm.categoryName"></el-input>
            </el-form-item>
            <el-form-item label="类别图标">
                <el-upload class="avatar-uploader" action="/sys/oss/upload" :headers="headers" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                    <img v-if="CategoryForm.categoryCover" :src="CategoryForm.categoryCover" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitCategoryForm('CategoryForm')">保存类别</el-button>
                <el-button @click="resetCategoryForm('CategoryForm')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
    <!-- 类别数据显示 -->
    <el-table :data="tableData" style="width:40%;margin-bottom:20px;" border :cell-style="{ padding: '2px' }" @selection-change="selectionChange">
        <el-table-column type="selection" width="50"></el-table-column>
        <el-table-column prop="categoryName" label="类别名称" width="120"></el-table-column>
        <el-table-column prop="categoryCover" label="类别图标" width="150">
            <template slot-scope="scope">
                <el-image :src="scope.row.categoryCover" style="width: 48px; height: 38px"></el-image>
            </template>
        </el-table-column>
        <el-table-column prop="type" label="删除状态" width="120">
            <template slot-scope="scope">
            <el-tag size="mini" v-if="scope.row.delTag===0" type="danger">已删除</el-tag>
            <el-tag size="mini" v-else-if="scope.row.delTag===1" type="success">正常</el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="" label="操作">
            <template slot-scope="scope">
                <el-button type="text" @click="editCategoryInformation(scope.row.categoryId)">编辑</el-button>
                <el-divider direction="vertical"></el-divider>
                <el-popconfirm title="确定要删除该类别数据吗" @confirm="deleteCategoryInformation(scope.row.categoryId)">
                    <el-button type="text" slot="reference">删除</el-button>
                </el-popconfirm>
            </template>
        </el-table-column>
    </el-table>
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
            CategoryRules: {
                categoryName: [{
                    required: true,
                    message: '请输入姓名',
                    trigger: 'blur'
                }],
            },
            CategoryForm: {
                categoryName: '',
                categoryCover: ''
            },
            headers: {
                token: JSON.parse(localStorage.getItem('TOKEN')),
            },
            searchForm: {
                categoryName: "",
            },
            dialogFormVisible: false,
            tableData: [],
            selection: [],
            deleteAllBtnStu: true,
            current: 1,
            size: 10,
            total: 0,
        }
    },
    created() {
        this.loadCategoryList()
    },
    computed: {

    },
    mounted() {

    },
    methods: {
        handleSizeChange(val) {
            this.size = val
            this.loadCategoryList()
        },
        handleCurrentChange(val) {
            this.current = val
            this.loadCategoryList()
        },
        handleAvatarSuccess(res, file) {
            // console.log(res)
            this.CategoryForm.categoryCover = res.resultdata;
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },
        editCategoryInformation(id) {
            this.dialogFormVisible = true
            this.$axios.get("/category/info/" + id).then(res => {
                this.CategoryForm = res.data.resultdata
            }).catch(e => {
                console.log(e);
            })
        },
        //保存新建表单
        submitCategoryForm(FormName) {
            //新建菜单： Java服务器控制器接口 /menu/save
            //编辑菜单： Java服务器控制器接口 /menu/update
            this.$refs[FormName].validate((valid) => {
                if (valid) {
                    const url = this.CategoryForm.categoryId != undefined ? "update" : "save"
                    console.log(this.CategoryForm, url)
                    this.$axios.post("/category/" + url, this.CategoryForm).then(res => {
                        let that = this
                        //显示菜单录入成功的消息框
                        this.$message({
                            showClose: true,
                            message: res.data.message,
                            type: 'success',
                        });
                        //添加新的菜单数据，重新加载表格菜单数据
                        that.loadCategoryList();
                        //重置 新建菜单对话框
                        that.resetCategoryForm(FormName);
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
        resetCategoryForm(formName) {
            this.CategoryForm = {};
            this.$refs[formName].resetFields();
        },
        //加载数据
        loadCategoryList() {
            this.$axios.get('/category/page', {
                params: {
                    name: this.searchForm.categoryName,
                    current: this.current,
                    size: this.size
                }
            }).then(response => {
                this.tableData = response.data.resultdata.records
                this.total = response.data.resultdata.total
                this.size = response.data.resultdata.size
                this.current = response.data.resultdata.current
            }).catch(e => {
                console.log(e);
            })
        },
        //选中表格中 复选框触发的事件
        selectionChange(value) {
            this.selection = value;
            this.deleteAllBtnStu = (value.length === 0)
        },
        //删除类别
        deleteCategoryInformation(id) {
            //批量删除没有传递id参数，单个删除id是有参数（要删除类别id）
            var categoryIds = []; //存储要删除的所有的类别id。单个删除数组存储就是一个id。
            id ? categoryIds.push(id) : this.selection.forEach(category => {
                categoryIds.push(category.categoryId)
            });
            this.$axios.post('/category/delete', categoryIds).then(response => {
                this.$message({
                    showClose: true,
                    message: response.data.resultdata,
                    type: 'success',
                });
                this.loadCategoryList() //重新加载类别的信息
            }).catch(e => {
                console.log(e);
            })
        }
    }
}
</script>

<style scoped>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.avatar-uploader .el-upload:hover {
    border-color: #409EFF;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}

.avatar {
    width: 178px;
    height: 178px;
    display: block;
}
</style>
