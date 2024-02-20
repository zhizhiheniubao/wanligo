<template>
    <div class="wrapper">
        <h1>{{ welcome }}</h1>
        <Child message="BAIDU.COM" @childMessage="getMessage"></Child>
    </div>
</template>

<script setup>
//导入computed
import Child from '../components/Child.vue'
import { computed, reactive , ref,toRefs } from "vue";

   
//参数param就是子组件传递给父组件 万里学院
function getMessage(param){
    state.welcome = param
}

//let count= ref(10); //对象.value=10
const state = reactive({
    welcome:''
})
       

</script>
<style >
</style>