<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Cadastro de Telefones</title>
	<link rel="stylesheet" href="resources/css/cadastro.css">
	<!-- Adicionando JQuery -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
</head>
<body>

	<a href="acessoliberado.jsp"><img alt="Início" title="Home" src="resources/img/home.png" width="35px" height="35px"></a>
	<a href="index.jsp"><img alt="Sair" title="Sair" src="resources/img/sair.png" width="35px" height="35px"></a>

	<center>
		<h1>Cadastro de Telefones</h1>	
		<h3 style="color: orange">${msg}</h3>
	</center>
	
	<form action="telefoneServlet" method="post" id="formUser" onsubmit="return validarCampos() ? true : false; ">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Usuário:</td>
						<td><input type="text" readonly="readonly" id="id" name="id" class="field-long" value="${userSession.id}"></td>														
						<td><input type="text" readonly="readonly" id="nome" name="nome" value="${userSession.nome}"></td>											
					</tr>
					<tr>
						<td>Número:</td>
						<td><input type="text" id="numero" name="numero" placeholder="Número telefone"></td>
					
						<td>
							<select id="tipo" name="tipo" style="width: 156px;">
								<option>Casa</option>
								<option>Contato</option>
								<option>Celular</option>
								<option>Recado</option>
								<option>Outros</option>
							</select>
						</td>
						
					</tr>									
					<tr>
						<td></td>
						<td>
							<input type="submit" value="Salvar">											
							<input type="submit" value="Voltar" onclick="document.getElementById('formUser').action='telefoneServlet?acao=voltar'">
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</form>

	<br>

	<div class="container">
		<table class="responsive-table">			
				<caption>Lista de Telefones</caption>
				<tr>
					<th>Id</th>
					<th>Número</th>
					<th>Tipo</th>					
					<th>Delete</th>					
				</tr>
				<c:forEach items="${telefones}" var="fone">
					<tr style="text-align: center; vertical-align: middle">
						<td style="width: 150px"><c:out value="${fone.id}"></c:out></td>
						<td style="width: 150px"><c:out value="${fone.numero}"></c:out></td>
						<td><c:out value="${fone.tipo}"></c:out></td>												
						<td>
							<a href="telefoneServlet?acao=delete&idFone=${fone.id}&idUser=${userSession.id}">
								<img alt="Excluir" title="Excluir" src="resources/img/delete.png" width="20px" height="20px">
							</a>
						</td>						
					</tr>
				</c:forEach>			
		</table>
	</div>
	
	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById('formUser').action != 'telefoneServlet'){
				//faz nd -> botão voltar
			}
			else if (document.getElementById("numero").value == ''){
				alert('Informe o Número!');
				return false;
			}		
			else if (document.getElementById("tipo").value == ''){
				alert('Informe o Tipo!');
				return false;
			}			
			return true;
		}		
	</script>	

</body>
</html>