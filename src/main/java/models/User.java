package models;
import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> borrowedBookIds;

    public User() {
        this.borrowedBookIds = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public List<String> getBorrowedBookIds() {
        if (borrowedBookIds == null) {
            borrowedBookIds = new ArrayList<>();
        }
        return borrowedBookIds;
    }
}
