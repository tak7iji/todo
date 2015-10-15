<!DOCTYPE html>
<html>
<head>
<title>Login Page</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/styles.css">
</head>
<body>
    <div id="wrapper">
        <h3>Login with Username and Password</h3>

        <!-- (1) -->
        <c:if test="${param.error}">
            <!-- (2) -->
            <t:messagesPanel messagesType="error"
                messagesAttributeName="SPRING_SECURITY_LAST_EXCEPTION" />
        </c:if>

        <!-- (3) -->
        <form:form action="${pageContext.request.contextPath}/authenticate">
            <table>
                <tr>
                    <td><label for="j_username">User:</label></td>
                    <td><input type="text" id="j_username"
                        name="j_username" value='demo'>(demo)</td><!-- (4) -->
                </tr>
                <tr>
                    <td><label for="j_password">Password:</label></td>
                    <td><input type="password" id="j_password"
                        name="j_password" value="demo" />(demo)</td><!-- (5) -->
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td><input name="submit" type="submit" value="Login" /></td>
                </tr>
            </table>
        </form:form>
    </div>
</body>
</html>