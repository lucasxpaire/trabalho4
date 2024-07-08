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
        switch (type.toUpperCase()) {
            case "VARCHAR":
            case "CHAR":
            case "TEXT":
            case "BPCHAR": // PostgreSQL específico para CHAR
                return "String";
            case "INTEGER":
            case "INT4":
            case "SERIAL": // PostgreSQL específico para INTEGER
                return "int";
            case "BIGINT":
            case "INT8": // PostgreSQL específico para BIGINT
                return "long";
            case "SMALLINT":
            case "INT2": // PostgreSQL específico para SMALLINT
                return "short";
            case "TINYINT":
                return "byte"; // PostgreSQL não tem TINYINT, mas é incluído para casos gerais
            case "FLOAT4":
                return "float"; // PostgreSQL específico para FLOAT
            case "FLOAT8":
                return "double"; // PostgreSQL específico para DOUBLE
            case "NUMERIC":
            case "DECIMAL":
                return "java.math.BigDecimal"; // BigDecimal para precisão com números decimais
            case "BOOLEAN":
                return "boolean";
            case "DATE":
                return "java.sql.Date";
            case "TIME":
                return "java.sql.Time";
            case "TIMESTAMP":
            case "TIMESTAMPTZ": // PostgreSQL específico para TIMESTAMP com fuso horário
                return "java.sql.Timestamp";
            case "BYTEA": // PostgreSQL específico para arrays de bytes
                return "byte[]";
            default:
                return "Object"; // Para tipos não mapeados, retornamos Object como fallback
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
