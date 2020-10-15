<#import "parts/common.ftlh" as c>
<#import "parts/forms.ftlh" as l>
<#import "parts/messageEditor.ftlh" as me>
<#include "parts/security.ftlh">
<@c.page>
    <h3>${userChannel.username}</h3>
    <div>${type}</div>
    <ul class="list-group">
        <#list users as user>
            <li class="list-group-item">
                <a href="/user/messages/${user.id}">${user.getUsername()}</a>
            </li>
        </#list>

    </ul>

</@c.page>
