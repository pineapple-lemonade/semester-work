<#include "base.ftl">

<html lang="en">
<#macro title>SignIn</#macro>
<#macro header>SignIn</#macro>
<#macro content>
    <body>
    <form action="/signIn" method="post">
        Login:<input type="text" name="userLogin"/>
        <br>
        Password:<input type="password" name="userPass"/>
        <br>
        <input type="submit" value="SignIn"/>
    </form>
    </body>
</#macro>
</html>