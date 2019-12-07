<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="myprefix" uri="WEB-INF/testetag.tld"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Insert title here</title>
</head>
<body>	
	<h1>Página index.jsp</h1>
	<br><br>
	
	<form action="cabecalho.jsp" method="post">
		<% session.setAttribute("user", "javaavancado"); %>
		<input type="text" id="nome" name="nome">
		<br>
		<input type="text" id="ano" name="ano">
		<br>
		<input type="submit" value="testar">
	</form>
</body>
</html>
