package CWAnhDuy;

import java.util.Scanner;

public class BookStoreOnlineMenu {

    static ArrayList<Book> arrayList = new ArrayList<>();
    static LinkedQueue<Order> linkedQueue = new LinkedQueue<>();
    static LinkedStack<Order> undoStack = new LinkedStack<>();

    private static void originalBooks() {
        arrayList.add(new Book("To Kill a Mockingbird", "Harper Lee", 10, 3, "B1"));
        arrayList.add(new Book("Brave New World", "Aldous Huxley", 15, 5, "B2"));
        arrayList.add(new Book("Pride and Prejudice", "Jane Austen", 8, 2, "B3"));
        arrayList.add(new Book("The Alchemist", "Paulo Coelho", 12, 7, "B4"));
        arrayList.add(new Book("The Catcher in the Rye", "J.D. Salinger", 7, 6, "B5"));
    }

    private static void displayMenu() {
        System.out.println("\nWelcome to Bookstore Online");
        System.out.println("1. Available Books");
        System.out.println("2. Place order books");
        System.out.println("3. Order tracking");
        System.out.println("4. Searching order");
        System.out.println("5. Add Book");
        System.out.println("6. Remove Book");
        System.out.println("7. Undo last order");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void displayAllBooks() {
        System.out.println("== Available Books ==");
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).display();
        }
        System.out.println("=======================");
    }

    private static void placeOrder() {
        Scanner scanner = new Scanner(System.in);
        displayAllBooks();

        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine();
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();

        Book[] books = new Book[10];
        int[] quantities = new int[10];
        int count = 0;
        double totalPrice = 0;

        while (true){
            System.out.print("\nEnter ISBN of the book you want to order: ");
            String isbn = scanner.nextLine();

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

            System.out.print("Enter quantity for \"" + selectedBook.title + "\" (Available: " + selectedBook.quantity + "): ");
            int orderQuantity = Integer.parseInt(scanner.nextLine());

            if (orderQuantity <= 0 || orderQuantity > selectedBook.quantity) {
                System.out.println("Not enough books or invalid quantity.");
                continue;
            }

            books[count] = selectedBook;
            quantities[count] = orderQuantity;
            totalPrice += selectedBook.price * orderQuantity;
            selectedBook.quantity -= orderQuantity;
            count++;

            System.out.print("➕ Do you want to order another book? (yes/no): ");
            if (!scanner.nextLine().equalsIgnoreCase("yes")) {
                break;
            }
        }

        Book[] finalBooks = new Book[count];
        int[] finalQuantities = new int[count];

        for (int i = 0; i < count; i++) {
            finalBooks[i] = books[i];
            finalQuantities[i] = quantities[i];
        }

        int orderID = linkedQueue.size() + 1;
        Order order = new Order(orderID, customerName, address, finalBooks, finalQuantities, totalPrice);
        linkedQueue.offer(order);
        undoStack.push(order);

        System.out.println("\n Order placed successfully!");
        order.displayOrderInformation();
        System.out.println("==========================");
    }


    private static void trackOrders() {
        System.out.println("== All Orders ==");
        LinkedQueue<Order> tempQueue = new LinkedQueue<>();
        while (!linkedQueue.isEmpty()) {
            Order order = linkedQueue.poll();
            order.displayOrderInformation();
            System.out.println("==========================");
            tempQueue.offer(order);
        }
        while(!tempQueue.isEmpty()){
            linkedQueue.offer(tempQueue.poll());
        }
    }

    private static void searchOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Search By: \n1. Order ID\n2. Name\n3. Address\n4. ISBN");
        int choice = Integer.parseInt(scanner.nextLine());
        System.out.print(" Enter your search keyword: ");
        String keyword = scanner.nextLine();
        boolean found = false;

        LinkedQueue<Order> tempQueue = new LinkedQueue<>();
        while (!linkedQueue.isEmpty()) {
            Order order = linkedQueue.poll();
            boolean isMatch = false;
            switch (choice) {
                case 1:
                    if (String.valueOf(order.orderID).equals(keyword)) isMatch = true;
                    break;
                case 2:
                    if (order.customerName.equalsIgnoreCase(keyword)) isMatch = true;
                    break;
                case 3:
                    if (order.address.equalsIgnoreCase(keyword)) isMatch = true;
                    break;
                case 4:
                    for (Book book : order.books) {
                        if (book != null && book.isbn.equalsIgnoreCase(keyword)) {
                            isMatch = true;
                            break;
                        }
                    }
                    break;
            }
            if(isMatch){
                order.displayOrderInformation();
                found = true;
            }
            tempQueue.offer(order);
        }
        while(!tempQueue.isEmpty()){
            linkedQueue.offer(tempQueue.poll());
        }
        if (!found) System.out.println("No matching order found.");
    }

    private static void addBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("== Add New Book ==");
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).isbn.equalsIgnoreCase(isbn)) {
                System.out.println("A book with this ISBN already exists.");
                return;
            }
        }

        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author name: ");
        String author = scanner.nextLine();
        System.out.print("Enter book price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        arrayList.add(new Book(title, author, price, quantity, isbn));
        System.out.println("Book added successfully.");
    }

    private static void removeBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ISBN of the book to remove: ");
        String isbn = scanner.nextLine();

        int indexToRemove = -1;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).isbn.equalsIgnoreCase(isbn)) {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove != -1) {
            arrayList.remove(indexToRemove);
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

        for(int i = 0; i < lastOrder.books.length; i++){
            for(int j = 0; j < arrayList.size(); j++){
                if(arrayList.get(j).isbn.equalsIgnoreCase(lastOrder.books[i].isbn)){
                    arrayList.get(j).quantity += lastOrder.quantities[i];
                    break;
                }
            }
        }

        LinkedQueue<Order> tempQueue = new LinkedQueue<>();
        while (!linkedQueue.isEmpty()) {
            Order current = linkedQueue.poll();
            if (current.orderID != lastOrder.orderID) {
                tempQueue.offer(current);
            }
        }
        linkedQueue = tempQueue;

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
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    displayAllBooks();
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
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please select from 1 to 8.");
            }
        }
    }
}