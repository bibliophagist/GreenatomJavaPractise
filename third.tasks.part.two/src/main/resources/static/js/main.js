import Vue from 'vue'
import Vuetify from "vuetify"
import '@babel/polyfill'
import 'api/resource'
import router from "./router/router"
import App from 'pages/App.vue'
import store from 'store/store'
import {connect} from "./utils/ws"
import 'vuetify/dist/vuetify.min.css'
import * as Sentry from "@sentry/browser"
import {Vue as VueIntegration} from "@sentry/integrations"
import {Integrations} from "@sentry/tracing"

Sentry.init({
    dsn: "https://cf6a95c3a74142de8ceffdd109e92822@o467159.ingest.sentry.io/5493079",
    integrations: [
        new VueIntegration({
            Vue,
            tracing: true,
        }),
        new Integrations.BrowserTracing(),
    ],

    // We recommend adjusting this value in production, or using tracesSampler
    // for finer control
    tracesSampleRate: 1.0,
});

Sentry.configureScope(scope => {
    scope.setUser({
        id: profile && profile.id,
        username: profile && profile.name
    })
})

import '@mdi/font/css/materialdesignicons.css'

if (profile) {
    connect()
}

Vue.use(Vuetify)

new Vue({
    el: '#app',
    store,
    router,
    render: a => a(App)
})
