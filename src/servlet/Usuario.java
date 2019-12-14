package servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeanCursoJsp;
import dao.DaoUsuario;

@WebServlet("/salvarUsuario")
@MultipartConfig
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
		
		if (acao != null && acao.equalsIgnoreCase("reset")) { //bot�o cancelar			
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
					
					Part imagemFoto = request.getPart("foto");
					
					String fotoBase64 = new Base64().encodeBase64String(converteStreamParaByte(imagemFoto.getInputStream()));
					
					usuario.setFotoBase64(fotoBase64);
					usuario.setContentTypeArquivo(imagemFoto.getContentType());
				}
				
				/*Fim    File upload de imagens e pdf*/
				
				if (login == null || login.isEmpty()) { //login n�o pode vir vazio
					request.setAttribute("msg", "Login n�o pode estar vazio!");
					request.setAttribute("user", usuario);
				}
				else if (senha == null || senha.isEmpty()) { //senha n�o pode vir vazio
					request.setAttribute("msg", "Senha n�o pode estar vazia!");
					request.setAttribute("user", usuario);
				}
				else if (nome == null || nome.isEmpty()) { //nome n�o pode vir vazio
					request.setAttribute("msg", "Nome n�o pode estar vazio!");
					request.setAttribute("user", usuario);
				} 
				else if (fone == null || fone.isEmpty()) { //fone n�o pode vir vazio
					request.setAttribute("msg", "Fone n�o pode estar vazio!");
					request.setAttribute("user", usuario);
				}
				else if (id == null || id.isEmpty() && !daoUsuario.validarLogin(login)) { //nao deixa cadastrar mais de um login iguais
					request.setAttribute("msg", "Usu�rio j� existe com o mesmo login!");
					request.setAttribute("user", usuario);
				} 
				else if (id == null || id.isEmpty() && !daoUsuario.validarSenha(senha)) { //n�o deixa cadastrar mais de uma senha iguais
					request.setAttribute("msg", "Senha j� existe para outro usu�rio!");
					request.setAttribute("user", usuario);
				}
				else if (id == null || id.isEmpty() && daoUsuario.validarLogin(login)) { 
					daoUsuario.salvar(usuario);		
					request.setAttribute("msg", "Salvo com sucesso!");
				}
				else if (id != null && !id.isEmpty()) { //atualizar
					if (!daoUsuario.validarLoginUpdate(login, id)) {
						request.setAttribute("msg", "Usu�rio j� existe com o mesmo login!");
						request.setAttribute("user", usuario);
					}
					else if (!daoUsuario.validarSenhaUpdate(senha, id)) {
						request.setAttribute("msg", "Senha j� existe para outro usu�rio!");
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
	
	
	private byte[] converteStreamParaByte(InputStream imagem) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read();
		
		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();
		}
		
		return baos.toByteArray();
	}
	
}
