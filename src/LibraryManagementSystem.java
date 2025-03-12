import model.*;
import service.LibraryService;
import java.util.Scanner;
import java.util.List;

public class LibraryManagementSystem {
    private static LibraryService libraryService;
    private static Scanner scanner;

    public static void main(String[] args) {
        libraryService = new LibraryService();
        scanner = new Scanner(System.in);

        while (true) {
            if (libraryService.getCurrentUser() == null) {
                if (!login()) {
                    System.out.println("Invalid credentials. Please try again.");
                    continue;
                }
            }

            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 0:
                    System.out.println("Goodbye!");
                    return;
                case 1:
                    handleUserManagement();
                    break;
                case 2:
                    handleBookManagement();
                    break;
                case 3:
                    handleBorrowReturn();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static boolean login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        return libraryService.login(username, password);
    }

    private static void showMenu() {
        System.out.println("\n=== Library Management System ===");
        System.out.println("1. User Management (Admin)");
        System.out.println("2. Book Management (Librarian)");
        System.out.println("3. Borrow/Return Books (Reader)");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private static void handleUserManagement() {
        if (libraryService.getCurrentUser().getType() != UserType.ADMIN) {
            System.out.println("Access denied. Admin rights required.");
            return;
        }

        while (true) {
            System.out.println("\n=== User Management ===");
            System.out.println("1. Add User");
            System.out.println("2. Remove User");
            System.out.println("3. List Users");
            System.out.println("0. Back");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 0:
                    return;
                case 1:
                    addUser();
                    break;
                case 2:
                    removeUser();
                    break;
                case 3:
                    listUsers();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void handleBookManagement() {
        if (libraryService.getCurrentUser().getType() != UserType.LIBRARIAN) {
            System.out.println("Access denied. Librarian rights required.");
            return;
        }

        while (true) {
            System.out.println("\n=== Book Management ===");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. List Books");
            System.out.println("0. Back");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 0:
                    return;
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    listBooks();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void handleBorrowReturn() {
        if (libraryService.getCurrentUser().getType() != UserType.READER) {
            System.out.println("Access denied. Reader rights required.");
            return;
        }

        while (true) {
            System.out.println("\n=== Borrow/Return Books ===");
            System.out.println("1. Borrow Book");
            System.out.println("2. Return Book");
            System.out.println("3. List Available Books");
            System.out.println("0. Back");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 0:
                    return;
                case 1:
                    borrowBook();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    listBooks();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.println("Select user type:");
        System.out.println("1. Admin");
        System.out.println("2. Librarian");
        System.out.println("3. Reader");
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        UserType type;
        switch (typeChoice) {
            case 1:
                type = UserType.ADMIN;
                break;
            case 2:
                type = UserType.LIBRARIAN;
                break;
            case 3:
                type = UserType.READER;
                break;
            default:
                System.out.println("Invalid user type.");
                return;
        }

        if (libraryService.addUser(username, password, type)) {
            System.out.println("User added successfully.");
        } else {
            System.out.println("Failed to add user. Username might already exist.");
        }
    }

    private static void removeUser() {
        System.out.print("Enter username to remove: ");
        String username = scanner.nextLine();
        if (libraryService.removeUser(username)) {
            System.out.println("User removed successfully.");
        } else {
            System.out.println("Failed to remove user.");
        }
    }

    private static void listUsers() {
        List<User> users = libraryService.listUsers();
        System.out.println("\nUsers:");
        for (User user : users) {
            System.out.println(user.getUsername() + " (" + user.getType() + ")");
        }
    }

    private static void addBook() {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.println("Select book type:");
        System.out.println("1. Printed Book");
        System.out.println("2. E-book");
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Book book;
        if (typeChoice == 1) {
            System.out.print("Enter number of pages: ");
            int pages = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            book = new PrintedBook(isbn, title, author, pages);
        } else if (typeChoice == 2) {
            System.out.print("Enter file format: ");
            String format = scanner.nextLine();
            book = new Ebook(isbn, title, author, format);
        } else {
            System.out.println("Invalid book type.");
            return;
        }

        if (libraryService.addBook(book)) {
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Failed to add book. ISBN might already exist.");
        }
    }

    private static void removeBook() {
        System.out.print("Enter ISBN to remove: ");
        String isbn = scanner.nextLine();
        if (libraryService.removeBook(isbn)) {
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Failed to remove book.");
        }
    }

    private static void listBooks() {
        List<Book> books = libraryService.listBooks();
        System.out.println("\nBooks:");
        for (Book book : books) {
            System.out.println(book.getTitle() + " by " + book.getAuthor() + 
                " (ISBN: " + book.getIsbn() + ") - " + 
                (book.isAvailable() ? "Available" : "Borrowed"));
        }
    }

    private static void borrowBook() {
        System.out.print("Enter ISBN to borrow: ");
        String isbn = scanner.nextLine();
        if (libraryService.borrowBook(isbn)) {
            System.out.println("Book borrowed successfully.");
        } else {
            System.out.println("Failed to borrow book. Book might not be available or might be an e-book.");
        }
    }

    private static void returnBook() {
        System.out.print("Enter ISBN to return: ");
        String isbn = scanner.nextLine();
        if (libraryService.returnBook(isbn)) {
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Failed to return book.");
        }
    }
} 