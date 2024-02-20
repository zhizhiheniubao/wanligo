<template>
  <div>
    <!--订单管理的工具栏-->
    <el-form ref="searchForm" :model="searchForm" :inline="true">
      <el-form-item prop="data">
        <el-input v-model="searchForm.data" placeholder="请输入用户名/商家/送货地址" style="padding-left: 10px; width: 300px;" ></el-input>
      </el-form-item>
      <el-form-item prop="orderDate" label="订单日期" style="padding-left: 10px;">
        <el-date-picker v-model="searchForm.orderDate.date" 
                        type="daterange" 
                        format="yyyy 年 MM 月 dd 日" 
                        value-format="yyyy-MM-dd" 
                        unlink-panels 
                        range-separator="至" 
                        start-placeholder="开始日期" 
                        end-placeholder="结束日期" 
                        @change='dateFormat'/>  
      </el-form-item>
      <el-form-item prop="orderState">
        <el-select v-model="searchForm.orderState" placeholder="筛选状态" style="padding-left: 10px; width: 150px;" clearable>
          <el-option v-for="item in options" :key="item.statu" :label="item.label" :value="item.statu">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="delTag">
        <el-select v-model="searchForm.delTag" placeholder="删除状态" style="padding-left: 10px; width: 150px;" clearable>
          <el-option v-for="item in delOption" :key="item.statu" :label="item.label" :value="item.statu">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button style="margin-left: 10px;"  @click="loadOrdersList" icon="el-icon-search">搜索</el-button>
        <el-button type="info" @click="resetOrdersForm('searchForm')">清空</el-button>
        <el-popconfirm title="确定要批量删除这些订单信息吗" style="padding-left: 10px;" @confirm="deleteOrders()">
          <el-button type="danger" slot="reference" :disabled="deleteAllBtnStu">批量删除</el-button>
        </el-popconfirm>
      </el-form-item>
    </el-form>

    <!--订单详情数据显示-->
    <el-dialog title="订单详情" :visible.sync="ordersDialogVisible"  width="900px" center v-for="item in orderDetailData.orderdeliveryData" :key="item.odId">    
      <el-table v-loading="orderDetailData.loading" :data="orderDetailData.foodData" border>
        <el-table-column label="餐品头像" width="80" prop="foodImg">
          <template slot-scope="scope">
            <el-avatar size="small" :src="scope.row.foodImg"></el-avatar>
          </template>
        </el-table-column>
        <el-table-column prop="foodName" label="购买餐品" width="150"></el-table-column>
        <el-table-column prop="foodExplain" label="餐品说明" width="150"></el-table-column>
        <el-table-column prop="foodPrice" label="餐品价格" width="150"></el-table-column>
        <el-table-column label="购买份数">
          <template>{{ item.quantity }}份</template>
        </el-table-column>
        <el-table-column label="餐品总价">
          <template slot-scope="scope">
            <template>{{ item.quantity * scope.row.foodPrice }}</template>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <!--订单表格数据显示-->
    <el-table v-loading="loading" :data="tableData" style="width: 100%;margin-bottom: 20px;" border :cell-style="{padding:'2px'}" @selection-change="selectionChange">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column prop="orderId" label="订单编号" width="80"></el-table-column>
      <el-table-column prop="accountId" label="用户" width="80">   
        <template slot-scope="scope">
          <template v-for="item in accountData" >
            <template v-if="scope.row.accountId == item.accountId">{{ item.accountName }}</template>
          </template>
        </template>
      </el-table-column>
      <el-table-column prop="businessId" label="购买商家" width="250">
        <template slot-scope="scope">
          <template v-for="item in businessData" >
            <template v-if="scope.row.businessId == item.businessId">{{ item.businessName }}</template>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="订单日期" width="220px">
        <template slot-scope="scope">
          {{ scope.row.orderDate | fmtDate }}
        </template> 
      </el-table-column>
      <el-table-column prop="orderTotal" label="订单总额" width="100"></el-table-column>     
      <el-table-column prop="daId" label="送货地址" width="330">   
        <template slot-scope="scope">
          <template v-for="item in addressData" >
            <template v-if="scope.row.daId == item.daId">{{ item.address }}</template>
          </template>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="订单状态" width="100">
        <template slot-scope="scope">
          <el-tag size="mini" v-if="scope.row.orderState===0" type="danger">支付失败</el-tag>
          <el-tag size="mini" v-else-if="scope.row.orderState===1" type="success">支付成功</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="删除状态" width="100">
        <template slot-scope="scope">
          <el-tag size="mini" v-if="scope.row.delTag===0" type="danger">已删除</el-tag>
          <el-tag size="mini" v-else-if="scope.row.delTag===1" type="success">正常</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right">
        <template slot-scope="scope">  
          <el-button size="small" slot="reference" type="success" plain @click="orderDetail(scope.row.orderId)">详情</el-button>
          <el-popconfirm title="确定要删除该订单信息吗" @confirm="deleteOrders(scope.row.orderId)" style="padding-left: 10px;">
            <el-button size="small" slot="reference" type="danger" plain>删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--分页的代码-->
    <el-pagination background layout="prev,pager,next,sizes,total,jumper" :total="total" :page-size="size" :page-sizes="[5,10,15,20,25]"
    :current-page="current" @size-change="sizeChangeHandler" @current-change="currentChangeHandler">
    </el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData:[],
      deleteAllBtnStu:true,
      ordersDialogVisible:false,
      //送货地址
      addressData:{},
      //用户数据
      accountData:{},
      //商家数据
      businessData:{},
      //订单详情数据
      orderDetailData:{
        loading:false,
        foodData:[],
        orderdeliveryData:[]
      },
      loading:false,
      //工具栏 搜索框的表单对象
      searchForm:{
        data:'',
        orderDate:{
          startTime:'',
          endTime:'',
          date:''
        },
        orderState:'',
        delTag:'',
      },
      //支付状态
      options:[
        {
          statu:0,
          label:'支付失败',
        },
        {
          statu:1,
          label:'支付成功',
        },
      ],
      //删除状态
      delOption:[
        {
          statu:0,
          label:'已删除',
        },
        {
          statu:1,
          label:'正常',
        },
      ],
      current:1,//当前的页码
      size: 10,//一页显示记录数
      total:0,  //总共的记录数
      selection:[], //复选框选中的角色对象,
    }
  },
  created(){
    this.loadOrdersList();
    this.$axios.get('/deliveryaddress/list').then(response=>{
      this.addressData = response.data.resultdata
    }).catch(e=>{ console.log(e)})
    this.$axios.get('/account/list').then(response=>{
      this.accountData = response.data.resultdata
    }).catch(e=>{ console.log(e)})
    this.$axios.get('/business/list').then(response=>{
      this.businessData = response.data.resultdata
    }).catch(e=>{ console.log(e)})
  },
  methods:{
    //订单详情
    orderDetail(orderId){
      this.orderDetailData.loading=true
      this.$axios.get('/orderdetailet/listByOrderId/'+orderId).then(response=>{
        this.orderDetailData.orderdeliveryData = response.data.resultdata
        this.ordersDialogVisible = true
      }).catch(e=>{ console.log(e)})
      this.$axios.get('/food/listByOrderId/'+orderId).then(response=>{
        this.orderDetailData.foodData = response.data.resultdata
        this.ordersDialogVisible = true
        this.orderDetailData.loading=false
      }).catch(e=>{ console.log(e)})
    },
    //获取日期区间
    dateFormat(picker) {
      this.searchForm.orderDate.startTime = this.searchForm.orderDate.date && this.searchForm.orderDate.date.length >= 2 ? this.searchForm.orderDate.date[0].toString() : null;
      this.searchForm.orderDate.endTime = this.searchForm.orderDate.date && this.searchForm.orderDate.date.length >= 2 ? this.searchForm.orderDate.date[1].toString() : null;
    },
    //点击页面的切换
    currentChangeHandler(val){
      this.current = val;
      this.loadOrdersList()
    },
    //修改一页数据记录数
    sizeChangeHandler(val){
      this.size = val;
      this.loadOrdersList()
    },
    //删除订单数据
    deleteOrders(orderId){
      var ordersIds = []; 
      orderId?ordersIds.push(orderId):this.selection.forEach(order => {
        console.log(order.orderId);
        ordersIds.push(order.orderId)
      })
      this.$axios.post("/orders/delete",ordersIds).then(response=>{
        this.$message({
          showClose:true,
          message:response.data.resultdata,
          type:'success',
          onClose:()=>{
            this.loadOrdersList();
          }  
        });            
        //将对话框重新隐藏
        this.dialogFormVisible=false;
      }).catch(e=>{console.log(e);})
    },
    //清空搜索框
    resetOrdersForm(searchForm){
      this.searchForm.orderDate.date = null
      this.searchForm.orderDate.startTime = ''
      this.searchForm.orderDate.endTime = ''
      this.$refs[searchForm].resetFields();
    },
    //请求Java服务器端获得所有的订单
    loadOrdersList(){
      this.loading=true
      this.$axios.get('/orders/page',{
        params:{
          data:this.searchForm.data,  //搜索关键词
          startTime:this.searchForm.orderDate.startTime,
          endTime:this.searchForm.orderDate.endTime,
          orderState:this.searchForm.orderState,
          delTag:this.searchForm.delTag,
          current:this.current,   //页码
          size:this.size, //一页显示多少条
        }
      }).then(response=>{
        this.tableData = response.data.resultdata.records
        this.total = response.data.resultdata.total
        this.size = response.data.resultdata.size
        this.current = response.data.resultdata.current
        this.loading=false
      }).catch(e=>{ console.log(e)})
    },
    //选中表格中 复选框触发的事件
    selectionChange(value){
      //value参数就是选中的复选框 对应的这一行粉丝对象
      this.selection = value
      if(this.selection){
        //this.selection.length === 0 如果选中，数组不为0，返回false
        //this.selection.length === 0 如果没有选中，数组长度为0，返回true
        this.deleteAllBtnStu = (this.selection.length === 0)
      }
    },
  },
}
</script>

<style scoped>

</style>
