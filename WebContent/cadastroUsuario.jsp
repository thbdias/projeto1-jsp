<%@page import="beans.BeanCursoJsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Cadastro de Usuários</title>
	<link rel="stylesheet" href="resources/css/cadastro.css">
	<!-- Adicionando JQuery -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
</head>
<body>

	<a href="acessoliberado.jsp"><img alt="Início" title="Home" src="resources/img/home.png" width="35px" height="35px"></a>
	<a href="index.jsp"><img alt="Sair" title="Sair" src="resources/img/sair.png" width="35px" height="35px"></a>

	<center>
		<h1>Cadastro de Usuário</h1>	
		<h3 style="color: orange">${msg}</h3>
	</center>
	
	<form action="salvarUsuario" method="post" id="formUser" onsubmit="return validarCampos() ? true : false; " enctype="multipart/form-data">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" id="id" name="id"
							value="${user.id}" class="field-long"></td>
					</tr>
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome" value="${user.nome}" placeholder="Nome do usuário"></td>
						
						<td>Ativo: </td>
						<td>
							<input type="checkbox" id="ativo" name="ativo" 
								<%
									if (request.getAttribute("user") != null){
										BeanCursoJsp usuario = (BeanCursoJsp) request.getAttribute("user");
										if (usuario.isAtivo()){
											out.print(" ");
											out.print("checked=\"checked\"");
											out.print(" ");
										}
									}
								%>
							>
						</td>					
					</tr>
					<tr>
						<td>Login:</td>
						<td><input type="text" id="login" name="login" value="${user.login}" placeholder="Informe Login" maxlength="10"></td>
					
						<td>Senha:</td>
						<td><input type="password" id="senha" name="senha" value="${user.senha}" placeholder="Informe senha" maxlength="10"></td>
					</tr>	
					<tr>
						<td>Cep:</td>
						<td><input type="text" id="cep" name="cep" onblur="consultaCep();" value="${user.cep}" placeholder="Informe cep válido"></td>
					
						<td>Rua:</td>
						<td><input type="text" id="rua" name="rua" value="${user.rua}" placeholder="Informe rua"></td>
					</tr>
					<tr>
						<td>Bairro:</td>
						<td><input type="text" id="bairro" name="bairro" value="${user.bairro}" placeholder="Informe bairro"></td>
					
						<td>Cidade:</td>
						<td><input type="text" id="cidade" name="cidade" value="${user.cidade}" placeholder="Informe Cidade"></td>
					</tr>
					<tr>
						<td>Estado:</td>
						<td><input type="text" id="estado" name="estado" value="${user.estado}" placeholder="Informe Cidade"></td>
					
						<td>IBGE:</td>
						<td><input type="text" id="ibge" name="ibge" value="${user.ibge}" placeholder="Informe cod. IBGE"></td>
					</tr>
					<tr>
						<td>
							Foto:
						</td>
						<td>
							<input type="file" id="foto" name="foto">
						</td>
					
						<td>
							Currículo:
						</td>
						<td>
							<input type="file" id="curriculo" name="curriculo">
						</td>
					</tr>	
					<tr>
						<td>Sexo:</td>
						<td>
							<input type="radio" id="sexo" name="sexo" value="masculino">Masculino</input>
							<input type="radio" id="sexo" name="sexo" value="feminino">Feminino</input>
						</td>
					</tr>				
					<tr>
						<td></td>
						<td>
							<input type="submit" value="Salvar">
							<input type="submit" value="Cancelar" onclick="document.getElementById('formUser').action = 'salvarUsuario?acao=reset'">
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</form>

	<br>

	<div class="container">
		<table class="responsive-table">			
				<caption>Lista de Usuários</caption>
				<tr>
					<th>Id</th>					
					<th>Foto</th>
					<th>Curriculo</th>
					<th>Nome</th>					
					<th>Cep</th>
					<th>Rua</th>
					<th>Bairro</th>
					<th>Cidade</th>
					<th>Estado</th>
					<th>IBGE</th>
					<th>Delete</th>
					<th>Editar</th>
					<th>Fones</th>
				</tr>
				<c:forEach items="${usuarios}" var="user">
					<tr style="text-align: center; vertical-align: middle">
						<td><c:out value="${user.id}"></c:out></td>											
						
						<c:if test="${user.fotoBase64Miniatura.isEmpty() == false}">
							<td>
								<a href="salvarUsuario?acao=download&tipo=imagem&user=${user.id}">
									<img src='<c:out value="${user.fotoBase64Miniatura}"></c:out>' width="20px" height="20px" title="Baixar imagem">
								</a>
							</td>
						</c:if>
						<c:if test="${user.fotoBase64Miniatura == null}">
							<td>
								<img src="resources/img/user_padrao.png" width="20px" height="20px" title="Não possui foto">
							</td>
						</c:if>
						
						<c:if test="${user.curriculoBase64.isEmpty() == false}">
							<td>
								<a href="salvarUsuario?acao=download&tipo=curriculo&user=${user.id}">
									<img title="Baixar PDF" src="resources/img/pdf1.png" width="20px" height="20px">
								</a>
							</td>
						</c:if>
						<c:if test="${user.curriculoBase64.isEmpty() == null}">
							<td>								
								<img title="Não possui PDF" src="resources/img/pdf2.png" width="20px" height="20px">
							</td>
						</c:if>
						<td><c:out value="${user.nome}"></c:out></td>						
						<td><c:out value="${user.cep}"></c:out></td>
						<td><c:out value="${user.rua}"></c:out></td>
						<td><c:out value="${user.bairro}"></c:out></td>
						<td><c:out value="${user.cidade}"></c:out></td>
						<td><c:out value="${user.estado}"></c:out></td>
						<td><c:out value="${user.ibge}"></c:out></td>
						<td>
							<a href="salvarUsuario?acao=delete&user=${user.id}" onclick="return confirm('Confirmar a exclusão?');">
								<img alt="Excluir" title="Excluir" src="resources/img/delete.png" width="20px" height="20px">
							</a>
						</td>
						<td>
							<a href="salvarUsuario?acao=editar&user=${user.id}">
								<img alt="Editar" title="Editar" src="resources/img/editar.png" width="20px" height="20px">
							</a>
						</td>
						<td>
							<a href="telefoneServlet?acao=listarTodos&idUser=${user.id}">
								<img alt="Cadastrar Telefones" title="Cadastrar Telefones" src="resources/img/phone.png" width="20px" height="20px">
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
			else if (document.getElementById("fone").value == ''){
				alert('Informe o Fone!');
				return false;
			}
			else if (document.getElementById("login").value == ''){
				alert('Informe o Login!');
				return false;
			}
			else if (document.getElementById("senha").value == ''){
				alert('Informe o Senha!');
				return false;
			}
			return true;
		}	
		
		
		function consultaCep() {
			var cep = $("#cep").val();
			
			//Consulta o webservice viacep.com.br/
            $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {
				//alert(dados.logradouro);
            	
                if (!("erro" in dados)) {
                	$("#rua").val(dados.logradouro);
					$("#bairro").val(dados.bairro);
					$("#cidade").val(dados.localidade);
					$("#estado").val(dados.uf);
					$("#ibge").val(dados.ibge);
                } 
                else {
                    //CEP pesquisado não foi encontrado.
                    $("#rua").val('');
					$("#bairro").val('');
					$("#cidade").val('');
					$("#estado").val('');
					$("#ibge").val('');
                    alert("CEP não encontrado.");
                }
            });
		}
	</script>	

</body>
</html>