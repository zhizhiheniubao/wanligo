import { createRouter, createWebHashHistory } from 'vue-router'
import Index from '../views/Index.vue'
import BusinessList from '../views/BusinessList.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import BusinessInfo from '../views/BusinessInfo.vue'
import Orders from '../views/Orders.vue'
import Personal from '../views/Personal.vue'

  //配置路由信息
  const routes = [{
    path: '/',
    name: 'Home',
    component: Index
  }, {
    path: '/index',
    name: 'Index',
    component: Index
  },
  {
    path: '/businessList',
    name: 'BusinessList',
    component: BusinessList
  },{
    path: '/login',
    name: 'Login',
    component: Login
  },{
    path: '/register',
    name: 'Register',
    component: Register
  }
  ,{
    path: '/businessInfo',
    name: 'BusinessInfo',
    component: BusinessInfo
  },
  {
    path: '/orders',
    name: 'Orders',
    component: Orders
  },
  {
    path: '/address',
    name: 'Address',
    component: () => import('../views/Address.vue'),
  },
  {
    path: '/addAddress',
    name: 'AddAddress',
    component: () => import('../views/AddAddress.vue'),
  },
  {
    path: '/editAddress',
    name: 'EditAddress',
    component: () => import('../views/EditAddress.vue'),
  },
  {
    path: '/payment',
    name: 'Payment',
    component: () => import('../views/Payment.vue'),
  },
  {
    path: '/personal',
    name: 'Personal',
    component: Personal
  },{
    path: '/updatePersonal',
    name: 'UpdatePersonal',
    component: () => import('../views/UpdatePersonal.vue'),
  },{
    path: '/cartList',
    name: 'CartList',
    component: () => import('../views/CartList.vue'),
  },{
    path: '/evaluation',
    name: 'Evaluation',
    component: () => import('../views/Evaluation.vue'),
  },{
    path: '/orderList',
    name: 'OrderList',
    component: () => import('../views/OrderList.vue'),
  },
]
  


const router = createRouter({
  history: createWebHashHistory(),
  routes
})

//路由守卫：
router.beforeEach(function(to,from,next){
  //获得登录用户对象
  let account = sessionStorage.getItem('account')
  //用户不进行登录的情况下可以访问这些路径
  if(!(to.path=='/' || to.path=='/index' || to.path=='/businessList' || to.path=='/login' || to.path=='/register')){
    if(account==null){
      router.push('/login')
    }
  }
  next();
})
  
export default router