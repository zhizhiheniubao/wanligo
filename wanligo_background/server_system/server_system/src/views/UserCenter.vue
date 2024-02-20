<template>
  <div style="text-align: center;">
    <h2>您好，{{userInfo.username}}用户</h2>
    <el-form ref="passForm" :model="passForm" label-width="100px" :rules="rules">
      <!-- <el-form-item label="客户头像" label-width="100px" prop="avatar">
        <el-upload class="avatar-uploader" action="/sys/oss/upload" :show-file-list="false" :on-success="handleAvatarSuccess" :headers="headers">
          <img
              v-if="userInfo.avatar"
              :src="userInfo.avatar"
              class="avatar"
              style="width: 100%; height: 100%; object-fit: cover"
          />
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item> -->
      <el-form-item label="旧密码" prop="currentPass">
        <el-input v-model="passForm.currentPass" type="password"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="password">
        <el-input v-model="passForm.password" type="password"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPass">
        <el-input v-model="passForm.checkPass" type="password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitPassForm('passForm')" >提交</el-button>
        <el-button @click="resetForm('passForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    //自定义验证规则，验证新密码必须和确认密码一样
    var validatePass = (rule,value,callback) => {
      //value就是确认密码
      if(value == ''){
        callback(new Error('请再次输入确认密码')) //callback（）验证失败，回调验证失败
      }else if(value !== this.passForm.password){
        callback(new Error('两次输入不一致'))
      }else{
        callback();
      }
    };
    
    return {
      userInfo:{},
      passForm:{
        // avatar:'',
        password:'',
        checkPass:'',
        currentPass:''
      },
      headers: {
        token: JSON.parse(localStorage.getItem('TOKEN')),
      },
      rules:{
        password:[
          {required:true,message:'请输入新密码',trigger:'blur'},
          {min:6,max:12,message:'密码长度在6到12位',trigger:'blur'}
        ],
        checkPass:[
          //validator指定使用自定义验证规则
          {required:true,validator:validatePass,trigger:'blur'},
        ],
        currentPass:[
          {required:true,message:'请输入当前密码',trigger:'blur'},        ]
      }     
    }
  },
  methods:{
    //提交修改密码的表单
    submitPassForm(fromName){
      this.$refs[fromName].validate((valid)=>{
      if(valid){
        this.$axios.get('/user/updatePassword',{
          para: {
            id:this.userInfo.id,
            password:this.passForm.password,
            currentPass:this.passForm.currentPass,
          },
        }).then(response=>{
            this.getUserInfo()
            this.$message({
              showClose:true,
              message:response.data.resultdata,
              type:'success',
              onClose:()=>{
                this.resetForm(passForm);
              }  
            });            
          }).catch(e=>{console.log(e);})
      }else{
          return false
      }
    })
    },
    //重置修改密码表单
    resetForm(formName){
      this.$refs[formName].resetFields();
    },
    handleAvatarSuccess(response, file) {
      this.userInfo.avatar = response.resultdata
    },
    getUserInfo(){
      this.$axios.get('/api/userinfo').then(response=>{
        this.userInfo = response.data.resultdata
        this.userId = response.data.resultdata
      })
    }
  },
  created(){
    this.getUserInfo()
  },
  components: {
  }
}
</script>

<style scoped>
.el-form{
width:420px;
margin:50px auto;
}
</style>
