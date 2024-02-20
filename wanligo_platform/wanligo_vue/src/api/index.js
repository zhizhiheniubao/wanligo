import qs from 'qs'
import axios from 'axios'

const axiosInstance = axios.create({
    //服务器端 目前不能设置跨域，跨域设置Vue前端，地址不能写http://localhost:10100
    baseURL: '/sys',  //使用前端代理拦截/sys，将地址转发 http://localhost:10100
    timeout:50000 
});

//封装get请求方法
//get请求使用RESTFul传递参数，参数就是在URL地址里。
//http://localhost:10100/category/info/22
export function get(url){
   return axiosInstance.get(url);
}

//对axios返回的response拦截进行状态处理：
axiosInstance.interceptors.response.use(
   response => {
     let Result = response.data
     if (Result.code == 1) {
       return response 
     } else {
       alert(Result.message ? Result.message : '系统异常');
       return Promise.reject(response.data.message)
     }
   },
   error => {  
     alert(error.message)
     return Promise.reject(error)
   }
 )

//封装post请求方法 参数：url，params传递参数, isJSON 参数格式
export function post(url,params,isJSON){
   let headers = {
    'Content-Type':'application/json'
   }
    if(!isJSON){
        //传递参数形式
        params = qs.stringify(params)
        headers['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
   }
   return axiosInstance.post(url,params,{headers})
}