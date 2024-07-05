package com.example.trabalho4;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Utils {
    public static String toClassName(String name) {
        String[] parts = name.split("_");
        StringBuilder className = new StringBuilder();
        for (String part : parts) {
            className.append(part.substring(0, 1).toUpperCase()).append(part.substring(1).toLowerCase());
        }
        return className.toString();
    }

    private static void generateEntityClass(String tableName, List<Column> columns) {
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

    private static void generateDaoClass(String tableName, List<Column> columns) {
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
            writer.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateExampleClass(String tableName, List<Column> columns) {
        String className = toClassName(tableName) + "Exemplo";
        try (PrintWriter writer = new PrintWriter(className + ".java")) {
            writer.println("import java.sql.*;");
            writer.println("import java.util.*;");
            writer.println();
            writer.println("public class " + className + " {");
            writer.println("    public static void main(String[] args) {");
            writer.println("        String url = \"jdbc:postgresql://localhost:5432/trabalhoFinal\";");
            writer.println("        String user = \"postgres\";");
            writer.println("        String password = \"lucas\";");
            writer.println();
            writer.println("        try (Connection conn = DriverManager.getConnection(url, user, password)) {");
            writer.println("            " + toClassName(tableName) + "Dao dao = new " + toClassName(tableName) + "Dao(conn);");
            writer.println("            List<" + toClassName(tableName) + "> list = dao.getAll();");
            writer.println("            for (" + toClassName(tableName) + " obj : list) {");
            writer.println("                System.out.println(obj);");
            writer.println("            }");
            writer.println("        } catch (SQLException e) {");
            writer.println("            e.printStackTrace();");
            writer.println("        }");
            writer.println("    }");
            writer.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
}
