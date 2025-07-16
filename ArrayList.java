package CWAnhDuy;

public class ArrayList implements AbstractArrayList<Book> {
    private Book[] bookArray;
    private int size;

    public ArrayList() {
        this.bookArray = new Book[10];
        this.size = 0;
    }

    @Override
    public boolean add(Book item) {
        if (size == bookArray.length) {
            resize();
        }
        bookArray[size++] = item;
        return true;
    }

    @Override
    public Book get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return bookArray[index];
    }

    @Override
    public boolean remove(String isbn) {
        for (int i = 0; i < size; i++) {
            if (bookArray[i].isbn.equals(isbn)) {
                for (int j = i; j < size - 1; j++) {
                    bookArray[j] = bookArray[j + 1];
                }
                bookArray[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(String isbn) {
        for (int i = 0; i < size; i++) {
            if (bookArray[i].isbn.equals(isbn)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void resize() {
        int newSize = bookArray.length * 2;
        Book[] newBooks = new Book[newSize];

        for (int i = 0; i < bookArray.length; i++) {
            newBooks[i] = bookArray[i];
        }
        bookArray = newBooks;
    }

    // Optional: in toàn bộ danh sách sách
    public void displayAllBooks() {
        for (int i = 0; i < size; i++) {
            bookArray[i].display();
        }
    }
}
