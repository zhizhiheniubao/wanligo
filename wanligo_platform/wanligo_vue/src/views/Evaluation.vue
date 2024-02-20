<template>
    <div class="wrapper">
        <!-- header部分 -->
        <div class="header">
            <button type="text" style="background-color: #0097ff;color: #fff;font-size: 4.8vw;height: 10vw;margin-bottom: 3vw;" @click="back">返回</button>
            <p style="margin-left: 25vw;">订单</p>
        </div>
        <el-card class="box-card">
            <template #header>
                <div class="card-header">
                    <span style="font-weight: bold;font-size: 20px;">您对商家/菜品满意吗?</span>
                </div>
            </template>
            <div style="display: flex;margin-left: 5%;">
                <span>评分</span>
                <el-rate v-model="evaluation.value" :texts="['非常差', '差', '一般', '满意', '非常满意']" show-text />
            </div>
            <textarea type="text" style="width: 90%;height: 200px;font-size: 4.8vw;" v-model="evaluation.comment"
                placeholder="说说味道怎么样，给大家参考"></textarea>
                <div style="text-align: left;">
                <span style="font-weight: bold;font-size: 4.8vw;margin-left: 5%;">好吃你就晒晒图夸夸它</span>
                </div>
            <el-upload v-model:file-list="fileList" action="/sys/oss/upload" list-type="picture-card"
                style="display: flex;margin-left: 5%;" :on-preview="handlePictureCardPreview"
                :on-success="handleAvatarSuccess" :on-remove="handleRemove">
                <el-icon>
                    <Plus />
                </el-icon>
            </el-upload>
            <el-dialog v-model="dialogVisible">
                <img w-full :src="dialogImageUrl" alt="Preview Image" />
            </el-dialog>
            <template #footer><el-button
                    style="height: 70px;width: 80%;background-color:#0097ff ;color: #fff;font-size: 4.8vw;"
                    @click="test">提交</el-button></template>
        </el-card>
        <Footer></Footer>
    </div>
    <!-- 底部菜单部分 -->
</template>
<script setup>
import Footer from "../components/Footer.vue";
import { useRoute,useRouter } from "vue-router";
import { ref } from "vue";
import { get, post } from "../api";
import { getSessionStorage } from "../common.js";
import { Plus } from '@element-plus/icons-vue';
// import { UploadProps, UploadUserFile } from 'element-plus'
const router = useRouter();
const route = useRoute();
const evaluation = ref({
    value: "",
    comment: "",
    businessId: route.query.businessId,
    accountId: "",
    commentImg: ""
}, true);
const fileList = ref([
])

const dialogImageUrl = ref('')
const dialogVisible = ref(false)
const test = () => {
    let myString = "";
    for (let index = 0; index < fileList.value.length; index++) {
        myString = myString + fileList.value[index].url + ","
    }
    const newString = myString.substring(0, myString.length - 1);
    console.log(newString)
    evaluation.value.accountId = getSessionStorage('account').accountId
    post("/comment/addComment", {
        grade: evaluation.value.value,
        comment: evaluation.value.comment,
        businessId: evaluation.value.businessId,
        accountId: evaluation.value.accountId,
        commentImg: newString
    }, true).then((response) => {
        router.push({
            path: '/businessInfo',
            query: { businessId: evaluation.value.businessId },
        })
    }).catch((e) => { console.log(e); });
}
const handleRemove = (uploadFile, uploadFiles) => {
    console.log(uploadFile, uploadFiles)
}
const handleAvatarSuccess = (res, file) => {
    fileList.value.pop()
    fileList.value.push({
        name: file.name,
        url: res.data.imagePath
    })
}

const handlePictureCardPreview = (uploadFile) => {
    dialogImageUrl.value = uploadFile.url
    dialogVisible.value = true
}
const back = () =>{
    router.go(-1)
  }
</script>
<style scoped>
/****************** header部分 ******************/
.wrapper {
    width: 100%;
    height: 100%;
}

.wrapper .header {
    width: 100%;
    height: 12vw;
    background-color: #0097ff;
    color: #fff;
    font-size: 4.8vw;
    position: fixed;
    left: 0;
    top: 0;
    z-index: 1000;
    display: flex;
    /* justify-content: center; */
    align-items: center;
}

/****************** 卡片部分 ******************/
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.text {
    font-size: 14px;
}

.item {
    margin-bottom: 18px;
}

.box-card {
    width: 100vw;
    margin-top: 12vw;
}

.demo-rate-block {
    padding: 30px 0;
    text-align: center;
    border-right: solid 1px var(--el-border-color);
    display: inline-block;
    width: 49%;
    box-sizing: border-box;
}

.demo-rate-block:last-child {
    border-right: none;
}

.demo-rate-block .demonstration {
    display: block;
    color: var(--el-text-color-secondary);
    font-size: 14px;
    margin-bottom: 20px;
}
</style>