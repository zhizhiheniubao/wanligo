//创建了一个新modules。结构同vuex是一样的
import Vue from 'vue'
import Vuex from 'vuex'

//共享数据的地方，任何地方都能使用
Vue.use(Vuex)

export default{
  state: {
    hasRoute:false, //菜单和动态路由是否加载配置过
    //菜单数据的存储
    menuList:[],
    permList:[],

    //存储选项卡数组：点击菜单触发路由，动态添加一个新的选项卡到数组
    //选项卡，默认有个首页
    TabsList:[
      {
        icon:'el-icon-s-home',
        title:'首页',
        name:'Index'
      }
    ],
    //记录选项卡激活的表示：
    TabsValue:"Index", //选项卡数组中，默认被激活的是首页
  },
  mutations: {
    //存储菜单的方法
    SET_MENULIST:(state,menus) => {
      sessionStorage.setItem("menuList",JSON.stringify(menus))
      state.menuList = menus
    },
    SET_PERMLIST:(state,authoritys) =>{
      state.permList = authoritys
    },
    //修改hasRoute值的方法
    SET_HASROUTE:(state,hasRoute) => {
      state.hasRoute = hasRoute
    },
    //添加选项卡到TabsList数组中
    ADD_TABS:(state,tab) => {
      //判断新选项卡是否已经存在,如果找到返回下标
      let index = state.TabsList.findIndex(items => items.name === tab.name)
      if (index === -1) {
        state.TabsList.push(tab) //添加新的选项卡到数组中
      }

      //激活当前的选项卡
      state.TabsValue = tab.name
    },
    //设置当前激活的选项卡 标识（tabName）
    SET_ACTIVE_TAB:(state,tabName) => {
      state.TabsValue = tabName
    }
  },
}
