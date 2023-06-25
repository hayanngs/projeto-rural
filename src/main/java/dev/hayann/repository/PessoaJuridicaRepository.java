package dev.hayann.repository;

import dev.hayann.database.ConnectionPool;
import dev.hayann.model.PessoaJuridica;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaJuridicaRepository implements Repository<PessoaJuridica> {

    public PessoaJuridica findById(int idPropriedade) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE %s = ?", PessoaJuridica.TABLE_NAME, PessoaJuridica.COLLUMN_ID_PROPRIETARIO_NAME);
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idPropriedade);
            ResultSet resultSet = stmt.executeQuery();
            connectionPool.releaseConnection(connection);
            if (resultSet.next()) {
                return new PessoaJuridica(
                        resultSet.getInt(PessoaJuridica.COLLUMN_ID_PROPRIETARIO_NAME),
                        resultSet.getLong(PessoaJuridica.COLLUMN_CNPJ_NAME),
                        resultSet.getString(PessoaJuridica.COLLUMN_RAZAO_SOCIAL_NAME),
                        resultSet.getDate(PessoaJuridica.COLUMN_DATA_CRIACAO_NAME).toLocalDate()
                );
            } else return null;
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
            return null;
        }
    }

    public List<PessoaJuridica> findAll() throws SQLException {
        String sql = String.format("SELECT * FROM %s", PessoaJuridica.TABLE_NAME);
        ArrayList<PessoaJuridica> producoes = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            producoes.add(new PessoaJuridica(
                    resultSet.getInt(PessoaJuridica.COLLUMN_ID_PROPRIETARIO_NAME),
                    resultSet.getLong(PessoaJuridica.COLLUMN_CNPJ_NAME),
                    resultSet.getString(PessoaJuridica.COLLUMN_RAZAO_SOCIAL_NAME),
                    resultSet.getDate(PessoaJuridica.COLUMN_DATA_CRIACAO_NAME).toLocalDate()
            ));
        }
        connectionPool.releaseConnection(connection);
        return producoes;
    }

    public void persist(PessoaJuridica pessoaJuridica) throws SQLException {
        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)", PessoaJuridica.TABLE_NAME,
                PessoaJuridica.COLLUMN_ID_PROPRIETARIO_NAME,
                PessoaJuridica.COLLUMN_CNPJ_NAME,
                PessoaJuridica.COLLUMN_RAZAO_SOCIAL_NAME,
                PessoaJuridica.COLUMN_DATA_CRIACAO_NAME
        );
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, pessoaJuridica.getId());
        stmt.setLong(2, pessoaJuridica.getCnpj());
        stmt.setString(3, pessoaJuridica.getRazaoSocial());
        stmt.setDate(4, Date.valueOf(pessoaJuridica.getDateCreation()));
        stmt.executeUpdate();
        connectionPool.releaseConnection(connection);
    }

    public void update(PessoaJuridica pessoaJuridica) throws SQLException {
        String sql = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?", PessoaJuridica.TABLE_NAME,
                PessoaJuridica.COLLUMN_CNPJ_NAME,
                PessoaJuridica.COLLUMN_RAZAO_SOCIAL_NAME,
                PessoaJuridica.COLUMN_DATA_CRIACAO_NAME,
                PessoaJuridica.COLLUMN_ID_PROPRIETARIO_NAME
        );
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, pessoaJuridica.getCnpj());
        stmt.setString(2, pessoaJuridica.getRazaoSocial());
        stmt.setDate(3, Date.valueOf(pessoaJuridica.getDateCreation()));
        stmt.setInt(4, pessoaJuridica.getId());
        stmt.executeUpdate();
        connectionPool.releaseConnection(connection);
    }

    public void delete(Integer id) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", PessoaJuridica.TABLE_NAME, PessoaJuridica.COLLUMN_ID_PROPRIETARIO_NAME);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}
