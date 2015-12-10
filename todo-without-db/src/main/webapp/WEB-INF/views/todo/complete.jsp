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
        <t:messagesPanel />
        ${todoForm.todoTitle}
        <a href="<spring:url value="/todo/list" />">Back</a>
    </div>
    <hr />
</body>
</html>