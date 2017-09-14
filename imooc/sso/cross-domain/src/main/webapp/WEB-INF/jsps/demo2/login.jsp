<%--
  Created by IntelliJ IDEA.
  User: tingkl
  Date: 2017/9/11
  Time: 上午10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false"%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<center>
    <h1>请登录</h1>
    <form action="/demo2/doLogin" method="post">
        <span>用户名:</span><input type="text" name="username"/>
        <span>密码:</span><input type="password" name="password"/>
        <input type="submit"/>
    </form>
</center>
</body>
</html>
