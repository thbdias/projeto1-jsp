<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Insert title here</title>
</head>
<body>
	<h1>Bem vindo ao curso de JSP</h1>
	
	<!-- indica qual pagina ir� tratar os erros -->
	<%@ page errorPage="receber-nome.jsp" %>
	
	<!-- provocando erro -->
	<%= 100/0 %>
	
</body>
</html>
