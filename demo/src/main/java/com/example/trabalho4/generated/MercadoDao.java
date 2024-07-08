package com.example.trabalho4.generated;
import java.sql.*;
import java.util.*;

public class MercadoDao {
    private Connection conn;

    public MercadoDao(Connection conn) {
        this.conn = conn;
    }

    public List<Mercado> getAll() throws SQLException {
        List<Mercado> list = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM \"Mercado\" ");
        while (rs.next()) {
            Mercado obj = new Mercado();
            obj.setProduto(rs.getString("produto"));
            obj.setPreco(rs.getFloat("preco"));
            obj.setIdProduto(rs.getInt("id_produto"));
            list.add(obj);
        }
        return list;
    }

    public void insert(Mercado obj) throws SQLException {
        String sql = "INSERT INTO \"Mercado\" (produto, preco, id_produto) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getProduto());
            stmt.setFloat(2, obj.getPreco());
            stmt.setInt(3, obj.getIdProduto());
            stmt.executeUpdate();
        }
    }

    public void delete(Mercado obj) throws SQLException {
        String sql = "DELETE FROM \"Mercado\" WHERE produto = ? AND preco = ? AND id_produto = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getProduto());
            stmt.setFloat(2, obj.getPreco());
            stmt.setInt(3, obj.getIdProduto());
            stmt.executeUpdate();
        }
    }

}
