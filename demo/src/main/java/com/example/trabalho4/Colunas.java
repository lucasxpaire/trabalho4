package com.example.trabalho4;

public class Colunas {
    private final String name;
    private final String type;

    public Colunas(String name, String type) {
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
        switch (type.toUpperCase()) {
            case "INT":
            case "INTEGER":
                return "int";
            case "FLOAT4":
            case "FLOAT8":
            case "DOUBLE PRECISION":
                return "double";
            case "VARCHAR":
            case "CHAR":
            case "TEXT":
                return "String";
            case "DATE":
            case "TIMESTAMP":
                return "Date";
            default:
                return "String";
        }
    }

    public String getResultSetMethod() {
        switch (type.toUpperCase()) {
            case "INT":
            case "INTEGER":
                return "Int";
            case "FLOAT4":
                return "Float";
            case "FLOAT8":
            case "DOUBLE PRECISION":
                return "Double";
            case "VARCHAR":
            case "CHAR":
            case "TEXT":
                return "String";
            case "DATE":
            case "TIMESTAMP":
                return "Date";
            default:
                return "String";
        }
    }
}
