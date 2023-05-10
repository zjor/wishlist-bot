import './assets/main.css'

import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import '@mdi/font/css/materialdesignicons.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'

const app = createApp(App)

app.use(createPinia())


const vuetify = createVuetify({
  components,
  directives,
  icons: {
    defaultSet: 'mdi' // This is already the default value - only for display purposes
  }
})
app.use(vuetify)

app.mount('#app')
