package com.example.trabalho4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

public class Utils {
    public static String toClassName(String name) {
        String[] parts = name.split("_");
        StringBuilder className = new StringBuilder();
        for (String part : parts) {
            className.append(part.substring(0, 1).toUpperCase()).append(part.substring(1).toLowerCase());
        }
        return className.toString();
    }

    public static void generateEntityClass(String tableName, List<Column> columns) {
        String className = toClassName(tableName);
        try (PrintWriter writer = new PrintWriter(className + ".java")) {
            writer.println("public class " + className + " {");
            for (Column column : columns) {
                writer.println("    private " + column.getJavaType() + " " + column.getName() + ";");
            }
            writer.println();
            for (Column column : columns) {
                writer.println("    public " + column.getJavaType() + " get" + toClassName(column.getName()) + "() {");
                writer.println("        return " + column.getName() + ";");
                writer.println("    }");
                writer.println();
                writer.println("    public void set" + toClassName(column.getName()) + "(" + column.getJavaType() + " " + column.getName() + ") {");
                writer.println("        this." + column.getName() + " = " + column.getName() + ";");
                writer.println("    }");
                writer.println();
            }
            writer.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateDaoClass(String tableName, List<Column> columns) {
        String className = toClassName(tableName) + "Dao";
        try (PrintWriter writer = new PrintWriter(className + ".java")) {
            writer.println("import java.sql.*;");
            writer.println("import java.util.*;");
            writer.println();
            writer.println("public class " + className + " {");
            writer.println("    private Connection conn;");
            writer.println();
            writer.println("    public " + className + "(Connection conn) {");
            writer.println("        this.conn = conn;");
            writer.println("    }");
            writer.println();
    
            // Método getAll
            writer.println("    public List<" + toClassName(tableName) + "> getAll() throws SQLException {");
            writer.println("        List<" + toClassName(tableName) + "> list = new ArrayList<>();");
            writer.println("        Statement stmt = conn.createStatement();");
            writer.println("        ResultSet rs = stmt.executeQuery(\"SELECT * FROM " + tableName + "\");");
            writer.println("        while (rs.next()) {");
            writer.println("            " + toClassName(tableName) + " obj = new " + toClassName(tableName) + "();");
            for (Column column : columns) {
                writer.println("            obj.set" + toClassName(column.getName()) + "(rs.get" + column.getResultSetMethod() + "(\"" + column.getName() + "\"));");
            }
            writer.println("            list.add(obj);");
            writer.println("        }");
            writer.println("        return list;");
            writer.println("    }");
            writer.println();
    
            // Método insert
            writer.println("    public void insert(" + toClassName(tableName) + " obj) throws SQLException {");
            writer.print("        String sql = \"INSERT INTO " + tableName + " (");
            for (int i = 0; i < columns.size(); i++) {
                writer.print(columns.get(i).getName());
                if (i < columns.size() - 1) {
                    writer.print(", ");
                }
            }
            writer.print(") VALUES (");
            for (int i = 0; i < columns.size(); i++) {
                writer.print("?");
                if (i < columns.size() - 1) {
                    writer.print(", ");
                }
            }
            writer.println(")\";");
            writer.println("        try (PreparedStatement stmt = conn.prepareStatement(sql)) {");
            int index = 1;
            for (Column column : columns) {
                writer.println("            stmt.set" + column.getPreparedStatementMethod() + "(" + index++ + ", obj.get" + toClassName(column.getName()) + "());");
            }
            writer.println("            stmt.executeUpdate();");
            writer.println("        }");
            writer.println("    }");
            writer.println();
    
            // Método delete
            writer.println("    public void delete(" + toClassName(tableName) + " obj) throws SQLException {");
            writer.print("        String sql = \"DELETE FROM " + tableName + " WHERE ");
            for (int i = 0; i < columns.size(); i++) {
                writer.print(columns.get(i).getName() + " = ?");
                if (i < columns.size() - 1) {
                    writer.print(" AND ");
                }
            }
            writer.println("\";");
            writer.println("        try (PreparedStatement stmt = conn.prepareStatement(sql)) {");
            index = 1;
            for (Column column : columns) {
                writer.println("            stmt.set" + column.getPreparedStatementMethod() + "(" + index++ + ", obj.get" + toClassName(column.getName()) + "());");
            }
            writer.println("            stmt.executeUpdate();");
            writer.println("        }");
            writer.println("    }");
            writer.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public static void generateExampleClass(String tableName, List<Column> columns) {
        String className = toClassName(tableName) + "Exemplo";
        try (PrintWriter writer = new PrintWriter(className + ".java")) {
            writer.println("import java.sql.*;");
            writer.println("import java.util.*;");
            writer.println();
            writer.println("public class " + className + " {");
            writer.println("    public static void main(String[] args) {");
            writer.println("        String url = \"jdbc:postgresql://localhost:5432/meta_dados\";");
            writer.println("        String user = \"postgres\";");
            writer.println("        String password = \"lucas\";");
            writer.println();
            writer.println("        try (Connection conn = DriverManager.getConnection(url, user, password)) {");
            writer.println("            " + toClassName(tableName) + "Dao dao = new " + toClassName(tableName) + "Dao(conn);");
            writer.println("            List<" + toClassName(tableName) + "> list = dao.getAll();");
            writer.println("            for (" + toClassName(tableName) + " obj : list) {");
            writer.println("                System.out.println(obj);");
            writer.println("            }");
            writer.println();

            // Exemplo de inserção
            writer.println("            // Exemplo de inserção");
            writer.println("            " + toClassName(tableName) + " novoObj = new " + toClassName(tableName) + "();");
            for (Column column : columns) {
                String columnName = column.getName();
                String javaType = column.getJavaType();
                writer.print("            ");

                Object javataType = randomObject();
            }
            writer.println("            dao.insert(novoObj);");

            // Exemplo de remoção
            writer.println();
            writer.println("            // Exemplo de remoção");
            writer.println("            // " + toClassName(tableName) + " objParaRemover = list.get(0); // Exemplo de objeto a ser removido");
            writer.println("            // dao.delete(objParaRemover);");

            writer.println("        } catch (SQLException e) {");
            writer.println("            e.printStackTrace();");
            writer.println("        }");
            writer.println("    }");
            writer.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int randomInt(){
        return new Random().nextInt();
    }

    public static float randomFloat(){
        return new Random().nextFloat();
    }

    public static long randomLong(){
        return new Random().nextLong();
    }

    public static String randomString(){
        int length = 10; // Tamanho da string
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = new Random().nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    public static java.util.Date randomDate() {
        long offset = Timestamp.valueOf("2020-01-01 00:00:00").getTime(); // Timestamp de início (1 de janeiro de 2020)
        long end = Timestamp.valueOf("2023-12-31 23:59:59").getTime(); // Timestamp de fim (31 de dezembro de 2023)
        long diff = end - offset + 1;
        return new java.util.Date(offset + (long)(Math.random() * diff)); // Retorna uma data aleatória dentro do intervalo especificado
    }

    public static Object randomObject() {
            Random random = new Random();
            int choice = random.nextInt(4); // Gera um número aleatório entre 0 e 3

            switch (choice) {
                case 0:
                    return randomInt();
                case 1:
                    return randomFloat();
                case 2:
                    return randomString();
                case 3:
                    return randomDate();
                default:
                    return null; // Caso inesperado
            }
        }
}
//
//int, float, string, data