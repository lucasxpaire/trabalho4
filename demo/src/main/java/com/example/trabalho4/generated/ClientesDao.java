package com.example.trabalho4.generated;
import java.sql.*;
import java.util.*;

public class ClientesDao {
    private Connection conn;

    public ClientesDao(Connection conn) {
        this.conn = conn;
    }

    public List<Clientes> getAll() throws SQLException {
        List<Clientes> list = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Clientes");
        while (rs.next()) {
            Clientes obj = new Clientes();
            obj.setCpf(rs.getString("cpf"));
            obj.setNome(rs.getString("nome"));
            obj.setDataNascimento(rs.getDate("data_nascimento"));
            obj.setNum(rs.getObject("num"));
            obj.setAa(rs.getFloat("aa"));
            obj.setBb(rs.getDouble("bb"));
            list.add(obj);
        }
        return list;
    }

    public void insert(Clientes obj) throws SQLException {
        String sql = "INSERT INTO Clientes (cpf, nome, data_nascimento, num, aa, bb) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getCpf());
            stmt.setString(2, obj.getNome());
            stmt.setDate(3, obj.getDataNascimento());
            stmt.setObject(4, obj.getNum());
            stmt.setFloat(5, obj.getAa());
            stmt.setDouble(6, obj.getBb());
            stmt.executeUpdate();
        }
    }

    public void delete(Clientes obj) throws SQLException {
        String sql = "DELETE FROM Clientes WHERE cpf = ? AND nome = ? AND data_nascimento = ? AND num = ? AND aa = ? AND bb = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getCpf());
            stmt.setString(2, obj.getNome());
            stmt.setDate(3, obj.getDataNascimento());
            stmt.setObject(4, obj.getNum());
            stmt.setFloat(5, obj.getAa());
            stmt.setDouble(6, obj.getBb());
            stmt.executeUpdate();
        }
    }

}
