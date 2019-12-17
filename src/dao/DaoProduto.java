package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanCategoria;
import beans.BeanCursoJsp;
import beans.BeanProduto;
import connection.SingleConnection;

public class DaoProduto {
	
	private Connection connection;
	
	public DaoProduto() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(BeanProduto produto) {		
		try {
			String sql = "insert into produto (nome, quantidade, valor, categoria_id) values (?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, produto.getNome());
			statement.setDouble(2, produto.getQuantidade());
			statement.setDouble(3, produto.getValor());
			statement.setLong(4, produto.getCategoria_id());
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
	
	public List<BeanProduto> listarProdutos() throws Exception{
		List<BeanProduto> produtos = new ArrayList<BeanProduto>();
		BeanProduto produto = null;		
		
		String sql = "select * from produto";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
	
		while(resultSet.next()) {
			produto = new BeanProduto();
			produto.setId(resultSet.getLong("id"));
			produto.setNome(resultSet.getString("nome"));
			produto.setQuantidade(resultSet.getDouble("quantidade"));
			produto.setValor(resultSet.getDouble("valor"));
			produto.setCategoria_id(resultSet.getLong("categoria_id"));
			produtos.add(produto);
		}		
		
		return produtos;
	}
	
	public List<BeanCategoria> listarCategorias() throws Exception{
		List<BeanCategoria> categorias = new ArrayList<BeanCategoria>();
		BeanCategoria categoria = null;
		
		String sql = "select * from categoria";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			categoria = new BeanCategoria();
			categoria.setId(resultSet.getLong("id"));
			categoria.setNome(resultSet.getString("nome"));
			categorias.add(categoria);
		}
		
		return categorias;
	}
	
	
	
	public boolean validarNomeProduto(String nomeProduto) throws Exception {
		String sql = "select count(1) as qtd from produto where nome = '"+nomeProduto+"'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			return resultSet.getInt("qtd") <= 0; 
		}
		
		return false;
	}

	public void delete(String id) {		
		try {
			String sql = "delete from produto where id = " + id;
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

	public BeanProduto consultar(String id) throws Exception {
		String sql = "select * from produto where id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			BeanProduto produto = new BeanProduto();
			produto.setId(resultSet.getLong("id"));
			produto.setNome(resultSet.getString("nome"));
			produto.setQuantidade(resultSet.getDouble("quantidade"));
			produto.setValor(resultSet.getDouble("valor"));
			produto.setCategoria_id(resultSet.getLong("categoria_id"));
			return produto;
		}
		
		return null;
	}
	
	public boolean validarNomeProdutoUpdate(String nomeProduto, Long id) throws Exception {
		String sql = "select count(1) as qtd from produto where nome = '"+nomeProduto+"' and id <> " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			return resultSet.getInt("qtd") <= 0; 
		}
		
		return false;
	}

	public void atualizar(BeanProduto produto) {
		try {
			String sql = "update produto set nome = ?, quantidade = ?, valor = ?, categoria_id = ? where id = " + produto.getId();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, produto.getNome());
			statement.setDouble(2, produto.getQuantidade());
			statement.setDouble(3, produto.getValor());
			statement.setLong(4, produto.getCategoria_id());
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
