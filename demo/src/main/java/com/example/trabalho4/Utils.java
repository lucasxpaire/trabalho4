package com.example.trabalho4;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
                String javaType = column.getJavaType();
                writer.println("    private " + javaType + " " + columnName + ";");
            }
            writer.println();

            // Getters e Setters
            for (Column column : columns) {
                String columnName = column.getName();
                String javaType = column.getJavaType();

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
            writer.println("        ResultSet rs = stmt.executeQuery(\"SELECT * FROM \\\"" + tableName + "\\\" \");");
            writer.println("        while (rs.next()) {");
            writer.println("            " + entityClassName + " obj = new " + entityClassName + "();");
            for (Column column : columns) {
                String columnName = column.getName();
                String javaType = column.getJavaType();
                writer.println("            obj.set" + toClassName(columnName) + "(rs.get" + Column.toResultSetMethod(javaType) + "(\"" + columnName + "\"));");
            }
            writer.println("            list.add(obj);");
            writer.println("        }");
            writer.println("        return list;");
            writer.println("    }");
            writer.println();

            // Método insert
            writer.println("    public void insert(" + entityClassName + " obj) throws SQLException {");
            writer.print("        String sql = \"INSERT INTO \\\"" + tableName + "\\\" (");
            for (int i = 0; i < columns.size(); i++) {
                writer.print(columns.get(i).getName());
                if (i < columns.size() - 1)
                    writer.print(", ");
                else
                    writer.print(") VALUES (");
            }
            for (int i = 0; i < columns.size(); i++) {
                writer.print("?");
                if (i < columns.size() - 1)
                    writer.print(", ");
                else
                    writer.println(")\";");
            }
            writer.println("        try (PreparedStatement stmt = conn.prepareStatement(sql)) {");
            for (int i = 0; i < columns.size(); i++) {
                String columnType = columns.get(i).getJavaType();
                writer.println("            stmt.set" + Column.toPreparedStatementMethod(columnType) + "(" + (i + 1) + ", obj.get" + toClassName(columns.get(i).getName()) + "());");
            }
            writer.println("            stmt.executeUpdate();");
            writer.println("        }");
            writer.println("    }");
            writer.println();

            // Método delete
            writer.println("    public void delete(" + entityClassName + " obj) throws SQLException {");
            writer.print("        String sql = \"DELETE FROM \\\"" + tableName + "\\\" WHERE ");
            for (int i = 0; i < columns.size(); i++) {
                writer.print(columns.get(i).getName() + " = ?");
                if (i < columns.size() - 1)
                    writer.print(" AND ");
                else
                    writer.println("\";");
            }
            writer.println("        try (PreparedStatement stmt = conn.prepareStatement(sql)) {");
            for (int i = 0; i < columns.size(); i++) {
                String columnType = columns.get(i).getJavaType();
                writer.println("            stmt.set" + Column.toPreparedStatementMethod(columnType) + "(" + (i + 1) + ", obj.get" + toClassName(columns.get(i).getName()) + "());");
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

    public static void generateExampleClass(String outputDir, String tableName, List<Column> columns) {
        String className = toClassName(tableName) + "Exemplo";
        String filePath = outputDir + File.separator + className + ".java";

        try (PrintWriter writer = new PrintWriter(filePath)) {
            generateExampleClassHeader(writer, className);
            generateExampleClassBody(writer, tableName, columns);
            generateExampleClassFooter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateExampleClassHeader(PrintWriter writer, String className) {
        writer.println("package com.example.trabalho4.generated;");
        writer.println("import java.sql.*;");
        writer.println("import java.sql.Date;");
        writer.println("import java.util.*;");
        writer.println();
        writer.println("public class " + className + " {");
        writer.println("        private static final Random RANDOM = new Random();");
        writer.println();
    }

    private static void generateExampleClassBody(PrintWriter writer, String tableName, List<Column> columns) {
        writer.println("    public static void main(String[] args) {");
        writer.println("        String url = \"jdbc:postgresql://localhost:5432/meta_dados\";");
        writer.println("        String user = \"postgres\";");
        writer.println("        String password = \"lucas\";");
        writer.println();
        writer.println("        try (Connection connection = DriverManager.getConnection(url, user, password)) {");
        writer.println("            " + toClassName(tableName) + "Dao dao = new " + toClassName(tableName) + "Dao(connection);");
        generateExampleInsert(writer, tableName, columns);
        generateExampleList(writer, tableName, columns);
        generateExampleDelete(writer, tableName, columns);
        writer.println("        } catch (SQLException e) {");
        writer.println("            e.printStackTrace();");
        writer.println("        }");
        writer.println("    }");
        writer.println();
    }

    private static void generateExampleInsert(PrintWriter writer, String tableName, List<Column> columns) {
        writer.println("            // Exemplo de insercao");
        writer.println("            " + toClassName(tableName) + " novoObj = new " + toClassName(tableName) + "();");
        for (Column column : columns) {
            String columnName = column.getName();
            String javaType = column.getJavaType();
            String exampleValue = getExampleValue(javaType);
            writer.println("            novoObj.set" + toClassName(columnName) + "(" + exampleValue + ");");
        }
        writer.println("            dao.insert(novoObj);");
        writer.println();
    }

    private static void generateExampleList(PrintWriter writer, String tableName, List<Column> columns) {
        writer.println("            // Exemplo de listagem");
        writer.println("            List<" + toClassName(tableName) + "> list = dao.getAll();");
        writer.println("            for (" + toClassName(tableName) + " obj : list) {");
        for (Column column : columns) {
            writer.println("                System.out.print(obj.get" + toClassName(column.getName()) + "() + \" | \");");
        }
        writer.println("                System.out.println();");
        writer.println("            }");
        writer.println();
    }

    private static void generateExampleDelete(PrintWriter writer, String tableName, List<Column> columns) {
        writer.println("            // Exemplo de exclusao");
        writer.println("            // Comentar para adicionar varios dados no banco");
        writer.println("            dao.delete(novoObj);");
        writer.println();
    }

    private static void generateExampleClassFooter(PrintWriter writer) {
        writer.println("    private static String getRandomString() {");
        writer.println("        int length = RANDOM.nextInt(10) + 1;");
        writer.println("        StringBuilder sb = new StringBuilder(length);");
        writer.println("        for (int i = 0; i < length; i++) {");
        writer.println("            char c = (char) (RANDOM.nextInt(26) + 'a');");
        writer.println("            sb.append(c);");
        writer.println("        }");
        writer.println("        return sb.toString();");
        writer.println("    }");
        writer.println();

        // Exemplo para getRandomDecimal
        writer.println("    private static String getRandomDecimal() {");
        writer.println("        return String.format(\"%.2f\", RANDOM.nextDouble() * 10000);");
        writer.println("    }");
        writer.println();

        // Exemplo para getRandomDate
        writer.println("    private static String getRandomDate() {");
        writer.println("        int year = RANDOM.nextInt(100) + 1900;");
        writer.println("        int month = RANDOM.nextInt(12) + 1;");
        writer.println("        int day = RANDOM.nextInt(28) + 1;");
        writer.println("        return String.format(\"%04d-%02d-%02d\", year, month, day); // Data no formato YYYY-MM-DD");
        writer.println("    }");
        writer.println();

        // Exemplo para getRandomTime
        writer.println("    private static String getRandomTime() {");
        writer.println("        int hour = RANDOM.nextInt(24); ");
        writer.println("        int minute = RANDOM.nextInt(60);");
        writer.println("        int second = RANDOM.nextInt(60);");
        writer.println("        return String.format(\"%02d:%02d:%02d\", hour, minute, second); // Tempo no formato HH:MM:SS");
        writer.println("    }");
        writer.println();

        // Exemplo para getRandomTimestamp
        writer.println("    private static String getRandomTimestamp() {");
        writer.println("        return getRandomDate() + \" \" + getRandomTime(); // Timestamp como combinação de data e hora");
        writer.println("    }");
        writer.println();

        // Exemplo para getRandomByteArray
        writer.println("    private static byte[] getRandomByteArray() {");
        writer.println("        byte[] bytes = new byte[RANDOM.nextInt(10) + 1];");
        writer.println("        RANDOM.nextBytes(bytes);");
        writer.println("        return bytes;");
        writer.println("    }");
        writer.println();

        writer.println("}");
    }

    private static String getExampleValue(String javaType) {
        switch (javaType.toUpperCase()) {
            case "STRING":
                return "getRandomString()";
            case "INT":
                return "RANDOM.nextInt(10000)";
            case "LONG":
                return "RANDOM.nextLong()";
            case "SHORT":
                return "(short) RANDOM.nextInt(Short.MAX_VALUE)";
            case "BYTE":
                return "(byte) RANDOM.nextInt(Byte.MAX_VALUE)";
            case "FLOAT":
                return "RANDOM.nextFloat() * 100";
            case "DOUBLE":
                return "RANDOM.nextDouble() * 100";
            case "JAVA.MATH.BIGDECIMAL":
                return "new BigDecimal(getRandomDecimal())";
            case "BOOL":
                return "RANDOM.nextBoolean()";
            case "JAVA.SQL.DATE":
                return "Date.valueOf(getRandomDate())";
            case "JAVA.SQL.TIME":
                return "Time.valueOf(getRandomTime())";
            case "JAVA.SQL.TIMESTAMP":
                return "Timestamp.valueOf(getRandomTimestamp())";
            case "BYTE[]":
                return "getRandomByteArray()";
            default:
                return "example";
        }
    }

}
