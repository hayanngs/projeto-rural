package dev.hayann.repository;

import dev.hayann.database.ConnectionPool;
import dev.hayann.model.Proprietario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioRepository implements Repository<Proprietario> {

    public Proprietario findById(int id) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE %s = ?", Proprietario.TABLE_NAME, Proprietario.COLLUMN_ID_NAME);
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            connectionPool.releaseConnection(connection);
            if (resultSet.next()) {
                return new Proprietario(
                        resultSet.getInt(Proprietario.COLLUMN_ID_NAME),
                        resultSet.getString(Proprietario.COLLUMN_NAME_NAME),
                        resultSet.getInt(Proprietario.COLLUMN_TELEFONE1_NAME),
                        resultSet.getInt(Proprietario.COLLUMN_TELEFONE2_NAME),
                        resultSet.getInt(Proprietario.COLLUMN_TELEFONE3_NAME)
                );
            } else return null;
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
            return null;
        }
    }

    public List<Proprietario> findAll() {
        try {
            String sql = String.format("SELECT * FROM %s", Proprietario.TABLE_NAME);
            ArrayList<Proprietario> municipios = new ArrayList<>();
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                municipios.add(new Proprietario(
                        resultSet.getInt(Proprietario.COLLUMN_ID_NAME),
                        resultSet.getString(Proprietario.COLLUMN_NAME_NAME),
                        resultSet.getInt(Proprietario.COLLUMN_TELEFONE1_NAME),
                        resultSet.getInt(Proprietario.COLLUMN_TELEFONE2_NAME),
                        resultSet.getInt(Proprietario.COLLUMN_TELEFONE3_NAME)
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

    public void persist(Proprietario proprietario) {
        try {
            String sql = String.format(
                    "INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
                    Proprietario.TABLE_NAME,
                    Proprietario.COLLUMN_NAME_NAME,
                    Proprietario.COLLUMN_TELEFONE1_NAME,
                    Proprietario.COLLUMN_TELEFONE2_NAME,
                    Proprietario.COLLUMN_TELEFONE3_NAME
            );
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, proprietario.getName());
            stmt.setDouble(2, proprietario.getTelefone1());
            stmt.setDouble(3, proprietario.getTelefone2());
            stmt.setDouble(4, proprietario.getTelefone3());
            stmt.executeUpdate();
            connectionPool.releaseConnection(connection);
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
        }
    }

    public void update(Proprietario proprietario) {
        try {
            String sql = String.format(
                    "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                    Proprietario.TABLE_NAME,
                    Proprietario.COLLUMN_NAME_NAME,
                    Proprietario.COLLUMN_TELEFONE1_NAME,
                    Proprietario.COLLUMN_TELEFONE2_NAME,
                    Proprietario.COLLUMN_TELEFONE3_NAME,
                    Proprietario.COLLUMN_ID_NAME
            );
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, proprietario.getName());
            stmt.setDouble(2, proprietario.getTelefone1());
            stmt.setDouble(3, proprietario.getTelefone2());
            stmt.setDouble(4, proprietario.getTelefone3());
            stmt.setInt(5, proprietario.getId());
            stmt.executeUpdate();
            connectionPool.releaseConnection(connection);
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
        }
    }
}
