<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Todo List</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css" type="text/css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/vendor/bootstrap-3.0.0/css/bootstrap.css"
    type="text/css" media="screen, projection">
</head>
<body>
    <h1>Todo List</h1>
    <div id="todoForm">
        <t:messagesPanel />

        <form:form
           action="${pageContext.request.contextPath}/todo/create"
            method="post" modelAttribute="todoForm">
            <form:input path="todoTitle" />
            <form:errors path="todoTitle" cssClass="text-error" />
            <input type="hidden" name="page" value="${Double.valueOf(Math.ceil((todos.totalElements+1) / todos.size)).intValue() - 1}"/>
            <form:button>Create Todo</form:button>
        </form:form>
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
                                <input type="hidden" name="page" value="${todos.number}"/>
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
                        <input type="hidden" name="page" value="${(todos.totalElements-1) % todos.size == 0 ? ((todos.number > 0) ? (todos.number) - 1 : todos.number) : todos.number}"/>
                        <form:button>Delete</form:button>
                    </form:form>
                </li>
            </c:forEach>
        </ul>
    </div>
    <t:pagination page="${todos}" outerElementClass="pagination" 
                  firstLinkText="${todos.number == 0 ? '' : '<<'}" 
                  previousLinkText="${todos.number == 0 ? '' : '<'}" 
                  nextLinkText="${todos.number == (Math.ceil(todos.totalElements / todos.size) - 1) ? '' : '>'}" 
                  lastLinkText="${todos.number == (Math.ceil(todos.totalElements / todos.size) - 1) ? '' : '>>'}" />
</body>
</html>