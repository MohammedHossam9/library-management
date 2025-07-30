import models.User;
import models.Admin;
import models.RegularUser;
import models.Book;
import services.UserService;
import services.BookService;
import java.util.List;
import java.util.Scanner;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();
        BookService bookService = new BookService();
        User currentUser = null;

        while (true) {
            System.out.println("\n--- Library Management System ---");
            System.out.println("1. Login (as user or admin)");
            System.out.println("2. Register as Regular User");
            System.out.println("3. Register as Admin");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                Optional<User> user = userService.getUserByUsername(username);
                if (user.isEmpty()) {
                    System.out.println("User not found.");
                    continue;
                }
                currentUser = user.get();
                if (currentUser instanceof Admin) {
                    adminMenu(scanner, userService, bookService, (Admin) currentUser);
                } else if (currentUser instanceof RegularUser) {
                    regularUserMenu(scanner, userService, bookService, (RegularUser) currentUser);
                } else {
                    System.out.println("Unknown user type.");
                }
            } else if (choice.equals("2")) {
                System.out.print("Enter your username: ");
                String username = scanner.nextLine();
                RegularUser newUser = new RegularUser();
                newUser.setUsername(username);
                userService.addUser(newUser);
                System.out.println("Registered! Your username is: " + newUser.getUsername());
            } else if (choice.equals("3")) {
                System.out.print("Enter your username: ");
                String username = scanner.nextLine();
                Admin newAdmin = new Admin();
                newAdmin.setUsername(username);
                userService.addUser(newAdmin);
                System.out.println("Registered! Your admin username is: " + newAdmin.getUsername());
            } else if (choice.equals("0")) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }

    private static void adminMenu(Scanner scanner, UserService userService, BookService bookService, Admin admin) {
        label:
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Edit Book");
            System.out.println("3. Delete Book");
            System.out.println("4. Register User");
            System.out.println("5. View All Books");
            System.out.println("6. View All Genres");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": {
                    Book book = new Book();
                    System.out.print("Enter book ID: ");
                    book.setId(Integer.parseInt(scanner.nextLine()));
                    System.out.print("Enter title: ");
                    book.setTitle(scanner.nextLine());
                    System.out.print("Enter author: ");
                    book.setAuthor(scanner.nextLine());
                    System.out.print("Enter genre: ");
                    book.setGenre(scanner.nextLine());
                    System.out.print("Enter available copies: ");
                    book.setAvailableCopies(Integer.parseInt(scanner.nextLine()));
                    bookService.addBook(book);
                    System.out.println("Book added.");
                    break;
                }
                case "2": {
                    System.out.print("Enter book ID to edit: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Book book = bookService.getBookById(id);
                    if (book == null) {
                        System.out.println("Book not found.");
                        continue;
                    }
                    System.out.print("Enter new title (" + book.getTitle() + "): ");
                    String title = scanner.nextLine();
                    if (!title.isEmpty()) book.setTitle(title);
                    System.out.print("Enter new author (" + book.getAuthor() + "): ");
                    String author = scanner.nextLine();
                    if (!author.isEmpty()) book.setAuthor(author);
                    System.out.print("Enter new genre (" + book.getGenre() + "): ");
                    String genre = scanner.nextLine();
                    if (!genre.isEmpty()) book.setGenre(genre);
                    System.out.print("Enter new available copies (" + book.getAvailableCopies() + "): ");
                    String copies = scanner.nextLine();
                    if (!copies.isEmpty()) book.setAvailableCopies(Integer.parseInt(copies));
                    bookService.updateBook(book);
                    System.out.println("Book updated.");
                    break;
                }
                case "3": {
                    System.out.print("Enter book ID to delete: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    bookService.deleteBook(id);
                    System.out.println("Book deleted.");
                    break;
                }
                case "4":
                    System.out.print("Enter user name: ");
                    String name = scanner.nextLine();
                    System.out.print("Is this user an admin? (y/n): ");
                    String isAdmin = scanner.nextLine();
                    User user;
                    if (isAdmin.equalsIgnoreCase("y")) {
                        user = new Admin();
                    } else {
                        user = new RegularUser();
                    }
                    user.setUsername(name);
                    user.setId((int) (System.currentTimeMillis() % Integer.MAX_VALUE));
                    userService.addUser(user);
                    System.out.println("User registered! ID: " + user.getId());
                    break;
                case "5":
                    List<Book> books = bookService.getAllBooks();
                    for (Book b : books) {
                        System.out.println(b.getId() + ": " + b.getTitle() + " by " + b.getAuthor() + " (" + b.getGenre() + ") - Copies: " + b.getAvailableCopies());
                    }
                    break;
                case "6":
                    System.out.println("Genres in library:");
                    for (String genre : bookService.getAllGenres()) {
                        System.out.println("- " + genre);
                    }
                    break;
                case "0":
                    break label;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private static void regularUserMenu(Scanner scanner, UserService userService, BookService bookService, RegularUser user) {
        label:
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View Book Catalog");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. View My Borrowed Books");
            System.out.println("5. View All Genres");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    List<Book> books = bookService.getAllBooks();
                    for (Book b : books) {
                        System.out.println(b.getId() + ": " + b.getTitle() + " by " + b.getAuthor() + " (" + b.getGenre() + ") - Copies: " + b.getAvailableCopies());
                    }
                    break;
                case "2": {
                    System.out.print("Enter book ID to borrow: ");
                    int bookId = Integer.parseInt(scanner.nextLine());
                    boolean success = userService.borrowBook(user.getId(), bookId);
                    if (success) {
                        System.out.println("Book borrowed!");
                    } else {
                        System.out.println("Could not borrow book. Check availability or if already borrowed.");
                    }
                    break;
                }
                case "3": {
                    System.out.print("Enter book ID to return: ");
                    int bookId = Integer.parseInt(scanner.nextLine());
                    boolean success = userService.returnBook(user.getId(), bookId);
                    if (success) {
                        System.out.println("Book returned!");
                    } else {
                        System.out.println("Could not return book. Check if you borrowed it.");
                    }
                    break;
                }
                case "4":
                    RegularUser freshUser = (RegularUser) userService.getUserById(user.getId());
                    List<String> borrowed = freshUser.getBorrowedBookIds();
                    if (borrowed == null || borrowed.isEmpty()) {
                        System.out.println("You have not borrowed any books.");
                    } else {
                        System.out.println("Your borrowed books:");
                        for (String bookId : borrowed) {
                            Book b = bookService.getBookById(Integer.parseInt(bookId));
                            if (b != null) {
                                System.out.println(b.getId() + ": " + b.getTitle() + " by " + b.getAuthor());
                            }
                        }
                    }
                    break;
                case "5":
                    System.out.println("Genres in library:");
                    for (String genre : bookService.getAllGenres()) {
                        System.out.println("- " + genre);
                    }
                    break;
                case "0":
                    break label;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }
}