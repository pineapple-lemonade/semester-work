<#include "base.ftl">

<html lang="en">
<#macro title>SignUp</#macro>
<#macro header>SignUp</#macro>
<#macro content>
    <body>
    <form action="/signUp" method="post">
        Email:<input type="text" name="userEmail"/>
        <br>
        Nickname:<input type="text" name="userNick"/>
        <br>
        Login:<input type="text" name="userLogin"/>
        <br>
        Password:<input type="password" name="userPass"/>
        <br>
        <input type="submit" value="SignUp"/>
    </form>
    </body>
</#macro>
</html>