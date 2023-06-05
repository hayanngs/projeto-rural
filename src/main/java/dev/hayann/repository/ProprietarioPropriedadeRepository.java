package dev.hayann.repository;

import dev.hayann.database.ConnectionPool;
import dev.hayann.model.ProprietarioPropriedade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioPropriedadeRepository implements Repository<ProprietarioPropriedade> {

    public ProprietarioPropriedade findById(int idPropriedade, int idProprietario) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?", ProprietarioPropriedade.TABLE_NAME, ProprietarioPropriedade.COLLUMN_ID_PROPRIEDADE_NAME, ProprietarioPropriedade.COLLUMN_ID_PROPRIETARIO_NAME);
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idPropriedade);
            stmt.setInt(2, idProprietario);
            ResultSet resultSet = stmt.executeQuery();
            connectionPool.releaseConnection(connection);
            if (resultSet.next()) {
                return new ProprietarioPropriedade(
                        resultSet.getInt(ProprietarioPropriedade.COLLUMN_ID_PROPRIEDADE_NAME),
                        resultSet.getInt(ProprietarioPropriedade.COLLUMN_ID_PROPRIETARIO_NAME),
                        resultSet.getDate(ProprietarioPropriedade.COLUMN_DATA_AQUISICAO_NAME).toLocalDate()
                );
            } else return null;
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
            return null;
        }
    }

    public List<ProprietarioPropriedade> findAll() {
        try {
            String sql = String.format("SELECT * FROM %s", ProprietarioPropriedade.TABLE_NAME);
            ArrayList<ProprietarioPropriedade> producoes = new ArrayList<>();
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                producoes.add(new ProprietarioPropriedade(
                        resultSet.getInt(ProprietarioPropriedade.COLLUMN_ID_PROPRIEDADE_NAME),
                        resultSet.getInt(ProprietarioPropriedade.COLLUMN_ID_PROPRIETARIO_NAME),
                        resultSet.getDate(ProprietarioPropriedade.COLUMN_DATA_AQUISICAO_NAME).toLocalDate()
                ));
            }
            connectionPool.releaseConnection(connection);
            return producoes;
        } catch (Exception e) {
            e.printStackTrace();
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
            return null;
        }
    }

    public void persist(ProprietarioPropriedade proprietarioPropriedade) {
        try {
            String sql = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", ProprietarioPropriedade.TABLE_NAME,
                    ProprietarioPropriedade.COLLUMN_ID_PROPRIEDADE_NAME,
                    ProprietarioPropriedade.COLLUMN_ID_PROPRIETARIO_NAME,
                    ProprietarioPropriedade.COLUMN_DATA_AQUISICAO_NAME
            );
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, proprietarioPropriedade.getIdPropriedade());
            stmt.setInt(2, proprietarioPropriedade.getIdProprietario());
            stmt.setDate(3, Date.valueOf(proprietarioPropriedade.getDateAcquisition()));
            stmt.executeUpdate();
            connectionPool.releaseConnection(connection);
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
        }
    }

    public void update(ProprietarioPropriedade proprietarioPropriedade) {
        try {

        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
        }
    }

    public void delete(Integer idProprietario, Integer idPropriedade) throws SQLException {
        String sql = String.format(
                "DELETE FROM %s WHERE %s = ? AND %s = ?",
                ProprietarioPropriedade.TABLE_NAME,
                ProprietarioPropriedade.COLLUMN_ID_PROPRIETARIO_NAME,
                ProprietarioPropriedade.COLLUMN_ID_PROPRIEDADE_NAME
        );
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, idProprietario);
        stmt.setInt(2, idPropriedade);
        stmt.executeUpdate();
    }
}
