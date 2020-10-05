<#import "parts/common.ftlh" as c>
<#import "parts/forms.ftlh" as l>
<@c.page>
    <div>
        <div>
            <@l.logout></@l.logout>
            <span> <a href="/user"> User list</a></span>
        </div>
        <div>
            <@l.add></@l.add>
        </div>

        <div> Список сообщений</div>
        <@l.filter></@l.filter>

        <#list messages as message>
            <div>
                <b>${message.id}</b>
                <span>${message.text}</span>
                <i>${message.tag}</i>
                <strong>${message.authorName}</strong>
            </div>
        <#else >
            No messages
        </#list>
    </div>
</@c.page>
