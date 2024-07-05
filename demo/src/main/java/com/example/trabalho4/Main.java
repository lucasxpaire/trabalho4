package com.example.trabalho4;

import java.sql.*;
import java.util.*;
import java.io.*;


public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/trabalhoFinal";
        String user = "postgres";
        String password = "lucas";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                List<Column> columns = getColumns(metaData, tableName);
                generateClasses(tableName, columns);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<Column> getColumns(DatabaseMetaData metaData, String tableName) throws SQLException {
        List<Column> columns = new ArrayList<>();
        ResultSet rs = metaData.getColumns(null, null, tableName, null);
        while (rs.next()) {
            String name = rs.getString("COLUMN_NAME");
            String type = rs.getString("TYPE_NAME");
            columns.add(new Column(name, type));
        }
        return columns;
    }

    private static void generateClasses(String tableName, List<Column> columns) {
        generateEntityClass(tableName, columns);
        generateDaoClass(tableName, columns);
        generateExampleClass(tableName, columns);
    }
}


