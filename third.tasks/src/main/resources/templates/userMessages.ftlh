<#import "parts/common.ftlh" as c>
<#import "parts/forms.ftlh" as l>
<#import "parts/messageEditor.ftlh" as me>
<#include "parts/security.ftlh">
<@c.page>
    <#if isCurrentUser>
        <@me.messageForm "/user/messages/${currentUserId}" "Edit message"></@me.messageForm>
    </#if>
    <#include "parts/messageList.ftlh">
</@c.page>
