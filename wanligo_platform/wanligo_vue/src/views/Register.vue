<template>
    <div class="wrapper">
      <!-- header部分 -->
      <header>
        <p>用户注册</p>
      </header>
  
      <!-- 表单部分 -->
      <ul class="form-box">
        <li>
          <div class="title">手机号码：</div>
          <div class="content">
            <input type="text" @blur="checkUserId" v-model="account.accountId" placeholder="手机号码"
            />
          </div>
        </li>
        <li>
          <div class="title">密码：</div>
          <div class="content">
            <input type="password" v-model="account.password" placeholder="密码" />
          </div>
        </li>
        <li>
          <div class="title">确认密码：</div>
          <div class="content">
            <input
              type="password"
              v-model="confirmPassword"
              placeholder="确认密码"
            />
          </div>
        </li>
        <li>
          <div class="title">用户名称：</div>
          <div class="content">
            <input type="text" v-model="account.accountName" placeholder="用户名称" />
          </div>
        </li>
        <li>
          <div class="title">性别：</div>
          <div class="content" style="font-size: 3vw">
            <input type="radio" v-model="account.accountSex" value="1" style="width: 6vw; height: 3.2vw"
            />男
            <input type="radio" v-model="account.accountSex" value="0" style="width: 6vw; height: 3.2vw"
            />女
          </div>
        </li>
      </ul>
  
      <div class="button-login">
        <button @click="register">注册</button>
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
  import { get,post } from "../api";
  const state = ref("");
  const router = useRouter();
  const route = useRoute();
  const account = ref({
    accountId: "",
    password: "",
    accountName: "",
    accountSex: 1,
  });
  const confirmPassword = ref("");
  
  const checkUserId = () => {
    //请求服务器端 验证手机号码是否重复  /account/getAccountById
    //重复: 清空输入文本框，焦点重新回到 手机号输入文本框
    get("/account/getAccountById/"+account.value.accountId)
    .then((response) => {
      let state = response.data.data.state
      if(state == 0){
        account.value.accountId=""
        let accountIdInput = document.getElementById("accountIdInput");
        accountIdInput.focus();
        alert("手机号码重复")
      }
    })
    .catch((e) => {
      console.log(e);
    }
  );
  };
  const register = () => {
    if (account.value.accountId == "") {
      alert("手机号码不能为空！");
      return;
    }
    if (account.value.password == "") {
      alert("密码不能为空！");
      return;
    }
    if (account.value.password != confirmPassword.value) {
      alert("两次输入的密码不一致！");
      return;
    }
    if (account.value.accountName == "") {
      alert("用户名不能为空！");
      return;
    }
  
    //注册请求 /account/register
  post(
    "/account/register",
    {
      accountId: account.value.accountId,
      password: account.value.password,
      accountName: account.value.accountName,
      accountSex: account.value.accountSex,
    },
    true
  )
    .then((response) => {
      alert("注册成功")
      router.push({ path: "/login" });
    })
    .catch((e) => {
      console.log(e);
    }
  );
  };
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
  
    display: flex;
    justify-content: center;
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