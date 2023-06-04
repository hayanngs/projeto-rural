package dev.hayann.repository;

import dev.hayann.database.ConnectionPool;
import dev.hayann.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProducaoRepository implements Repository<Producao> {

//    public Producao findById(int idPropriedade, int idProduto) {
//        try {
//            String sql = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?", Producao.TABLE_NAME, Producao.COLLUMN_ID_PROPRIEDADE_NAME, Producao.COLLUMN_ID_PRODUTO_NAME);
//            ConnectionPool connectionPool = ConnectionPool.getInstance();
//            Connection connection = connectionPool.getConnection();
//            PreparedStatement stmt = connection.prepareStatement(sql);
//            stmt.setInt(1, idPropriedade);
//            stmt.setInt(2, idProduto);
//            ResultSet resultSet = stmt.executeQuery();
//            connectionPool.releaseConnection(connection);
//            if (resultSet.next()) {
//                return new Producao(
//                        resultSet.getInt(Producao.COLLUMN_ID_PROPRIEDADE_NAME),
//                        resultSet.getInt(Producao.COLLUMN_ID_PRODUTO_NAME),
//                        resultSet.getDate(Producao.COLLUMN_DATA_INICIO_PROV_COLHEITA_NAME).toLocalDate(),
//                        resultSet.getDate(Producao.COLLUMN_DATA_FIM_PROV_COLHEITA_NAME).toLocalDate(),
//                        resultSet.getDouble(Producao.COLLUMN_QTD_PROV_COLHIDA_NAME),
//                        resultSet.getDate(Producao.COLLUMN_DATA_INICIO_REAL_COLHEITA_NAME).toLocalDate(),
//                        resultSet.getDate(Producao.COLLUMN_DATA_FIM_REAL_COLHEITA_NAME).toLocalDate(),
//                        resultSet.getDouble(Producao.COLLUMN_QTD_REAL_COLHIDA_NAME)
//                );
//            } else return null;
//        } catch (Exception e) {
//            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
//            return null;
//        }
//    }

    public List<Producao> findAll() throws SQLException {
        String sql = String.format("""
                        select p.%s, p.%s, p.%s, p.%s, p.%s, p.%s, p.%s,\s
                        p2.%s, p2.%s, p2.%s, p2.%s, p2.%s,\s
                        m.%s, m.%s, m.%s, \s
                        p3.%s, p3.%s\s
                        from %s p\s
                        inner join %s p2 on p2.%s = p.%s\s
                        inner join %s p3 on p3.%s = p.%s\s
                        inner join %s m on m.%s = p2.%s\s
                        """,
                Producao.COLLUMN_ID_NAME,
                Producao.COLLUMN_DATA_INICIO_PROV_COLHEITA_NAME,
                Producao.COLLUMN_DATA_FIM_PROV_COLHEITA_NAME,
                Producao.COLLUMN_QTD_PROV_COLHIDA_NAME,
                Producao.COLLUMN_DATA_INICIO_REAL_COLHEITA_NAME,
                Producao.COLLUMN_DATA_FIM_REAL_COLHEITA_NAME,
                Producao.COLLUMN_QTD_REAL_COLHIDA_NAME,
                Propriedade.COLLUMN_ID_NAME,
                Propriedade.COLLUMN_NAME_NAME,
                Propriedade.COLLUMN_AREA_PROPRIEDADE_NAME,
                Propriedade.COLLUMN_DISTANCIA_MUNICIPIO_NAME,
                Propriedade.COLLUMN_VALOR_AQUISICAO_NAME,
                Municipio.COLLUMN_ID_NAME,
                Municipio.COLLUMN_NAME_NAME,
                Municipio.COLLUMN_UF_NAME,
                Produto.COLLUMN_ID_NAME,
                Produto.COLLUMN_DESCRIPTION_NAME,
                Producao.TABLE_NAME,
                Propriedade.TABLE_NAME,
                Propriedade.COLLUMN_ID_NAME,
                Producao.COLLUMN_ID_PROPRIEDADE_NAME,
                Produto.TABLE_NAME,
                Produto.COLLUMN_ID_NAME,
                Producao.COLLUMN_ID_PRODUTO_NAME,
                Municipio.TABLE_NAME,
                Municipio.COLLUMN_ID_NAME,
                Propriedade.COLLUMN_ID_MUNICIPIO_NAME
        );
        ArrayList<Producao> producoes = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            producoes.add(new Producao(
                    resultSet.getInt(Producao.COLLUMN_ID_NAME),
                    resultSet.getDate(Producao.COLLUMN_DATA_INICIO_PROV_COLHEITA_NAME).toLocalDate(),
                    resultSet.getDate(Producao.COLLUMN_DATA_FIM_PROV_COLHEITA_NAME).toLocalDate(),
                    resultSet.getDouble(Producao.COLLUMN_QTD_PROV_COLHIDA_NAME),
                    resultSet.getDate(Producao.COLLUMN_DATA_INICIO_REAL_COLHEITA_NAME).toLocalDate(),
                    resultSet.getDate(Producao.COLLUMN_DATA_FIM_REAL_COLHEITA_NAME).toLocalDate(),
                    resultSet.getDouble(Producao.COLLUMN_QTD_REAL_COLHIDA_NAME),
                    new Propriedade(
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
                    ),
                    new Produto(
                            resultSet.getInt(Produto.COLLUMN_ID_NAME),
                            resultSet.getString(Produto.COLLUMN_DESCRIPTION_NAME)
                    )
            ));
        }
        connectionPool.releaseConnection(connection);
        return producoes;
    }

    public void persist(Producao producao) throws SQLException {
            String sql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING %s", Producao.TABLE_NAME,
                    Producao.COLLUMN_ID_PROPRIEDADE_NAME,
                    Producao.COLLUMN_ID_PRODUTO_NAME,
                    Producao.COLLUMN_DATA_INICIO_PROV_COLHEITA_NAME,
                    Producao.COLLUMN_DATA_FIM_PROV_COLHEITA_NAME,
                    Producao.COLLUMN_QTD_PROV_COLHIDA_NAME,
                    Producao.COLLUMN_DATA_INICIO_REAL_COLHEITA_NAME,
                    Producao.COLLUMN_DATA_FIM_REAL_COLHEITA_NAME,
                    Producao.COLLUMN_QTD_REAL_COLHIDA_NAME,
                    Producao.COLLUMN_ID_NAME
            );
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, producao.getPropriedade().getId());
            stmt.setInt(2, producao.getProduto().getId());
            stmt.setDate(3, Date.valueOf(producao.getDataInicioColheitaProv()));
            stmt.setDate(4, Date.valueOf(producao.getDataFimColheitaProv()));
            stmt.setDouble(5, producao.getQtdProvColhida());
            stmt.setDate(6, Date.valueOf(producao.getDataInicioColheitaReal()));
            stmt.setDate(7, Date.valueOf(producao.getDataFimColheitaReal()));
            stmt.setDouble(8, producao.getQtdRealColhida());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                producao.setId(resultSet.getInt(Producao.COLLUMN_ID_NAME));
            }
            connectionPool.releaseConnection(connection);
    }

    public void update(Producao producao) throws SQLException {
        String sql = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ? AND %s = ?", Producao.TABLE_NAME,
                Producao.COLLUMN_ID_PROPRIEDADE_NAME,
                Producao.COLLUMN_ID_PRODUTO_NAME,
                Producao.COLLUMN_DATA_INICIO_PROV_COLHEITA_NAME,
                Producao.COLLUMN_DATA_FIM_PROV_COLHEITA_NAME,
                Producao.COLLUMN_QTD_PROV_COLHIDA_NAME,
                Producao.COLLUMN_DATA_INICIO_REAL_COLHEITA_NAME,
                Producao.COLLUMN_DATA_FIM_REAL_COLHEITA_NAME,
                Producao.COLLUMN_QTD_REAL_COLHIDA_NAME
        );
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setDate(1, Date.valueOf(producao.getDataInicioColheitaProv()));
        stmt.setDate(2, Date.valueOf(producao.getDataFimColheitaProv()));
        stmt.setDouble(3, producao.getQtdProvColhida());
        stmt.setDate(4, Date.valueOf(producao.getDataInicioColheitaReal()));
        stmt.setDate(5, Date.valueOf(producao.getDataFimColheitaReal()));
        stmt.setDouble(6, producao.getQtdRealColhida());
        stmt.setInt(7, producao.getPropriedade().getId());
        stmt.setInt(8, producao.getProduto().getId());
        stmt.executeUpdate();
        connectionPool.releaseConnection(connection);
    }

    public void delete(Integer id) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", Producao.TABLE_NAME, Producao.COLLUMN_ID_NAME);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}
