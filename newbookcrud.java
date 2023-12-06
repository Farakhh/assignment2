import java.sql.*;

public class newbookcrud {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/2assignment";
    static final String USER = "postgres";
    static final String PASS = "5864";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            insertBook(conn, 6, "Harry Potter", "Fiction", 52); // Inserting a new book
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch the last inserted book_id from Books table
    public static int getLastBookId(Connection conn) throws SQLException {
        int lastId = 0;
        String query = "SELECT MAX(book_id) FROM Books";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                lastId = rs.getInt(1);
            }
        }
        return lastId;
    }

    // Create - Inserting a new book into the Books table
    public static void insertBook(Connection conn, int authorId, String title, String genre, int stockCount) {
        String insertQuery = "INSERT INTO Books (book_id, title, author_id, genre, stock_count) VALUES (?, ?, ?, ?, ?)";
        try {
            int lastBookId = getLastBookId(conn); // Fetching the last book_id
            int newBookId = lastBookId + 1;

            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setInt(1, newBookId);
            pstmt.setString(2, title);
            pstmt.setInt(3, authorId);
            pstmt.setString(4, genre);
            pstmt.setInt(5, stockCount);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("New book inserted with ID: " + newBookId + ", Author ID: " + authorId + ", Title: " + title + ", Genre: " + genre + ", Stock Count: " + stockCount);
            } else {
                System.out.println("No book added.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
