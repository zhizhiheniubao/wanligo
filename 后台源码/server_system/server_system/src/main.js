import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import echarts from 'echarts'
//导入element-ui
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

//导入axios
import myaxios from '@/api'
//axios对象就是在 任何组件中进行Ajax请求对象

import qs from 'qs'

//导入common.js中封装的WebStorage的方法
import {setSessionStorage,
      getSessionStorage,
      removeSessionStorage,
      setLocalStorage,
      getLocalStorage,
      removeLocalStorage
} from './common'

import filters from './filters'

//注册全局过滤器
// Object.keys(便利对象).forEach(循环代码)
Object.keys(filters).forEach((key,index) => {
  Vue.filter(key,filters[key])
})

//将axios对象挂载到Vue实例中
Vue.prototype.$axios = myaxios
Vue.prototype.$echarts = echarts
Vue.prototype.$qs = qs

Vue.prototype.$setSessionStorage=setSessionStorage
Vue.prototype.$getSessionStorage=getSessionStorage
Vue.prototype.$removeSessionStorage=removeSessionStorage
Vue.prototype.$setLocalStorage=setLocalStorage
Vue.prototype.$getLocalStorage=getLocalStorage
Vue.prototype.$removeLocalStorage=removeLocalStorage

Vue.config.productionTip = false
Vue.use(Element)


//引入mock.js   只要编写这个代码,mock生效,如果注释掉,mock失效
// require('./mock.js')

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
