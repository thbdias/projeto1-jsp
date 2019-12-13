<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>
	<center>
		<h1>Cadastro de Produto</h1>
		<h3 style="color: red">${msg}</h3>
	</center>

	<form action="produtoServlet" method="post">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" id="id" name="id"
							class="field-long" value="${produto.id}"></td>
					</tr>
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome" value="${produto.nome}"></td>
					</tr>
					<tr>
						<td>Quantidade:</td>
						<td><input type="text" id="quant" name="quant" value="${produto.quantidade}"></td>
					</tr>
					<tr>
						<td>Valor:</td>
						<td><input type="text" id="valor" name="valor" value="${produto.valor}"></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Salvar"></td>
					</tr>
				</table>
			</li>
		</ul>
	</form>

	<br>

	<div class="container">
		<table class="responsive-table">
			<caption>Produtos Cadastrados</caption>
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Quantidade</th>
				<th>Valor</th>
				<th>Delete</th>
				<th>Editar</th>
			</tr>
			<c:forEach items="${produtos}" var="produto">
				<tr style="text-align: center; vertical-align: middle">
					<td><c:out value="${produto.id}"></c:out></td>
					<td><c:out value="${produto.nome}"></c:out></td>
					<td><c:out value="${produto.quantidade}"></c:out></td>
					<td><c:out value="${produto.valor}"></c:out></td>
					<td>
						<a href="produtoServlet?acao=delete&idProduto=${produto.id}">
							<img alt="Excluir" title="Excluir" src="resources/img/delete.png" width="20px" height="20px">
						</a>
					</td>
					<td>
						<a href="produtoServlet?acao=editar&user=${produto.id}">
							Editar
						</a>
					</td>
				</tr>				
			</c:forEach>
		</table>
	</div>
</body>
</html>

















