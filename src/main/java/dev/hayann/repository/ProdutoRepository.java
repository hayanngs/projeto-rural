package dev.hayann.repository;

import dev.hayann.database.ConnectionPool;
import dev.hayann.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public List<Produto> findAll() {
        try {
            String sql = String.format("SELECT * FROM %s", Produto.TABLE_NAME);
            ArrayList<Produto> municipios = new ArrayList<>();
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                municipios.add(new Produto(
                        resultSet.getInt(Produto.COLLUMN_ID_NAME),
                        resultSet.getString(Produto.COLLUMN_DESCRIPTION_NAME)
                ));
            }
            connectionPool.releaseConnection(connection);
            return municipios;
        } catch (Exception e) {
            e.printStackTrace();
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
            return null;
        }
    }

    public void persist(Produto produto) {
        try {
            String sql = String.format("INSERT INTO %s (%s) VALUES (?)", Produto.TABLE_NAME, Produto.COLLUMN_DESCRIPTION_NAME);
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, produto.getDescricao());
            stmt.executeUpdate();
            connectionPool.releaseConnection(connection);
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
        }
    }

    public void update(Produto municipio) {
        try {
            String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ?", Produto.TABLE_NAME, Produto.COLLUMN_DESCRIPTION_NAME, Produto.COLLUMN_ID_NAME);
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, municipio.getDescricao());
            stmt.setInt(2, municipio.getId());
            stmt.executeUpdate();
            connectionPool.releaseConnection(connection);
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
        }
    }
}
