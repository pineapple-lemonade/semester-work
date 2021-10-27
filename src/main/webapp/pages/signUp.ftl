
<html>

<body>
<title>Sign Up</title>
</body>
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

    form {
        display: flex;
        flex-direction: column;
    }

    input {
        margin-top: 20px;
    }
</style>

    <body>
    <div class="container">
        <form action="/signUp" method="post">
            <input name="userNick" type="text" placeholder="NickName"/>
            <input name="userLogin" type="text" placeholder="Login"/>
            <input name="userEmail" type="email" placeholder="Email"/>
            <input name="userPass" type="password" placeholder="Password"/>
            <input type="submit" value="Save and Enter">
        </form>
    </div>
    </body>
</html>
