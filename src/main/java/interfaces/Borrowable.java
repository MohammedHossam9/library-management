package interfaces;

public interface Borrowable {
    boolean borrowBook(String bookId);
    boolean returnBook(String bookId);
}
