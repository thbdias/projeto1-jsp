<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="myprefix" uri="WEB-INF/testetag.tld"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Insert title here</title>
</head>
<body>	
	<h1>P�gina index.jsp</h1>
	
	<%@ include file="pagina_include.jsp" %>
	
	<h3>P�gina index.jsp continua��o</h3>
	<br><br>
	<myprefix:minhatag/>
</body>
</html>
