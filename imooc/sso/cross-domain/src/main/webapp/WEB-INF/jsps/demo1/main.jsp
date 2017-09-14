<%--
  Created by IntelliJ IDEA.
  User: tingkl
  Date: 2017/9/11
  Time: 下午12:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
demo1.jsp
${cookie.token.value}
<c:forEach var="url" items="${hiddenUrl}">
    <iframe src="${url}?token=${token}" width="0px" height="0px" style="display: none"></iframe>
</c:forEach>
</body>
</html>
