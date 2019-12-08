<jsp:useBean id="calcula" class="beans.BeanCursoJsp" type="beans.BeanCursoJsp" scope="page"/>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="myprefix" uri="WEB-INF/testetag.tld"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Insert title here</title>
</head>
<body>		
	<h1>Página index.jsp</h1>	
	<h1>-----------------------------</h1>
	<br><br>	
	
	<c:out value="${ 'Bem vindo ao JSTL' }"/>
	<br>
	
	<!-- <c:import var="data" url="https://www.google.com.br" /> -->
	<!-- <c:out value="${ data }"/> -->
	<br>
	
	<c:set var="data" scope="page" value="${500*6}"/>
	<c:out value="${ data }"/>
	<br>
	<c:remove var="data"/>
	<c:out value="${ data }"/>
	<br>
	
	<c:catch var="erro">
		<% int var = 100/0; %>	
	</c:catch>
	<c:if test="${erro != null}">
		${ erro.message }
	</c:if>
	<br>
	
	<c:set var="numero" value="${100/2}" />
	<c:choose>
		<c:when test="${numero > 50}">
			<c:out value="${'Marior que 50'}"/>
		</c:when>
		<c:when test="${numero < 50}">
			<c:out value="${'Menor que 50'}"/>
		</c:when>
		<c:otherwise>
			<c:out value="${'Não encontrou valor correto.'}"></c:out>
		</c:otherwise>
	</c:choose>
	<br>
	 
	<c:set var="num" value="${100/25}" />
	<c:forEach var="n" begin="1" end="${num}">
		Item : ${n}
		<br>
	</c:forEach> 
	<br>
	 
	<c:forTokens items="Thiago-Henrique-Balbino-Dias" delims="-" var="nome">
		nome : ${nome}
		<br>
	</c:forTokens>	
	<br>
	 
	<c:url value="/acessoliberado.jsp" var="acesso">
		<c:param name="para1" value="111"/>
		<c:param name="para2" value="222"/>
	</c:url>
	${acesso}
	<br> 
	<!-- 
	<c:set var="knumero" value="${100/3}" />
	<c:if test="${knumero > 50}">
		<c:redirect url="https://www.google.com.br" />
	</c:if>	 
	<c:if test="${knumero <= 50}">
		<c:redirect url="http://www.javaavancado.com" />
	</c:if>--> <!-- apagar exemplos acima para esse funcionar ? -->
	<br> 
	 
	<br><br>
	
	<form action="LoginServlet" method="post">
		Login: <input type="text" id="login" name="login">
		<br>
		Senha: <input type="text" id="senha" name="senha">
		<br>
		<input type="submit" value="Logar">
	</form>
	
</body>
</html>
