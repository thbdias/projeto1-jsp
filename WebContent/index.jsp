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
	
	<jsp:include page="cabecalho.jsp"/>
	<br>
	<h3>corpo da página</h3>
	<br>
	<jsp:include page="rodape.jsp"/>
</body>
</html>
