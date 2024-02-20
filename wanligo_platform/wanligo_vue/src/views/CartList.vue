<template>
    <div class="wrapper">
      <!-- header部分 -->
      <header>
        <p>购物车</p>
      </header> 
      <div style="margin-top: 10vw; margin-bottom: 30vw;">
        <template v-for="item,index in cartList" :key="index">      
          <!-- 商家信息部分 -->
          <div class="business-info">
            <input type="radio" value="business.value" name="1" @click="checkBusiness(item)" >
            <!-- <div class="business-logo">
              <img :src="item.business.businessImg" />
            </div> -->
            <h1>{{ item.business.businessName }}</h1>
            <!-- <p>
              &#165;{{ item.business.starPrice }}起送 &#165;{{item.business.deliveryPrice}}配送
            </p> -->
            <p>{{ item.business.businessExplain }}</p>
          </div>
      
          <!-- 食品列表部分 -->
          <ul class="food">
            <li v-for="(child, k) in item.foodList" :key="child.foodId">
              <div class="food-left">
                <img :src="child.foodImg" />
                <div class="food-left-info">
                  <h3>{{ child.foodName }}</h3>
                  <p>{{ child.foodExplain }}</p>
                  <p>&#165;{{ child.foodPrice }}</p>
                </div>
              </div>
              <div class="food-right">
                <div>
                  <i class="fa fa-minus-circle" @click="minus(child)" v-show="child.quantity != 0"></i>
                </div>
                <p>
                  <span v-show="child.quantity != 0">{{ child.quantity }}</span>
                </p>
                <div>
                  <i class="fa fa-plus-circle" @click="add(child)"></i>
                </div>
              </div>
            </li>
          </ul>
        </template>
      </div>
     
      <div class="cart">
        <div class="cart-left">
          <div class="cart-left-icon" :style="totalQuantity == 0? 'background-color:#505051;': 'background-color:#3190E8;'">
            <i class="fa fa-shopping-cart"></i>
            <div class="cart-left-icon-quantity" v-show="totalQuantity != 0">
              {{ totalQuantity }}
            </div>
          </div>
          <div class="cart-left-info">
            <p>&#165;{{ totalPrice }}</p>
            <p>另需配送费{{ business.deliveryPrice }}元</p>
          </div>
        </div>
        <div class="cart-right">
          <div class="cart-right-item" v-show="business.starPrice == null" style="background-color: #535356; cursor: default">
            未选择
          </div>
          <!-- 不够起送费 -->
          <div class="cart-right-item" v-show="totalSettle < business.starPrice" style="background-color: #535356; cursor: default">
            &#165;{{ business.starPrice }}起送
          </div>
          <!-- 达到起送费 -->
          <div class="cart-right-item" @click="toOrder" v-show="totalSettle >= business.starPrice">
            去结算
          </div>
        </div>
      </div>
      <!-- 底部菜单部分 -->
      <Footer></Footer>
    </div>
  </template>
  
  <script setup>
  import Footer from "../components/Footer.vue";
  import qs from "qs";
  import { useRouter, useRoute } from "vue-router";
  import { ref, inject, computed } from "vue";
  import {get,post} from '../api'
  import { getSessionStorage } from "../common";
  const account = getSessionStorage('account');
  const router = useRouter();
  const cartList = ref([])
  const businessId = ref("")  
  const business = ref({});
  const foodArr = ref([]);  //因为一个商家下面发布多个餐品
  const init =()=>{
    let url = "/cart/listInfoByAccountId/"+account.accountId
    get(url).then(response=>{
      cartList.value = response.data.data.cartList
    }).catch(e=>{ console.log(e); })
  }
  init();
  const checkBusiness=(item)=>{

    businessId.value = item.business.businessId
    business.value = item.business
    foodArr.value = item.foodList
  }
  //计算属性
  //食品总价格
  const totalPrice = computed(() => {
    let total =0;
    for(let item of foodArr.value){
      //餐品单价 * 数量
      total += item.foodPrice * item.quantity
    }
    return total;
  });
  
  //食品总数量
  const totalQuantity = computed(() => {
    let number =0;
    for(let item of foodArr.value){
      number += item.quantity
    }
    return number;
  });
  
  //结算总价格
  const totalSettle = computed(() => {
    //餐品的总价格 + 配送费
    return totalPrice.value + business.value.deliveryPrice;
  });
//向购物车添加一件餐品
const add = (child) => {
    if(account==null){
        router.push({path:'/login'})
        return;
    }

    if(child.quantity == 0){
        //第一次添加该餐品
        savaCart(child)
    }else{
        //不是第一次添加餐品，数据库sys_cart表中已经存在该餐品的记录
        updateCart(child,1);
    }

  };
  //向购物车减去一件餐品
  const minus = (child) => {
    if(account==null){
        router.push({path:'/login'})
        return;
    }
    if(child.quantity > 1){
        updateCart(child,-1)
    }else{
        //如果餐品数量是1，减去数量没有记录，从数据库删除
        removeCart(child);
    }
  };
  //第一次添加该餐品，调用saveCart
  const savaCart = (child) => {
    //index就是餐品数组的下标，使用index就可以获得餐品数组中点击的那个餐品
    post('/cart/saveCart',
    {
        foodId:child.foodId,
        businessId:  businessId.value,
        accountId: account.accountId,
        quantity: child.quantity,  
    },true).then(response=>{
        child.quantity = 1;
    }).catch(e=>{console.log(e);})
  };
  //第二次对餐品数量进行修改，+1或者-1   num参数如果 正数加，负数减
  const updateCart = (child, num) => {
    post('/cart/updateCart',
    {
        foodId:child.foodId,
        businessId:  businessId.value,
        accountId: account.accountId,
        quantity: child.quantity + num  
    },true).then(response=>{
      child.quantity += num
    }).catch(e=>{console.log(e);})
  };
  //如果该餐品的数量就是1，减去的话就从数据库中删除这条购物车记录
  const removeCart = (child) => {
    post('/cart/removeCart',
    {
        foodId:child.foodId,
        businessId:  businessId.value,
        accountId: account.accountId,
    },true).then(response=>{
      child.quantity =0
    }).catch(e=>{console.log(e);})
  };

  const toOrder = () => {
    router.push({
      path: "/orders",
      query: { businessId: business.value.businessId },
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
  
  /****************** 商家logo部分 ******************/
  .wrapper .business-info .business-logo {
    width: 19vw;
    height: 20vw;
    /*使用上外边距避开header部分*/
    margin-top: 0vw;
  
    /* display: flex;
    justify-content: center;
    align-items: center; */
  }
  .wrapper .business-info .business-logo img {
    width:19vw;
    height: 19vw;
    border-radius: 5px;
  }
  
  /****************** 商家信息部分 ******************/
  .wrapper .business-info {
    width: 100%;
    height: 20vw;
  
    display: flex;
    /* flex-direction: column; */
    justify-content: center;
    align-items: center;
  }
  .wrapper .business-info input{
    justify-content: left;
  }
  .wrapper .business-info h1 {
    font-size: 5vw;
  }
  .wrapper .business-info p {
    font-size: 3vw;
    color: #666;
    margin-top: 1vw;
  }
  
  /****************** 食品列表部分 ******************/
  .wrapper .food {
    width: 100%;
    /*使用下外边距避开footer部分*/
    margin-bottom: 10vw;
  }
  .wrapper .food li {
    width: 100%;
    box-sizing: border-box;
    padding: 2.5vw;
    user-select: none;
  
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .wrapper .food li .food-left {
    display: flex;
    align-items: center;
  }
  .wrapper .food li .food-left img {
    width: 20vw;
    height: 20vw;
  }
  .wrapper .food li .food-left .food-left-info {
    margin-left: 3vw;
  }
  .wrapper .food li .food-left .food-left-info h3 {
    font-size: 3.8vw;
    color: #555;
  }
  .wrapper .food li .food-left .food-left-info p {
    font-size: 3vw;
    color: #888;
    margin-top: 2vw;
  }
  .wrapper .food li .food-right {
    width: 16vw;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .wrapper .food li .food-right .fa-minus-circle {
    font-size: 5.5vw;
    color: #999;
    cursor: pointer;
  }
  .wrapper .food li .food-right p {
    font-size: 3.6vw;
    color: #333;
  }
  .wrapper .food li .food-right .fa-plus-circle {
    font-size: 5.5vw;
    color: #0097ef;
    cursor: pointer;
  }
  
  /****************** 购物车部分 ******************/
  .wrapper .cart {
    width: 100%;
    height: 14vw;
    margin-bottom: 13vw;
    position: fixed;
    left: 0;
    bottom: 0;
  
    display: flex;
  }
  .wrapper .cart .cart-left {
    flex: 2;
    background-color: #505051;
    display: flex;
  }
  .wrapper .cart .cart-left .cart-left-icon {
    width: 16vw;
    height: 16vw;
    box-sizing: border-box;
    border: solid 1.6vw #444;
    border-radius: 8vw;
    background-color: #3190e8;
    font-size: 7vw;
    color: #fff;
  
    display: flex;
    justify-content: center;
    align-items: center;
  
    margin-top: -4vw;
    margin-left: 3vw;
  
    position: relative;
  }
  .wrapper .cart .cart-left .cart-left-icon-quantity {
    width: 5vw;
    height: 5vw;
    border-radius: 2.5vw;
    background-color: red;
    color: #fff;
    font-size: 3.6vw;
  
    display: flex;
    justify-content: center;
    align-items: center;
  
    position: absolute;
    right: -1.5vw;
    top: -1.5vw;
  }
  .wrapper .cart .cart-left .cart-left-info p:first-child {
    font-size: 4.5vw;
    color: #fff;
    margin-top: 1vw;
  }
  .wrapper .cart .cart-left .cart-left-info p:last-child {
    font-size: 2.8vw;
    color: #aaa;
  }
  
  .wrapper .cart .cart-right {
    flex: 1;
  }
  /*达到起送费时的样式*/
  .wrapper .cart .cart-right .cart-right-item {
    width: 100%;
    height: 100%;
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