import java.sql.*;

public class UpdateBookTitle {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/2assignment";
    static final String USER = "postgres";
    static final String PASS = "5864";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            updateBookTitle(conn, 2, "Go Set a Watchman");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBookTitle(Connection conn, int bookId, String newTitle) throws SQLException {
        String updateQuery = "UPDATE Books SET title = ? WHERE book_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, newTitle);
            pstmt.setInt(2, bookId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book title updated successfully for ID: " + bookId);
            } else {
                System.out.println("No book found with ID: " + bookId);
            }
        }
    }
}
