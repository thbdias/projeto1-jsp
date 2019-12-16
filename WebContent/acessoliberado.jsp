<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>	
	
	<center style="padding-top: 10%">
		<h1>Seja bem vido ao sistema em jsp</h1>
		<br>
		
		<table>
			<tr>
				<td>
					<a href="salvarUsuario?acao=listarTodos">	
						<img alt="Cadastro de Usuários" title="Cadastro de Usuários" src="resources/img/user.png" width="100px" height="100px">		
					</a>
				</td>
				<td>
					<a href="produtoServlet?acao=listarTodos">
						<img alt="Cadastro de Produto" title="Cadastro de Produto" src="resources/img/produtos.png" width="100px" height="100px">
					</a>
				</td>
			</tr>
			<tr>
				<td>Cad. Usuário</td>
				<td>Cad. Produto</td>
			</tr>
		</table>
	</center>	
</body>
</html>	
		