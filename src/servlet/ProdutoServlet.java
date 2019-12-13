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
		try {
			String acao = request.getParameter("acao");
			
			if (acao.equalsIgnoreCase("listarTodos")) {
				RequestDispatcher view = request.getRequestDispatcher("cadastroProdutos.jsp");				
				request.setAttribute("produtos", daoProduto.listarProdutos());				
				view.forward(request, response);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BeanProduto produto = new BeanProduto();
		
		String id = request.getParameter("id");
		
		produto.setId(!id.isEmpty() ? Long.parseLong(id) : null);
		produto.setNome(request.getParameter("nome"));
		produto.setQuantidade(Double.parseDouble(request.getParameter("quant")));
		produto.setValor(Double.parseDouble(request.getParameter("valor")));
		
		try {
			if (id == null || id.isEmpty() && daoProduto.validarNomeProduto(produto.getNome())) {
				daoProduto.salvar(produto);
			}
			else {
				request.setAttribute("msg", "Nome já existe para outro produto!");
				request.setAttribute("produto", produto);
			}
			
			RequestDispatcher view = request.getRequestDispatcher("cadastroProdutos.jsp");
			request.setAttribute("produtos", daoProduto.listarProdutos());
			view.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
