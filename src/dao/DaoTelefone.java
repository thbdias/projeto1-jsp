package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;

import beans.BeanCursoJsp;
import beans.BeanProduto;
import beans.BeanTelefone;
import connection.SingleConnection;

public class DaoTelefone {
	
	private Connection connection;
	
	public DaoTelefone() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(BeanTelefone telefone) {		
		try {
			String sql = "insert into telefone (numero, tipo, usuario) values (?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, telefone.getNumero());
			statement.setString(2, telefone.getTipo());
			statement.setLong(3, telefone.getUsuario());
			statement.execute();
			connection.commit();
		} catch (SQLException e) {			
			try {
				e.printStackTrace();
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}		
	}
	
	public List<BeanTelefone> listarTelefones(Long idUser) throws Exception{
		List<BeanTelefone> telefones = new ArrayList<BeanTelefone>();
		BeanTelefone telefone = null;		
		
		String sql = "select * from telefone where usuario = " + idUser;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
	
		while(resultSet.next()) {
			telefone = new BeanTelefone();
			telefone.setId(resultSet.getLong("id"));
			telefone.setNumero(resultSet.getString("numero"));
			telefone.setTipo(resultSet.getString("tipo"));
			telefone.setUsuario(resultSet.getLong("usuario"));
			telefones.add(telefone);
		}		
		
		return telefones;
	}

	public void delete(String id) {		
		try {
			String sql = "delete from telefone where id = " + id;
			PreparedStatement statement;
			statement = connection.prepareStatement(sql);
			statement.execute();
			connection.commit();
		} catch (SQLException e) {			
			try {
				e.printStackTrace();
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}		
	}

	
}
