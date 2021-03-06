<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Todo List</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css" type="text/css">
<style type="text/css">
.pagination li {
    display: inline;
}

.pagination li>a {
    margin-left: 10px;
}
</style>
</head>
<sec:authentication property="principal.username" var="username"/>
<body>
    <h1>Todo List for ${username}</h1>
    <div id="todoForm">
        <t:messagesPanel />

        <form:form
           action="${pageContext.request.contextPath}/todo/create"
            method="post" modelAttribute="todoForm">
            <form:input path="todoTitle" />
            <form:errors path="todoTitle" cssClass="text-error" />
            <form:button>Create Todo</form:button>
        </form:form>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <form:form
               action="${pageContext.request.contextPath}/todo/deleteAll"
                method="post" modelAttribute="todoForm">
                <form:button>Delete All Todo</form:button>
            </form:form>
        </sec:authorize>
    </div>
    <hr />
    <div id="todoList">
        <ul>
            <c:forEach items="${todos.content}" var="todo">
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
                                <form:button>Finish</form:button>
                            </form:form>
                         </c:otherwise>
                    </c:choose>
                    <form:form
                        action="${pageContext.request.contextPath}/todo/delete"
                        method="post" modelAttribute="todoForm"
                        cssStyle="display: inline-block;">
                        <form:hidden path="todoId"
                            value="${f:h(todo.todoId)}" />
                        <form:button>Delete</form:button>
                    </form:form>
                </li>
            </c:forEach>
        </ul>
    </div>
    <t:pagination page="${todos}" outerElementClass="pagination" />
	<div>
	    <fmt:formatNumber value="${(todos.number * todos.size) + 1}" /> -
	    <fmt:formatNumber value="${(todos.number * todos.size) + todos.numberOfElements}" />
	    <ul>
	       <li>        ${todos.size}
	       <li>        ${todos.number}
	       <li>        ${todos.numberOfElements}
	       <li>        ${todos.totalPages}
	       <li>        ${todos.totalElements}
	    </ul>
	</div>
</body>
</html>