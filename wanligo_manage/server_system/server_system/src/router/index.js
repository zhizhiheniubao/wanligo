import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store/index'
// import axios from 'axios'
import axios from '@/api'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/Home.vue'),
    children:[
      {
        path: '/userCenter',
        name: 'UserCenter',
        meta:{
          icon:'el-icon-user',
          title:'个人中心'
        },
        component: () => import('@/views/UserCenter.vue')
      },
      {
        path: '/index',
        name: 'Index',
        meta:{
          icon:'el-icon-s-home',
          title:'首页'
        },
        component: () => import('@/views/system/Index.vue')
      },
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/notFound',
    name: 'NotFound',
    component: () => import('@/views/NotFound.vue')
  },
]

const router = new VueRouter({
  routes,
})

// 路由守卫(导航首位)
router.beforeEach((to,from,next) => {
  //加载菜单数据：（判断：菜单数据加载过，就不再加载）
  let hasRoute = store.state.menus.hasRoute
  let menus = store.state.menus.menuList
  let token = store.state.token

  //路由守卫会拦截所有请求，需要对请求路径进行过滤
  if (to.path == '/login'){
    // console.log('菜单和路由已经加载过了')
    next()  //放行
  // }else if (!token){
    // console.log('没有token')
    // next({path: '/login'}) //强制跳转回登录页面
  }else if(to.path=='/' || to.path==''){
    next({to:'/'})
  }
  if (!hasRoute){
    let newRoutes = router.options.routes
    //hasRoute如果为false,就加载菜单数据
    axios.get('/menu/nav').then(response => {
        store.commit('SET_MENULIST',response.data.resultdata.nav) //将获取菜单数据存储到Vuex中
        store.commit('SET_PERMLIST',response.data.resultdata.authoritys)  //将权限数据存储到Vuex中

        //TOOD  创建动态路由
        response.data.resultdata.nav.forEach(menu => {
          if (menu.children){
            //循环二级菜单
            menu.children.forEach(child => {
              let route = menuToRoute(child)  //得到创建好的路由配置对象
              if (route){
                //需要将创建路由配置对象，加到目前路由配置中
                newRoutes[0].children.push(route)
              }
            })
          }
        })
        //将配置完毕的新路由配置到数组newRoutes，重新放回router路由对象中
        router.addRoutes(newRoutes)
        store.commit('SET_HASROUTE',true)
        next({path:to.path})  //跳转至原本需要访问的地址
    }).catch(error => {
        console.log(error)
    })
    store.commit('SET_HASROUTE',true)
    next({path:to.path})
  } else{
    // console.log('菜单和路由已经加载过了')
    next()
  }
})

//分装方法：由菜单数据创建出 路由配置对象
const menuToRoute = child =>{
  if (!child.component){
    //菜单如果component组件为空，直接返回null
    return null
  }
  //创建路由对象
  let route ={
    path: child.path,
    name: child.name,
    meta:{
      //配置元数据（在路由对象中携带一些对象数据），做Tab选项卡
      icon: child.icon, //图标
      title:child.title,  //菜单标题
    }
  }
  route.component = ()=>import('@/views/'+child.component+'.vue')
  return route
}

export default router
