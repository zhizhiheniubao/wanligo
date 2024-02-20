<template>
  <!--v-model是将选中的选项卡名字的name赋值给它-->
  <el-tabs v-model="TabsValue" type="card" closable  @tab-click="clickTab" @tab-remove="removeTab">
    <el-tab-pane v-for="item in TabsList" :key="item.name" :name="item.name">
      <span slot="label"><i :class="item.icon"></i> {{ item.title }}</span>
    </el-tab-pane>
  </el-tabs>
</template>

<script>

export default {
  data() {
    return {

    }
  },
  computed: {
    //选项卡的数据在随时变化，定义在计算属性中
    TabsList:{
      get(){
        return this.$store.state.menus.TabsList;
      },
      set(val){
        this.$store.state.menus.TabsList = val
      }
    },
    TabsValue:{
      get(){
        return this.$store.state.menus.TabsValue;
      },
      set(val){
        this.$store.state.menus.TabsValue = val
      }
    },
  },
  methods:{
    //点击选项卡，页面切换
    //菜单的name === 选项卡name === 路由name
    clickTab(target){
      this.$router.push({name:target.name}).catch(e=>{})
    },
    removeTab(targetName){
      //参数targetName: 要删除选项卡的name
      //首页不能删除
      if(targetName == 'Index'){
        return
      }
      let Tabs = this.TabsList //取到所有的选项卡
      let activeName = this.TabsValue //取到当前激活的选项卡name
      
      if(activeName === targetName){ //删除的正是当前激活的选项卡
        Tabs.forEach((tab,index) => {
          ///数组中当前循环的这个选项卡name 等于 要删除的选项卡name
          if(tab.name === targetName){
            //下一个默认被激活
            let nextTab = Tabs[index + 1] || Tabs[index - 1]
            if (nextTab){
              activeName = nextTab.name
            }
          }
        })
      }
      //将获得activeName 激活选项卡名字，赋值回计算属性
      this.TabsValue = activeName     
      this.TabsList = Tabs.filter( tab => tab.name !== targetName);
      this.$router.push({name:activeName}).catch(e=>{})
    }
  }
}
</script>

<style scoped>
</style>
