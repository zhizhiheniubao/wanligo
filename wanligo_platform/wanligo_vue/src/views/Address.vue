<template>
    <div class="wrapper">
      <!-- header部分 -->
      <header>
        <button type="text" style="background-color: #0097ff;color: #fff;font-size: 4.8vw;height: 10vw;margin-bottom: 3vw;" @click="back">返回</button>
        <p style="margin-left: 20vw;">地址管理</p>
      </header>
  
      <!-- 地址列表部分 -->
      <ul class="addresslist">
        <li v-for="item in deliveryAddressArr" :key="item.daId">
          <div class="addresslist-left" @click="setDeliveryAddress(item)">
            <h3>
              {{ item.contactName }}{{ item.contactSex == 1 ? "先生" : "女士" }}
              {{ item.contactTel }}
            </h3>
            <p>{{ item.address }}</p>
          </div>
          <div class="addresslist-right">
            <i class="fa fa-edit" @click="editAccountAddress(item.daId)"></i>
            <i class="fa fa-remove" @click="removeAccountAddress(item.daId)"></i>
          </div>
        </li>
      </ul>
  
      <!-- 新增地址部分 -->
      <div class="addbtn" @click="toAddAccountAddress">
        <i class="fa fa-plus-circle"></i>
        <p>新增收货地址</p>
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
  import { getSessionStorage, getLocalStorage, setLocalStorage, removeLocalStorage } from "../common.js";
  import { get,post } from "../api";

  const router = useRouter();
  const route = useRoute();
  
  const account = getSessionStorage("account");
  const businessId = route.query.businessId;
  const deliveryAddressArr = ref([]);
  
  //加载配送地址信息
  const listDeliveryAddressByAccountId = () => {
    //通过accountId 查询当前用户的送货地址
    let url = '/deliveryaddress/listAddressByAccountId/'+account.accountId
    get(url).then(response=>{
     
      deliveryAddressArr.value = response.data.data.addressList
    }).catch(e=>{ console.log(e); })
  };
  listDeliveryAddressByAccountId();
  
  const setDeliveryAddress = (deliveryAddress) => {
    //把用户选择的默认送货地址存储到localStorage中
    setLocalStorage(account.accountId,deliveryAddress)

    router.push({
      path: "/orders",
      query: { businessId: businessId }
    });
  };

  //添加到添加配送地址页面
  const toAddAccountAddress = () => {
    router.push({
      path: "/addAddress",
      query: { businessId: businessId }
    });
  };

  //点击跳转到编辑配送地址页面
  const editAccountAddress = (daId) => {
    router.push({
      path: "/editAddress",
      query: { businessId: businessId, daId: daId }
    });
  };
  const removeAccountAddress = (daId) => {
    if (!confirm("确认要删除此送货地址吗？")) {
      return;
    }
    //调用服务器端，删除配送地址  false参数形式传递
    post('/deliveryaddress/delete',{daId:daId},false).then(response=>{
      if(response.data.code == 1){
        let defaultAddress = getLocalStorage(account.accountId);
        //删除的地址 是不是正好就是设置的默认的配送地址
        
        if(defaultAddress!=null && defaultAddress.daId == daId){ 
          removeLocalStorage(account.accountId)
        }
        listDeliveryAddressByAccountId();
      }else{
        alert('删除地址失败')
      }
    }).catch(e=>{ console.log(e); })
  };
  const back = () =>{
    router.go(-1)
  }
  </script>
  
  <style scoped>
  /*************** 总容器 ***************/
  .wrapper {
    width: 100%;
    height: 100%;
    background-color: #f5f5f5;
  }
  
  /*************** header ***************/
  .wrapper header {
    width: 100%;
    height: 12vw;
    background-color: #0097ff;
    /* display: flex;
    justify-content: space-around; */
    align-items: center;
    color: #fff;
    font-size: 4.8vw;
    position: fixed;
    left: 0;
    top: 0;
    /*保证在最上层*/
    z-index: 1000;
  }
  
  /*************** addresslist ***************/
  .wrapper .addresslist {
    width: 100%;
    margin-top: 12vw;
    background-color: #fff;
    text-align: left;
  }
  
  .wrapper .addresslist li {
    width: 100%;
    box-sizing: border-box;
    border-bottom: solid 1px #ddd;
    padding: 3vw;
  
    display: flex;
  }
  
  .wrapper .addresslist li .addresslist-left {
    flex: 5;
    /*左边这块区域是可以点击的*/
    user-select: none;
    cursor: pointer;
  }
  
  .wrapper .addresslist li .addresslist-left h3 {
    font-size: 4.6vw;
    font-weight: 300;
    color: #666;
  }
  
  .wrapper .addresslist li .addresslist-left p {
    font-size: 4vw;
    color: #666;
  }
  
  .wrapper .addresslist li .addresslist-right {
    flex: 1;
    font-size: 5.6vw;
    color: #999;
    cursor: pointer;
  
    display: flex;
    justify-content: space-around;
    align-items: center;
  }
  
  /*************** 新增地址部分 ***************/
  .wrapper .addbtn {
    width: 100%;
    height: 14vw;
    border-top: solid 1px #ddd;
    border-bottom: solid 1px #ddd;
    background-color: #fff;
    margin-top: 4vw;
  
    display: flex;
    justify-content: center;
    align-items: center;
  
    font-size: 4.5vw;
    color: #0097ff;
    user-select: none;
    cursor: pointer;
  }
  
  .wrapper .addbtn p {
    margin-left: 2vw;
  }
  </style>