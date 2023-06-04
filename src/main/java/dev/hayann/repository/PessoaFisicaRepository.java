package dev.hayann.repository;

import dev.hayann.database.ConnectionPool;
import dev.hayann.model.PessoaFisica;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaRepository implements Repository<PessoaFisica> {

    public PessoaFisica findById(int idPropriedade) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE %s = ?", PessoaFisica.TABLE_NAME, PessoaFisica.COLLUMN_ID_PROPRIETARIO_PF_NAME);
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idPropriedade);
            ResultSet resultSet = stmt.executeQuery();
            connectionPool.releaseConnection(connection);
            if (resultSet.next()) {
                return new PessoaFisica(
                        resultSet.getInt(PessoaFisica.COLLUMN_ID_PROPRIETARIO_PF_NAME),
                        resultSet.getInt(PessoaFisica.COLLUMN_CPF_NAME),
                        resultSet.getInt(PessoaFisica.COLLUMN_RG_NAME),
                        resultSet.getString(PessoaFisica.COLLUMN_NAME_NAME),
                        resultSet.getInt(PessoaFisica.COLLUMN_ID_CONJUGE_NAME),
                        resultSet.getDate(PessoaFisica.COLLUMN_DATA_NASCIMENTO_NAME).toLocalDate()
                );
            } else return null;
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
            return null;
        }
    }

    public PessoaFisica findByName(String name) throws SQLException {
        String sql = String.format(
                "SELECT * " +
                "FROM %s pf " +
                "WHERE pf.%s ILIKE '%%%s%%'",
                PessoaFisica.TABLE_NAME,
                PessoaFisica.COLLUMN_NAME_NAME,
                name
                );
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        PessoaFisica pessoaFisica = null;
        if (resultSet.next()) {
            pessoaFisica = new PessoaFisica(
                    resultSet.getInt(PessoaFisica.COLLUMN_ID_PROPRIETARIO_PF_NAME),
                    resultSet.getInt(PessoaFisica.COLLUMN_CPF_NAME),
                    resultSet.getInt(PessoaFisica.COLLUMN_RG_NAME),
                    resultSet.getString(PessoaFisica.COLLUMN_NAME_NAME),
                    resultSet.getInt(PessoaFisica.COLLUMN_ID_CONJUGE_NAME),
                    resultSet.getDate(PessoaFisica.COLLUMN_DATA_NASCIMENTO_NAME).toLocalDate()
            );
        }
        connectionPool.releaseConnection(connection);
        return pessoaFisica;
    }

    public List<PessoaFisica> findAll() throws SQLException {
        String sql = String.format("SELECT * FROM %s", PessoaFisica.TABLE_NAME);
        ArrayList<PessoaFisica> producoes = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            producoes.add(new PessoaFisica(
                    resultSet.getInt(PessoaFisica.COLLUMN_ID_PROPRIETARIO_PF_NAME),
                    resultSet.getInt(PessoaFisica.COLLUMN_CPF_NAME),
                    resultSet.getInt(PessoaFisica.COLLUMN_RG_NAME),
                    resultSet.getString(PessoaFisica.COLLUMN_NAME_NAME),
                    resultSet.getInt(PessoaFisica.COLLUMN_ID_CONJUGE_NAME),
                    resultSet.getDate(PessoaFisica.COLLUMN_DATA_NASCIMENTO_NAME).toLocalDate()
            ));
        }
        connectionPool.releaseConnection(connection);
        return producoes;
    }

    public void persist(PessoaFisica pessoaFisica) throws SQLException {
        String sql = pessoaFisica.getIdConjuge() == null ?
                String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)", PessoaFisica.TABLE_NAME,
                        PessoaFisica.COLLUMN_ID_PROPRIETARIO_PF_NAME,
                        PessoaFisica.COLLUMN_CPF_NAME,
                        PessoaFisica.COLLUMN_RG_NAME,
                        PessoaFisica.COLLUMN_NAME_NAME,
                        PessoaFisica.COLLUMN_DATA_NASCIMENTO_NAME
                ) :
        String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)", PessoaFisica.TABLE_NAME,
                PessoaFisica.COLLUMN_ID_PROPRIETARIO_PF_NAME,
                PessoaFisica.COLLUMN_CPF_NAME,
                PessoaFisica.COLLUMN_RG_NAME,
                PessoaFisica.COLLUMN_NAME_NAME,
                PessoaFisica.COLLUMN_ID_CONJUGE_NAME,
                PessoaFisica.COLLUMN_DATA_NASCIMENTO_NAME
        );
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, pessoaFisica.getIdProprietarioPessoaFisica());
        stmt.setInt(2, pessoaFisica.getCpf());
        stmt.setInt(3, pessoaFisica.getRg());
        stmt.setString(4, pessoaFisica.getName());
        if (pessoaFisica.getIdConjuge() != null) {
            stmt.setInt(5, pessoaFisica.getIdConjuge());
            stmt.setDate(6, Date.valueOf(pessoaFisica.getDataNascimento()));
        } else {
            stmt.setDate(5, Date.valueOf(pessoaFisica.getDataNascimento()));
        }

        stmt.executeUpdate();
        connectionPool.releaseConnection(connection);
    }

    public void update(PessoaFisica pessoaFisica) throws SQLException {
        String sql = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?", PessoaFisica.TABLE_NAME,
                PessoaFisica.COLLUMN_CPF_NAME,
                PessoaFisica.COLLUMN_RG_NAME,
                PessoaFisica.COLLUMN_NAME_NAME,
                PessoaFisica.COLLUMN_ID_CONJUGE_NAME,
                PessoaFisica.COLLUMN_DATA_NASCIMENTO_NAME,
                PessoaFisica.COLLUMN_ID_PROPRIETARIO_PF_NAME
        );
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(6, pessoaFisica.getIdProprietarioPessoaFisica());
        stmt.setInt(1, pessoaFisica.getCpf());
        stmt.setInt(2, pessoaFisica.getRg());
        stmt.setString(3, pessoaFisica.getName());
        stmt.setInt(4, pessoaFisica.getIdConjuge());
        stmt.setDate(5, Date.valueOf(pessoaFisica.getDataNascimento()));
        stmt.executeUpdate();
        connectionPool.releaseConnection(connection);
    }

    public void delete(Integer id) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", PessoaFisica.TABLE_NAME, PessoaFisica.COLLUMN_ID_PROPRIETARIO_PF_NAME);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}
