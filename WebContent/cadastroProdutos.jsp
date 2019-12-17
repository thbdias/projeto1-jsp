<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	
<!DOCTYPE html>
<html>
<head>	
	<meta charset="ISO-8859-1">
	<title>Insert title here</title>
	<link rel="stylesheet" href="resources/css/cadastro.css">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js" type="text/javascript"></script>
	<script src="resources/javascript/jquery.maskMoney.js" type="text/javascript"></script>
</head>
<body>

	<a href="acessoliberado.jsp"><img alt="Início" title="Home" src="resources/img/home.png" width="35px" height="35px"></a>
	<a href="index.jsp"><img alt="Sair" title="Sair" src="resources/img/sair.png" width="35px" height="35px"></a>

	<center>
		<h1>Cadastro de Produto</h1>
		<h3 style="color: orange">${msg}</h3>
	</center>

	<form action="produtoServlet" method="post" id="formUser" onsubmit="return validarCampos() ? true : false;">
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
						<td><input type="text" id="nome" name="nome" value="${produto.nome}" placeholder="Nome do produto"></td>
					</tr>
					<tr>
						<td>Quantidade:</td>
						<td><input type="text" id="quant" name="quant" value="${produto.quantidade}" placeholder="Informe a quant" maxlength="6"></td>
					</tr>
					<tr>
						<td>Valor:</td>
						<td>
							<input type="text" id="valor" name="valor" value="${produto.valorEmTexto}" placeholder="Informe o valor" data-thousands="." data-decimal=",">
						</td>
					</tr>
					<tr>
						<td>Categoria: </td>
						<td>
							<select id="categorias" name="categoria_id">
								<c:forEach items="${categorias}" var="categoria">
									<option value="${categoria.id}" id="${categoria.id}"> ${categoria.nome} </option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<input type="submit" value="Salvar">
							<input type="submit" value="Cancelar" onclick="document.getElementById('formUser').action = 'produtoServlet?acao=reset'">
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</form>

	<br>

	<div class="container">
		<table class="responsive-table">
			<caption>Lista de Produtos</caption>
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
					<td>						
						<fmt:formatNumber type="number" maxFractionDigits="2" value="${produto.valor}"/>
					</td>					
					<td>
						<a href="produtoServlet?acao=delete&idProduto=${produto.id}" onclick="return confirm('Confirmar a exclusão?');">
							<img alt="Excluir" title="Excluir" src="resources/img/delete.png" width="20px" height="20px">
						</a>
					</td>
					<td>
						<a href="produtoServlet?acao=editar&idProduto=${produto.id}">
							<img alt="Editar" title="Editar" src="resources/img/editar.png" width="20px" height="20px">
						</a>
					</td>
				</tr>				
			</c:forEach>
		</table>
	</div>	
	
	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById("nome").value == ''){
				alert('Informe o Nome!');
				return false;
			}		
			else if (document.getElementById("quant").value == ''){
				alert('Informe a Quantidade!');
				return false;
			}
			else if (document.getElementById("valor").value == ''){
				alert('Informe o Valor!');
				return false;
			}			
			return true;
		}		
	</script>	
</body>

<script>
  $(function() {
    $('#valor').maskMoney();
  })
  
  $(document).ready(function(){
	  $("#quant").keyup(function(){
		  $("#quant").val(this.value.match(/[0-9]*/));
	  });
  });
</script>

</html>

















