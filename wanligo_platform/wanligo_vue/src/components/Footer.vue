<template>
    <ul class="footer">
      <li @click="toIndex">
        <font-awesome-icon icon="fal fa-house" />
        <p>首页</p>
      </li>
      <li @click="toCartList">
        <i class="fa fa-cart-plus"></i>
        <p>购物车</p>
      </li>
      <li @click="toOrderList">
        <i class="fa fa-file-text-o"></i>
        <p>订单</p>
      </li>
      <li @click="toPersonal">
        <i class="fa fa-user-o"></i>
        <p>我的</p>
      </li>
    </ul>
  </template>
  <!-- 组合式API -->
  <script setup>
  import { useRouter } from "vue-router";
  const router = useRouter();
  /*
  data:{},
  methdos:{
    toIndex(){
        sjdksadjlksajdklsajldklka
    }
  }
  */

  const toIndex = () => {
    router.push({ path: "/index" });
  };
  const toOrderList = () => {
    router.push({ path: "/orderList" });
  };
  const toCartList = () => {
    router.push({ path: "/cartList" });
  };
  const toPersonal = () => {
    router.push({ path: "/personal" });
  };
  </script>
  
  <style scoped>
  .wrapper .footer {
    width: 100%;
    height: 14vw;
    border-top: solid 1px #ddd;
    background-color: #fff;
  
    position: fixed;
    left: 0;
    bottom: 0;
  
    display: flex;
    justify-content: space-around;
    align-items: center;
  }
  
  .wrapper .footer li {
    /*li本身的尺寸完全由内容撑起*/
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  
    color: #999;
    user-select: none;
    cursor: pointer;
  }
  
  .wrapper .footer li p {
    font-size: 2.8vw;
  }
  
  .wrapper .footer li i {
    font-size: 5vw;
  }
  </style>