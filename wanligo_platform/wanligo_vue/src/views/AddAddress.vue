<template>
    <div class="wrapper">
      <!-- header部分 -->
      <header>
        <button type="text" style="background-color: #0097ff;color: #fff;font-size: 4.8vw;height: 10vw;margin-bottom: 3vw;" @click="back">返回</button>
        <p style="margin-left: 16vw;">新增送货地址</p>
      </header>
  
      <!-- 表单部分 -->
      <ul class="form-box">
        <li>
          <div class="title">联系人：</div>
          <div class="content">
            <input
              type="text"
              v-model="deliveryAddress.contactName"
              placeholder="联系人姓名"
            />
          </div>
        </li>
        <li>
          <div class="title">性别：</div>
          <div class="content" style="font-size: 3vw">
            <input
              type="radio"
              v-model="deliveryAddress.contactSex"
              value="1"
              style="width: 6vw; height: 3.2vw"
            />男
            <input
              type="radio"
              v-model="deliveryAddress.contactSex"
              value="0"
              style="width: 6vw; height: 3.2vw"
            />女
          </div>
        </li>
        <li>
          <div class="title">电话：</div>
          <div class="content">
            <input
              type="tel"
              v-model="deliveryAddress.contactTel"
              placeholder="电话"
            />
          </div>
        </li>
        <li>
          <div class="title">收货地址：</div>
          <div class="content">
            <input
              type="text"
              v-model="deliveryAddress.address"
              placeholder="收货地址"
            />
          </div>
        </li>
      </ul>
  
      <div class="button-add">
        <button @click="addAddress">保存</button>
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
  import {getSessionStorage} from "../common.js";
  import {get,post} from '../api'
  
  const router = useRouter();
  const route = useRoute();
  
  const account = getSessionStorage("account");
  const businessId = route.query.businessId;
  const deliveryAddress = ref({
    contactName: "",
    contactSex: 1,
    contactTel: "",
    address: "",
  });
  
  const addAddress = () => {
    if (deliveryAddress.value.contactName == "") {
      alert("联系人姓名不能为空！");
      return;
    }
    if (deliveryAddress.value.contactTel == "") {
      alert("联系人电话不能为空！");
      return;
    }
    if (deliveryAddress.value.address == "") {
      alert("联系人地址不能为空！");
      return;
    }
    deliveryAddress.value.accountId = account.accountId
    //调用服务器端代码，保存配送地址  true是JSON传递，false参数形式
    post('/deliveryaddress/save',deliveryAddress.value,true).then(response=>{
      if(response.data.code == 1){
        router.push({
          path: "/address",
          query: { businessId: businessId }
        });
      }else{
        alert('新增地址失败')
      }
      
    }).catch(e=>{ console.log(e); })
  };
  </script>
  
  <style scoped>
  /*************** 总容器 ***************/
  .wrapper {
    width: 100%;
    height: 100%;
  }
  
  /*************** header ***************/
  .wrapper header {
    width: 100%;
    height: 12vw;
    background-color: #0097ff;
    display: flex;
    justify-content: space-around;
    align-items: center;
    color: #fff;
    font-size: 4.8vw;
    position: fixed;
    left: 0;
    top: 0;
    /*保证在最上层*/
    z-index: 1000;
  }
  
  /*************** （表单信息） ***************/
  .wrapper .form-box {
    width: 100%;
    margin-top: 12vw;
  }
  
  .wrapper .form-box li {
    box-sizing: border-box;
    padding: 4vw 3vw 0vw 3vw;
    display: flex;
  }
  
  .wrapper .form-box li .title {
    flex: 0 0 18vw;
    font-size: 3vw;
    font-weight: 700;
    color: #666;
  }
  
  .wrapper .form-box li .content {
    flex: 1;
  
    display: flex;
    align-items: center;
  }
  
  .wrapper .form-box li .content input {
    border: none;
    outline: none;
    width: 100%;
    height: 4vw;
    font-size: 3vw;
  }
  
  .wrapper .button-add {
    box-sizing: border-box;
    padding: 4vw 3vw 0vw 3vw;
  }
  
  .wrapper .button-add button {
    width: 100%;
    height: 10vw;
    font-size: 3.8vw;
    font-weight: 700;
  
    border: none;
    outline: none;
    border-radius: 4px;
    color: #fff;
    background-color: #38ca73;
  }
  </style>