<template>
    <el-container>
      <!--导航栏-->
      <el-aside width="201px">
        <!--使用菜单栏的子组件-->
        <NavMenu></NavMenu>
      </el-aside>
      <el-container>
        <!--头部分-->
        <el-header>
          <strong>万里够后台管理系统v1.0</strong>
          <div class="header-avatar">
            <el-avatar size="medium" :src="userInfo.avatar"></el-avatar>
            <el-dropdown>
              <span class="el-dropdown-link">
                {{ userInfo.username }}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                  <router-link :to="{name:'UserCenter'}">个人信息</router-link>
                </el-dropdown-item>
                <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-header>
        <!--主体-->
        <el-main>
          <!--配置菜单中子路由出口-->
          <Tabs/>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
</template>

<script>
import NavMenu from "@/components/NavMenu.vue"
import Tabs from "@/components/Tabs.vue"
export default {
  data() {
    return {
      //登录之后，存储登录用户基本信息；头像、用户名
      userInfo:{
        username:"",
        avatar: ''
      }
    }
  },
  methods:{
    //退出系统的方法
    logout(){
      this.$axios.get("/logout").then(response=>{
        this.$store.commit("SET_TOKEN",'');
        sessionStorage.clear();
        localStorage.clear();
        this.$router.push("/login");
      })
    },
    //获取用户登录的基本数据
    getUserInfo(){
      //请求JAVA服务器端，获得用户基本数据
      this.$axios.get('/api/userinfo').then(response=>{
        this.userInfo = response.data.resultdata
      })
    },
  },
  created(){
    //生命周期钩子函数
    this.getUserInfo();
  },
  components: {
    NavMenu,
    Tabs
  }
}
</script>

<style scoped>
  .el-container{
    padding: 0;
    margin: 0;
    height: 100%;
  }
  .el-header{
    background-color: rgb(117, 174, 231);
    color: #333;
    text-align: left;
    line-height: 60px;
  }
  .el-aside{
    background-color: rgb(177, 209, 236);
  }
  .el-main{
    background-color: #e6eff0;
    padding: 0;
  }
  .header-avatar{
    float: right;
    width: 110px;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }
  a{text-decoration: none; color: black;}
  .el-avatar{
    margin-right: 10px;
  }
  .el-dropdown-link{
    cursor: pointer;
  }
</style>
