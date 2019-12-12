package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanCursoJsp;
import connection.SingleConnection;

public class DaoUsuario {
	
	private Connection connection;
	
	public DaoUsuario() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar (BeanCursoJsp usuario) {
		try {
			String sql = "insert into Usuario (login, senha, nome, fone) values (?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, usuario.getLogin());
			statement.setString(2, usuario.getSenha());
			statement.setString(3, usuario.getNome());
			statement.setString(4, usuario.getFone());
			statement.execute();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
	}
	
	public List<BeanCursoJsp> listar() throws Exception {
		List<BeanCursoJsp> listBeanCursoJsp = new ArrayList<BeanCursoJsp>();
		BeanCursoJsp usuario = null;
		
		String sql = "select * from usuario";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			usuario = new BeanCursoJsp();
			usuario.setId(resultSet.getLong("id"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setFone(resultSet.getString("fone"));
			listBeanCursoJsp.add(usuario);
		}
		
		return listBeanCursoJsp;
	}
	
	public void delete (String id) {		
		try {
			String sql = "delete from usuario where id = '" + id + "'";
			PreparedStatement statement = connection.prepareStatement(sql);
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

	public BeanCursoJsp consultar(String id) throws Exception {
		String sql = "select * from usuario where id = '" + id + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			BeanCursoJsp usuario = new BeanCursoJsp();
			usuario.setId(resultSet.getLong("id"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setFone(resultSet.getString("fone"));
			return usuario;
		}
		
		return null;
	}
	
	public boolean validarLogin(String login) throws Exception {
		String sql = "select count(1) as qtd from usuario where login = '" + login + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {			
			return resultSet.getInt("qtd") <= 0;
		}
		
		return false;
	}

	public void atualizar(BeanCursoJsp usuario) {
		try {
			String sql = "update usuario set login = ?, senha = ?, nome = ?, fone = ? where id = " + usuario.getId();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, usuario.getLogin());
			statement.setString(2, usuario.getSenha());
			statement.setString(3, usuario.getNome());
			statement.setString(4, usuario.getFone());
			statement.executeUpdate();
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
