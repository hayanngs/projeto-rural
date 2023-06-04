package dev.hayann.repository;

import dev.hayann.database.ConnectionPool;
import dev.hayann.model.Municipio;
import dev.hayann.view.municipio.MunicipioUpdate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MunicipioRepository implements Repository<Municipio> {

    public Municipio findById(int id) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE %s = ?", Municipio.TABLE_NAME, Municipio.COLLUMN_ID_NAME);
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            connectionPool.releaseConnection(connection);
            if (rs.next()) {
                return new Municipio(
                        rs.getInt(Municipio.COLLUMN_ID_NAME),
                        rs.getString(Municipio.COLLUMN_NAME_NAME),
                        rs.getString(Municipio.COLLUMN_UF_NAME)
                );
            } else return null;
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
            return null;
        }
    }

    public Municipio findByName(String name) throws SQLException {
        String sql = String.format(
                "SELECT * FROM %s m WHERE m.%s ILIKE '%%%s%%'",
                Municipio.TABLE_NAME,
                Municipio.COLLUMN_NAME_NAME,
                name
        );
        ArrayList<Municipio> municipios = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        Municipio municipio = null;
        if (resultSet.next()) {
            municipio = new Municipio(
                    resultSet.getInt(Municipio.COLLUMN_ID_NAME),
                    resultSet.getString(Municipio.COLLUMN_NAME_NAME),
                    resultSet.getString(Municipio.COLLUMN_UF_NAME)
            );
        }
        connectionPool.releaseConnection(connection);
        return municipio;
    }

    public List<Municipio> findAll() throws SQLException {
        String sql = String.format("SELECT * FROM %s ORDER BY %s", Municipio.TABLE_NAME, Municipio.COLLUMN_ID_NAME);
        ArrayList<Municipio> municipios = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            municipios.add(new Municipio(
                    resultSet.getInt(Municipio.COLLUMN_ID_NAME),
                    resultSet.getString(Municipio.COLLUMN_NAME_NAME),
                    resultSet.getString(Municipio.COLLUMN_UF_NAME)
            ));
        }
        connectionPool.releaseConnection(connection);
        return municipios;
    }

    public void persist(Municipio municipio) throws SQLException {
        String sql = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?) RETURNING %s", Municipio.TABLE_NAME, Municipio.COLLUMN_NAME_NAME, Municipio.COLLUMN_UF_NAME, Municipio.COLLUMN_ID_NAME);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, municipio.getName());
        stmt.setString(2, municipio.getUf());
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            municipio.setId(resultSet.getInt(Municipio.COLLUMN_ID_NAME));
        }
        connectionPool.releaseConnection(connection);
    }

    public void update(Municipio municipio) throws SQLException {
        String sql = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", Municipio.TABLE_NAME, Municipio.COLLUMN_NAME_NAME, Municipio.COLLUMN_UF_NAME, Municipio.COLLUMN_ID_NAME);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, municipio.getName());
        stmt.setString(2, municipio.getUf());
        stmt.setInt(3, municipio.getId());
        stmt.executeUpdate();
        connectionPool.releaseConnection(connection);
    }

    public void delete(Integer id) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", Municipio.TABLE_NAME, Municipio.COLLUMN_ID_NAME);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}
