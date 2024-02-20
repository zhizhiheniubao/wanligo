<template>
	<div class="wrapper">
	  	<header>
        	<p>个人中心</p>
       	</header>
	  	<div class="top">
	  		<div>
	   			<img class="tar" :src="account.accountImg" alt="">
	   			<p style="float:left;color:#FFFFFF;margin-left:15px;margin-top:20px">{{account.accountName}}</p>
				<p v-if="account.accountSex == 1" style="float:left;color:#FFFFFF;margin-left:15px;margin-top:20px">男</p>
				<p v-else style="float:left;color:#FFFFFF;margin-left:15px;margin-top:20px">女</p>
	   			<div style="float:right;height:25px;background-color:#295F96;margin-top:25px;width: 80PX; border-radius: 15px 0px 0px 15px;color:#FFFFFF">&nbsp;<a href="/#/updatePersonal" style="color:#FFFFFF">修改信息</a></div>
	   		</div>
	   		<div>&nbsp;</div>
	   		<div style="clear: both">
		  		<div style="margin-top:10px;color:#FFFFFF;float:left;margin-left:30px;">
			  		<div>评价</div>
			  		<div>{{count}}</div>
		  		</div>
		  		<div style="margin-top:10px;color:#FFFFFF;float:left;margin-left:90px;">
					<a href="/#/OrderList" style="color: #FFFFFF;">消费订单</a>
			  		<div>{{order}}</div>
		  		</div>
				<div style="margin-top:10px;color:#FFFFFF;float:left;margin-left:90px;">
			  		<a href="/#/address" style="color: #FFFFFF;">地址管理</a>
			  		<div>{{address}}</div>
		  		</div>
	   		</div>
	  	</div>
	  	<div style="">
		  	<div style="float:left;margin-left:158px;margin-top:10px">
			  	<div style="margin-top:3px" @click="list">全部评价</div>
		  	</div>
		  	<div style="clear: both;;width:100%;height:10px"></div>
	  	</div>
	  	<div style="max-height:10px;margin-top:10px; clear: both; border-top: 1px solid #e2e2ee;">
			<ul v-if="commentArr == null"><li>暂无评价</li></ul>
			<ul class="comment" v-else>
				<li v-for="(item, index) in commentArr">
					<div style="display: flex;  margin-left: 10px;">
						<div style="margin-top: 23px;">
							<el-image :src="account.accountImg" style=" width: 40px;margin-right: 18px;border-radius: 50%;"></el-image>
						</div>
						<div style="padding: 23px 0px 0px 0px;">
							<div class="firstComment">
								<div style="text-align: left;margin-bottom: 7px;">
									<a href="#" style="font-weight: bold;text-align: left;font-size: 14px;color: #6d757a;">{{item.accountName }}</a>
								</div>
							<p class="commentInfo" style="max-width: 300px;overflow-y: auto;text-align: left;">{{ item.comment }}</p>
							<div style="display: flex;">
								<div style="width: 140px;margin-right: 10px;" v-for="(img, i) in item.commentImgList"><el-image
									v-bind:src="img"></el-image></div>
								</div>
								<div style="text-align: left;margin-top: 5px;position: relative;width: 300px;">
									<span style="color: #99a2aa;font-size: 12px;margin-right: 15px;">{{ item.createTime }}</span>	
									<template v-for="(business,index) in businessArr">
										<span v-if="item.businessId == business.businessId" style="color: #99a2aa;font-size: 12px;margin-right: 15px;">{{ business.businessName }}</span>	
									</template>
								</div>
							</div>
						</div>
					</div>
				</li>
				<div style="height: 20vw;width: 100%;">
				</div>
			</ul>
	  	</div>
	  	<Footer></Footer>
	</div> 
</template>
<script setup>
	import Footer from "../components/Footer.vue";
  	import qs from "qs";
  	import { useRouter, useRoute } from "vue-router";
  	import { ref } from "vue";
  	import { get,post } from "../api";
	import { getSessionStorage } from "../common.js";
	import { Plus } from '@element-plus/icons-vue';
	const account = getSessionStorage("account");
	const count = ref('');
	const order = ref('')
	const address = ref('')
	const commentArr = ref([]);
	const disShow = ref(false);
	const businessArr = ref([])
	const init = () => {
		get("/comment/countByAccount/"+account.accountId).then((response) => {
			count.value = response.data.data.comment
		})
		get("/orders/countByAccount/"+account.accountId).then((response) => {
			order.value = response.data.data.order
		})
		get("/deliveryaddress/countByAccount/"+account.accountId).then((response) => {
			address.value = response.data.data.address
		})
		let comment_url = "/comment/infoByAccountId/" + account.accountId;
		get(comment_url)
			.then((response) => {
			commentArr.value = response.data.data.comments
		}).catch((e) => {
      		console.log(e);
    	});
		get("/business/lists").then((response) => {
			businessArr.value = response.data.data.businessList
		})
	}
  	init();
	
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
  .wrapper .top{
	  height: 150px;
	  width: 100%;
	  background-color: #1e87ff;
	  margin-top:47px
   }
   .wrapper .tar{
		  margin-top: 20px;
		  height: 70px;
		  width: 70px;
		  border-radius: 50%;
		  float: left;
		  margin-left:10px;
	  }
</style>
