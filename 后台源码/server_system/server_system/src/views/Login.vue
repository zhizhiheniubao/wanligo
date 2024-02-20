<template>
    <el-row>
        <el-col :span="6">
            <h1>欢迎使用万里够后台管理系统</h1>
            <el-image style="width: 100px; height: 100px" :src="require('@/assets/logo.jpg')"></el-image>
            <p>万里学院大数据与软件工程学院提供技术支持</p>
            <P>基于Vue+SpringBoot开发</P>
        </el-col>     
        <el-col :span="1">
            <el-divider direction="vertical"></el-divider>
        </el-col>
        <el-col :span="6">
            <!--登录表单 ref属性（定义引用名字）-->
            <el-form ref="loginForm" :model="loginObject" label-width="80px" :rules="rules" label-position="left">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="loginObject.username" style="width: 360px;"></el-input>
                </el-form-item>
                <el-form-item label="密    码" prop="password">
                    <el-input type="password" v-model="loginObject.password" style="width: 360px;" show-password></el-input>
                </el-form-item>
                <el-form-item label="验证码" prop="code">
                    <el-input v-model="loginObject.code" style="width: 150px; float: left;" maxlength="5"></el-input>
                    <el-image :src="captchaImg" class="captchaImg"></el-image>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitLoginForm('loginForm')" >提交</el-button>
                    <el-button type="info" @click="forgetLoginPwd">忘记密码</el-button>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<script>
export default {
  data() {
    return {
        //验证码图片字符串：图片---》Base64转码（字符串）
        captchaImg:'',
        //登录对象
        loginObject:{
            username:'',
            password:'',
            code:'',    //验证码
            key:'', //服务端随机码进行存储
        },
        //登录表单的验证规则
        rules:{
            //登录表单验证规则
            username:[
                //非空验证
                {required:true,message:'请输入登录的用户名',trigger:'blur'}
            ],
            password:[
                //非空验证
                {required:true,message:'请输入登录的密码',trigger:'blur'}
            ],
            code:[
                //非空验证
                {required:true,message:'请输入验证码',trigger:'blur'},
                {min:5,max:5,message:'验证码长度为5个字符',trigger:'blur'}
            ],
        },
    }
  },
  methods:{
    submitLoginForm(fromName){
        //上面表达ref="loginForm" 在JS代码中可以使用表单定义引用名 获得表单对象。fromName参数中存储的就是loginForm引用名
        this.$refs[fromName].validate((valid)=>{
            //valid就是验证结果，如果true就是全部通过，false就是没有通过
            if(valid){
                //请求Java服务器进行登陆操作：使用axios异步请求
                //Post请求  Post（路径、参数）
                //参数可以直接书写 this.loginObject,如果这样传参，java服务器端就要接收一个JSON对象
                //假设Java服务器端登录接收一个正常对象，不是一个JSON对象。需要将loginObject转换为参数形式
                //使用this.$qs.stringify()将JSON对象转换为参数形式
                this.getCaptcha()
                this.$axios.post('/login',this.$qs.stringify(this.loginObject))
                .then((response)=>{
                    //判断response.code == 20000,还有catch()一场输出。统一代码中做 响应码判断还有异常处理
                    //登录成功，接收token（token会放到响应头传输回来） 
                    this.$message({
                        message:"登录成功",
                        type:'success'
                    })
                    const jwt = response.headers['token']  
                    // const jwt = "dsahdoashjdioasd.jdasodjoaisdjas.dasjdiaisdjasidjasd"
                    this.$store.commit('SET_TOKEN',jwt)
                    this.$router.push('/index')
                }).catch(error=>{
                    //刷新页面验证码
                    console.log(error)
                })             
            }else{
                return false
            }
        })
    },
    getCaptcha(){
        this.$axios.get('/api/captcha')
        .then((response)=>{
            this.captchaImg=response.data.resultdata.captchaImg
            this.loginObject.key= response.data.resultdata.key
        }).catch((error)=>{
            console.log(error)
        })
    },
    forgetLoginPwd(){
        
    },
  },
  created(){    //生命周期钩子函数，Vue实例创建完毕阶段（请求Java服务器验证码）
    this.getCaptcha();
  },
  components: {
  }
}
</script>

<style scoped>
    .el-row{
        min-width: 1400px;
        background-color: rgb(220, 236, 231);
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        text-align: center;
    }
    .el-divider{
        height: 400px;
        width: 3px;
    }
    .captchaImg{
        float: left;
        margin-left: 10px;
        border-radius: 5px;
        /* border: 1px solid #e2dbdb; */
    }
</style>
