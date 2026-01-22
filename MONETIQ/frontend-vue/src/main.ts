import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'
import { createPinia } from 'pinia'
import '@fortawesome/fontawesome-free/css/all.css'

const app = createApp(App)
const pinia = createPinia()

//za debug
window.$pinia = pinia

app.use(router)
app.use(pinia)
app.mount('#app')
