<#macro login path>
    <form action="${path}" method="post">
        <div><label> User Name : <input type="text" name="username"/> </label></div>
        <div><label> Password: <input type="password" name="password"/> </label></div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div><input type="submit" value="Sign In"/></div>
    </form>
</#macro>
<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" value="Sign Out"/>
    </form>
</#macro>

<#macro add>
    <form method="post" action="/add" enctype="multipart/form-data">
        <input type="text" name="text" placeholder="Введите сообщение"/>
        <input type="text" name="tag" placeholder="Тэг">
        <input type="file" name="file">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Добавить сообщение</button>
    </form>
</#macro>

<#macro filter>
    <form method="get" action="/main">
        <input type="text" name="tag" value="${RequestParameters.tag!""}">
        <button type="submit">Найти</button>
    </form>
</#macro>

<#macro editor>
    <form action="/user" method="post">
        <input type="text" value="${user.username}" name="username">
        <#list roles as role>
            <div>
                <label>
                    <input type="checkbox" name="${role}" ${user.roleSet?seq_contains(role)?string("checked", "")}>
                    ${role}
                </label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Save</button>
    </form>
</#macro>
