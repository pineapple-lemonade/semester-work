<#include "base.ftl">
<html>

<#macro title>
    <title>Sign Up</title>
    <link rel="shortcut icon" href="/files/img_3.png" type="image/png">
</#macro>
<style>
    @font-face {
        font-family: Helvetica;
        src: url(/fonts/Helvetica.ttf)
    }

    html {
        font-family: Helvetica, system-ui;
    }

    form {
        display: flex;
        flex-direction: column;
    }

    input {
        margin-top: 20px;
    }
</style>




<#macro content>
    <br>
    <h1>Sign Up</h1>
    <br>
    <form action="/signUp" method="post">

        <p class="lead">
            Nickname:
            <input name="userNick" type="text" placeholder="NickName"/>
        </p>
        <p class="lead">
            Login:
            <input name="userLogin" type="text" placeholder="Login"/>
        </p>
        <p class="lead">
            Email:
            <input name="userEmail" type="email" placeholder="Email"/>
        </p>
        <p class="lead">
            Password:
            <input name="userPass" type="password" placeholder="Password"/>
        </p>
        <input type="submit" value="Sign Up">
        <br>
        <br>
    </form>
</#macro>
</html>
