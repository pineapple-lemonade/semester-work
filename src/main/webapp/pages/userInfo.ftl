<#ftl encoding="UTF-8">
<#include 'base.ftl'>

<#macro title>
    <title>Profile</title>
    <link rel="shortcut icon" href="/files/img_3.png" type="image/png">
</#macro>

<#macro content>

    <#if detailUser?has_content>
        <br>
        <h1>User page</h1>
        <br>
        <table>
            <tr>
                <td>
                    <#if detailUser.avatarUrl?has_content>
                         <img alt="user_img" src="${detailUser.avatarUrl}" width="150" height="150" class="hover-square">
                    <#else>
                        <img alt="user_img" src="/files/img.png" width="150" height="150" class="hover-square">
                </#if>
                </td>
                <td>
                    <table>
                        <tr>
                            <td>
                                <h2>
                                    <strong>${detailUser.nick}</strong>
                                </h2>
                            </td>

                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <br>
        <small class="text-muted" style="font-size:20px"><a href="/chat?id=${detailUser.id}">Написать</a></small>
        <br>
        <br>
    <#else>
        <br>
        <p class="lead"><em>Что-то пошло не так...</em></p>
        <br>
    </#if>

</#macro>