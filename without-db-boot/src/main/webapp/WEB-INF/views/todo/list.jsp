<!DOCTYPE html>
<%@ include file="/WEB-INF/views/common/include.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="title.todo.list"/></title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css" type="text/css">
<script>
function clickButton(button) {
    button.disabled=false;
    button.parentNode.submit();
}
</script>
<sec:authentication property="principal.account" var="account" />
</head>
<body>
    <h1><spring:message code="title.todo.list"/></h1>
    <div id="logout">
        <p>Login User: ${f:h(account.firstName)} ${f:h(account.lastName)}</p>
        <form:form action="${pageContext.request.contextPath}/logout">
            <button type="submit">Logout</button>
       </form:form>
       <p>
    </div>
    <div id="todoForm">
        <t:messagesPanel />

        <form:form
           action="${pageContext.request.contextPath}/todo/create"
            method="post" modelAttribute="todoForm">
            <form:input path="todoTitle" />
            <form:errors path="todoTitle" cssClass="text-error" />
            <form:button id="create" onclick="clickButton(this)"><spring:message code="label.td.list.create"/></form:button>
        </form:form>
    </div>
    <hr />
    <div id="todoList">
        <ul>
            <c:forEach items="${todos}" var="todo">
                <li>
                    <c:choose>
                        <c:when test="${todo.finished}">
                            <span class="strike">
                            ${f:h(todo.todoTitle)}
                            </span>
                        </c:when>
                        <c:otherwise>
                            ${f:h(todo.todoTitle)}
                            <form:form
                                action="${pageContext.request.contextPath}/todo/finish"
                                method="post"
                                modelAttribute="todoForm"
                                cssStyle="display: inline-block;">
                                <form:hidden path="todoId"
                                    value="${f:h(todo.todoId)}" />
                                <form:button onclick="clickButton(this)"><spring:message code="label.td.list.finish"/></form:button>
                            </form:form>
                         </c:otherwise>
                    </c:choose>
                    <form:form
                        action="${pageContext.request.contextPath}/todo/delete"
                        method="post" modelAttribute="todoForm"
                        cssStyle="display: inline-block;">
                        <form:hidden path="todoId"
                            value="${f:h(todo.todoId)}" />
                        <form:button onclick="clickButton(this)"><spring:message code="label.td.list.delete"/></form:button>
                    </form:form>
                </li>
            </c:forEach>
        </ul>
    </div>
</body>
</html>