<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
index

<%--<a href="${pageContext.request.contextPath }/test.do">test</a>--%>
<%--<a href="${APP_PATH}/test.do">test</a>--%>

<%--经过控制器跳转--%>
<jsp:forward page="${APP_PATH}/index.htm"></jsp:forward>

<p>${APP_PATH}</p>
</body>
</html>