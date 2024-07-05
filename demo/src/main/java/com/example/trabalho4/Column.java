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
            case "text":
                return "String";
            default:
                return "Object";
        }
    }

    public String getResultSetMethod() {
        switch (type.toLowerCase()) {
            case "int":
            case "integer":
                return "Int";
            case "bigint":
                return "Long";
            case "real":
                return "Float";
            case "double precision":
                return "Double";
            case "varchar":
            case "text":
                return "String";
            default:
                return "Object";
        }
    }

    public String getPreparedStatementMethod() {
        switch (type.toLowerCase()) {
            case "int":
            case "integer":
                return "Int";
            case "bigint":
                return "Long";
            case "real":
                return "Float";
            case "double precision":
                return "Double";
            case "varchar":
            case "text":
                return "String";
            default:
                return "Object";
        }
    }
}
