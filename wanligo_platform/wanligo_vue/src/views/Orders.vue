<template>
    <div class="wrapper">
      <!-- header部分 -->
      <header>
        <button type="text" style="background-color: #0097ff;color: #fff;font-size: 4.8vw;height: 10vw;margin-bottom: 3vw;" @click="back">返回</button>
        <p style="margin-left: 20vw;">确认订单</p>
      </header>
  
      <!-- 订单信息部分 -->
      <div class="order-info">
        <h5>订单配送至：</h5>
        <div class="order-info-address" @click="toUserAddress">
          <p>
            {{
              deliveryaddress != null ? deliveryaddress.address : "请选择送货地址"
            }}
          </p>
          <i class="fa fa-angle-right"></i>
        </div>
        <p>
          {{ deliveryaddress != null ? deliveryaddress.contactName :'收件人' }}
          {{ deliveryaddress != null ? (deliveryaddress.contactSex == 1 ? "先生" : "女士") : '先生' }}
          {{ deliveryaddress != null ? deliveryaddress.contactTel:'收件人电话' }}
        </p>
      </div>
  
      <h3>{{ business.businessName }}</h3>
  
      <!-- 订单明细部分 -->
      <ul class="order-detailed">
        <li v-for="item in cartArr" :key="item.cartId">
          <div class="order-detailed-left">
            <img :src="item.food.foodImg" />
            <p>{{ item.food.foodName }} x {{ item.quantity }}</p>
          </div>
          <p>&#165;{{ item.food.foodPrice * item.quantity }}</p>
        </li>
      </ul>
      <div class="order-deliveryfee">
        <p>配送费</p>
        <p>&#165;{{ business.deliveryPrice }}</p>
      </div>
  
      <!-- 合计部分 -->
      <div class="total">
        <div class="total-left">&#165;{{ totalPrice }}</div>
        <div class="total-right" @click="toPayment">去支付</div>
      </div>
    </div>
  </template>
  
  <script setup>
  import qs from "qs";
  import { useRouter, useRoute } from "vue-router";
  import { ref, computed, inject } from "vue";
  import { getSessionStorage, getLocalStorage } from "../common.js";
  import {get,post} from '../api'
  
  const router = useRouter();
  const route = useRoute();
  
  const account = getSessionStorage("account");
  const businessId = route.query.businessId;  //路由地址获得businessId
  const business = ref({});
  const cartArr = ref([]);
  const deliveryaddress = ref({});
  
  //订单的总价
  const totalPrice = computed(() => {
    let totalPrice =0;
    for(let cartItem of cartArr.value){
      totalPrice += cartItem.food.foodPrice * cartItem.quantity;
    }
    totalPrice += business.value.deliveryPrice

    return totalPrice;
  });
  
  const init = () => {
    //查询当前商家
    let url = '/business/info/'+businessId
    get(url).then(response=>{
      console.log(response.data.data.business);
      business.value = response.data.data.business
    }).catch(e=>{ console.log(e); })

    //查询当前用户在购物车中的当前商家食品列表
    get('/cart/listCart/'+account.accountId+"/"+businessId).then(response=>{
      cartArr.value = response.data.data.cartList
    }).catch(e=>{ console.log(e); })

    //读取默认的配送地址:
    deliveryaddress.value = getLocalStorage(account.accountId)
  };
  init();
  
  const toUserAddress = () => {
    router.push({
      path: "/address",
      query: { businessId: businessId },
    });
  };


  
  const toPayment = () => {
    if (deliveryaddress == null) {
      alert("请选择送货地址！");
      return;
    }
  
    //创建订单
    post('/orders/save',
    {
      accountId:account.accountId,
      businessId:businessId,
      daId:deliveryaddress.value.daId,
      orderTotal: totalPrice.value
    },true).then(response=>{
       if(response.data.code==1){
          //接受服务器端创建成功订单orderId
          let orderId = response.data.data.orderId
          router.push({
            path:'/payment',
            query:{ orderId: orderId }
          });
       }else{
        alert('订单创建失败')
       }
    }).catch(e=>{ console.log(e); })
  };
  const back = () => {
    router.go(-1)
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
  
    /* display: flex; */
    /* justify-content: center; */
    align-items: center;
  }
  
  /****************** 订单信息部分 ******************/
  .wrapper .order-info {
    /*注意这里，不设置高，靠内容撑开。因为地址有可能折行*/
    width: 100%;
    margin-top: 12vw;
    background-color: #0097ef;
    box-sizing: border-box;
    padding: 2vw;
    color: #fff;
    text-align: left;
  }
  
  .wrapper .order-info h5 {
    font-size: 3vw;
    font-weight: 300;
  }
  
  .wrapper .order-info .order-info-address {
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
  
    font-weight: 700;
    user-select: none;
    cursor: pointer;
    margin: 1vw 0;
  }
  
  .wrapper .order-info .order-info-address p {
    width: 90%;
    font-size: 5vw;
  }
  
  .wrapper .order-info .order-info-address i {
    font-size: 6vw;
  }
  
  .wrapper .order-info p {
    font-size: 3vw;
  }
  
  .wrapper h3 {
    box-sizing: border-box;
    padding: 3vw;
    font-size: 4vw;
    color: #666;
    border-bottom: solid 1px #ddd;
  }
  
  /****************** 订单明细部分 ******************/
  .wrapper .order-detailed {
    width: 100%;
  }
  
  .wrapper .order-detailed li {
    width: 100%;
    height: 16vw;
    box-sizing: border-box;
    padding: 3vw;
    color: #666;
  
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .wrapper .order-detailed li .order-detailed-left {
    display: flex;
    align-items: center;
  }
  
  .wrapper .order-detailed li .order-detailed-left img {
    width: 10vw;
    height: 10vw;
  }
  
  .wrapper .order-detailed li .order-detailed-left p {
    font-size: 3.5vw;
    margin-left: 3vw;
  }
  
  .wrapper .order-detailed li p {
    font-size: 3.5vw;
  }
  
  .wrapper .order-deliveryfee {
    width: 100%;
    height: 16vw;
    box-sizing: border-box;
    padding: 3vw;
    color: #666;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 3.5vw;
  }
  
  /****************** 订单合计部分 ******************/
  .wrapper .total {
    width: 100%;
    height: 14vw;
  
    position: fixed;
    left: 0;
    bottom: 0;
  
    display: flex;
  }
  
  .wrapper .total .total-left {
    flex: 2;
    background-color: #505051;
    color: #fff;
    font-size: 4.5vw;
    font-weight: 700;
    user-select: none;
  
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .wrapper .total .total-right {
    flex: 1;
    background-color: #38ca73;
    color: #fff;
    font-size: 4.5vw;
    font-weight: 700;
    user-select: none;
    cursor: pointer;
  
    display: flex;
    justify-content: center;
    align-items: center;
  }
  </style>