<jsp:useBean id="calcula" class="beans.BeanCursoJsp" type="beans.BeanCursoJsp" scope="page"/>

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
		nome: <input type="text" id="nome" name="nome" >
		<br>
		ano: <input type="text" id="ano" name="ano" >
		<br>
		sexo: <input type="text" id="sexo" name="sexo" >
		<br>
		<input type="submit" value="testar">
	</form>
	
</body>
</html>
