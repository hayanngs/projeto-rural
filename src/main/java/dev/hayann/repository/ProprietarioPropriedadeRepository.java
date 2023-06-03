package dev.hayann.repository;

import dev.hayann.database.ConnectionPool;
import dev.hayann.model.DonoPessoaJuridica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioPropriedadeRepository implements Repository<DonoPessoaJuridica> {

    public DonoPessoaJuridica findById(int idProprietarioPF, int ididProprietarioPJ) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?", DonoPessoaJuridica.TABLE_NAME, DonoPessoaJuridica.COLLUMN_ID_PROPRIETARIO_PF_NAME, DonoPessoaJuridica.COLLUMN_ID_PROPRIETARIO_PJ_NAME);
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idProprietarioPF);
            stmt.setInt(2, ididProprietarioPJ);
            ResultSet resultSet = stmt.executeQuery();
            connectionPool.releaseConnection(connection);
            if (resultSet.next()) {
                return new DonoPessoaJuridica(
                        resultSet.getInt(DonoPessoaJuridica.COLLUMN_ID_PROPRIETARIO_PF_NAME),
                        resultSet.getInt(DonoPessoaJuridica.COLLUMN_ID_PROPRIETARIO_PJ_NAME)
                );
            } else return null;
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
            return null;
        }
    }

    public List<DonoPessoaJuridica> findAll() {
        try {
            String sql = String.format("SELECT * FROM %s", DonoPessoaJuridica.TABLE_NAME);
            ArrayList<DonoPessoaJuridica> producoes = new ArrayList<>();
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                producoes.add(new DonoPessoaJuridica(
                        resultSet.getInt(DonoPessoaJuridica.COLLUMN_ID_PROPRIETARIO_PF_NAME),
                        resultSet.getInt(DonoPessoaJuridica.COLLUMN_ID_PROPRIETARIO_PJ_NAME)
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

    public void persist(DonoPessoaJuridica donoPessoaJuridica) {
        try {
            String sql = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?)", DonoPessoaJuridica.TABLE_NAME,
                    DonoPessoaJuridica.COLLUMN_ID_PROPRIETARIO_PF_NAME,
                    DonoPessoaJuridica.COLLUMN_ID_PROPRIETARIO_PJ_NAME
            );
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, donoPessoaJuridica.getIdProprietarioPessoaFisica());
            stmt.setInt(2, donoPessoaJuridica.getIdProprietarioPessoaJuridica());
            stmt.executeUpdate();
            connectionPool.releaseConnection(connection);
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
        }
    }

    public void update(DonoPessoaJuridica donoPessoaJuridica) {
        try {

        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
        }
    }
}
