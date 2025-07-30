package models;
import interfaces.Borrowable;
import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("REGULAR")
public class RegularUser extends User implements Borrowable {
    @Override
    public boolean borrowBook(String bookId) {
        List<String> borrowed = getBorrowedBookIds();
        if (borrowed.contains(bookId)) return false;
        borrowed.add(bookId);
        
        return true;
    }

    @Override
    public boolean returnBook(String bookId) {
        List<String> borrowed = getBorrowedBookIds();
        if (!borrowed.contains(bookId)) return false;
        borrowed.remove(bookId);
        
        return true;
    }
}
