<#import "parts/common.ftlh" as c>
<#import "parts/forms.ftlh" as l>
<@c.page>
    <div>
        <@l.filter></@l.filter>
        <div>
            <@l.add></@l.add>
        </div>

        <div class="card-columns">
            <#list messages as message>
                <div class="card my-3">
                    <div class="card-img-top">
                        <#if message.filename??>
                            <img src="/img/${message.filename}">
                        </#if>
                    </div>
                    <div class="m-3">
                        <span>${message.text}</span>

                        <i>${message.tag}</i>
                    </div>
                    <div class="card-footer text-muted">
                        ${message.authorName}
                    </div>
                </div>
            <#else >
                No messages
            </#list>
        </div>
    </div>
</@c.page>
