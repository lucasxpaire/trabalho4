package com.example.trabalho4.generated;
import java.sql.*;
import java.util.*;

public class ClientesExemplo {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/meta_dados";
        String user = "postgres";
        String password = "kise";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            ClientesDao dao = new ClientesDao(conn);
            List<Clientes> list = dao.getAll();
            for (Clientes obj : list) {
                System.out.println(obj);
            }

            // Exemplo de inserção
            Clientes novoObj = new Clientes();
            novoObj.setCpf("example");
            novoObj.setNome("example");
            novoObj.setDataNascimento(new java.sql.Date(System.currentTimeMillis()));
            novoObj.setNum(generateExampleValue("Object"));
            novoObj.setAa(123.45f);
            novoObj.setBb(123.45);
            dao.insert(novoObj);

            // Exemplo de exclusão
            dao.delete(novoObj);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String generateExampleValue(String type) {
        switch (type) {
            case "int": return (int) (Math.random() * 100);
            case "float": return (float) (Math.random() * 100);
            case "double": return (double) (Math.random() * 100);
            case "String": return "exemplo";
            case "java.sql.Date": return new java.sql.Date(System.currentTimeMillis());
            case "java.sql.Timestamp": return new java.sql.Timestamp(System.currentTimeMillis());
            default: return null;
        }
    }

}
