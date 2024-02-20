export default{
    fmtDateTime:(value) =>{
        let date = new Date(value)
        let y = date.getFullYear()
        let M = (date.getMonth() + 1).toString().padStart(2,'0')
        let d = (date.getDate()).toString().padStart(2,'0')

        let h = date.getHours().toString().padStart(2,'0')
        let m = date.getMinutes().toString().padStart(2,'0')
        let s = date.getSeconds().toString().padStart(2,'0')

        return `${y}年${M}月${d}日 ${h}:${m}:${s}`
    },
    fmtDate:(value) =>{
        let date = new Date(value)
        let y = date.getFullYear()
        let M = (date.getMonth() + 1).toString().padStart(2,'0')
        let d = (date.getDate()).toString().padStart(2,'0')

        return `${y}年${M}月${d}日`
    }
}