<#include "security.ftlh">
<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> User Name :</label>
            <div class="col-sm-10">
                <input type="text" class="form-control ${(usernameError??)?string('is-invalid', '')}"
                       value="<#if user??>${user.username}</#if>" placeholder="User name" name="username"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password :</label>
            <div class="col-sm-10">
                <input type="password" class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       placeholder="Password" name="password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label"> Password :</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control ${(password2Error??)?string('is-invalid', '')}"
                           placeholder="Retype password" name="password2"/>
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label"> Email :</label>
                <div class="col-sm-10">
                    <input type="email" class="form-control ${(emailError??)?string('is-invalid', '')}"
                           value="<#if user??>${user.email}</#if>" placeholder="enter email" name="email"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="g-recaptcha" data-sitekey="6LfKTdQZAAAAAKTfinUuZ-GVZBNSE3bUSlEkJ-Ib"></div>
                <#if captchaError??>
                    <div class="alert alert-danger" role="alert">
                        ${captchaError}
                    </div>
                </#if>
            </div>
        </#if>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm>
            <a href="/registration">Add new user</a>
        </#if>
        <button class="btn btn-primary" type="submit">
            <#if isRegisterForm> Create<#else>Sign In</#if>
        </button>

    </form>
</#macro>
<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit"><#if user??>Sign Out<#else>Log in</#if>
        </button>
    </form>
</#macro>

<#macro filter>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input type="text" name="tag" value="${RequestParameters.tag!""}" placeholder="Search by tag"/>
                <button type="submit" class="btn btn-primary ml-2">Search</button>
            </form>
        </div>
    </div>
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
