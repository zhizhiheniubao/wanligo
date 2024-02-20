<template>
    <div>我是子组件</div>
    <button @click="send">传参给父组件</button>
</template>
<script>
export default{
    setup(props,context){
        //props 父->子
        //父组件传递message
        console.log('子组件=',props.message); 
        
        function send(){
            //emit触发父组件中 自定义事件。
            context.emit('childMessage',"万里学院")
        } 

        return{
            send,
        }
    },
    props:{
        message: String,
    }

}
</script>
  
 