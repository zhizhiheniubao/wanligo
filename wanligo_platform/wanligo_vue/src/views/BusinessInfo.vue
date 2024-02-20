<template>
  <div class="wrapper">
    <!-- header部分 --> 
    <header>
      <button type="text" style="background-color: #0097ff;color: #fff;font-size: 4.8vw;height: 10vw;margin-bottom: 3vw;" @click="back">返回</button>
      <p style="margin-left: 20vw;">商家信息</p>
    </header>
    <!-- 商家logo部分 -->
    <div class="business-logo">
      <img :src="business.businessImg" />
    </div>
    <!-- 商家信息部分 -->
    <div class="business-info">
      <h1>{{ business.businessName }}</h1>
      <p>
        &#165;{{ business.starPrice }}起送 &#165;{{
          business.deliveryPrice
        }}配送
      </p>
      <p>{{ business.businessExplain }}</p>
    </div>
    <el-tabs v-model="activeName" class="demo-tabs">
      <el-tab-pane label="点餐" name="first">
        <!-- 食品列表部分 -->
        <ul class="food">
          <li v-for="(item, index) in foodArr" :key="item.foodId">
            <div class="food-left">
              <img :src="item.foodImg" />
              <div class="food-left-info">
                <h3>{{ item.foodName }}</h3>
                <p>{{ item.foodExplain }}</p>
                <p>&#165;{{ item.foodPrice }}</p>
              </div>
            </div>
            <div class="food-right">
              <div>
                <i class="fa fa-minus-circle" @click="minus(index)" v-show="item.quantity != 0"></i>
              </div>
              <p>
                <span v-show="item.quantity != 0">{{ item.quantity }}</span>
              </p>
              <div>
                <i class="fa fa-plus-circle" @click="add(index)"></i>
              </div>
            </div>
          </li>
        </ul>
      </el-tab-pane>
      <!-- 评价部分 -->
      <el-tab-pane label="评价" name="second">
        <ul v-if="commentArr.length==0"><li>暂无评价</li></ul>
        <ul class="comment" v-else>
          <li v-for="(item, index) in commentArr">
            <div style="display: flex;">
              <div style="margin-top: 23px;">
                <el-image :src="item.accountImg" style=" width: 40px;margin-right: 18px;border-radius: 50%;"></el-image>
              </div>
              <div style="padding: 23px 0px 0px 0px;">
                <div class="firstComment">
                  <div style="text-align: left;margin-bottom: 7px;">
                    <a href="#" style="font-weight: bold;text-align: left;font-size: 14px;color: #6d757a;">{{
                      item.accountName }}</a>
                  </div>
                  <p class="commentInfo" style="max-width: 300px;overflow-y: auto;text-align: left;">{{ item.comment }}
                  </p>
                  <div style="display: flex;">
                    <div style="width: 140px;margin-right: 10px;" v-for="(img, i) in item.commentImgList"><el-image
                        v-bind:src="img"></el-image></div>
                  </div>
                  <div style="text-align: left;margin-top: 5px;position: relative;width: 880px;">
                    <span style="color: #99a2aa;font-size: 12px;margin-right: 15px;">
                      {{ item.createTime }}
                    </span>
                    <span style="color: #99a2aa;font-size: 12px;" @click="openReply(item, index, index2)">回复</span>
                  </div>
                </div>
                <div style="padding-top: 20px" v-if="item.openReply">
                  <div style="display: flex;">
                    <textarea class="reply" type="text" style="" v-model="comment" placeholder="讲话，我让你讲话!!"></textarea>
                    <button @click="addComment(item)" type="submit"
                      style="width: 80px;height: 40px;margin-left: 10px;background-color: #00a1d6;border: 1px solid #00a1d6;border: none;border-radius: 4px;color: white;font-size: 3vw;font-weight: bold;cursor: pointer;">
                      发表评论
                    </button>
                  </div>
                </div>
                <div class="reply-box">
                  <div v-for="(item2, index2) in item.children">
                    <div style="display: flex;" v-if="item2.pname != null">
                      <div style="margin-top: 23px;">
                        <el-image :src="item2.accountImg"
                          style=" width: 40px;margin-right: 10px;border-radius: 50%;"></el-image>
                      </div>
                      <div style="padding: 23px 0px 0px 0px;">
                        <div class="commentInfo"
                          style="text-align: left;margin-bottom: 7px;font-size: 14px;width: 820px;">
                          <a href="#" style="font-weight: bold;text-align: left;font-size: 14px;color: #6d757a;">{{
                            item2.accountName }}</a>
                          <span v-if="item2.pid != item2.originId">
                            &nbsp回复&nbsp
                            <a href="#" style="font-weight: bold;text-align: left;font-size: 14px;color: #00a1d6;">@{{
                              item2.pname }}</a>
                            :
                          </span>
                          <span v-else>
                            :
                          </span>
                          {{ item2.comment }}
                          <div style="text-align: left;margin-top: 5px;position: relative;width: 880px;">
                            <span style="color: #99a2aa;font-size: 12px;margin-right: 15px;">
                              {{ item2.createTime }}
                            </span>
                            <span style="color: #99a2aa;font-size: 12px;"
                              @click="openReply(item2, index, index2)">回复</span>
                          </div>
                        </div>
                        <div style="padding-top: 20px" v-if="item2.openReply">
                          <div style="display: flex;">
                            <textarea class="reply" type="text" style="" v-model="comment"
                              placeholder="讲话，我让你讲话!!"></textarea>
                            <button @click="addComment(item2)" type="submit"
                              style="width: 80px;height: 40px;margin-left: 10px;background-color: #00a1d6;border: 1px solid #00a1d6;border: none;border-radius: 4px;color: white;font-size: 3vw;font-weight: bold;cursor: pointer;">
                              发表评论
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </li>
          <div style="height: 20vw;width: 100%;">
          </div>
        </ul>
      </el-tab-pane>
    </el-tabs>

    <!-- 购物车部分 -->
    <div class="cart">
      <div class="cart-left">
        <div class="cart-left-icon" :style="totalQuantity == 0
          ? 'background-color:#505051;'
          : 'background-color:#3190E8;'
          ">
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
        <!-- 不够起送费 -->
        <div class="cart-right-item" v-show="totalSettle < business.starPrice"
          style="background-color: #535356; cursor: default">
          &#165;{{ business.starPrice }}起送
        </div>
        <!-- 达到起送费 -->
        <div class="cart-right-item" @click="toOrder" v-show="totalSettle >= business.starPrice">
          去结算
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import qs from "qs";
import { useRouter, useRoute } from "vue-router";
import { ref, computed } from "vue";
import { getSessionStorage } from "../common.js";
import { get, post } from "../api";
const router = useRouter();
const route = useRoute();
const account = getSessionStorage("account");
const businessId = route.query.businessId; //从路由地址中获取传递的商家id
const business = ref({});
const commentArr = ref([]);
const foodArr = ref([]); //因为一个商家下面发布多个餐品
const activeName = ref('first')
const pcommentIndex = ref([]);
const comment = ref("");
const commentInfo = ref([]);
//计算属性
//食品总价格
const totalPrice = computed(() => {
  let total = 0;
  for (let item of foodArr.value) {
    total += item.foodPrice * item.quantity
  }
  return total;
});
//食品总数量
const totalQuantity = computed(() => {
  let foodNumber = 0
  for (let item of foodArr.value) {
    foodNumber += item.quantity
  }
  return foodNumber;
});
//结算总价格
const totalSettle = computed(() => {
  return totalPrice.value + business.value.deliveryPrice;
});

const init = () => {
  //根据商家Id获得该商家的详细数据
  let url = "/business/info/" + businessId;
  get(url)
    .then((response) => {
      business.value = response.data.data.business;
    })
    .catch((e) => {
      console.log(e);
    });
  let comment_url = "/comment/info/" + businessId;
  get(comment_url)
    .then((response) => {
      console.log(response)
      commentArr.value = response.data.data.comment
    })
    .catch((e) => {
      console.log(e);
    });
  //根据businessId查询该商家下面的所有发布的餐品
  let foot_url = "/food/listByBusinessId/" + businessId;
  get(foot_url)
    .then((response) => {
      let tempArr = response.data.data.foodList;
      for (let i = 0; i < tempArr.length; i++) {
        tempArr[i].quantity = 0;
        //查询出的餐品的对象都添加一个数量属性quantity，默认0
      }
      foodArr.value = tempArr;
      if (account != null) {
        listCart();
      }
    })
    .catch((e) => {
      console.log(e);
    });
};
init();
const listCart = () => {
  let url = "/cart/list/" + account.accountId
  get(url)
    .then((response) => {
      let cartArr = response.data.data.cartList;
      for (let foodItem of foodArr.value) {
        foodItem.quantity = 0
        for (let cartItem of cartArr) {
          if (cartItem.foodId === foodItem.foodId) {
            foodItem.quantity = cartItem.quantity
          }
        }
      }

    })
    .catch((e) => {
      console.log(e);
    });
};
//向购物车添加一件餐品
const add = (index) => {
  if (account == null) {
    router.push({ path: "/login" });
    return;
  }
  if (foodArr.value[index].quantity == 0) {
    //第一次添加该餐品
    savaCart(index);
  } else {
    //不是第一次添加餐品，数据库sys_cart表中已经存在该餐品的记录
    updateCart(index, 1);
  }
};
//向购物车减去一件餐品
const minus = (index) => {
  if (account == null) {
    router.push({ path: "/login" });
    return;
  }
  if (foodArr.value[index].quantity > 1) {
    updateCart(index, -1);
  } else {
    //如果餐品数量是1，减去数量没有记录，从数据库删除
    removeCart(index);
  }
};
//第一次添加该餐品，调用saveCart
const savaCart = (index) => {
  //index就是餐品数组的下标，使用index就可以获得餐品数组中点击的那个餐品
  post(
    "/cart/saveCart",
    {
      foodId: foodArr.value[index].foodId,
      businessId: businessId,
      accountId: account.accountId,
      quantity: 1,
    },
    true
  )
    .then((response) => {
      foodArr.value[index].quantity = 1;
    })
    .catch((e) => {
      console.log(e);
    }
    );
};
//第二次对餐品数量进行修改，+1或者-1 num参数如果 正数加，负数减
const updateCart = (index, num) => {
  post(
    "/cart/updateCart",
    {
      foodId: foodArr.value[index].foodId,
      businessId: businessId,
      accountId: account.accountId,
      quantity: foodArr.value[index].quantity + num,
    },
    true
  )
    .then((response) => {
      foodArr.value[index].quantity += num;
    })
    .catch((e) => {
      console.log(e);
    });
};
//如果该餐品的数量就是1，减去的话就从数据库中删除这条购物车记录
const removeCart = (index) => {
  post(
    "/cart/removeCart",
    {
      foodId: foodArr.value[index].foodId,
      businessId: businessId,
      accountId: account.accountId,
    },
    true
  )
    .then((response) => {
      foodArr.value[index].quantity = 0;
    })
    .catch((e) => {
      console.log(e);
    });
};
//打开回复评论
const openReply = (item, index, index2) => {
  comment.value = ""
  if (pcommentIndex.value.length > 0) {
    if (pcommentIndex.value[2]) {
      if (commentArr.value[pcommentIndex.value[0]].children.length > 0) {
        commentArr.value[pcommentIndex.value[0]].children[pcommentIndex.value[1]].openReply = false
      }
    } else {
      commentArr.value[pcommentIndex.value[0]].openReply = false
    }
  }
  if (item.originId == null) {
    commentArr.value[index].openReply = true
    pcommentIndex.value = [index, 0, false]
  } else {
    commentArr.value[index].children[index2].openReply = true
    pcommentIndex.value = [index, index2, true]
  }
}
//回复评论
const addComment = (item) => {
  let data = {
    comment: comment.value,
    businessId: businessId,
  }
  console.log(data)
  if (data.comment.length <= 0) {
    return
  }
  if (item != null) {
    if (item.originId == null && item.pid == null) {
      data.originId = item.id
      data.pid = item.id
    }
    if (item.pid) {
      data.originId = item.originId
      data.pid = item.id
    }
  }
  data.accountId = account.accountId
  console.log(data)
  post("/comment/addComment", data, true).then(res => {
      init();
      comment.value = ""
  })
}
const toOrder = () => {
  router.push({
    path: "/orders",
    query: { businessId: business.value.businessId },
  });
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
  /* align-items: center; */
}

/****************** 商家logo部分 ******************/
.wrapper .business-logo {
  width: 100%;
  height: 35vw;
  /*使用上外边距避开header部分*/
  margin-top: 12vw;
  display: flex;
  justify-content: center;
  align-items: center;
}

.wrapper .business-logo img {
  width: 40vw;
  height: 30vw;
  border-radius: 5px;
}

/****************** 商家信息部分 ******************/
.wrapper .business-info {
  width: 100%;
  height: 20vw;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
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
  margin-bottom: 19vw;
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

/*评论*/
.reply {
  transition: 0s;
  padding: 10px 10px;
  font-size: 12px;
  display: inline-block;
  box-sizing: border-box;
  background-color: #f4f5f7;
  border: 1px solid #e5e9ef;
  overflow: auto;
  border-radius: 4px;
  width: 200px;
  height: 40px;
  max-width: 200px;
  min-width: 200px;
  max-height: 40px;
  min-height: 40px;
}
</style>
