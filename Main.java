import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Product> products = new ArrayList<>();
    static ArrayList<Sale> salesHistory = new ArrayList<>();

    static int nextId = 1;
    static int nextInvoiceId = 1001;

    static final String DATA_FOLDER = "stockmaster-data";
    static final String DATA_FILE = DATA_FOLDER + "/stockmaster.dat";

    // ================= PRODUCT CLASS =================

    static class Product implements Serializable {

        private static final long serialVersionUID = 1L;

        int id;
        String name;
        String category;
        double price;
        int quantity;

        Product(int id, String name, String category,
                double price, int quantity) {

            this.id = id;
            this.name = name;
            this.category = category;
            this.price = price;
            this.quantity = quantity;
        }
    }

    // ================= BILL ITEM CLASS =================

    static class BillItem implements Serializable {

        private static final long serialVersionUID = 1L;

        String productName;
        int quantity;
        double price;
        double amount;

        BillItem(String productName, int quantity, double price) {

            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
            this.amount = quantity * price;
        }
    }

    // ================= SALE CLASS =================

    static class Sale implements Serializable {

        private static final long serialVersionUID = 1L;

        int invoiceId;
        String customerName;
        String customerPhone;
        String date;
        ArrayList<BillItem> items;
        double total;

        Sale(int invoiceId, String customerName,
             String customerPhone, String date,
             ArrayList<BillItem> items, double total) {

            this.invoiceId = invoiceId;
            this.customerName = customerName;
            this.customerPhone = customerPhone;
            this.date = date;
            this.items = items;
            this.total = total;
        }
    }

    // ================= STORAGE CLASS =================

    static class StoreData implements Serializable {

        private static final long serialVersionUID = 1L;

        ArrayList<Product> products;
        ArrayList<Sale> salesHistory;
        int nextId;
        int nextInvoiceId;

        StoreData(ArrayList<Product> products,
                  ArrayList<Sale> salesHistory,
                  int nextId, int nextInvoiceId) {

            this.products = products;
            this.salesHistory = salesHistory;
            this.nextId = nextId;
            this.nextInvoiceId = nextInvoiceId;
        }
    }

    // ================= MAIN METHOD =================

    public static void main(String[] args) {

        loadData();

        if (products.isEmpty() && salesHistory.isEmpty()) {
            addSampleProducts();
            saveData();
        }

        while (true) {

            showMenu();

            System.out.print("\nEnter your choice: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    dashboard();
                    break;

                case 2:
                    addProduct();
                    break;

                case 3:
                    viewProducts();
                    break;

                case 4:
                    searchProduct();
                    break;

                case 5:
                    updateProduct();
                    break;

                case 6:
                    deleteProduct();
                    break;

                case 7:
                    generateBill();
                    break;

                case 8:
                    viewSalesHistory();
                    break;

                case 9:
                    inventoryReport();
                    break;

                case 10:
                    saveData();
                    System.out.println("\nThank you for using StockMaster!");
                    System.out.println("Your data has been saved.");
                    return;

                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }

    // ================= MAIN MENU =================

    static void showMenu() {

        System.out.println("\n");
        System.out.println("================================================");
        System.out.println("                  STOCKMASTER");
        System.out.println("        INVENTORY & BILLING MANAGEMENT");
        System.out.println("================================================");

        System.out.println("1.  Dashboard");
        System.out.println("2.  Add Product");
        System.out.println("3.  View All Products");
        System.out.println("4.  Search Product");
        System.out.println("5.  Update Product");
        System.out.println("6.  Delete Product");
        System.out.println("7.  Generate Customer Bill");
        System.out.println("8.  Sales History");
        System.out.println("9.  Inventory Report");
        System.out.println("10. Exit");

        System.out.println("================================================");
    }

    // ================= SAMPLE PRODUCTS =================

    static void addSampleProducts() {

        products.add(new Product(
                nextId++, "Laptop", "Electronics", 55000, 10));

        products.add(new Product(
                nextId++, "Mouse", "Accessories", 500, 25));

        products.add(new Product(
                nextId++, "Keyboard", "Accessories", 1200, 15));

        products.add(new Product(
                nextId++, "Monitor", "Electronics", 12000, 8));

        products.add(new Product(
                nextId++, "Headphones", "Accessories", 1800, 12));
    }

    // ================= DASHBOARD =================

    static void dashboard() {

        int totalUnits = 0;
        int lowStock = 0;
        double stockValue = 0;
        double totalRevenue = 0;

        for (Product p : products) {

            totalUnits += p.quantity;
            stockValue += p.price * p.quantity;

            if (p.quantity <= 5) {
                lowStock++;
            }
        }

        for (Sale s : salesHistory) {
            totalRevenue += s.total;
        }

        System.out.println("\n============== DASHBOARD ==============");

        System.out.println("Total Products : " + products.size());
        System.out.println("Total Units    : " + totalUnits);

        System.out.printf("Stock Value    : Rs. %.2f%n", stockValue);

        System.out.printf("Total Revenue  : Rs. %.2f%n", totalRevenue);

        System.out.println("Total Sales    : " + salesHistory.size());

        System.out.println("Low Stock Items: " + lowStock);

        System.out.println("=======================================");
    }

    // ================= ADD PRODUCT =================

    static void addProduct() {

        System.out.println("\n============== ADD PRODUCT ==============");

        System.out.print("Enter product name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter category: ");
        String category = sc.nextLine().trim();

        System.out.print("Enter price: ");
        double price = readDouble();

        System.out.print("Enter quantity: ");
        int quantity = readInt();

        if (name.isEmpty() || category.isEmpty()
                || price <= 0 || quantity < 0) {

            System.out.println("Invalid product details.");
            return;
        }

        Product p = new Product(
                nextId++, name, category, price, quantity
        );

        products.add(p);

        saveData();

        System.out.println("\nProduct added successfully!");
        System.out.println("Product ID: " + p.id);
    }

    // ================= VIEW PRODUCTS =================

    static void viewProducts() {

        System.out.println("\n============== PRODUCT LIST ==============");

        if (products.isEmpty()) {

            System.out.println("No products available.");
            return;
        }

        System.out.printf(
                "%-5s %-18s %-15s %-12s %-10s %-15s%n",
                "ID", "NAME", "CATEGORY", "PRICE", "QUANTITY", "STATUS"
        );

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        for (Product p : products) {

            System.out.printf(
                    "%-5d %-18s %-15s %-12.2f %-10d %-15s%n",
                    p.id,
                    p.name,
                    p.category,
                    p.price,
                    p.quantity,
                    getStatus(p)
            );
        }
    }

    // ================= SEARCH PRODUCT =================

    static void searchProduct() {

        System.out.print("\nEnter product name or category: ");

        String search = sc.nextLine().trim().toLowerCase();

        boolean found = false;

        for (Product p : products) {

            if (p.name.toLowerCase().contains(search)
                    || p.category.toLowerCase().contains(search)) {

                displayProduct(p);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching products found.");
        }
    }

    // ================= UPDATE PRODUCT =================

    static void updateProduct() {

        System.out.print("\nEnter product ID to update: ");

        int id = readInt();

        Product p = findProduct(id);

        if (p == null) {

            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter new product name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter new category: ");
        String category = sc.nextLine().trim();

        System.out.print("Enter new price: ");
        double price = readDouble();

        System.out.print("Enter new quantity: ");
        int quantity = readInt();

        if (name.isEmpty() || category.isEmpty()
                || price <= 0 || quantity < 0) {

            System.out.println("Invalid product details.");
            return;
        }

        p.name = name;
        p.category = category;
        p.price = price;
        p.quantity = quantity;

        saveData();

        System.out.println("Product updated successfully!");
    }

    // ================= DELETE PRODUCT =================

    static void deleteProduct() {

        System.out.print("\nEnter product ID to delete: ");

        int id = readInt();

        Product p = findProduct(id);

        if (p == null) {

            System.out.println("Product not found.");
            return;
        }

        System.out.print("Confirm deletion? (yes/no): ");

        String confirm = sc.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes")) {

            products.remove(p);

            saveData();

            System.out.println("Product deleted successfully!");

        } else {

            System.out.println("Deletion cancelled.");
        }
    }

    // ================= GENERATE BILL =================

    static void generateBill() {

        System.out.println("\n============== BILLING SYSTEM ==============");

        System.out.print("Enter customer name: ");
        String customer = sc.nextLine().trim();

        if (customer.isEmpty()) {

            System.out.println("Customer name cannot be empty.");
            return;
        }

        System.out.print("Enter customer phone number: ");
        String phone = sc.nextLine().trim();

        if (!phone.matches("[0-9]{10}")) {

            System.out.println("Enter a valid 10-digit phone number.");
            return;
        }

        ArrayList<BillItem> items = new ArrayList<>();

        ArrayList<Product> selectedProducts = new ArrayList<>();
        ArrayList<Integer> selectedQuantities = new ArrayList<>();

        double total = 0;

        while (true) {

            viewProducts();

            System.out.print("\nEnter product ID (0 to finish): ");

            int id = readInt();

            if (id == 0) {
                break;
            }

            Product p = findProduct(id);

            if (p == null) {

                System.out.println("Invalid product ID.");
                continue;
            }

            System.out.print("Enter quantity: ");

            int quantity = readInt();

            if (quantity <= 0) {

                System.out.println("Quantity must be greater than zero.");
                continue;
            }

            int existingIndex = selectedProducts.indexOf(p);

            int alreadySelected = existingIndex >= 0
                    ? selectedQuantities.get(existingIndex) : 0;

            if (alreadySelected + quantity > p.quantity) {

                System.out.println("Insufficient stock.");
                continue;
            }

            if (existingIndex >= 0) {

                selectedQuantities.set(
                        existingIndex,
                        alreadySelected + quantity
                );

            } else {

                selectedProducts.add(p);
                selectedQuantities.add(quantity);
            }

            double amount = p.price * quantity;

            total += amount;

            System.out.println("Product added to bill.");
        }

        if (selectedProducts.isEmpty()) {

            System.out.println("No products selected. Bill cancelled.");
            return;
        }

        for (int i = 0; i < selectedProducts.size(); i++) {

            Product p = selectedProducts.get(i);

            int quantity = selectedQuantities.get(i);

            p.quantity -= quantity;

            items.add(new BillItem(
                    p.name, quantity, p.price
            ));
        }

        String date = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss"
        ).format(new Date());

        Sale sale = new Sale(
                nextInvoiceId++,
                customer,
                phone,
                date,
                items,
                total
        );

        salesHistory.add(sale);

        saveData();

        printInvoice(sale);
    }

    // ================= PRINT INVOICE =================

    static void printInvoice(Sale sale) {

        System.out.println("\n");
        System.out.println("================================================");
        System.out.println("                 STOCKMASTER");
        System.out.println("                   INVOICE");
        System.out.println("================================================");

        System.out.println("Invoice ID : " + sale.invoiceId);
        System.out.println("Date       : " + sale.date);
        System.out.println("Customer   : " + sale.customerName);
        System.out.println("Phone      : " + sale.customerPhone);

        System.out.println("------------------------------------------------");

        System.out.printf(
                "%-18s %-8s %-10s %-12s%n",
                "PRODUCT", "QTY", "PRICE", "AMOUNT"
        );

        System.out.println("------------------------------------------------");

        for (BillItem item : sale.items) {

            System.out.printf(
                    "%-18s %-8d %-10.2f %-12.2f%n",
                    item.productName,
                    item.quantity,
                    item.price,
                    item.amount
            );
        }

        System.out.println("------------------------------------------------");

        System.out.printf("TOTAL AMOUNT: Rs. %.2f%n", sale.total);

        System.out.println("================================================");
        System.out.println("          Thank you for shopping!");
        System.out.println("================================================");
    }

    // ================= SALES HISTORY =================

    static void viewSalesHistory() {

        System.out.println("\n============== SALES HISTORY ==============");

        if (salesHistory.isEmpty()) {

            System.out.println("No sales recorded.");
            return;
        }

        double totalRevenue = 0;

        for (Sale s : salesHistory) {

            System.out.println("\nInvoice ID : " + s.invoiceId);
            System.out.println("Customer   : " + s.customerName);
            System.out.println("Phone      : " + s.customerPhone);
            System.out.println("Date       : " + s.date);

            System.out.printf(
                    "Total      : Rs. %.2f%n", s.total
            );

            totalRevenue += s.total;

            System.out.println("----------------------------------------");
        }

        System.out.printf(
                "TOTAL REVENUE: Rs. %.2f%n", totalRevenue
        );
    }

    // ================= INVENTORY REPORT =================

    static void inventoryReport() {

        System.out.println("\n============== INVENTORY REPORT ==============");

        if (products.isEmpty()) {

            System.out.println("No products available.");
            return;
        }

        double totalValue = 0;

        for (Product p : products) {

            double value = p.price * p.quantity;

            totalValue += value;

            System.out.println("\nProduct      : " + p.name);
            System.out.println("Category     : " + p.category);
            System.out.println("Available    : " + p.quantity);

            System.out.printf(
                    "Stock Value  : Rs. %.2f%n", value
            );

            System.out.println("Status       : " + getStatus(p));
        }

        System.out.println("----------------------------------------");

        System.out.printf(
                "TOTAL INVENTORY VALUE: Rs. %.2f%n", totalValue
        );
    }

    // ================= PRODUCT STATUS =================

    static String getStatus(Product p) {

        if (p.quantity == 0) {
            return "OUT OF STOCK";
        }

        if (p.quantity <= 5) {
            return "LOW STOCK";
        }

        return "IN STOCK";
    }

    // ================= FIND PRODUCT =================

    static Product findProduct(int id) {

        for (Product p : products) {

            if (p.id == id) {
                return p;
            }
        }

        return null;
    }

    // ================= DISPLAY PRODUCT =================

    static void displayProduct(Product p) {

        System.out.println("\nProduct ID : " + p.id);
        System.out.println("Name       : " + p.name);
        System.out.println("Category   : " + p.category);

        System.out.printf("Price      : Rs. %.2f%n", p.price);

        System.out.println("Quantity   : " + p.quantity);
        System.out.println("Status     : " + getStatus(p));
    }

    // ================= SAVE DATA =================

    static void saveData() {

        File folder = new File(DATA_FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        StoreData data = new StoreData(
                products,
                salesHistory,
                nextId,
                nextInvoiceId
        );

        try (ObjectOutputStream output = new ObjectOutputStream(
                new FileOutputStream(DATA_FILE))) {

            output.writeObject(data);

        } catch (IOException e) {

            System.out.println(
                    "Error saving data: " + e.getMessage()
            );
        }
    }

    // ================= LOAD DATA =================

    static void loadData() {

        File file = new File(DATA_FILE);

        if (!file.exists()) {

            System.out.println("Starting StockMaster for the first time.");
            return;
        }

        try (ObjectInputStream input = new ObjectInputStream(
                new FileInputStream(file))) {

            StoreData data = (StoreData) input.readObject();

            products = data.products;
            salesHistory = data.salesHistory;

            nextId = data.nextId;
            nextInvoiceId = data.nextInvoiceId;

            System.out.println("Previous data loaded successfully.");

        } catch (IOException | ClassNotFoundException e) {

            System.out.println(
                    "Unable to load saved data: " + e.getMessage()
            );

            products = new ArrayList<>();
            salesHistory = new ArrayList<>();
        }
    }

    // ================= INPUT VALIDATION =================

    static int readInt() {

        while (true) {

            try {

                return Integer.parseInt(sc.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.print("Enter a valid integer: ");
            }
        }
    }

    static double readDouble() {

        while (true) {

            try {

                double value = Double.parseDouble(
                        sc.nextLine().trim()
                );

                if (!Double.isFinite(value)) {
                    System.out.print("Enter a finite number: ");
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {

                System.out.print("Enter a valid number: ");
            }
        }
    }
}