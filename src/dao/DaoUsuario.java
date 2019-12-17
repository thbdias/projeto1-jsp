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

	public void salvar(BeanCursoJsp usuario) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" insert into Usuario");
			sql.append(
					" (login, senha, nome, fone, cep, rua, bairro, cidade, estado, ibge, fotoBase64, contentTypeArquivo, curriculoBase64, contentTypeArquivoCurriculo, fotoBase64Miniatura, ativo)");
			sql.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			PreparedStatement statement = connection.prepareStatement(sql.toString());
			statement.setString(1, usuario.getLogin());
			statement.setString(2, usuario.getSenha());
			statement.setString(3, usuario.getNome());
			statement.setString(4, usuario.getFone());
			statement.setString(5, usuario.getCep());
			statement.setString(6, usuario.getRua());
			statement.setString(7, usuario.getBairro());
			statement.setString(8, usuario.getCidade());
			statement.setString(9, usuario.getEstado());
			statement.setString(10, usuario.getIbge());
			statement.setString(11, usuario.getFotoBase64());
			statement.setString(12, usuario.getContentTypeArquivo());
			statement.setString(13, usuario.getCurriculoBase64());
			statement.setString(14, usuario.getContentTypeArquivoCurriculo());
			statement.setString(15, usuario.getFotoBase64Miniatura());
			statement.setBoolean(16, usuario.isAtivo());
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

		String sql = "select * from usuario where login <> 'admin'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			usuario = new BeanCursoJsp();
			usuario.setId(resultSet.getLong("id"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setFone(resultSet.getString("fone"));
			usuario.setCep(resultSet.getString("cep"));
			usuario.setRua(resultSet.getString("rua"));
			usuario.setBairro(resultSet.getString("bairro"));
			usuario.setCidade(resultSet.getString("cidade"));
			usuario.setEstado(resultSet.getString("estado"));
			usuario.setIbge(resultSet.getString("ibge"));
//			usuario.setFotoBase64(resultSet.getString("fotoBase64"));
			usuario.setFotoBase64Miniatura(resultSet.getString("fotoBase64Miniatura"));
			usuario.setContentTypeArquivo(resultSet.getString("contentTypeArquivo"));
			usuario.setCurriculoBase64(resultSet.getString("curriculoBase64"));
			usuario.setContentTypeArquivoCurriculo(resultSet.getString("contentTypeArquivoCurriculo"));
			listBeanCursoJsp.add(usuario);
		}

		return listBeanCursoJsp;
	}

	public void delete(String id) {
		try {
			String sql = "delete from usuario where id = '" + id + "' and login <> 'admin'";
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
		String sql = "select * from usuario where id = '" + id + "' and login <> 'admin'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			BeanCursoJsp usuario = new BeanCursoJsp();
			usuario.setId(resultSet.getLong("id"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setFone(resultSet.getString("fone"));
			usuario.setCep(resultSet.getString("cep"));
			usuario.setRua(resultSet.getString("rua"));
			usuario.setBairro(resultSet.getString("bairro"));
			usuario.setCidade(resultSet.getString("cidade"));
			usuario.setEstado(resultSet.getString("estado"));
			usuario.setIbge(resultSet.getString("ibge"));
			usuario.setFotoBase64Miniatura(resultSet.getString("fotoBase64Miniatura"));
			usuario.setFotoBase64(resultSet.getString("fotoBase64"));
			usuario.setContentTypeArquivo(resultSet.getString("contentTypeArquivo"));
			usuario.setCurriculoBase64(resultSet.getString("curriculoBase64"));
			usuario.setContentTypeArquivoCurriculo(resultSet.getString("contentTypeArquivoCurriculo"));
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

	public boolean validarLoginUpdate(String login, String id) throws Exception {
		String sql = "select count(1) as qtd from usuario where login = '" + login + "' and id <> " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			return resultSet.getInt("qtd") <= 0;
		}

		return false;
	}

	public boolean validarSenha(String senha) throws Exception {
		String sql = "select count(1) as qtd from usuario where senha = '" + senha + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			return resultSet.getInt("qtd") <= 0;
		}

		return false;
	}

	public boolean validarSenhaUpdate(String senha, String id) throws Exception {
		String sql = "select count(1) as qtd from usuario where senha = '" + senha + "' and id <> " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			return resultSet.getInt("qtd") <= 0;
		}

		return false;
	}

	public void atualizar(BeanCursoJsp usuario) {
		try {
			int cont = 1;
			StringBuffer sql = new StringBuffer();
			sql.append(" update usuario set ");
			sql.append(" login = ?, senha = ?, nome = ?, fone = ?, ");
			sql.append(" cep = ?, rua = ?, bairro = ?, cidade = ?, ");
			sql.append(" estado = ?, ibge = ?, ativo = ? ");

			if (usuario.isAtualizarImage()) {
				sql.append(" , fotoBase64 = ?, contentTypeArquivo = ? ");
			}
			
			if (usuario.isAtualizarPdf()) {
				sql.append(" , curriculoBase64 = ?, contentTypeArquivoCurriculo = ? ");
			}
			
			if (usuario.isAtualizarImage()) {
				sql.append(" , fotoBase64Miniatura = ? ");
			}
			
			sql.append(" where id = " + usuario.getId());

			PreparedStatement statement = connection.prepareStatement(sql.toString());
			statement.setString(cont, usuario.getLogin()); cont++;
			statement.setString(cont, usuario.getSenha()); cont++;
			statement.setString(cont, usuario.getNome()); cont++;
			statement.setString(cont, usuario.getFone()); cont++;
			statement.setString(cont, usuario.getCep()); cont++;
			statement.setString(cont, usuario.getRua()); cont++;
			statement.setString(cont, usuario.getBairro()); cont++;
			statement.setString(cont, usuario.getCidade()); cont++;
			statement.setString(cont, usuario.getEstado()); cont++;
			statement.setString(cont, usuario.getIbge()); cont++;
			statement.setBoolean(cont, usuario.isAtivo()); cont++;

			if (usuario.isAtualizarImage()) {
				statement.setString(cont, usuario.getFotoBase64()); cont++;
				statement.setString(cont, usuario.getContentTypeArquivo()); cont++;
			}

			if (usuario.isAtualizarPdf()) {
				statement.setString(cont, usuario.getCurriculoBase64()); cont++;
				statement.setString(cont, usuario.getContentTypeArquivoCurriculo()); cont++;
			}

			if (usuario.isAtualizarImage()) {
				statement.setString(cont, usuario.getFotoBase64Miniatura()); cont++;
			}

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
