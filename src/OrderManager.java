import java.sql.*;
import java.util.List;

public class OrderManager {

    public void addProduct(Product product) {

        String checkSql =
                "SELECT * FROM products WHERE name=?";

        String insertSql =
                "INSERT INTO products(name,price) VALUES(?,?)";

        try (Connection conn =
                     DBConnection.getConnection()) {

            PreparedStatement check =
                    conn.prepareStatement(checkSql);

            check.setString(
                    1,
                    product.getName());

            ResultSet rs =
                    check.executeQuery();

            if (rs.next()) {

                System.out.println(
                        "Product already exists!");

                return;
            }

            PreparedStatement insert =
                    conn.prepareStatement(insertSql);

            insert.setString(
                    1,
                    product.getName());

            insert.setDouble(
                    2,
                    product.getPrice());

            insert.executeUpdate();

            System.out.println(
                    "Add product successfully!");

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage());
        }
    }
    public void addCustomer(Customer customer) {

        String sql =
                "INSERT INTO customers(name,email) VALUES(?,?)";

        try (Connection conn =
                     DBConnection.getConnection()) {

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(
                    1,
                    customer.getName());

            ps.setString(
                    2,
                    customer.getEmail());

            ps.executeUpdate();

            System.out.println(
                    "Add customer successfully!");

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage());
        }
    }

    public void updateCustomer(
            int customerId,
            Customer customer) {

        String checkSql =
                "SELECT * FROM customers WHERE id=?";

        String updateSql =
                "UPDATE customers " +
                        "SET name=?, email=? " +
                        "WHERE id=?";

        try (Connection conn =
                     DBConnection.getConnection()) {

            PreparedStatement check =
                    conn.prepareStatement(checkSql);

            check.setInt(1, customerId);

            ResultSet rs =
                    check.executeQuery();

            if (!rs.next()) {

                System.out.println(
                        "Customer not found!");

                return;
            }

            PreparedStatement update =
                    conn.prepareStatement(updateSql);

            update.setString(
                    1,
                    customer.getName());

            update.setString(
                    2,
                    customer.getEmail());

            update.setInt(
                    3,
                    customerId);

            update.executeUpdate();

            System.out.println(
                    "Update customer successfully!");

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage());
        }
    }

    public void createOrder(
            int customerId,
            List<OrderDetail> details) {

        Connection conn = null;

        try {

            conn =
                    DBConnection.getConnection();

            conn.setAutoCommit(false);

            double totalAmount = 0;

            String productSql =
                    "SELECT price FROM products WHERE id=?";

            for (OrderDetail detail : details) {

                PreparedStatement ps =
                        conn.prepareStatement(productSql);

                ps.setInt(
                        1,
                        detail.getProductId());

                ResultSet rs =
                        ps.executeQuery();

                if (rs.next()) {

                    totalAmount +=
                            rs.getDouble("price")
                                    * detail.getQuantity();
                }
            }

            String orderSql =
                    "INSERT INTO orders" +
                            "(customer_id,order_date,total_amount) " +
                            "VALUES(?,?,?)";

            PreparedStatement orderPs =
                    conn.prepareStatement(
                            orderSql,
                            Statement.RETURN_GENERATED_KEYS);

            orderPs.setInt(
                    1,
                    customerId);

            orderPs.setDate(
                    2,
                    new Date(
                            System.currentTimeMillis()));

            orderPs.setDouble(
                    3,
                    totalAmount);

            orderPs.executeUpdate();

            ResultSet keys =
                    orderPs.getGeneratedKeys();

            keys.next();

            int orderId =
                    keys.getInt(1);

            String detailSql =
                    "INSERT INTO order_details" +
                            "(order_id,product_id,quantity) " +
                            "VALUES(?,?,?)";

            for (OrderDetail detail : details) {

                PreparedStatement detailPs =
                        conn.prepareStatement(detailSql);

                detailPs.setInt(
                        1,
                        orderId);

                detailPs.setInt(
                        2,
                        detail.getProductId());

                detailPs.setInt(
                        3,
                        detail.getQuantity());

                detailPs.executeUpdate();
            }

            conn.commit();

            System.out.println(
                    "Create order successfully!");

        } catch (Exception e) {

            try {

                if (conn != null) {

                    conn.rollback();
                }

            } catch (Exception ignored) {
            }

            System.out.println(
                    "Error: " + e.getMessage());

        }
    }

    public void listAllOrders() {

        String sql =
                "SELECT o.id, " +
                        "c.name AS customer_name, " +
                        "o.order_date, " +
                        "o.total_amount " +
                        "FROM orders o " +
                        "JOIN customers c " +
                        "ON o.customer_id = c.id";

        try (Connection conn =
                     DBConnection.getConnection()) {

            Statement st =
                    conn.createStatement();

            ResultSet rs =
                    st.executeQuery(sql);

            while (rs.next()) {

                System.out.println(
                        rs.getInt("id")
                                + " | "
                                + rs.getString("customer_name")
                                + " | "
                                + rs.getDate("order_date")
                                + " | "
                                + rs.getDouble("total_amount"));
            }

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage());
        }
    }

    public void getOrdersByCustomer(
            int customerId) {

        String sql =
                "SELECT * FROM orders " +
                        "WHERE customer_id=?";

        try (Connection conn =
                     DBConnection.getConnection()) {

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(
                    1,
                    customerId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                System.out.println(
                        rs.getInt("id")
                                + " | "
                                + rs.getDate("order_date")
                                + " | "
                                + rs.getDouble("total_amount"));
            }

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage());
        }
    }
}