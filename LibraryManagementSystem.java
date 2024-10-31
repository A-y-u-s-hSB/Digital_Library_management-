import java.util.ArrayList;
import java.util.Scanner;

// Class representing a Book
class Book {
    private int bookId;
    private String title;
    private String author;
    private String category;
    private boolean isIssued;

    public Book(int bookId, String title, String author, String category) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isIssued = false;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void issueBook() {
        this.isIssued = true;
    }

    public void returnBook() {
        this.isIssued = false;
    }

    @Override
    public String toString() {
        return "Book ID: " + bookId + ", Title: " + title + ", Author: " + author + ", Category: " + category +
                ", Status: " + (isIssued ? "Issued" : "Available");
    }
}

// Class representing a User (both Admin and Normal Users)
class User {
    private String username;
    private String password;
    private String email;
    private boolean isAdmin;

    public User(String username, String password, String email, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Username: " + username + ", Email: " + email + ", Role: " + (isAdmin ? "Admin" : "User");
    }
}

// Class representing the Library System with Admin and User modules
class LibrarySystem {
    private ArrayList<Book> books;
    private ArrayList<User> users;
    private ArrayList<IssuedBook> issuedBooks;
    private User loggedInUser;

    public LibrarySystem() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        issuedBooks = new ArrayList<>();
        loadSampleData();
    }

    // Load sample books and users
    private void loadSampleData() {
        // Add some books
        books.add(new Book(1, "To Kill a Mockingbird", "Harper Lee", "Fiction"));
        books.add(new Book(2, "1984", "George Orwell", "Dystopia"));
        books.add(new Book(3, "The Great Gatsby", "F. Scott Fitzgerald", "Classic"));
        books.add(new Book(4, "Introduction to Algorithms", "Cormen", "Education"));

        // Add an admin and some users
        users.add(new User("admin", "admin123", "admin@library.com", true));
        users.add(new User("john", "password", "john@example.com", false));
        users.add(new User("jane", "1234", "jane@example.com", false));
    }

    // Function to login user
    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter Password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                System.out.println("Login successful! Welcome, " + loggedInUser.getUsername());
                return;
            }
        }
        System.out.println("Invalid credentials! Please try again.");
        login();
    }

    // Main menu
    public void start() {
        if (loggedInUser.isAdmin()) {
            adminMenu();
        } else {
            userMenu();
        }
    }

    // Admin Menu
    private void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Delete Book");
            System.out.println("3. View All Books");
            System.out.println("4. View Issued Books");
            System.out.println("5. Add User");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    deleteBook();
                    break;
                case 3:
                    viewAllBooks();
                    break;
                case 4:
                    viewIssuedBooks();
                    break;
                case 5:
                    addUser();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 6);
    }

    // User Menu
    private void userMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nUser Menu:");
            System.out.println("1. View Available Books");
            System.out.println("2. Search Book by Title/Category");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewAvailableBooks();
                    break;
                case 2:
                    searchBook();
                    break;
                case 3:
                    issueBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 5);
    }

    // Admin functionality to add a new book
    private void addBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author Name: ");
        String author = scanner.nextLine();
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();

        books.add(new Book(bookId, title, author, category));
        System.out.println("Book added successfully!");
    }

    // Admin functionality to delete a book
    private void deleteBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Book ID to delete: ");
        int bookId = scanner.nextInt();

        books.removeIf(book -> book.getBookId() == bookId);
        System.out.println("Book deleted successfully!");
    }

    // View all books (Admin)
    private void viewAllBooks() {
        System.out.println("\nAll Books in the Library:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    // View issued books (Admin)
    private void viewIssuedBooks() {
        System.out.println("\nIssued Books:");
        for (IssuedBook issuedBook : issuedBooks) {
            System.out.println(issuedBook);
        }
    }

    // Add a new user (Admin)
    private void addUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Is Admin? (true/false): ");
        boolean isAdmin = scanner.nextBoolean();

        users.add(new User(username, password, email, isAdmin));
        System.out.println("User added successfully!");
    }

    // User functionality to view available books
    private void viewAvailableBooks() {
        System.out.println("\nAvailable Books:");
        for (Book book : books) {
            if (!book.isIssued()) {
                System.out.println(book);
            }
        }
    }

    // Search book by title or category (User)
    private void searchBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter title or category to search: ");
        String searchKey = scanner.nextLine().toLowerCase();

        System.out.println("\nSearch Results:");
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(searchKey) || book.getCategory().toLowerCase().contains(searchKey)) {
                System.out.println(book);
            }
        }
    }

    // Issue a book (User)
    private void issueBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Book ID to issue: ");
        int bookId = scanner.nextInt();

        for (Book book : books) {
            if (book.getBookId() == bookId && !book.isIssued()) {
                book.issueBook();
                issuedBooks.add(new IssuedBook(book, loggedInUser));
                System.out.println("Book issued successfully!");
                return;
            }
        }
        System.out.println("Book is either not available or already issued.");
    }

    // Return a book (User)
    private void returnBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Book ID to return: ");
        int bookId = scanner.nextInt();

        for (IssuedBook issuedBook : issuedBooks) {
            if (issuedBook.getBook().getBookId() == bookId && issuedBook.getUser().getUsername().equals(loggedInUser.getUsername())) {
                issuedBook.getBook().returnBook();
                issuedBooks.remove(issuedBook);
                System.out.println("Book returned successfully!");
                return;
            }
        }
        System.out.println("Book not found or not issued by you.");
    }
}

// Class representing an Issued Book record
class IssuedBook {
    private Book book;
    private User user;

    public IssuedBook(Book book, User user) {
        this.book = book;
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Book: " + book.getTitle() + ", Issued to: " + user.getUsername();
    }
}

// Main class to run the Library System
public class LibraryManagementSystem {
    public static void main(String[] args) {
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.login();
        librarySystem.start();
    }
}
