package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeanCursoJsp;
import dao.DaoUsuario;

@WebServlet("/salvarUsuario")
public class Usuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoUsuario daoUsuario = new DaoUsuario();
       
    public Usuario() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			String user = request.getParameter("user");
		
			if (acao.equalsIgnoreCase("delete")) {					
				daoUsuario.delete(user);
				RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			}
			else if (acao.equalsIgnoreCase("editar")) {					
				BeanCursoJsp usuario = daoUsuario.consultar(user);
				RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("user", usuario);
				view.forward(request, response);
			} 
			else if (acao.equalsIgnoreCase("listarTodos")) {
				RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String acao = request.getParameter("acao");
		
		if (acao != null && acao.equalsIgnoreCase("reset")) { //botão cancelar			
			try {
				RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response); //faz o redirecionamento
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		else { //botao salvar
			String id = request.getParameter("id");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String nome = request.getParameter("nome");
			String fone = request.getParameter("fone");
			String cep = request.getParameter("cep"); 
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String estado = request.getParameter("estado");
			String ibge = request.getParameter("ibge");
			
			BeanCursoJsp usuario = new BeanCursoJsp();
			
			if (id != null) {
				if (!id.isEmpty()) 
					usuario.setId(Long.parseLong(id));				
				else 
					usuario.setId(null);				
			}
			else 
				usuario.setId(null);			
			
			
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setNome(nome);
			usuario.setFone(fone);
			usuario.setCep(cep);
			usuario.setRua(rua);
			usuario.setBairro(bairro);
			usuario.setCidade(cidade);
			usuario.setEstado(estado);
			usuario.setIbge(ibge);
			
			try {		
				
				/*Inicio File upload de imagens e pdf*/
				
				if (ServletFileUpload.isMultipartContent(request)) {
					
					//recuperando todos os campos da tela
					List<FileItem> fileItems = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request); 
										
					for (FileItem fileItem : fileItems) {
						//achando o campo da foto
						if (fileItem.getFieldName().equals("foto")) {
							//base 64 é o tipo que tem que gravar no banco de dados
							String foto = new Base64().encodeBase64String(fileItem.get());
						}
						
						
					}
					
				}
				
				/*Fim    File upload de imagens e pdf*/
				
				if (login == null || login.isEmpty()) { //login não pode vir vazio
					request.setAttribute("msg", "Login não pode estar vazio!");
					request.setAttribute("user", usuario);
				}
				else if (senha == null || senha.isEmpty()) { //senha não pode vir vazio
					request.setAttribute("msg", "Senha não pode estar vazia!");
					request.setAttribute("user", usuario);
				}
				else if (nome == null || nome.isEmpty()) { //nome não pode vir vazio
					request.setAttribute("msg", "Nome não pode estar vazio!");
					request.setAttribute("user", usuario);
				} 
				else if (fone == null || fone.isEmpty()) { //fone não pode vir vazio
					request.setAttribute("msg", "Fone não pode estar vazio!");
					request.setAttribute("user", usuario);
				}
				else if (id == null || id.isEmpty() && !daoUsuario.validarLogin(login)) { //nao deixa cadastrar mais de um login iguais
					request.setAttribute("msg", "Usuário já existe com o mesmo login!");
					request.setAttribute("user", usuario);
				} 
				else if (id == null || id.isEmpty() && !daoUsuario.validarSenha(senha)) { //não deixa cadastrar mais de uma senha iguais
					request.setAttribute("msg", "Senha já existe para outro usuário!");
					request.setAttribute("user", usuario);
				}
				else if (id == null || id.isEmpty() && daoUsuario.validarLogin(login)) { 
					daoUsuario.salvar(usuario);		
					request.setAttribute("msg", "Salvo com sucesso!");
				}
				else if (id != null && !id.isEmpty()) { //atualizar
					if (!daoUsuario.validarLoginUpdate(login, id)) {
						request.setAttribute("msg", "Usuário já existe com o mesmo login!");
						request.setAttribute("user", usuario);
					}
					else if (!daoUsuario.validarSenhaUpdate(senha, id)) {
						request.setAttribute("msg", "Senha já existe para outro usuário!");
						request.setAttribute("user", usuario);
					}
					else {
						daoUsuario.atualizar(usuario);
						request.setAttribute("msg", "Atualizado com sucesso!");
					}
				}
			
			
				RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response); //faz o redirecionamento
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}		
	}		
}
