<!DOCTYPE html>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://terasoluna.org/functions" prefix="f"%>
<%@ taglib uri="http://terasoluna.org/tags" prefix="t"%>
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
        <a href="<spring:url value="/todo/list" />">Back</a>
    </div>
    <hr />
</body>
</html>