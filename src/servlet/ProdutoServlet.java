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
			String acao = request.getParameter("acao") != null ? request.getParameter("acao") : "listarTodos";
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
		
		if (acao != null && acao.equalsIgnoreCase("reset")) { //bot�o cancelar			
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
			
			if (request.getParameter("quant") != null && !request.getParameter("quant").isEmpty()) {
				String valor = request.getParameter("quant").replaceAll("\\.", ""); //1.500,00 -> 1500,00
				valor = valor.replaceAll("\\ ", "."); //1500,00 -> 1500.00				
				produto.setQuantidade(Double.parseDouble(valor));
			}
			
			if (request.getParameter("valor") != null && !request.getParameter("valor").isEmpty()) {
				String valor = request.getParameter("valor").replaceAll("\\.", ""); //1.500,00 -> 1500,00
				valor = valor.replaceAll("\\,", "\\."); //1500,00 -> 1500.00				
				produto.setValor(Double.parseDouble(valor));
			}
				
			
			try {
				
				if (request.getParameter("nome") == null || request.getParameter("nome").isEmpty()) {//nome n�o pode vir vazio
					request.setAttribute("msg", "Nome n�o pode estar vazio!");
					request.setAttribute("produto", produto);
				}
				else if (request.getParameter("quant") == null || request.getParameter("quant").isEmpty()) {//quantidade n�o pode vir vazio
					request.setAttribute("msg", "Quantidade n�o pode estar vazia!");
					request.setAttribute("produto", produto);
				}
				else if (request.getParameter("valor") == null || request.getParameter("valor").isEmpty()) {//valor n�o pode vir vazio
					request.setAttribute("msg", "Valor n�o pode estar vazio!");
					request.setAttribute("produto", produto);
				} 
				else if (id == null || id.isEmpty() && daoProduto.validarNomeProduto(produto.getNome())) {
					daoProduto.salvar(produto);
					request.setAttribute("msg", "Produto salvo com sucesso!");
				}
				else if (id == null || id.isEmpty() && !daoProduto.validarNomeProduto(produto.getNome())) { 
					request.setAttribute("msg", "Nome j� existe para outro produto!");
					request.setAttribute("produto", produto);
				}
				else if (id != null && !id.isEmpty()) { //atualizar
					if (daoProduto.validarNomeProdutoUpdate(produto.getNome(), produto.getId())) {
						daoProduto.atualizar(produto);
						request.setAttribute("msg", "Atualizado com sucesso!");
					}
					else {
						request.setAttribute("msg", "Nome j� existe para outro produto!");
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
