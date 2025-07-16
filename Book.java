package CWAnhDuy;

public class Book {
    public String title;
    public String author;
    public double price;
    public int quantity;
    public String isbn;

    public Book(String title, String author, double price, int quantity, String isbn) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.isbn = isbn;
    }

    // Display all books
    public void display() {
        System.out.println( "Title: " + title +
                "| Author: " + author +
                "| Price: " + price +
                "| Quantity: " + quantity +
                "| ISBN: " + isbn);
    }
}
