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
            case "serial":
                return "int";
            case "bigint":
            case "bigserial":
                return "long";
            case "smallint":
                return "short";
            case "real":
            case "float4":
                return "float";
            case "double precision":
            case "float8":
                return "double";
            case "varchar":
            case "character varying":
            case "text":
            case "char":
            case "character":
                return "String";
            case "date":
                return "java.sql.Date";
            case "timestamp":
            case "timestamp without time zone":
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
            case "java.sql.Date":
                return "Date";
            case "java.sql.Timestamp":
                return "Timestamp";
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
            case "java.sql.Date":
                return "Date";
            case "java.sql.Timestamp":
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
            case "short":
                return "SMALLINT";
            case "float":
                return "REAL";
            case "double":
                return "DOUBLE PRECISION";
            case "java.sql.Date":
                return "DATE";
            case "java.sql.Timestamp":
                return "TIMESTAMP";
            default:
                return "VARCHAR"; // ou tratar outros tipos conforme necessário
        }
    }

}
