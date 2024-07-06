package com.example.trabalho4;

public class Column {
    private final String name;
    private final String type;

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
                return "int";
            case "bigint":
                return "long";
            case "real":
                return "float";
            case "double precision":
                return "double";
            case "varchar":
            case "character varying":
            case "text":
                return "String";
            case "date":
            case "timestamp":
                return "java.util.Date";
            default:
                return "Object";
        }
    }

    public static String toPreparedStatementMethod(String javaType) {
        switch (javaType) {
            case "String":
                return "String";
            case "int":
                return "Int";
            case "long":
                return "Long";
            case "float":
                return "Float";
            case "double":
                return "Double";
            case "java.util.Date":
                return "Timestamp";
            default:
                return "Object";
        }
    }

    public static String toResultSetMethod(String javaType) {
        switch (javaType) {
            case "String":
                return "String";
            case "int":
                return "Int";
            case "long":
                return "Long";
            case "float":
                return "Float";
            case "double":
                return "Double";
            case "java.util.Date":
                return "Timestamp";
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
            case "long":
                return "BIGINT";
            case "float":
                return "REAL";
            case "double":
                return "DOUBLE PRECISION";
            case "java.util.Date":
                return "TIMESTAMP";
            default:
                return "VARCHAR";
        }
    }
}