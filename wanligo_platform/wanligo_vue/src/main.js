import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './style.css'
import App from './App.vue'
import router from './router'
import 'font-awesome/css/font-awesome.min.css'

//Vue.use(router)
//createApp()替代 new Vue()
createApp(App).use(router).use(ElementPlus).mount('#app')
