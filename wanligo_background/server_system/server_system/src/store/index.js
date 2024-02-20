import Vue from 'vue'
import Vuex from 'vuex'

import menus from './modules/menus'

//共享数据的地方，任何地方都能使用
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token:''
  },
  mutations: {
    //存储token到Vuex中
    SET_TOKEN:(state,tokenValue) => {
      state.token = tokenValue
      //存储到LocalStorage中
      localStorage.setItem('TOKEN',JSON.stringify(tokenValue))
    }
  },
  modules:{
    //大型项目使用，state中数据很多，可以分类存储到不同的modules
    menus,
  }
})
