<#ftl encoding="UTF-8">

<#include 'base.ftl'>

<#macro title>
    <title>All builds</title>
    <link rel="shortcut icon" href="/files/img_3.png" type="image/png">
</#macro>

<#macro content>
    <br>
    <h1>All builds</h1>
    <br>

    <form action="/allBuilds" method="post" novalidate>
        <p class="lead" id="1" style="float: left; margin-right: 50px;">
            Search for nick:<br>
            <input name="title" type="text"/><br>
        </p>

        <br>
        <p class="lead" style="margin-right: 1000px;">
            <input type="submit" value="Search">
        </p>
    </form>

    <br>

    <#if users??>
        <#if users?has_content>
            <#list users as user>
                    <a href="/userInfo?id=${user.id}">
                        <div class="alert alert-dark" role="alert">
                            <br>
                            <#if user.avatarUrl?has_content>
                                <img src="${user.avatarUrl}" width="150" height="150" class="hover-square">
                            <#else>
                                <img src="/files/img.png" width="150" height="150" class="hover-square">
                            </#if>
                            <strong class="text-muted">${user.nick}</strong>
                        </div>
                    </a>

            </#list>

        <#else>
            <p class="lead">No users that match these requirements!</p>
        </#if>
    </#if>

</#macro>