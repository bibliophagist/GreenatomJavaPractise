<#import "parts/common.ftlh" as c>
<#import "parts/forms.ftlh" as l>
<#import "parts/messageEditor.ftlh" as me>
<@c.page>
    <div>
        <@l.filter></@l.filter>
        <@me.messageForm "/add" "Save message"></@me.messageForm>

        <#include "parts/messageList.ftlh">
    </div>
</@c.page>
