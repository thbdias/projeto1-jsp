package filter;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import connection.SingleConnection;

//interceptar todas urls
@WebFilter(urlPatterns = {"/*"})
public class Filter implements javax.servlet.Filter {
	
	private static Connection connection;

	//intercepta todas as conexões como banco de dados. 
	//executado toda vez que se clica com algum botão na tela que necessita de conexão cm BD
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
			connection.commit();
		}
		catch (Exception e) {
			try {
				e.printStackTrace();
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		
	}
	
	//inicia uma única vez quando o servidor é levantado
	@Override
	public void init(FilterConfig arg0) throws ServletException{
		connection = SingleConnection.getConnection();
	}

	
	
}
