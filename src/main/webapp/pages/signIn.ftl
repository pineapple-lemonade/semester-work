<#include "base.ftl">

<html>
<#macro title>Sign In</#macro>
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

</style>
<#macro content>
    <body>
    <div class="container">
        <form action="/signIn" method="post">
            <input name="userLogin" type="text" placeholder="Login"/>
            <input name="userPass" type="password" placeholder="Password"/>
            <input type="submit" value="Enter">
        </form>
    </div>
    </body>
</#macro>
</html>
