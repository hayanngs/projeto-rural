package dev.hayann.repository;

import dev.hayann.database.ConnectionPool;
import dev.hayann.model.Producao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProducaoRepository implements Repository<Producao> {

    public Producao findById(int idPropriedade, int idProduto) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?", Producao.TABLE_NAME, Producao.COLLUMN_ID_PROPRIEDADE_NAME, Producao.COLLUMN_ID_PRODUTO_NAME);
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idPropriedade);
            stmt.setInt(2, idProduto);
            ResultSet resultSet = stmt.executeQuery();
            connectionPool.releaseConnection(connection);
            if (resultSet.next()) {
                return new Producao(
                        resultSet.getInt(Producao.COLLUMN_ID_PROPRIEDADE_NAME),
                        resultSet.getInt(Producao.COLLUMN_ID_PRODUTO_NAME),
                        resultSet.getDate(Producao.COLLUMN_DATA_INICIO_PROV_COLHEITA_NAME).toLocalDate(),
                        resultSet.getDate(Producao.COLLUMN_DATA_FIM_PROV_COLHEITA_NAME).toLocalDate(),
                        resultSet.getDouble(Producao.COLLUMN_QTD_PROV_COLHIDA_NAME),
                        resultSet.getDate(Producao.COLLUMN_DATA_INICIO_REAL_COLHEITA_NAME).toLocalDate(),
                        resultSet.getDate(Producao.COLLUMN_DATA_FIM_REAL_COLHEITA_NAME).toLocalDate(),
                        resultSet.getDouble(Producao.COLLUMN_QTD_REAL_COLHIDA_NAME)
                );
            } else return null;
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
            return null;
        }
    }

    public List<Producao> findAll() {
        try {
            String sql = String.format("SELECT * FROM %s", Producao.TABLE_NAME);
            ArrayList<Producao> producoes = new ArrayList<>();
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                producoes.add(new Producao(
                        resultSet.getInt(Producao.COLLUMN_ID_PROPRIEDADE_NAME),
                        resultSet.getInt(Producao.COLLUMN_ID_PRODUTO_NAME),
                        resultSet.getDate(Producao.COLLUMN_DATA_INICIO_PROV_COLHEITA_NAME).toLocalDate(),
                        resultSet.getDate(Producao.COLLUMN_DATA_FIM_PROV_COLHEITA_NAME).toLocalDate(),
                        resultSet.getDouble(Producao.COLLUMN_QTD_PROV_COLHIDA_NAME),
                        resultSet.getDate(Producao.COLLUMN_DATA_INICIO_REAL_COLHEITA_NAME).toLocalDate(),
                        resultSet.getDate(Producao.COLLUMN_DATA_FIM_REAL_COLHEITA_NAME).toLocalDate(),
                        resultSet.getDouble(Producao.COLLUMN_QTD_REAL_COLHIDA_NAME)
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

    public void persist(Producao producao) {
        try {
            String sql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Producao.TABLE_NAME,
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
            stmt.setInt(1, producao.getIdPropriedade());
            stmt.setInt(2, producao.getIdProduto());
            stmt.setDate(3, Date.valueOf(producao.getDataInicioColheitaProv()));
            stmt.setDate(4, Date.valueOf(producao.getDataFimColheitaProv()));
            stmt.setDouble(5, producao.getQtdProvColhida());
            stmt.setDate(6, Date.valueOf(producao.getDataInicioColheitaReal()));
            stmt.setDate(7, Date.valueOf(producao.getDataFimColheitaReal()));
            stmt.setDouble(8, producao.getQtdRealColhida());
            stmt.executeUpdate();
            connectionPool.releaseConnection(connection);
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
        }
    }

    public void update(Producao producao) {
        try {
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
            stmt.setInt(7, producao.getIdPropriedade());
            stmt.setInt(8, producao.getIdProduto());
            stmt.executeUpdate();
            connectionPool.releaseConnection(connection);
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
        }
    }
}
