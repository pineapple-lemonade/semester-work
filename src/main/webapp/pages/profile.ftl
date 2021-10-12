<#include 'base.ftl'>

<#macro title>
    <title>Личный кабинет</title>
<#--    <link rel="shortcut icon" href="static/img/icon.png" type="image/png">-->
</#macro>
<#macro header>Hold on</#macro>
<#macro content>
    <br>
    <h1>Ваш личный кабинет</h1>
    <br>
    <table>
        <tr>
<#--            <td><img alt="user_img" src="/static/img/{{p}}" width="50" height="50" class="rounded-circle"></td>-->
            <td><p class="lead"><strong><#if user?has_content>${user.nick}</#if></strong></p></td>
<#--            <td><p class="lead"><em>${user.firstName}  ${user.secondName}</em></p></td>-->
        </tr>
    </table>
    <form action="" method="post" novalidate enctype="multipart/form-data">
        Смена аватарки
        <table>
            <tr>
                <td>Поменять аватарку</td>
                <td>Подтвердить</td>
            </tr>
        </table>
    </form>

    <br>
    <table>
        <tr>
            <td><p class="lead">Ваша почта: ${user.email}</p></td>
            <td><p class="lead">Ваш id: 0</p></td>
        </tr>
        <tr>
            <td><p class="lead">Добавить рецепт</p></td>
            <td><p class="lead"><a href="/add_theory">Добавить рецепт</a></p></td>
        </tr>
        <tr>
            <td><p class="lead"><a href="/logout">Выйти</a></p></td>
            <td><p class="lead"><a href="/delete">Удалить аккаунт</a></p></td>
        </tr>
    </table>
    <br>
<#--    <p class="lead"><a href="/read_my_theories">Мои рецепты</a></p>-->
    <br>
</#macro>