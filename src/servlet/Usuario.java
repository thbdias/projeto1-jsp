package servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			String user = request.getParameter("user");

			if (acao != null) {
				if (acao.equalsIgnoreCase("delete")) {
					daoUsuario.delete(user);
					RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
					request.setAttribute("usuarios", daoUsuario.listar());
					view.forward(request, response);
				} else if (acao.equalsIgnoreCase("editar")) {
					BeanCursoJsp usuario = daoUsuario.consultar(user);
					RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
					request.setAttribute("user", usuario);
					view.forward(request, response);
				} else if (acao.equalsIgnoreCase("listarTodos")) {
					RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
					request.setAttribute("usuarios", daoUsuario.listar());
					view.forward(request, response);
				} else if (acao.equalsIgnoreCase("download")) {
					BeanCursoJsp usuario = daoUsuario.consultar(user);

					if (usuario != null) {
						byte[] fileBytes = null;
						String contentType = "";
						String tipo = request.getParameter("tipo");

						if (tipo.equalsIgnoreCase("imagem")) {
							// converte a base64 da imagem do banco para byte[]
							fileBytes = Base64.decodeBase64(usuario.getFotoBase64());
							contentType = usuario.getContentTypeArquivo();
						} else if (tipo.equalsIgnoreCase("curriculo")) {
							// converte a base64 da imagem do banco para byte[]
							fileBytes = Base64.decodeBase64(usuario.getCurriculoBase64());
							contentType = usuario.getContentTypeArquivoCurriculo();
						}

						// response -> resposta para o navegador
						response.setHeader("Content-Disposition",
								"attachment;filename=arquivo." + contentType.split("\\/")[1]);

						OutputStream os = response.getOutputStream();
						os.write(fileBytes);
					}

				}
			}
			else {
				RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) { // botão cancelar
			try {
				RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response); // faz o redirecionamento
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else { // botao salvar
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
			} else
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

				/* Inicio File upload de imagens e pdf */
				if (ServletFileUpload.isMultipartContent(request)) {

					// img
					Part imagemFoto = request.getPart("foto");

					if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {//salvar
						byte [] bytesImagem = converteStreamParaByte(imagemFoto.getInputStream());
						String fotoBase64 = new Base64().encodeBase64String(bytesImagem);

						usuario.setFotoBase64(fotoBase64);
						usuario.setContentTypeArquivo(imagemFoto.getContentType());
						
						/*inicio miniatura imagem*/
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytesImagem));						
						int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType(); //tipo da imagem
						
						//cria miniatura
						BufferedImage resizedImage = new BufferedImage(100, 100, type); //img 100px por 100px
						Graphics2D g = resizedImage.createGraphics();
						g.drawImage(resizedImage, 0, 0, 100, 100, null);
						
						//escrever imagem novamente
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(resizedImage, "png", baos);
						
						String miniaturaBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());
						usuario.setFotoBase64Miniatura(miniaturaBase64);
						/*fim miniatura imagem*/
						
					} else { // atualizar
						usuario.setFotoBase64(request.getParameter("fotoTemp"));
						usuario.setContentTypeArquivo(request.getParameter("contentTypeTemp"));
					}

					// pdf
					Part curriculoPdf = request.getPart("curriculo");

					if (curriculoPdf != null && curriculoPdf.getInputStream().available() > 0) {
						String curriculoBase64 = new Base64()
								.encodeBase64String(converteStreamParaByte(curriculoPdf.getInputStream()));

						usuario.setCurriculoBase64(curriculoBase64);
						usuario.setContentTypeArquivoCurriculo(curriculoPdf.getContentType());
					} else { // atualizar
						usuario.setCurriculoBase64(request.getParameter("curriculoTemp"));
						usuario.setContentTypeArquivoCurriculo(request.getParameter("curriculoContentTypeTemp"));
					}
				}
				/* Fim File upload de imagens e pdf */

				if (login == null || login.isEmpty()) { // login não pode vir vazio
					request.setAttribute("msg", "Login não pode estar vazio!");
					request.setAttribute("user", usuario);
				} else if (senha == null || senha.isEmpty()) { // senha não pode vir vazio
					request.setAttribute("msg", "Senha não pode estar vazia!");
					request.setAttribute("user", usuario);
				} else if (nome == null || nome.isEmpty()) { // nome não pode vir vazio
					request.setAttribute("msg", "Nome não pode estar vazio!");
					request.setAttribute("user", usuario);
				}

				else if (id == null || id.isEmpty() && !daoUsuario.validarLogin(login)) { // nao deixa cadastrar mais de
																							// um login iguais
					request.setAttribute("msg", "Usuário já existe com o mesmo login!");
					request.setAttribute("user", usuario);
				} else if (id == null || id.isEmpty() && !daoUsuario.validarSenha(senha)) { // não deixa cadastrar mais
																							// de uma senha iguais
					request.setAttribute("msg", "Senha já existe para outro usuário!");
					request.setAttribute("user", usuario);
				} else if (id == null || id.isEmpty() && daoUsuario.validarLogin(login)) {
					daoUsuario.salvar(usuario);
					request.setAttribute("msg", "Salvo com sucesso!");
				} else if (id != null && !id.isEmpty()) { // atualizar
					if (!daoUsuario.validarLoginUpdate(login, id)) {
						request.setAttribute("msg", "Usuário já existe com o mesmo login!");
						request.setAttribute("user", usuario);
					} else if (!daoUsuario.validarSenhaUpdate(senha, id)) {
						request.setAttribute("msg", "Senha já existe para outro usuário!");
						request.setAttribute("user", usuario);
					} else {
						daoUsuario.atualizar(usuario);
						request.setAttribute("msg", "Atualizado com sucesso!");
					}
				}

				RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response); // faz o redirecionamento
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
