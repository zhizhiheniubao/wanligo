import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: { //主要是加上这段代码
    host: 'localhost',
    port: 3000,
    proxy: {
      '/sys': {
        target: 'http://localhost:6868',	//实际请求地址
        changeOrigin: true,   
        rewrite: (path) => path.replace(/^\/sys/, '')
      },
    }
  } 
})
