<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Home</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/styles.css">
</head>
<body>
    <div id="wrapper">
        <h1>Hello world!</h1>
        <p>Hi, <sec:authentication property="principal.username" />!</p>
        <sec:authorize access="hasRole('ROLE_USER')">
            <p>This screen is for ROLE_USER</p>
        </sec:authorize>
        <p>The time on the server is ${serverTime}.</p>
        <a href="<spring:url value='todo/list'/>">Todo list</a>
        <p>
            <form:form action="${pageContext.request.contextPath}/logout">
                <button>Logout</button>
            </form:form>
        </p>
    </div>
</body>
</html>
