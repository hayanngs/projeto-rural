package dev.hayann.repository;

import dev.hayann.database.ConnectionPool;
import dev.hayann.model.Municipio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public List<Municipio> findAll() {
        try {
            String sql = String.format("SELECT * FROM %s", Municipio.TABLE_NAME);
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
        } catch (Exception e) {
            e.printStackTrace();
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
            return null;
        }
    }

    public void persist(Municipio municipio) {
        try {
            String sql = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", Municipio.TABLE_NAME, Municipio.COLLUMN_ID_NAME, Municipio.COLLUMN_NAME_NAME, Municipio.COLLUMN_UF_NAME);
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, municipio.getId());
            stmt.setString(2, municipio.getName());
            stmt.setString(3, municipio.getUf());
            stmt.executeUpdate();
            connectionPool.releaseConnection(connection);
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
        }
    }

    public void update(Municipio municipio) {
        try {
            String sql = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", Municipio.TABLE_NAME, Municipio.COLLUMN_NAME_NAME, Municipio.COLLUMN_UF_NAME, Municipio.COLLUMN_ID_NAME);
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, municipio.getName());
            stmt.setString(2, municipio.getUf());
            stmt.setInt(3, municipio.getId());
            stmt.executeUpdate();
            connectionPool.releaseConnection(connection);
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
        }
    }
}
