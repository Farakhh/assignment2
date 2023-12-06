import java.sql.*;

public class transactions {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/2assignment";
    static final String USER = "postgres";
    static final String PASS = "5864";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            conn.setAutoCommit(false); // Begin transaction

            int customerId = 4; // Sample customer ID
            int bookId = 5; // Sample book ID
            int quantity = 3; // Sample quantity

            // Get the maximum order ID and increment it by 1 for the new order
            int newOrderId = getLastOrderId(conn) + 1;

            // Check if enough books are available in the inventory
            PreparedStatement checkAvailability = conn.prepareStatement("SELECT stock_count FROM Books WHERE book_id = ?");
            checkAvailability.setInt(1, bookId);
            ResultSet resultSet = checkAvailability.executeQuery();

            if (resultSet.next()) {
                int stockCount = resultSet.getInt("stock_count");
                if (stockCount >= quantity) {
                    // If enough books available, insert order into Orders table
                    Timestamp orderTimestamp = new Timestamp(System.currentTimeMillis());
                    PreparedStatement insertOrder = conn.prepareStatement("INSERT INTO Orders (order_id, customer_id, book_id, quantity, order_date) VALUES (?, ?, ?, ?, ?)");
                    insertOrder.setInt(1, newOrderId);
                    insertOrder.setInt(2, customerId);
                    insertOrder.setInt(3, bookId);
                    insertOrder.setInt(4, quantity);
                    insertOrder.setTimestamp(5, orderTimestamp);
                    insertOrder.executeUpdate();

                    // Update the Books table with reduced stock count
                    PreparedStatement updateBooks = conn.prepareStatement("UPDATE Books SET stock_count = stock_count - ? WHERE book_id = ?");
                    updateBooks.setInt(1, quantity);
                    updateBooks.setInt(2, bookId);
                    updateBooks.executeUpdate();

                    System.out.println("Order placed successfully with Order ID: " + newOrderId + " and timestamp: " + orderTimestamp);
                    conn.commit(); // Commit the transaction
                } else {
                    System.out.println("Not enough books available.");
                    conn.rollback(); // Rollback the transaction if there are not enough books
                }
            } else {
                System.out.println("Book ID not found.");
                conn.rollback(); // Rollback the transaction if book ID is not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get the maximum order ID from Orders table
    public static int getLastOrderId(Connection conn) throws SQLException {
        int lastOrderId = 0;
        String query = "SELECT MAX(order_id) AS last_order_id FROM Orders";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                lastOrderId = rs.getInt("last_order_id");
            }
        }
        return lastOrderId;
    }
}
