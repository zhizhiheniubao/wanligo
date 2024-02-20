<template>
    <div class="wrapper">
      <!-- header部分 -->
      <header>
        <button type="text" style="background-color: #0097ff;color: #fff;font-size: 4.8vw;height: 10vw;margin-bottom: 3vw;" @click="back">返回</button>
        <p style="margin-left: 20vw;">更改信息</p>
      </header>
  
      <!-- 表单部分 -->
      <ul class="form-box">
        <li>
          <div class="title">用户名：</div>
          <div class="content">
            <input type="text" v-model="account.accountName" placeholder="请输入用户名" />
          </div>
        </li>
        <li>
          <div class="title">性别：</div>
            <input type="radio" value="1" name="sex" checked v-model="account.accountSex"><div style="font-size: small;">男</div>
            <input type="radio" value="0" name="sex" v-model="account.accountSex"><div style="font-size: small;">女</div>
        </li>
		    <li>
          <div class="title">原密码：</div>
          <div class="content">
            <input type="password" v-model="originalPassword" placeholder="请输入原密码" />
          </div>
        </li>
		    <li>
          <div class="title">新密码：</div>
          <div class="content">
            <input type="password" v-model="password" placeholder="请输入新密码" />
          </div>
        </li>
        <li>
          <div class="title">确认密码：</div>
          <div class="content">
            <input
              type="password"
              v-model="confirmPassword"
              placeholder="请确认密码"
            />
          </div>
        </li>
      </ul>
  
      <div class="button-login">
        <button @click="register" style="margin-top: 10px;">修改</button>
      </div>
  
      <!-- 底部菜单部分 -->
      <Footer></Footer>
    </div>
  </template>
  
  <script setup>
  import Footer from "../components/Footer.vue";
  import qs from "qs";
  import { useRouter, useRoute } from "vue-router";
  import { ref } from "vue";
  import { post } from "../api";
  import { getSessionStorage } from "../common.js";
  const account = getSessionStorage("account");
  const state = ref("");
  const router = useRouter();
  const route = useRoute();
  const originalPassword = ref("");
  const password = ref("");
  const confirmPassword = ref("");
  const register = () => {
	if (originalPassword.value == "") {
      alert("原密码不能为空！");
      return;
    }
    if (password.value == "") {
      alert("新密码不能为空！");
      return;
    }
    if (password.value != confirmPassword.value) {
      alert("两次输入的密码不一致！");
      return;
    }
  post(
    "/account/updatePassword",
    {
	  originalPassword:originalPassword.value,
	  password:password.value,
	  accountId:account.accountId,
    accountName:account.accountName,
    accountSex:account.accountSex
	},
  false
  )
    .then((response) => {
      //页面跳转至之前的页面： 
      sessionStorage.clear ()
      router.push({ path: "/login" })
    })
    .catch((e) => {
      console.log(e);
    });
    alert("密码修改成功,请重新登录")
  };
  const back = () =>{
    router.go(-1)
  }
  </script>
  
  <style scoped>
  /****************** 总容器 ******************/
  .wrapper {
    width: 100%;
    height: 100%;
  }
  
  /****************** header部分 ******************/
  .wrapper header {
    width: 100%;
    height: 12vw;
    background-color: #0097ff;
    color: #fff;
    font-size: 4.8vw;
  
    position: fixed;
    left: 0;
    top: 0;
    z-index: 1000;
  
    align-items: center;
  }
  
  /****************** 表单部分 ******************/
  .wrapper .form-box {
    width: 100%;
    margin-top: 12vw;
  }
  
  .wrapper .form-box li {
    box-sizing: border-box;
    padding: 4vw 3vw 0 3vw;
    display: flex;
    align-items: center;
  }
  
  .wrapper .form-box li .title {
    flex: 0 0 18vw;
    font-size: 3vw;
    font-weight: 700;
    color: #666;
  }
  
  .wrapper .form-box li .content {
    flex: 1;
  }
  
  .wrapper .form-box li .content input {
    border: none;
    outline: none;
    width: 100%;
    height: 4vw;
    font-size: 3vw;
  }
  
  .wrapper .button-login {
    width: 100%;
    box-sizing: border-box;
    
    padding: 4vw 3vw 0 3vw;
  }
  
  .wrapper .button-login button {
    width: 100%;
    height: 10vw;
    font-size: 3.8vw;
    font-weight: 700;
    color: #fff;
    background-color: #38ca73;
    border-radius: 4px;
  
    border: none;
    outline: none;
  }
  
  .wrapper .button-register {
    width: 100%;
    box-sizing: border-box;
    padding: 4vw 3vw 0 3vw;
  }
  
  .wrapper .button-register button {
    width: 100%;
    height: 10vw;
    font-size: 3.8vw;
    font-weight: 700;
    color: #666;
    background-color: #eee;
    border-radius: 4px;
  
    border: none;
    outline: none;
    border: solid 1px #ddd;
  }
  </style>