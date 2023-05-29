package dev.hayann.repository;

import dev.hayann.database.ConnectionPool;
import dev.hayann.model.Producao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProducaoRepository implements Repository<Producao> {

    public Producao findById(int id) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE %s = ?", Producao.TABLE_NAME, Producao.COLLUMN_ID_NAME);
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            connectionPool.releaseConnection(connection);
            if (rs.next()) {
                return new Producao(
                        rs.getInt(Producao.COLLUMN_ID_NAME),
                        rs.getString(Producao.COLLUMN_DESCRIPTION_NAME)
                );
            } else return null;
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
            return null;
        }
    }

    public List<Producao> findAll() {
        try {
            String sql = String.format("SELECT * FROM %s", Producao.TABLE_NAME);
            ArrayList<Producao> municipios = new ArrayList<>();
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                municipios.add(new Producao(
                        resultSet.getInt(Producao.COLLUMN_ID_NAME),
                        resultSet.getString(Producao.COLLUMN_DESCRIPTION_NAME)
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

    public void persist(Producao producao) {
        try {
            String sql = String.format("INSERT INTO %s (%s) VALUES (?)", Producao.TABLE_NAME, Producao.COLLUMN_DESCRIPTION_NAME);
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, producao.getDescricao());
            stmt.executeUpdate();
            connectionPool.releaseConnection(connection);
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
        }
    }

    public void update(Producao municipio) {
        try {
            String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ?", Producao.TABLE_NAME, Producao.COLLUMN_DESCRIPTION_NAME, Producao.COLLUMN_ID_NAME);
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
