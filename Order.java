package CWAnhDuy;

public class Order {
    public int orderID;
    public String customerName;
    public String address;
    public Book[] books;
    public int[] quantities;
    public double totalPrice;

    public Order(int orderID, String customerName, String address, Book[] books, int[] quantities, double totalPrice) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.address = address;
        this.books = books;
        this.quantities = quantities;
        this.totalPrice = totalPrice;
    }

    private void selectionSortBookOrder() {
        if (books == null || quantities == null) return;
        int n = books.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (quantities[j] < quantities[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int tempQty = quantities[i];
                quantities[i] = quantities[minIndex];
                quantities[minIndex] = tempQty;

                Book tempBook = books[i];
                books[i] = books[minIndex];
                books[minIndex] = tempBook;
            }
        }
    }

    public void displayOrderInformation() {
        selectionSortBookOrder();

        System.out.println("=== ORDER ===");
        System.out.println("Order ID: " + orderID );
        System.out.println("Customer Name: " + customerName);
        System.out.println("Address: " + address);
        System.out.println("Book Order: ");

        for (int i = 0; i < books.length; i++) {
            if (books[i] != null) {
                System.out.println("- " + books[i].title + " x " + quantities[i]);
            }
        }

        System.out.println("Total price: " + totalPrice);
    }
}