package dev.hayann.repository;

import dev.hayann.database.ConnectionPool;
import dev.hayann.model.Municipio;
import dev.hayann.model.Propriedade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PropriedadeRepository implements Repository<Propriedade> {

    public Propriedade findById(int id) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE %s = ?", Propriedade.TABLE_NAME, Propriedade.COLLUMN_ID_NAME);
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            connectionPool.releaseConnection(connection);
            if (resultSet.next()) {
                return new Propriedade(
                        resultSet.getInt(Propriedade.COLLUMN_ID_NAME),
                        resultSet.getString(Propriedade.COLLUMN_NAME_NAME),
                        resultSet.getDouble(Propriedade.COLLUMN_AREA_PROPRIEDADE_NAME),
                        resultSet.getDouble(Propriedade.COLLUMN_DISTANCIA_MUNICIPIO_NAME),
                        resultSet.getDouble(Propriedade.COLLUMN_VALOR_AQUISICAO_NAME)
                );
            } else return null;
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
            return null;
        }
    }

    public List<Propriedade> findAll() throws SQLException {
        String sql = String.format(
                "SELECT p.%s, p.%s, p.%s, p.%s, p.%s, m.%s, m.%s, m.%s " +
                        "FROM %s p " +
                        "INNER JOIN %s m ON m.%s = p.%s ",
                Propriedade.COLLUMN_ID_NAME,
                Propriedade.COLLUMN_NAME_NAME,
                Propriedade.COLLUMN_AREA_PROPRIEDADE_NAME,
                Propriedade.COLLUMN_DISTANCIA_MUNICIPIO_NAME,
                Propriedade.COLLUMN_VALOR_AQUISICAO_NAME,
                Municipio.COLLUMN_ID_NAME,
                Municipio.COLLUMN_NAME_NAME,
                Municipio.COLLUMN_UF_NAME,
                Propriedade.TABLE_NAME,
                Municipio.TABLE_NAME,
                Municipio.COLLUMN_ID_NAME,
                Propriedade.COLLUMN_ID_MUNICIPIO_NAME
        );
        ArrayList<Propriedade> propriedades = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            propriedades.add(new Propriedade(
                    resultSet.getInt(Propriedade.COLLUMN_ID_NAME),
                    resultSet.getString(Propriedade.COLLUMN_NAME_NAME),
                    resultSet.getDouble(Propriedade.COLLUMN_AREA_PROPRIEDADE_NAME),
                    resultSet.getDouble(Propriedade.COLLUMN_DISTANCIA_MUNICIPIO_NAME),
                    resultSet.getDouble(Propriedade.COLLUMN_VALOR_AQUISICAO_NAME),
                    new Municipio(
                            resultSet.getInt(Municipio.COLLUMN_ID_NAME),
                            resultSet.getString(Municipio.COLLUMN_NAME_NAME),
                            resultSet.getString(Municipio.COLLUMN_UF_NAME)
                    )
            ));
        }
        connectionPool.releaseConnection(connection);
        return propriedades;
    }

    public void persist(Propriedade propriedade) throws SQLException {
        String sql = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?) RETURNING %s",
                Propriedade.TABLE_NAME,
                Propriedade.COLLUMN_NAME_NAME,
                Propriedade.COLLUMN_AREA_PROPRIEDADE_NAME,
                Propriedade.COLLUMN_DISTANCIA_MUNICIPIO_NAME,
                Propriedade.COLLUMN_VALOR_AQUISICAO_NAME,
                Propriedade.COLLUMN_ID_MUNICIPIO_NAME,
                Propriedade.COLLUMN_ID_NAME
        );
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, propriedade.getName());
        stmt.setDouble(2, propriedade.getAreaPropriedade());
        stmt.setDouble(3, propriedade.getDistanciaMunicipio());
        stmt.setDouble(4, propriedade.getValorAquisicao());
        stmt.setInt(5, propriedade.getMunicipio().getId());
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            propriedade.setId(resultSet.getInt(Propriedade.COLLUMN_ID_NAME));
        }
        connectionPool.releaseConnection(connection);
    }

    public void update(Propriedade propriedade) throws SQLException {
        String sql = String.format(
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                Propriedade.TABLE_NAME,
                Propriedade.COLLUMN_NAME_NAME,
                Propriedade.COLLUMN_AREA_PROPRIEDADE_NAME,
                Propriedade.COLLUMN_DISTANCIA_MUNICIPIO_NAME,
                Propriedade.COLLUMN_VALOR_AQUISICAO_NAME,
                Propriedade.COLLUMN_ID_NAME
        );
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, propriedade.getName());
        stmt.setDouble(2, propriedade.getAreaPropriedade());
        stmt.setDouble(3, propriedade.getDistanciaMunicipio());
        stmt.setDouble(4, propriedade.getValorAquisicao());
        stmt.setInt(5, propriedade.getId());
        stmt.executeUpdate();
        connectionPool.releaseConnection(connection);
    }

    public void delete(Integer id) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", Propriedade.TABLE_NAME, Propriedade.COLLUMN_ID_NAME);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}
