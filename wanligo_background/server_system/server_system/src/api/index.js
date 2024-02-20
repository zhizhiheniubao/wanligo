//配置全局axios的代码文件
import axios from "axios";
import router from "@/router";
import Element from 'element-ui'

//进行全局axios设置，需要定义一个新axios,进行去全局设置
//myaxios就是进行全局设置之后的对象，组件中使用myaxios对象
const myaxios = axios.create({
    timeout:5000,//设置超时时间
})

//进行基础路径设置：基础路径就请求Java服务器端路径前缀。假设：http://localhost:10001
//目前开发阶段，使用是mockjs，没有地址前缀，后续生产环境中只需要解开
myaxios.defaults.baseURL = '/sys'

//设置axios请求携带token，进行全局设置
//interceptors拦截器（请求前，请求之后）
myaxios.interceptors.request.use(config => {
    //从localStorage取出token，设置到axios的请求头中，这样请求头中就会含有token信息 
    config.headers['TOKEN'] = JSON.parse(localStorage.getItem('TOKEN'))
    return config
})

//对于axios响应状态（包括20000，或者失败）做全局处理
myaxios.interceptors.response.use(response=>{
    let Result = response.data
    if (Result.code == 20000) {
        return response //如果请求成功，就将response对象传递到组件axios请求方法的then（）方法中
    } else {
        //不是20000，那就是各种请求失败错误码，直接进行错误的提示
        Element.Message.error(Result.message ? Result.message : '系统执行异常',{duration:2000})
        return Promise.reject(response.data.message)
    }
    },
    error => {
        //error错误不是Java程序返回的，而是HTTP请求错误。404，500
        if(error.response.status == 401){
            //Java服务器上的API接口方法是权限限制，当前用户没有权限访问时，会报401错误
            router.push('/login').catch(e=>{})
            return Promise.reject(error)
        }
        if(error.response.status == 404){
            //如果请求状态为404，找不到访问资源，页面会自动跳转到NotFound.vue
            router.push('/notFound')
            return Promise.reject(error)
        }
        if(error.response.status == 400){
            //如果请求状态为400，就是请求Java服务器端方法方式有问题（错误请求），就是没有按照Java方法定义 要求来 使用vue请求
            error.message = '服务器请求方法错误'
        }
        if(error.response.status == 401){
            //如果状态码为500，Java的代码错误。Vue--->Java报500错误
            error.message = '服务器代码错误'
        }
        Element.Message.error(error.message)
        return Promise.reject(error)
    }
)
export default myaxios