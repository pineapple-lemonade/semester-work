<#include "base.ftl">
<html>
<#macro title>Profile</#macro>
<style>
    @font-face {
        font-family: Helvetica;
        src: url(/fonts/Helvetica.ttf)
    }

    html {
        font-family: Helvetica, system-ui;
    }

    .container {
        position: fixed;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }

    .avatar {
        width: 200px;
        height: 200px;
        border-radius: 50%;
        border: 2px solid black;
        object-fit: cover;
        display: block;
        margin: 20px auto;
    }

    .button_load {
        margin-top: 10px;
        margin-bottom: 40px;
    }

</style>

<#macro content>
    <body>
    <div class="container">
        <h1>Your Profile</h1>
            <#if user.avatarUrl?has_content>
                <img class="avatar" alt="IMAGE" src="${user.avatarUrl}"/>
            <#else>
                <img class="avatar" alt="IMAGE" src="/files/img.png"/>
            </#if>

        <form action="/upload" method="post" enctype="multipart/form-data" class="button_load">
            <input type="file" name="avatar" accept=".jpg, .png, .jpeg">
            <input type="submit" value="Upload" name="upload">
        </form>
        <#if user?has_content>
        <p><b>Nickname:</b> ${user.nick}
        </p>
        <p><b>Login:</b> ${user.login}
        </p>
        <p><b>Email:</b> ${user.email}
        </p>
        </#if>
        <form action="/profile" method="post">
            <input type="submit" value="Sign Out" name="exit">
        </form>
    </div>
    </body>
</#macro>
</html>
