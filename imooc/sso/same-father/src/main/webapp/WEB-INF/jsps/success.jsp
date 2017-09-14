<%--
  Created by IntelliJ IDEA.
  User: tingkl
  Date: 2017/9/11
  Time: 上午11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
success
${gotoUrl}
${cookie.ssocookie.value}
<script>
    var a = '${gotoUrl}';
    setTimeout(function () {
        window.location.href = a;
    }, 2000);
</script>
</body>
</html>
