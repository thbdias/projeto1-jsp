<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%=	"Nome recebido: " + request.getParameter("nome") %>		
	<br><br>
	<%= "request.getContextPath() ===> " + request.getContextPath()%>
	<br><br>
	<%= "request.getContentType() ===> " + request.getContentType()%>
	<br><br>
	<%= "request.getLocalAddr() ===> " + request.getLocalAddr()%>
	<br><br>
	<%= "request.getLocalName() ===> " + request.getLocalName()%>
	<br><br>
	<%= "request.getLocalPort() ===> " + request.getLocalPort()%>
	<br><br>
	<%= "request.getProtocol() ===> " + request.getProtocol()%>
	<br><br>
	<%= "request.getRequestedSessionId() ===> " + request.getRequestedSessionId()%>
	<br><br>
	<%= "----------------------------------------------------------------------------------" %>
	<br><br><br>
	<%= "RESPONSE -> Envia uma resposta a uma requisição solicitada (pra quem o chamou)" %>
	<br>
	<% //response.sendRedirect("/cadastro-pessoa.jsp") %>
	<% response.sendRedirect("http://www.oantagonista.com"); %>
</body>
</html>