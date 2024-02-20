<template>
  <el-menu class="el-menu-vertical-demo" active-text-color="rgb(193, 34, 268)" text-color="#333" :default-active="activeMenu">
     <router-link to="/index">
        <el-menu-item index="Index" style="text-align: left;">
                <template slot="title">
                    <i class="el-icon-s-home"></i>
                    <span>首页</span>
                </template>
        </el-menu-item>
    </router-link>  
        <!--循环显示菜单数据-->
    <el-submenu :index="item.name + ''"  v-for="(item,index) in menuList" :key="index">
        <template slot="title">
            <i :class="item.icon"></i>
            <span>{{ item.title }}</span>
        </template>
        <!--管理系统子菜单-->
        <router-link :to="child.path" v-for="(child,i) in item.children" :key="i">
            <el-menu-item :index="child.name" @click="selectMenu(child)">
                <template slot="title">
                    <i :class="child.icon"></i>
                    <span>{{ child.title }}</span>
                </template>
            </el-menu-item>
        </router-link>
    </el-submenu>    
  </el-menu>
</template>

<script>
export default {
  data() {
    return {
        menuList:JSON.parse(sessionStorage.getItem("menuList"))
        // menuList:this.$store.state.menus.menuList
    }
  },
  components: {
  },
  created(){
    this.getMenuList();
  },
  methods:{
    getMenuList(){
        this.$axios.get("/menu/nav").then(response=>{
            this.menuList = response.data.resultdata.nav
        }).catch(e=>{
            console.log(e)
        })
        
    },
    selectMenu(child){
        //需要使用child数据  创建一个Tab选项卡对象
        let tab = { 
            icon:child.icon,
            name:child.name,
            title:child.title
        }
        //调用vuex的ADD_TABLES的内容，添加到选项卡数组中
        this.$store.commit('ADD_TABS',tab)
    }
  },
  computed:{
    activeMenu(){
        return this.$store.state.menus.TabsValue
    }
  }
}
</script>

<style scoped>
/* .el-menu{ height:100%; } */
a{ text-decoration:none; }
.el-submenu{
    text-align: left;
}
</style>
