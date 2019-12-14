package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import dao.DaoUsuario;

@WebServlet("/telefoneServlet")
public class TelefoneServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	DaoUsuario daoUsuario = new DaoUsuario();
	
       
    public TelefoneServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		try {
			String idUser = request.getParameter("idUser");
			BeanCursoJsp usuario = daoUsuario.consultar(idUser);
			
			RequestDispatcher view = request.getRequestDispatcher("cadastroTelefone.jsp");
			view.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
