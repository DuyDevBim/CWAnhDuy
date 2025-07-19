package CWAnhDuy;
import java.util.Scanner;

public class BookStoreOnlineMenu {
    static ArrayList arrayList = new ArrayList();
    static LinkedQueue linkedQueue = new LinkedQueue();
    static LinkedStack<Order> undoStack = new LinkedStack<>();

    // Original Book
    private static void originalBooks() {
        arrayList.add(new Book("To Kill a Mockingbird", "Harper Lee", 10, 3, "TKAM"));
        arrayList.add(new Book("Brave New World", "Aldous Huxley", 15, 5, "BNW"));
        arrayList.add(new Book("Pride and Prejudice", "Jane Austen", 8, 2, "PAP"));
        arrayList.add(new Book("The Alchemist", "Paulo Coelho", 12, 7, "TA"));
        arrayList.add(new Book("The Catcher in the Rye", "J.D. Salinger", 7, 6, "TCITR"));
    }

    // Display Menu
    private static void displayMenu() {
        System.out.println("Welcome to Bookstore Online");
        System.out.println("1. Available Books ");
        System.out.println("2. Place order books");
        System.out.println("3. Order tracking");
        System.out.println("4. Searching order");
        System.out.println("5. Add Book");
        System.out.println("6. Remove Book");
        System.out.println("7. Undo last order");
        System.out.println("8. Exit");
        System.out.println(" Enter your choice: ");
    }

    private static void placeOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Available Books:");
        arrayList.displayAllBooks();

        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter your address: ");
        String address = scanner.nextLine();

        Book[] books = new Book[10]; // tối đa 10 loại sách
        int[] quantities = new int[10];
        int count = 0;
        double totalPrice = 0;

        while (true){
            System.out.print("\nEnter ISBN of the book you want to order: ");
            String isbn = scanner.nextLine();

            // Tìm sách theo ISBN
            Book selectedBook = null;
            for (int i = 0; i < arrayList.size(); i++) {
                Book book = arrayList.get(i);
                if (book.isbn.equalsIgnoreCase(isbn)) {
                    selectedBook = book;
                    break;
                }
            }

            if (selectedBook == null) {
                System.out.println("Invalid ISBN. Try again.");
                continue;
            }

            if (selectedBook.quantity == 0) {
                System.out.println("Book \"" + selectedBook.title + "\" is out of stock.");
                continue;
            }

            // Kiểm tra số lượng tồn kho
            int orderQuantity;
            while (true) {
                System.out.print("📦 Enter quantity for \"" + selectedBook.title + "\" (Available: " + selectedBook.quantity + "): ");
                String input = scanner.nextLine();

                try {
                    orderQuantity = Integer.parseInt(input);

                    if (orderQuantity <= 0) {
                        System.out.println(" Quantity must be greater than 0.");
                    } else if (orderQuantity > selectedBook.quantity) {
                        System.out.println(" Not enough book");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            books[count] = selectedBook;
            quantities[count] = orderQuantity;
            totalPrice += selectedBook.price * orderQuantity;
            selectedBook.quantity -= orderQuantity;
            count++;

            System.out.print("➕ Do you want to order another book? (yes/no): ");
            String more = scanner.nextLine();
            if (!more.equalsIgnoreCase("yes")) {
                break;
            }
        }

        Book[] finalBooks = new Book[count];
        int[] finalQuantities = new int[count];
        for (int i = 0; i < count; i++) {
            finalBooks[i] = books[i];
            finalQuantities[i] = quantities[i];
        }

        // Tạo đơn hàng
        int orderID = linkedQueue.size() + 1;
        Order order = new Order(orderID, customerName, address, finalBooks, finalQuantities, totalPrice);
        linkedQueue.offer(order);
        undoStack.push(order); // lưu vào stack lịch sử

        System.out.println("\n Order placed successfully!");
        order.displayOrderInformation();
        System.out.println("Status: In progress");
        System.out.println("==========================");
    }


    private static void trackOrders() {
        linkedQueue.displayAllOrders();
        System.out.println("Status: Completed");
        System.out.println("==========================");
    }

    private static void searchOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Search By: ");
        System.out.println("1. Search by Order ID");
        System.out.println("2. Search by Name");
        System.out.println("3. Search by Address");
        System.out.println("4. Search by ISBN");
        int choice = Integer.parseInt(scanner.nextLine());

        boolean found = false;
        LinkedQueue.Node current = linkedQueue.getHead();

        System.out.print("🔎 Enter your search keyword: ");
        String keyword = scanner.nextLine();

        while (current != null) {
            Order order = (Order) current.order;

            switch (choice) {
                case 1:
                    if (String.valueOf(order.orderID).equals(keyword)) {
                        order.displayOrderInformation();
                        found = true;
                    }
                    break;
                case 2:
                    if (order.customerName.equalsIgnoreCase(keyword)) {
                        order.displayOrderInformation();
                        found = true;
                    }
                    break;
                case 3:
                    if (order.address.equalsIgnoreCase(keyword)) {
                        order.displayOrderInformation();
                        found = true;
                    }
                    break;
                case 4:
                    for (Book book : order.books) {
                        if (book != null && book.isbn.equalsIgnoreCase(keyword)) {
                            order.displayOrderInformation();
                            found = true;
                            break;
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }

            current = current.next;
        }

        if (!found) {
            System.out.println("No matching order found.");
        }

    }

    private static void addBook() {
        Scanner scanner = new Scanner(System.in);
        double price = -1;
        int quantity = -1;

        System.out.println("== Add New Book ==");

        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author name: ");
        String author = scanner.nextLine();


        while (price <= 0) {
            System.out.print("Enter book price: ");

            if (scanner.hasNextDouble()) {
                price = scanner.nextDouble();
                scanner.nextLine();

                if (price <= 0) {
                    System.out.println("Price must be greater than 0. Please try again.");
                }

            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
        }

        while (quantity <= 0) {
            System.out.print("Enter quantity: ");

            if (scanner.hasNextInt()) {
                quantity = scanner.nextInt();
                scanner.nextLine();

                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than 0. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next();
            }
        }


        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        if (arrayList.contains(isbn)) {
            System.out.println("A book with this ISBN already exists.");
            return;
        }

        Book newBook = new Book(title, author, price, quantity, isbn);
        arrayList.add(newBook);
        System.out.println("Book added successfully.");
    }

    private static void removeBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ISBN of the book to remove: ");
        String isbn = scanner.nextLine();

        if (arrayList.remove(isbn)) {
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book not found with that ISBN.");
        }
    }

    private static void undoLastOrder() {
        if (undoStack.isEmpty()) {
            System.out.println("No order to undo.");
            return;
        }

        Order lastOrder = undoStack.pop();
        LinkedQueue tempQueue = new LinkedQueue();

        while (!linkedQueue.isEmpty()) {
            Order current = linkedQueue.poll();
            if (current != lastOrder) {
                tempQueue.offer(current);
            }
        }

        linkedQueue = tempQueue; // Gán lại hàng đợi mới
        System.out.println("Last order has been undone:");
        lastOrder.displayOrderInformation();
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        originalBooks();

        while (true) {
            displayMenu();
            int choice = -1;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input. Please enter a number from 1 to 6.");
                continue;
            }

            switch (choice) {
                case 1:
                    arrayList.displayAllBooks();
                    break;
                case 2:
                    placeOrder();
                    break;
                case 3:
                    trackOrders();
                    break;
                case 4:
                    searchOrder();
                    break;
                case 5:
                    addBook();
                    break;
                case 6:
                    removeBook();
                    break;
                case 7:
                    undoLastOrder();
                    break;
                case 8:
                    System.out.println(" Existing....... ");
                    return;
                default:
                    System.out.println(" Invalid choice. Please select from 1 to 6.");
            }
        }
    }
}

