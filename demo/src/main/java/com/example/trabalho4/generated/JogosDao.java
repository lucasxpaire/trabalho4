package com.example.trabalho4.generated;
import java.sql.*;
import java.util.*;

public class JogosDao {
    private Connection conn;

    public JogosDao(Connection conn) {
        this.conn = conn;
    }

    public List<Jogos> getAll() throws SQLException {
        List<Jogos> list = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM \"Jogos\" ");
        while (rs.next()) {
            Jogos obj = new Jogos();
            obj.setNome(rs.getString("nome"));
            obj.setValor(rs.getFloat("valor"));
            obj.setNota(rs.getInt("nota"));
            list.add(obj);
        }
        return list;
    }

    public void insert(Jogos obj) throws SQLException {
        String sql = "INSERT INTO \"Jogos\" (nome, valor, nota) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getNome());
            stmt.setFloat(2, obj.getValor());
            stmt.setInt(3, obj.getNota());
            stmt.executeUpdate();
        }
    }

    public void delete(Jogos obj) throws SQLException {
        String sql = "DELETE FROM \"Jogos\" WHERE nome = ? AND valor = ? AND nota = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getNome());
            stmt.setFloat(2, obj.getValor());
            stmt.setInt(3, obj.getNota());
            stmt.executeUpdate();
        }
    }

}
