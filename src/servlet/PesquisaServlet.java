package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoUsuario;

@WebServlet("/pesquisaServlet")
public class PesquisaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PesquisaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		try {
			String descricaoconsulta = request.getParameter("descricaoconsulta");
			DaoUsuario daoUsuario = new DaoUsuario();
			
			if (descricaoconsulta != null) {			
				RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");				
				request.setAttribute("usuarios", daoUsuario.listar(descricaoconsulta));				
				view.forward(request, response);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
