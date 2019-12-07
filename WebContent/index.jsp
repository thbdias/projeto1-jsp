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
	
	<jsp:forward page="receber-nome.jsp">
		<jsp:param value="este é um parâmetro do index!" name="paramforward"/>
	</jsp:forward>
</body>
</html>
