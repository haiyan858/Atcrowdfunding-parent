<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
ERROR.
获取发生的错误!!!<br>

简短的错误描述：<br>
<%= exception.getMessage()%>

<br>
详细的错误描述如下：<br>
<%= exception.toString()%>
</body>
</html>