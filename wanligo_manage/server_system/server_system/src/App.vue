<template>
      <!-- 路由出口 -->
      <router-view></router-view>
</template>
<script>
  export default{
    data(){
      return{
        num:0,
      } 
    },
    watch:{
      //监听路由，浏览器刷新（直接从路由对象中取出 选项卡数据 创建一个选项卡）
      $route(to,from){
        if(to.path != '/login' && to.path != '/'){
          let tab = {
            name: to.name,
            icon: to.meta.icon,
            title: to.meta.title,
          }
          this.$store.commit("ADD_TABS",tab)
        }
      }
    }
    // beforeCreate(){
    //   console.log('beforeCreate创建之前钩子函数')
    // },
    // created(){
    //   console.log('Created创建完毕')
    // },
    // beforeMount(){
    //   console.log('beforeMount挂载之前')
    // },
    // mounted(){
    //   console.log('mounted挂载完毕')
    // }
  }
</script>
<style >

html,body,#app{
  height: 100%;
  padding: 0;
  margin: 0 ;
  font-size: 14px;
  font-family: 'Microsoft Yahei';
  color: #494949;
  text-align: left;
}
/**
上传电影图片的添加框样式
*/
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.dialog-height {
  height: 90%;
  overflow: auto;
  margin-top: 5vh !important;
}
</style>
