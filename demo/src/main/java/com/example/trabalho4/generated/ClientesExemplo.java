package com.example.trabalho4.generated;
import java.sql.*;
import java.sql.Date;
import java.util.*;


public class ClientesExemplo {
        private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/meta_dados";
        String user = "postgres";
        String password = "kise";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            ClientesDao dao = new ClientesDao(connection);
            // Exemplo de insercao
            Clientes novoObj = new Clientes();
            novoObj.setCpf(getRandomString());
            novoObj.setNome(getRandomString());
            novoObj.setDataNascimento(Date.valueOf(getRandomDate()));
            novoObj.setNum(RANDOM.nextInt(10000));
            novoObj.setAa(RANDOM.nextFloat() * 100);
            novoObj.setBb(RANDOM.nextDouble() * 100);
            dao.insert(novoObj);

            // Exemplo de listagem
            List<Clientes> list = dao.getAll();
            for (Clientes obj : list) {
                System.out.print(obj.getCpf() + " | ");
                System.out.print(obj.getNome() + " | ");
                System.out.print(obj.getDataNascimento() + " | ");
                System.out.print(obj.getNum() + " | ");
                System.out.print(obj.getAa() + " | ");
                System.out.print(obj.getBb() + " | ");
                System.out.println();
            }

            // Exemplo de exclusao
            // Comentar para adicionar varios dados no banco
            dao.delete(novoObj);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getRandomString() {
        int length = RANDOM.nextInt(10) + 1;
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = (char) (RANDOM.nextInt(26) + 'a');
            sb.append(c);
        }
        return sb.toString();
    }

    private static String getRandomDecimal() {
        return String.format("%.2f", RANDOM.nextDouble() * 10000);
    }

    private static String getRandomDate() {
        int year = RANDOM.nextInt(100) + 1900;
        int month = RANDOM.nextInt(12) + 1;
        int day = RANDOM.nextInt(28) + 1;
        return String.format("%04d-%02d-%02d", year, month, day); // Data no formato YYYY-MM-DD
    }

    private static String getRandomTime() {
        int hour = RANDOM.nextInt(24); 
        int minute = RANDOM.nextInt(60);
        int second = RANDOM.nextInt(60);
        return String.format("%02d:%02d:%02d", hour, minute, second); // Tempo no formato HH:MM:SS
    }

    private static String getRandomTimestamp() {
        return getRandomDate() + " " + getRandomTime(); // Timestamp como combinação de data e hora
    }

    private static byte[] getRandomByteArray() {
        byte[] bytes = new byte[RANDOM.nextInt(10) + 1];
        RANDOM.nextBytes(bytes);
        return bytes;
    }

}
