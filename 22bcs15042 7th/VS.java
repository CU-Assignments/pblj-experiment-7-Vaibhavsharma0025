import java.sql.*;
import java.util.Scanner;

public class VS {
    static final String DB_URL = "jdbc:mysql://localhost:3306/your_database";
    static final String USER = "your_username";
    static final String PASS = "your_password";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            conn.setAutoCommit(false); // Enable transaction handling

            while (true) {
                System.out.println("\n----- Product Management -----");
                System.out.println("1. Add Product");
                System.out.println("2. View Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1 -> addProduct(conn, sc);
                    case 2 -> viewProducts(conn);
                    case 3 -> updateProduct(conn, sc);
                    case 4 -> deleteProduct(conn, sc);
                    case 5 -> {
                        conn.close();
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void addProduct(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter ProductID: ");
            int id = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.print("Enter ProductName: ");
            String name = sc.nextLine();
            System.out.print("Enter Price: ");
            double price = sc.nextDouble();
            System.out.print("Enter Quantity: ");
            int qty = sc.nextInt();

            String sql = "INSERT INTO Product (ProductID, ProductName, Price, Quantity) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.setString(2, name);
                pstmt.setDouble(3, price);
                pstmt.setInt(4, qty);
                pstmt.executeUpdate();
                conn.commit();
                System.out.println("Product added successfully.");
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
                System.out.println("Error occurred. Rolled back.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    static void viewProducts(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Product");

            System.out.println("\nProduct List:");
            System.out.println("ID\tName\t\tPrice\tQuantity");
            while (rs.next()) {
                System.out.printf("%d\t%s\t\t%.2f\t%d\n",
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateProduct(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter ProductID to update: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter new ProductName: ");
            String name = sc.nextLine();
            System.out.print("Enter new Price: ");
            double price = sc.nextDouble();
            System.out.print("Enter new Quantity: ");
            int qty = sc.nextInt();

            String sql = "UPDATE Product SET ProductName = ?, Price = ?, Quantity = ? WHERE ProductID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setDouble(2, price);
                pstmt.setInt(3, qty);
                pstmt.setInt(4, id);
                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    conn.commit();
                    System.out.println("Product updated.");
                } else {
                    System.out.println("Product not found.");
                }
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
                System.out.println("Error occurred. Rolled back.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    static void deleteProduct(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter ProductID to delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM Product WHERE ProductID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    conn.commit();
                    System.out.println("Product deleted.");
                } else {
                    System.out.println("Product not found.");
                }
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
                System.out.println("Error occurred. Rolled back.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
