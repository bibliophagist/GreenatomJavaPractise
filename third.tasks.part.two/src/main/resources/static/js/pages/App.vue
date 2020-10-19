<template>
    <v-app>
        <v-toolbar app>
            <v-toolbar-title>Third Task Part Two</v-toolbar-title>
            <v-spacer></v-spacer>
            <span v-if="profile"> {{profile.name}}</span>
            <v-btn v-if="profile" icon href="/logout">
                <v-icon>mdi-exit-to-app</v-icon>
            </v-btn>
        </v-toolbar>
        <v-content>
            <v-container v-if="!profile">Please log in via <a href="/login">Google</a>
            </v-container>
            <v-container v-if="profile">
                <messages-list :messages="messages"/>
            </v-container>
        </v-content>


    </v-app>
</template>

<script>
    import MessagesList from 'components/messages/MessageList.vue'
    import {addHandler} from "../utils/ws";
    import {getIndex} from "../utils/collections";

    export default {
        components: {
            MessagesList
        },
        data() {
            return {
                messages: frontendData.messages,
                profile: frontendData.profile
            }
        },
        created() {
            addHandler(data => {
                let index = getIndex(this.messages, data.id);
                if (index > -1) {
                    this.messages.splice(index, 1, data)
                } else {
                    this.messages.push(data)
                }
            })
        }
    }
</script>

<style>

</style>
