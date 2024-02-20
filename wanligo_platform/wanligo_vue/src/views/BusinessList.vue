<template>
    <div class="wrapper">
      <!-- header部分 -->
      <header>
        <p>商家列表</p>
      </header> 
      <!-- 商家列表部分 -->
      <ul class="business">
        <li v-for="item in businessArr" :key="item.businessId" @click="toBusinessInfo(item.businessId)" >
          <div class="business-img">
            <img :src="item.businessImg" />
            <!-- 当前用户购买该商家的餐品数量的角标 -->
            <div class="business-img-quantity" v-show="item.quantity > 0">
              {{ item.quantity }}
            </div>
          </div>
          <div class="business-info">
            <h3>{{ item.businessName }}</h3>
            <p>
              &#165;{{ item.starPrice }}起送 | &#165;{{ item.deliveryPrice }}配送
            </p>
            <p>{{ item.businessExplain }}</p>
          </div>
        </li>
      </ul>
  
      <!-- 底部菜单部分 -->
      <Footer></Footer>
    </div>
  </template>
  
  <script setup>
  import Footer from "../components/Footer.vue";
  import qs from "qs";
  import { useRouter, useRoute } from "vue-router";
  import { ref, inject } from "vue";
  import {get} from '../api'
  import { getSessionStorage } from "../common";

  
  const router = useRouter();  //跳转路径.
  const route = useRoute();   //获得路由传递参数。
  //获得路由提交参数：获得餐品类别的id
  const categoryId = route.query.categoryId;
  //const categoryId = 1  //假设是 路由获得首页跳转过来参数值
  const businessArr = ref([]);
  const account = getSessionStorage('account');
  //请求当前登录的用户---购物车数据。
  const init =()=>{
    let url = "/business/listByCategoryId/"+categoryId
    get(url).then(response=>{
      //console.log(response.data.data.businessList);
      businessArr.value = response.data.data.businessList
      //判断是否登录，如果登录，就加载该用户的购物车里购买商家餐品的数量
      if(account!=null){
        listCart();  
      }
    }).catch(e=>{ console.log(e); })
  }
  init();


  //根据登录用户的accountId加载该用户购买的餐品的数量角标
  const listCart = ()=>{
    let url = "/cart/list/"+account.accountId
    get(url).then(response=>{
      //console.log(response.data);
      let cartArr = response.data.data.cartList
      for( let businessItem of businessArr.value ){
        //定义一个商家临时变量quantity
        businessItem.quantity =0;
        for( let cartItem of cartArr){
          if(cartItem.businessId === businessItem.businessId){
            businessItem.quantity += cartItem.quantity
          }
        }
      }
    }).catch(e=>{console.log(e);})
  }


  
  
  
  //跳转至 商家详情页面，需要传递businessId作为路由参数
  const toBusinessInfo = (businessId) => {
    router.push({
      path: "/businessInfo",
      query: { businessId: businessId },
    });
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
  
  /****************** 商家列表部分 ******************/
  .wrapper .business {
    width: 100%;
    margin-top: 12vw;
    margin-bottom: 14vw;
  }
  
  .wrapper .business li {
    width: 100%;
    box-sizing: border-box;
    padding: 2.5vw;
    border-bottom: solid 1px #ddd;
    user-select: none;
    cursor: pointer;
    text-align: left;
    display: flex;
    align-items: left;
  }
  
  .wrapper .business li .business-img {
    /*这里设置为相当定位，成为business-img-quantity元素的父元素*/
    position: relative;
  }
  
  .wrapper .business li .business-img img {
    width: 20vw;
    height: 20vw;
  }
  
  .wrapper .business li .business-img .business-img-quantity {
    width: 5vw;
    height: 5vw;
    background-color: red;
    color: #fff;
    font-size: 3.6vw;
    border-radius: 2.5vw;
  
    display: flex;
    justify-content: center;
    align-items: center;
  
    /*设置成绝对定位，不占文档流空间*/
    position: absolute;
    right: -1.5vw;
    top: -1.5vw;
  }
  
  .wrapper .business li .business-info {
    margin-left: 3vw;
  }
  
  .wrapper .business li .business-info h3 {
    font-size: 3.8vw;
    color: #555;
  }
  
  .wrapper .business li .business-info p {
    font-size: 3vw;
    color: #888;
    margin-top: 2vw;
  }
  </style>