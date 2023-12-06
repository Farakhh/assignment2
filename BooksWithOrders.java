import java.sql.*;

public class BooksWithOrders {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/2assignment";
    static final String USER = "postgres";
    static final String PASS = "5864";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            displayBooksWithOrders(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayBooksWithOrders(Connection conn) throws SQLException {
        String query = "SELECT b.book_id, b.title, b.author_id, b.genre, b.stock_count, o.order_id, o.customer_id, o.quantity, o.order_date " +
                "FROM Books b " +
                "LEFT JOIN Orders o ON b.book_id = o.book_id";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String title = rs.getString("title");
                int authorId = rs.getInt("author_id");
                String genre = rs.getString("genre");
                int stockCount = rs.getInt("stock_count");
                int orderId = rs.getInt("order_id");
                int customerId = rs.getInt("customer_id");
                int quantity = rs.getInt("quantity");
                Timestamp orderDate = rs.getTimestamp("order_date");

                System.out.println("Book ID: " + bookId + ", Title: " + title + ", Author ID: " + authorId +
                        ", Genre: " + genre + ", Stock Count: " + stockCount +
                        ", Order ID: " + orderId + ", Customer ID: " + customerId +
                        ", Quantity: " + quantity + ", Order Date: " + orderDate);
            }
        }
    }
}
