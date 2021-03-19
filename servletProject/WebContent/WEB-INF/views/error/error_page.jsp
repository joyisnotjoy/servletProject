<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%> 
<%
request.setAttribute("exception", exception);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error page...</title>
</head>
<body>
<div class="container">
<h1>Error Page...</h1>
<div class="panel panel-default">
  <div class="panel-heading">에러 메시지</div>
  <div class="panel-body">${exception.message}</div>
</div>
	
</div>
</body>
</html>