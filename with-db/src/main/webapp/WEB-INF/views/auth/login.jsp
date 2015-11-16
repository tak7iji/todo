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

        ${SPRING_SECURITY_LAST_EXCEPTION}
        <c:if test="${param.error}">
            <div id="has_error">
	            <t:messagesPanel messagesType="error"
	                messagesAttributeName="SPRING_SECURITY_LAST_EXCEPTION" />
            </div>
        </c:if>

        <form:form action="${pageContext.request.contextPath}/authenticate">
            <table>
                <tr>
                    <td><label for="j_username">User:</label></td>
                    <td><input type="text" id="j_username"
                        name="j_username" value='user1'>(demo)</td>
                </tr>
                <tr>
                    <td><label for="j_password">Password:</label></td>
                    <td><input type="password" id="j_password"
                        name="j_password" value="demo" />(demo)</td>
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