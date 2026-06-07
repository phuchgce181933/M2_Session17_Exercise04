import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc =
                new Scanner(System.in);

        OrderManager manager =
                new OrderManager();

        while (true) {

            System.out.println("\n===== SHOP MANAGEMENT =====");

            System.out.println("1. Add Product");
            System.out.println("2. Add Customer");
            System.out.println("3. Update Customer");
            System.out.println("4. Create Order");
            System.out.println("5. List All Orders");
            System.out.println("6. Search Orders By Customer");
            System.out.println("0. Exit");

            try {

                System.out.print("Choose: ");

                int choice =
                        Integer.parseInt(
                                sc.nextLine());

                switch (choice) {

                    case 1:

                        System.out.print(
                                "Product Name: ");

                        String productName =
                                sc.nextLine();

                        System.out.print(
                                "Price: ");

                        double price =
                                Double.parseDouble(
                                        sc.nextLine());

                        manager.addProduct(
                                new Product(
                                        productName,
                                        price));

                        break;

                    case 2:

                        System.out.print(
                                "Customer Name: ");

                        String customerName =
                                sc.nextLine();

                        System.out.print(
                                "Email: ");

                        String email =
                                sc.nextLine();

                        manager.addCustomer(
                                new Customer(
                                        customerName,
                                        email));

                        break;

                    case 3:

                        System.out.print(
                                "Customer ID: ");

                        int customerId =
                                Integer.parseInt(
                                        sc.nextLine());

                        System.out.print(
                                "New Name: ");

                        customerName =
                                sc.nextLine();

                        System.out.print(
                                "New Email: ");

                        email =
                                sc.nextLine();

                        manager.updateCustomer(
                                customerId,
                                new Customer(
                                        customerName,
                                        email));

                        break;

                    case 4:

                        System.out.print(
                                "Customer ID: ");

                        customerId =
                                Integer.parseInt(
                                        sc.nextLine());

                        System.out.print(
                                "Number of products: ");

                        int count =
                                Integer.parseInt(
                                        sc.nextLine());

                        List<OrderDetail> details =
                                new ArrayList<>();

                        for (int i = 0;
                             i < count;
                             i++) {

                            System.out.print(
                                    "Product ID: ");

                            int productId =
                                    Integer.parseInt(
                                            sc.nextLine());

                            System.out.print(
                                    "Quantity: ");

                            int quantity =
                                    Integer.parseInt(
                                            sc.nextLine());

                            details.add(
                                    new OrderDetail(
                                            productId,
                                            quantity));
                        }

                        manager.createOrder(
                                customerId,
                                details);

                        break;

                    case 5:

                        manager.listAllOrders();

                        break;

                    case 6:

                        System.out.print(
                                "Customer ID: ");

                        customerId =
                                Integer.parseInt(
                                        sc.nextLine());

                        manager.getOrdersByCustomer(
                                customerId);

                        break;

                    case 0:

                        System.out.println(
                                "Goodbye!");

                        System.exit(0);

                    default:

                        System.out.println(
                                "Invalid choice!");
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number!");

            } catch (Exception e) {

                System.out.println(
                        e.getMessage());
            }
        }
    }
}