<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	session.setAttribute("appPath", path);
%>
<!DOCTYPE html>
<html>
	<head>
		<title>没有权限</title>
	</head>
	<body>
		没有权限
	</body>
</html>