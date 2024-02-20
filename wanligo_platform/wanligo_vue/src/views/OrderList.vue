<template>
    <div class="wrapper">
        <!-- header部分 -->
        <el-header>
            <h2 style="color: white;text-align:center">订单信息</h2>
        </el-header>

        <el-main style="padding-bottom:10px;">
            <!-- 未支付订单列表 -->
            <el-card v-for="order in unpaidOrders" :key="order.orderId" class="box-card">
                <template v-slot:header>
                    <div class="clearfix">
                        <span v-if="order.business">商家名称: {{ order.business.businessName }}</span>
                        <el-tag type="danger" class="float-right">未支付</el-tag>
                    </div>
                </template>
                <div>


                    <el-list style="margin-bottom:10px">
                        <el-list-item v-for="item in order.list" :key="item.orderId" style="display: flex; align-items: center;margin-bottom:20px">
                            
                            <el-image :src="item.food.foodImg" style="text-align:left"></el-image>&nbsp;
                            <h4>{{ item.food.foodName }}x{{ item.quantity }} &nbsp; ¥{{ item.food.foodPrice*item.quantity }}</h4>
                        </el-list-item>
                    </el-list>
                    <el-divider />
                    <el-list style="margin-bottom: 30px;">
                        <el-list-item style="display: flex; align-items: center; justify-content: space-between;">
                            <p style="float:left;margin-right:20px">下单时间: {{ order.orderDate }}</p>
                            <el-button type="primary" @click="init(order.orderId)">立即支付</el-button>
                        </el-list-item>
                    </el-list>

                </div>
            </el-card>

            <!-- 已支付订单列表 -->
            <el-card v-for="order in paidOrders" :key="order.orderId" class="box-card">
                <template v-slot:header>
                    <div class="clearfix">
                        <span v-if="order.business">商家名称: {{ order.business.businessName }}</span>
                        <el-tag type="success" class="float-right">已支付</el-tag>
                        <el-tag type="success" class="float-right" @click="check(order.business.businessId)">去评价</el-tag>
                    </div>
                </template>
                <div>


                    <el-list style="margin-bottom:10px">
                        <el-list-item v-for="item in order.list" :key="item.orderId" style="display: flex; align-items: center;margin-bottom:20px">
                            <el-image :src="item.food.foodImg" style="text-align:left"></el-image>
                            <h4>{{ item.food.foodName }} x {{ item.quantity }}</h4>&nbsp;&nbsp;
                            <h4>¥{{ item.food.foodPrice * item.quantity }}</h4>
                        </el-list-item>
                    </el-list>
                    <el-divider />
                    <el-list style="margin-bottom: 30px;">
                        <el-list-item style="display: flex; align-items: center; justify-content: space-between;">
                            <p style="float:left;margin-right:20px">下单时间: {{ order.orderDate }}</p>
                           
                        </el-list-item>
                    </el-list>
                    
                </div>
            </el-card>
        </el-main>

        <Footer></Footer>
    </div>
</template>

<script setup>
import Footer from "../components/Footer.vue";
import { ref, onMounted } from 'vue'
import { get, post } from '../api'
import { useRouter, useRoute } from "vue-router";
import { getSessionStorage } from "../common.js";
const account = getSessionStorage("account");
const unpaidOrders = ref([]) // 未支付订单
const paidOrders = ref([]) // 已支付订单

const route = useRoute();
const router = useRouter();
const order = ref({});

// 获取未支付订单和已支付订单的数据
onMounted(() => {
    getUnpaidOrders() // 获取未支付订单
    getPaidOrders() // 获取已支付订单
})

const getUnpaidOrders = () => {
    // 调用接口获取未支付订单数据
    get('/orders/unpaid/'+account.accountId)
        .then((response) => {
            unpaidOrders.value = response.data.data.orders
        })
        .catch((e) => {
            console.log(e)
        })
}
const init = (orderId) => {
    router.push({
        path: "/payment",
        query: { orderId: orderId }
    });
}

const check = (businessId) => {
    router.push({
        path: "/evaluation",
        query: { businessId: businessId }
    });
}

const getPaidOrders = () => {
    // 调用接口获取已支付订单数据
    get('/orders/paid/'+account.accountId)
        .then((response) => {
            paidOrders.value = response.data.data.orders
        })
        .catch((e) => {
            console.log(e)
        })
}
</script>
<style scoped>
.box-card {
    margin-bottom: 10vw;
}

.el-list-item {
    display: flex;
    align-items: center;
}

.el-image {
    width: 60px;
    height: 60px;
    margin-right: 5px;
    float: left;
}
</style>
