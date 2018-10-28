<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Index</title>
</head>
<body>
Welcome: <shiro:principal></shiro:principal>
<br/>
Authenticated:<shiro:authenticated>yes</shiro:authenticated>
<br/>
User:<shiro:user></shiro:user>
<br/>
</body>
</html>