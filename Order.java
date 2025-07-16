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

    private void SelectionSortBookOrder() {
        int n = quantities.length;
        for (int i = 0; i < n - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (quantities[j] < quantities[minIndex]) {
                    minIndex = j;
                }
            }

            int tempQty = quantities[i];
            quantities[i] = quantities[minIndex];
            quantities[minIndex] = tempQty;

            // Hoán đổi books tương ứng
            Book tempBook = books[i];
            books[i] = books[minIndex];
            books[minIndex] = tempBook;
        }
    }

    public void displayOrderInformation() {
        SelectionSortBookOrder();

        System.out.println("=== ORDER ===");
        System.out.println("Order ID: " + orderID );
        System.out.println("Customer Name: " + customerName);
        System.out.println("Address: " + address);
        System.out.println("Book Order: ");

        for (int i = 0; i < books.length; i++) {
            System.out.println("- " + books[i].title + " x " + quantities[i]);
        }

        System.out.println("Total price: " + totalPrice);
//        System.out.println("==========================");
    }
}
