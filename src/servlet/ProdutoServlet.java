package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanProduto;
import dao.DaoProduto;

@WebServlet("/produtoServlet")
public class ProdutoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private DaoProduto daoProduto = new DaoProduto();
       
    public ProdutoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("cadastroProduto.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BeanProduto produto = new BeanProduto();
		
		String id = request.getParameter("id");
		
		produto.setId(!id.isEmpty() ? Long.parseLong(id) : null);
		produto.setNome(request.getParameter("nome"));
		produto.setQuantidade(Double.parseDouble(request.getParameter("quant")));
		produto.setValor(Double.parseDouble(request.getParameter("valor")));
		
		daoProduto.salvar(produto);
		
		RequestDispatcher view = request.getRequestDispatcher("cadastroProduto.jsp");
		view.forward(request, response);
	}
}
