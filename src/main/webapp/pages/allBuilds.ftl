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
            Search for title:<br>
            <input name="title" type="text"/><br>
        </p>

        <br>
        <p class="lead" style="margin-right: 1000px;">
            <input type="submit" value="Search">
        </p>
    </form>

    <br>

    <#if builds??>
        <#if builds?has_content>
            <#list builds as build>

                    <div class="alert alert-dark" role="alert">
                        <a href="/buildInfo?id=${build.id}">
                            <h3>${build.title}</h3>
                        </a>
                        <div>${build.text}</div>
                        <br>
                        <div><small class="text-muted">By: ${build.userNick} At: ${build.data}</small></div>
                        <div><small class="text-muted"><strong>Build Id:</strong> ${build.id}</small></div>
                    </div>
            </#list>

        <#else>
            <p class="lead">No builds that match these requirements!</p>
        </#if>
    </#if>

</#macro>