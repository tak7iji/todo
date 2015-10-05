<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="title.todo.list"/></title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css" type="text/css">
<script>
function clickButton(button) {
    button.disabled=true;
    button.parentNode.submit();
}
</script>
</head>
<body>
    <h1><spring:message code="title.todo.list"/></h1>
    <div id="todoForm">
        <form:form
           action="${pageContext.request.contextPath}/todo/confirm"
            method="post" modelAttribute="todoForm">
            ${todoForm.todoTitle}
            <input type="hidden" name="todoTitle" value="${todoForm.todoTitle}"/>
            <form:button onclick="clickButton(this)"><spring:message code="label.td.list.confirm"/></form:button>
        </form:form>
    </div>
    <hr />
</body>
</html>