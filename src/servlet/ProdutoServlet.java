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
			String idProduto = request.getParameter("idProduto");
			
			if (acao.equalsIgnoreCase("listarTodos")) {
				RequestDispatcher view = request.getRequestDispatcher("cadastroProdutos.jsp");				
				request.setAttribute("produtos", daoProduto.listarProdutos());				
				view.forward(request, response);
			}
			else if (acao.equalsIgnoreCase("delete")) {
				daoProduto.delete(idProduto);
				RequestDispatcher view = request.getRequestDispatcher("cadastroProdutos.jsp");
				request.setAttribute("produtos", daoProduto.listarProdutos());
				view.forward(request, response);
			}
			else if (acao.equalsIgnoreCase("editar")) {
				BeanProduto produto = daoProduto.consultar(idProduto);
				RequestDispatcher view = request.getRequestDispatcher("cadastroProdutos.jsp");
				request.setAttribute("produto", produto);
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
				RequestDispatcher view = request.getRequestDispatcher("cadastroProdutos.jsp");
				request.setAttribute("produtos", daoProduto.listarProdutos());
				view.forward(request, response); //faz o redirecionamento
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		else {			
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
				else if (id == null || id.isEmpty() && !daoProduto.validarNomeProduto(produto.getNome())) {
					request.setAttribute("msg", "Nome já existe para outro produto!");
					request.setAttribute("produto", produto);
				}
				else if (id != null && !id.isEmpty()) { //atualizar
					if (daoProduto.validarNomeProdutoUpdate(produto.getNome(), produto.getId())) {
						daoProduto.atualizar(produto);
					}
					else {
						request.setAttribute("msg", "Nome já existe para outro produto!");
						request.setAttribute("produto", produto);
					}
				}
				
				RequestDispatcher view = request.getRequestDispatcher("cadastroProdutos.jsp");
				request.setAttribute("produtos", daoProduto.listarProdutos());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
