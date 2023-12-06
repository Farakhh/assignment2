import java.sql.*;

public class RemoveBook {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/2assignment";
    static final String USER = "postgres";
    static final String PASS = "5864";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            removeBook(conn, 6);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeBook(Connection conn, int bookId) throws SQLException {
        String deleteQuery = "DELETE FROM Books WHERE book_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, bookId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book deleted successfully with ID: " + bookId);
            } else {
                System.out.println("No book found with ID: " + bookId);
            }
        }
    }
}
