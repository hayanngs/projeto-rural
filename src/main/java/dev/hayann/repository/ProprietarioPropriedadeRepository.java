package dev.hayann.repository;

import dev.hayann.database.ConnectionPool;
import dev.hayann.dto.Questao2DTO;
import dev.hayann.model.ProprietarioPropriedade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioPropriedadeRepository implements Repository<ProprietarioPropriedade> {

    public ArrayList<Questao2DTO> findQuestao2() throws SQLException {
        String sql = "select pf.nam_pf, pj.razao_social_pj, p.nam_propriedade, p.area_propriedade, p.valor_aquisicao " +
                "from proprietario_propriedade pp " +
                "inner join propriedade p on p.id_propriedade = pp.id_propriedade " +
                "inner join pessoa_juridica pj on pj.id_proprietario_pj = pp.id_proprietario " +
                "inner join dono_pj dp on dp.id_proprietario_pj = pj.id_proprietario_pj " +
                "inner join pessoa_fisica pf on pf.id_proprietario_pf = dp.id_proprietario_pf ";

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        connectionPool.releaseConnection(connection);
        ArrayList<Questao2DTO> questao2DTOList = new ArrayList<>();
        while (resultSet.next()) {
            questao2DTOList.add(new Questao2DTO(
                    resultSet.getString("nam_pf"),
                    resultSet.getString("razao_social_pj"),
                    resultSet.getString("nam_propriedade"),
                    resultSet.getDouble("area_propriedade"),
                    resultSet.getDouble("valor_aquisicao")
            ));
        }
        return questao2DTOList;
    }

    public List<ProprietarioPropriedade> findAll() throws SQLException {
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
    }

    public void persist(ProprietarioPropriedade proprietarioPropriedade) throws SQLException {
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
    }

    public void update(ProprietarioPropriedade proprietarioPropriedade) {
        /* TODO: Método não implementado */
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
