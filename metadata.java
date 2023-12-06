import java.sql.*;

public class metadata {
    static final String DB_URL = "jdbc:postgresql://localhost:5432/2assignment";
    static final String USER = "postgres";
    static final String PASS = "5864";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            DatabaseMetaData metaData = conn.getMetaData();

            // Retrieve table names
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            System.out.println("Tables:");
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);

                // Retrieve column details for each table
                ResultSet columns = metaData.getColumns(null, null, tableName, null);
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    System.out.println("\tColumn: " + columnName + ", Type: " + columnType);
                }
                columns.close();

                // Retrieve primary keys for each table
                ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
                System.out.print("\tPrimary keys:");
                while (primaryKeys.next()) {
                    String primaryKeyColumn = primaryKeys.getString("COLUMN_NAME");
                    System.out.print(" " + primaryKeyColumn);
                }
                System.out.println();

                // Retrieve foreign keys for each table
                ResultSet foreignKeys = metaData.getImportedKeys(null, null, tableName);
                System.out.print("\tForeign keys:");
                while (foreignKeys.next()) {
                    String foreignKeyColumn = foreignKeys.getString("FKCOLUMN_NAME");
                    System.out.print(" " + foreignKeyColumn);
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
