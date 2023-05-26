package dev.hayann.repository;

import dev.hayann.model.Municipio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MunicipioRepository {

    private final String COLLUMN_ID_MUN = "id_mun";

    private final String COLLUMN_NAM_MUN = "nam_mun";

    private final String COLLUMN_UF_MUN = "uf_mun";

    public Municipio findById(int id) {
        try {
            String sql = "SELECT id, nome, uf FROM municipios WHERE id = ?";
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Municipio(
                        rs.getInt(COLLUMN_ID_MUN),
                        rs.getString(COLLUMN_NAM_MUN),
                        rs.getString(COLLUMN_UF_MUN)
                );
            } else return null;
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
            return null;
        }
    }

    public List<Municipio> findAll() {
        try {
            String sql = "SELECT * FROM MUNICIPIO";
            ArrayList<Municipio> municipios = new ArrayList<>();
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                municipios.add(new Municipio(
                        resultSet.getInt(COLLUMN_ID_MUN),
                        resultSet.getString(COLLUMN_NAM_MUN),
                        resultSet.getString(COLLUMN_UF_MUN)
                ));
            }
            connectionPool.releaseConnection(connection);
            return municipios;
        } catch (Exception e) {
            /* TODO: Criar método de render de erro para renderizar um JDialog de erro na tela */
            return null;
        }
    }

    public void persist(Municipio municipio) {
        try {
            String sql = "INSERT INTO municipios (id, nome, uf) VALUES (?, ?, ?)";
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
            String sql = "UPDATE municipios SET nome = ?, uf = ? WHERE id = ?";
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
