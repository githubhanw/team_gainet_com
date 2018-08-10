<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	session.setAttribute("appPath", path);
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>没有发现</title>
	</head>
	<body>
		没有发现
	</body>
</html>