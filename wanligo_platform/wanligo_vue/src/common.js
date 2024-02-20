//Web存储的操作进行封装:
//存储数据到sessionStorage
export function setSessionStorage(key, value) {
  sessionStorage.setItem(key, JSON.stringify(value)) //value可能是一个 JSON对象，存储需要将JSON转换字符串。
}
//获取sessionStorage中的数据
export function getSessionStorage(key) {
  let str = sessionStorage.getItem(key) //存储时，转换为字符串，所以取出数据也是字符串
  if (str == '' || str == null || str == 'null' || str == undefined) {
    return null
  } else {
    //取出字符串 再转化为JSON对象
    return JSON.parse(str)
  }
}
//删除sessionStorage中的数据
export function removeSessionStorage(key) {
  sessionStorage.removeItem(key)
}

export function setLocalStorage(key, value) {
  localStorage.setItem(key, JSON.stringify(value)) //value可能是一个 JSON对象，存储需要将JSON转换字符串。
}
export function getLocalStorage(key) {
  let str = localStorage.getItem(key) //存储时，转换为字符串，所以取出数据也是字符串
  if (str == '' || str == null || str == 'null' || str == undefined) {
    return null
  } else {
    //取出字符串 再转化为JSON对象
    return JSON.parse(str)
  }
}
export function removeLocalStorage(key) {
  localStorage.removeItem(key)
}
