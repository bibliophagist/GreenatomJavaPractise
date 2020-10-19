import Vue from 'vue'
import VueResource from 'vue-resource'
import App from 'pages/App.vue'
import {connect} from "./utils/ws";
import 'vuetify/dist/vuetify.min.css'
import Vuetify from "vuetify"

import '@mdi/font/css/materialdesignicons.css'

if (frontendData.profile) {
    connect();
}
Vue.use(VueResource)
Vue.use(Vuetify, {
    iconfont: 'mdi'
})



new Vue({
    el: '#app',
    render: a => a(App)
})
