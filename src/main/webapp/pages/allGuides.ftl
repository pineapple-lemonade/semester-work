<#ftl encoding="UTF-8">
<#include 'base.ftl'>

<#macro title>
    <title>Все рецепты</title>
    <link rel="shortcut icon" href="/files/img_3.png" type="image/png">
</#macro>

<#macro content>
    <br>
    <h1>Все рецепты</h1>
    <br>

    <form action="/allGuides" method="post" novalidate>
        <p class="lead" id="1" style="float: left; margin-right: 50px;">
            Search for title:<br>
            <input name="title" type="text"/><br>
        </p>

        <br>
        <p class="lead" style="margin-right: 1000px;">
            <input type="submit" value="Find">
        </p>
    </form>

    <br>
        <#if guidesList?has_content>
            <#list guidesList as guide>
                    <div class="alert alert-dark" role="alert">
                        <a href="/guideInfo?id=${guide.id}">
                            <h3>${guide.title}</h3>
                        </a>
                        <br>
                        <img src="${guide.photoUrl}" width="665" height="350">
                        <br>
                        <div>${guide.text}</div>
                        <br>
                        <div><small class="text-muted">By: ${guide.userNick} At: ${guide.data}</small></div>
                        <div><small class="text-muted"><strong>Guide Id:</strong> ${guide.id}</small></div>
                    </div>
            </#list>

        <#else>
            <p class="lead">No guides that match these requirements!</p>
        </#if>

</#macro>