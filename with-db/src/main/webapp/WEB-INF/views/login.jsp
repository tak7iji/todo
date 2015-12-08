<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Home</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/styles.css">
</head>
<body>
    <div id="login">
        <c:if test="${param.error}">
            <t:messagesPanel
                messagesAttributeName="SPRING_SECURITY_LAST_EXCEPTION"/>
        </c:if>
        <form:form action="${pageContext.request.contextPath}/authentication" method="post">
            <label for="username">Username</label>
            <input type="text" id="username" name="j_username"><br>
            <label for="password">Password</label>
            <input type="password" id="password" name="j_password"><br>
            <input type="submit" value="Login">
        </form:form>
    </div>
</body>
</html>
