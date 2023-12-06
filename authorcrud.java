import java.sql.*;

public class authorcrud {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/2assignment";
    static final String USER = "postgres";
    static final String PASS = "5864";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            insertAuthor(conn, "J.K. Rowling"); // Inserting a new author
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch the last inserted author_id from Authors table
    public static int getLastAuthorId(Connection conn) throws SQLException {
        int lastId = 0;
        String query = "SELECT MAX(author_id) FROM Authors";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                lastId = rs.getInt(1);
            }
        }
        return lastId;
    }

    // Create - Inserting a new author into the Authors table
    public static void insertAuthor(Connection conn, String authorName) {
        String insertQuery = "INSERT INTO Authors (author_id, author_name) VALUES (?, ?)";
        try {
            int lastId = getLastAuthorId(conn); // Fetching the last author_id
            int newId = lastId + 1;

            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setInt(1, newId);
            pstmt.setString(2, authorName);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("New author inserted with ID: " + newId + ", Name: " + authorName);
            } else {
                System.out.println("No author added.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
