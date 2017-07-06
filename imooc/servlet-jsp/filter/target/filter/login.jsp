<%--
  Created by IntelliJ IDEA.
  User: tingkl
  Date: 2016/12/13
  Time: 下午3:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/servlet/LoginServlet" method="post">
用户名:<input type="text" name="username">
密码:<input type="password" name="password">
<input type="submit" value="提交">
</form>
</body>
</html>
