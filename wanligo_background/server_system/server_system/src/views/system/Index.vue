<template>
  <div>
    <div ref="echarts" style="height: 600px;width: 800px;"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
export default {
  data() {
    return {
      name: 'App',
      orderCounts:[],
      businessNames:[],
      business:[]
    }
  },
  mounted() {
    

    let chart = this.$echarts.init(this.$refs.echarts)
    // this.$nextTick(()=>{
    //   this.ordersList();
    // })
    var option={
      title: {
        text: "订单前5商家",
      },
      tooltip: {},
      legend: {
        data: ["订单"],
      },
      xAxis: {
        data: []
        // this.orderCounts.map(function (item) {
        //         return item.businessId;
        //     }),
      },
      yAxis: {},
      series: [
        {
          name: "订单",
          type: "bar",
          data: []
          // this.orderCounts.map(function (item) {
          //         return item.count;
          //       }),
        },
      ],
      
    }
    this.$axios.get('/orders/ordersGroupBy').then(response=>{
      
          response.data.resultdata.map(item =>{
            this.orderCounts.push(item.count)
            for (let index = 0; index < this.business.length; index++) {
              if(this.business[index].businessId == item.businessId){
                this.businessNames.push(this.business[index].businessName)
              }
              
            }
            option.xAxis.data=this.businessNames
            option.series[0].data=this.orderCounts
            chart.setOption(option)
          })
          
          // this.orderCounts.map(function (item) {
          //         return item.count;
          //       }),
      }).catch(e=>{ console.log(e); })
    chart.setOption({
      
    })
  },
  created(){
    this.ordersList();
    this.$axios.get('/business/list').then(response=>{
          this.business=response.data.resultdata
      }).catch(e=>{ console.log(e); })
  },
  computed:{

  },
  methods:{
    ordersList(){
      this.$axios.get('/orders/ordersGroupBy').then(response=>{
        
          // this.orderCounts.map(function (item) {
          //         return item.count;
          //       }),
      }).catch(e=>{ console.log(e); })
    }
    
  }
}
</script>

<style scoped>
</style>
