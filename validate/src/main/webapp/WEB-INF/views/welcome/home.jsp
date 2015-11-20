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
        <p>The time on the server is ${serverTime}.</p>
    </div>
    <div>
        <form:form modelAttribute="validateForm" method="post"
            action="${pageContext.request.contextPath}/validate">
            <form:errors path="*"/><br/>
            High:<form:input path="high"/><br/>
            Middle:<form:input path="middle"/><br/>
            Low:<form:input path="low"/><br/>
            <br/>
            Threshold:<form:input path="threshold"/>
            <br/>
            <form:button>submit</form:button>            
        </form:form>
    </div>
</body>
</html>
