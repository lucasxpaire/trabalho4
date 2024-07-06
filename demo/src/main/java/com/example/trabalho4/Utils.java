package com.example.trabalho4;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;

public class Utils {
    public static String toClassName(String name) {
        String[] parts = name.split("_");
        StringBuilder className = new StringBuilder();
        for (String part : parts) {
            className.append(part.substring(0, 1).toUpperCase()).append(part.substring(1).toLowerCase());
        }
        return className.toString();
    }
    
    public static void generateEntityClass(String outputDir, String tableName, List<Column> columns) {
        String className = toClassName(tableName);
        String filePath = outputDir + File.separator + className + ".java";
    
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("package com.example.trabalho4.generated;");
            writer.println();
            writer.println("public class " + className + " {");
    
            // Atributos
            for (Column column : columns) {
                String columnName = column.getName();
                String javaType = column.getJavaType(); // Utiliza o método getJavaType() da classe Column
                writer.println("    private " + javaType + " " + columnName + ";");
            }
            writer.println();
    
            // Getters e Setters
            for (Column column : columns) {
                String columnName = column.getName();
                String javaType = column.getJavaType(); // Utiliza o método getJavaType() da classe Column
    
                // Getter
                writer.println("    public " + javaType + " get" + toClassName(columnName) + "() {");
                writer.println("        return " + columnName + ";");
                writer.println("    }");
                writer.println();
    
                // Setter
                writer.println("    public void set" + toClassName(columnName) + "(" + javaType + " " + columnName + ") {");
                writer.println("        this." + columnName + " = " + columnName + ";");
                writer.println("    }");
                writer.println();
            }
    
            writer.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public static void generateExampleClass(String outputDir, String tableName, List<Column> columns) {
        String className = toClassName(tableName) + "Exemplo";
        String filePath = outputDir + File.separator + className + ".java";
    
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("package com.example.trabalho4.generated;");
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
                String javaType = column.getJavaType(); // Utiliza o método getJavaType() da classe Column
                writer.print("            ");
                switch (javaType) {
                    case "int":
                        writer.println("novoObj.set" + toClassName(columnName) + "(" + randomInt() + ");");
                        break;
                    case "long":
                        writer.println("novoObj.set" + toClassName(columnName) + "(" + randomLong() + "L);");
                        break;
                    case "float":
                        writer.println("novoObj.set" + toClassName(columnName) + "(" + randomFloat() + "F);");
                        break;
                    case "double":
                        writer.println("novoObj.set" + toClassName(columnName) + "(" + randomDouble() + "D);");
                        break;
                    case "String":
                        writer.println("novoObj.set" + toClassName(columnName) + "(\"" + randomString() + "\");");
                        break;
                    case "java.util.Date":
                        writer.println("novoObj.set" + toClassName(columnName) + "(new java.util.Date(" + randomDate().getTime() + "L));");
                        break;
                    default:
                        writer.println("// Tipo não suportado: " + javaType);
                        break;
                }
            }
            writer.println("            dao.insert(novoObj);");
    
            writer.println("        } catch (SQLException e) {");
            writer.println("            e.printStackTrace();");
            writer.println("        }");
            writer.println("    }");
    
            writer.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void generateDaoClass(String outputDir, String tableName, List<Column> columns) {
        String className = toClassName(tableName) + "Dao";
        String entityClassName = toClassName(tableName);
    
        String filePath = outputDir + File.separator + className + ".java";
    
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("package com.example.trabalho4.generated;");
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
            writer.println("    public List<" + entityClassName + "> getAll() throws SQLException {");
            writer.println("        List<" + entityClassName + "> list = new ArrayList<>();");
            writer.println("        Statement stmt = conn.createStatement();");
            writer.println("        ResultSet rs = stmt.executeQuery(\"SELECT * FROM " + tableName + "\");");
            writer.println("        while (rs.next()) {");
            writer.println("            " + entityClassName + " obj = new " + entityClassName + "();");
            for (Column column : columns) {
                String columnName = column.getName();
                String javaType = column.getJavaType(); // Utiliza o método getJavaType() da classe Column
                writer.println("            obj.set" + toClassName(columnName) + "(rs.get" + Column.toResultSetMethod(javaType) + "(\"" + columnName + "\"));");
            }
            writer.println("            list.add(obj);");
            writer.println("        }");
            writer.println("        return list;");
            writer.println("    }");
            writer.println();
    
            // Método insert
            writer.println("    public void insert(" + entityClassName + " obj) throws SQLException {");
            writer.print("        String sql = \"INSERT INTO " + tableName + " (");
            for (int i = 0; i < columns.size(); i++) {
                writer.print(columns.get(i).getName());
                if (i < columns.size() - 1) writer.print(", ");
                else writer.print(") VALUES (");
            }
            for (int i = 0; i < columns.size(); i++) {
                writer.print("?");
                if (i < columns.size() - 1) writer.print(", ");
                else writer.println(")\";");
            }
            writer.println("        try (PreparedStatement stmt = conn.prepareStatement(sql)) {");
            for (int i = 0; i < columns.size(); i++) {
                String columnType = columns.get(i).getJavaType(); // Utiliza o método getJavaType() da classe Column
                writer.println("            stmt.set" + Column.toPreparedStatementMethod(columnType) + "(" + (i+1) + ", obj.get" + toClassName(columns.get(i).getName()) + "());");
            }
            writer.println("            stmt.executeUpdate();");
            writer.println("        }");
            writer.println("    }");
            writer.println();
    
            // Método delete
            writer.println("    public void delete(" + entityClassName + " obj) throws SQLException {");
            writer.print("        String sql = \"DELETE FROM " + tableName + " WHERE ");
            for (int i = 0; i < columns.size(); i++) {
                writer.print(columns.get(i).getName() + " = ?");
                if (i < columns.size() - 1) writer.print(" AND ");
                else writer.println("\";");
            }
            writer.println("        try (PreparedStatement stmt = conn.prepareStatement(sql)) {");
            for (int i = 0; i < columns.size(); i++) {
                String columnType = columns.get(i).getJavaType(); // Utiliza o método getJavaType() da classe Column
                writer.println("            stmt.set" + Column.toPreparedStatementMethod(columnType) + "(" + (i+1) + ", obj.get" + toClassName(columns.get(i).getName()) + "());");
            }
            writer.println("            stmt.executeUpdate();");
            writer.println("        }");
            writer.println("    }");
            writer.println();
    
            writer.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public static int randomInt() {
        return new Random().nextInt(100) + 1;
    }
    
    public static long randomLong() {
        return new Random().nextLong();
    }
    
    public static float randomFloat() {
        return new Random().nextFloat() * 100;
    }
    
    public static double randomDouble() {
        return new Random().nextDouble() * 100;
    }
    
    public static String randomString() {
        int length = 5 + new Random().nextInt(10); // tamanho entre 5 e 14 caracteres
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char ch = (char) ('a' + new Random().nextInt(26));
            builder.append(ch);
        }
        return builder.toString();
    }
    
    public static java.util.Date randomDate() {
        long offset = Timestamp.valueOf("2020-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2023-01-01 00:00:00").getTime();
        long diff = end - offset + 1;
        return new java.util.Date(offset + (long)(Math.random() * diff));
    }
    
}
