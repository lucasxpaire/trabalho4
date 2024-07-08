package com.example.trabalho4;

import java.sql.*;
import java.util.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        //String outputDir = "C:/Users/leand/OneDrive/Área de Trabalho/trabalho4/trabalho4/demo/src/main/java/com/example/trabalho4/generated";
        String outputDir = "C:/Users/lucas/Desktop/Trabalho4/demo/src/main/java/com/example/trabalho4/generated";

        String url = "jdbc:postgresql://localhost:5432/meta_dados";
        String user = "postgres";
        String password = "lucas";
        //String password = "kise";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                List<Column> columns = getColumns(metaData, tableName);
                generateClasses(outputDir, tableName, columns);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<Column> getColumns(DatabaseMetaData metaData, String tableName) throws SQLException {
        List<Column> columns = new ArrayList<>();
        try (ResultSet rs = metaData.getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                String name = rs.getString("COLUMN_NAME");
                String type = rs.getString("TYPE_NAME");
                columns.add(new Column(name, type));
            }
        }
        return columns;
    }

    public static void generateClasses(String outputDir, String tableName, List<Column> columns) {
        try {
            File dir = new File(outputDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Utils.generateEntityClass(outputDir, tableName, columns);
            Utils.generateDaoClass(outputDir, tableName, columns);
            Utils.generateExampleClass(outputDir, tableName, columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
