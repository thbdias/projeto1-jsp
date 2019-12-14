package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import beans.BeanTelefone;
import dao.DaoTelefone;
import dao.DaoUsuario;

@WebServlet("/telefoneServlet")
public class TelefoneServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	DaoUsuario daoUsuario = new DaoUsuario();
	DaoTelefone daoTelefone = new DaoTelefone();
	
       
    public TelefoneServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		try {
			String idUser = request.getParameter("idUser");			
			BeanCursoJsp usuario = null; 
			String acao = request.getParameter("acao");
			String idFone = request.getParameter("idFone");						
			
			if (acao.equalsIgnoreCase("listarTodos")) {
				usuario = daoUsuario.consultar(idUser);
				request.setAttribute("telefones", daoTelefone.listarTelefones(usuario.getId()));				
			}
			else if (acao.equalsIgnoreCase("delete")) {
				daoTelefone.delete(idFone);
				usuario = daoUsuario.consultar(idUser);
				request.setAttribute("telefones", daoTelefone.listarTelefones(usuario.getId()));
			}			
			
			request.getSession().setAttribute("userSession", usuario);			
			
			RequestDispatcher view = request.getRequestDispatcher("cadastroTelefone.jsp");
			view.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			BeanCursoJsp usuario = (BeanCursoJsp) request.getSession().getAttribute("userSession");		
			String numero = request.getParameter("numero");
			String tipo = request.getParameter("tipo");
			
			BeanTelefone telefone = new BeanTelefone();
			telefone.setNumero(numero);
			telefone.setTipo(tipo);
			telefone.setUsuario(usuario.getId());
			
			daoTelefone.salvar(telefone);		
						
			RequestDispatcher view = request.getRequestDispatcher("cadastroTelefone.jsp");			
			request.setAttribute("telefones", daoTelefone.listarTelefones(usuario.getId()));			
			request.setAttribute("msg", "Telefone salvo com sucesso!");
			request.getSession().setAttribute("userSession", usuario);
			view.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
