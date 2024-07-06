package com.example.trabalho4;

public class Column {
    private String name;
    private String type;

    public Column(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getJavaType() {
        switch (type.toLowerCase()) {
            case "int":
            case "integer":
            case "bigint":
            case "smallint":
            case "serial":
            case "bigserial":
                return "int";
            case "real":
                return "float";
            case "double precision":
                return "double";
            case "varchar":
            case "character varying":
            case "text":
                return "String";
            case "date":
                return "java.sql.Date";
            case "timestamp":
                return "java.sql.Timestamp";
            default:
                return "Object";
        }
    }
    

    public static String toPreparedStatementMethod(String javaType) {
        switch (javaType) {
            case "int":
                return "Int";
            case "float":
                return "Float";
            case "double":
                return "Double";
            case "String":
                return "String";
            // Adicione outros tipos conforme necessário
            default:
                return "Object";
        }
    }

    public static String toResultSetMethod(String javaType) {
        switch (javaType) {
            case "int":
                return "Int";
            case "float":
                return "Float";
            case "double":
                return "Double";
            case "String":
                return "String";
            // Adicione outros tipos conforme necessário
            default:
                return "Object";
        }
    }

    public static String toSQLType(String javaType) {
        switch (javaType) {
            case "String":
                return "VARCHAR";
            case "int":
                return "INTEGER";
            case "float":
                return "REAL";
            default:
                return "VARCHAR"; // ou tratar outros tipos conforme necessário
        }
    }
}
