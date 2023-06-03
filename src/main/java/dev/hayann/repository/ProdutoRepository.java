package dev.hayann.repository;

import dev.hayann.database.ConnectionPool;
import dev.hayann.model.Municipio;
import dev.hayann.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository implements Repository<Produto> {

    public Produto findById(int id) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE %s = ?", Produto.TABLE_NAME, Produto.COLLUMN_ID_NAME);
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            connectionPool.releaseConnection(connection);
            if (rs.next()) {
                return new Produto(
                        rs.getInt(Produto.COLLUMN_ID_NAME),
                        rs.getString(Produto.COLLUMN_DESCRIPTION_NAME)
                );
            } else return null;
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
            return null;
        }
    }

    public List<Produto> findAll() throws SQLException {
        String sql = String.format("SELECT * FROM %s", Produto.TABLE_NAME);
        ArrayList<Produto> produtos = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            produtos.add(new Produto(
                    resultSet.getInt(Produto.COLLUMN_ID_NAME),
                    resultSet.getString(Produto.COLLUMN_DESCRIPTION_NAME)
            ));
        }
        connectionPool.releaseConnection(connection);
        return produtos;
    }

    public void persist(Produto produto) throws SQLException {
        String sql = String.format("INSERT INTO %s (%s) VALUES (?) RETURNING %s", Produto.TABLE_NAME, Produto.COLLUMN_DESCRIPTION_NAME, Produto.COLLUMN_ID_NAME);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, produto.getDescricao());
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            produto.setId(resultSet.getInt(Produto.COLLUMN_ID_NAME));
        }
        connectionPool.releaseConnection(connection);
    }

    public void update(Produto produto) throws SQLException {
        String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ?", Produto.TABLE_NAME, Produto.COLLUMN_DESCRIPTION_NAME, Produto.COLLUMN_ID_NAME);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, produto.getDescricao());
        stmt.setInt(2, produto.getId());
        stmt.executeUpdate();
        connectionPool.releaseConnection(connection);
    }

    public void delete(Integer id) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", Produto.TABLE_NAME, Produto.COLLUMN_ID_NAME);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}
