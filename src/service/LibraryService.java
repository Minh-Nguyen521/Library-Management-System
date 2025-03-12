package service;

import model.*;
import java.util.*;

public class LibraryService {
    private List<User> users;
    private List<Book> books;
    private FileService fileService;
    private User currentUser;

    public LibraryService() {
        this.fileService = new FileService();
        this.users = fileService.loadUsers();
        this.books = fileService.loadBooks();
        
        // Add default admin if no users exist
        if (users.isEmpty()) {
            users.add(new User("admin", "admin123", UserType.ADMIN));
            fileService.saveUsers(users);
        }
    }

    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public boolean addUser(String username, String password, UserType type) {
        if (currentUser == null || currentUser.getType() != UserType.ADMIN) {
            return false;
        }

        if (users.stream().anyMatch(u -> u.getUsername().equals(username))) {
            return false;
        }

        users.add(new User(username, password, type));
        fileService.saveUsers(users);
        return true;
    }

    public boolean removeUser(String username) {
        if (currentUser == null || currentUser.getType() != UserType.ADMIN) {
            return false;
        }

        boolean removed = users.removeIf(u -> u.getUsername().equals(username));
        if (removed) {
            fileService.saveUsers(users);
        }
        return removed;
    }

    public List<User> listUsers() {
        if (currentUser == null || currentUser.getType() != UserType.ADMIN) {
            return Collections.emptyList();
        }
        return new ArrayList<>(users);
    }

    public boolean addBook(Book book) {
        if (currentUser == null || currentUser.getType() != UserType.LIBRARIAN) {
            return false;
        }

        if (books.stream().anyMatch(b -> b.getIsbn().equals(book.getIsbn()))) {
            return false;
        }

        books.add(book);
        fileService.saveBooks(books);
        return true;
    }

    public boolean removeBook(String isbn) {
        if (currentUser == null || currentUser.getType() != UserType.LIBRARIAN) {
            return false;
        }

        boolean removed = books.removeIf(b -> b.getIsbn().equals(isbn));
        if (removed) {
            fileService.saveBooks(books);
        }
        return removed;
    }

    public List<Book> listBooks() {
        return new ArrayList<>(books);
    }

    public boolean borrowBook(String isbn) {
        if (currentUser == null || currentUser.getType() != UserType.READER) {
            return false;
        }

        Optional<Book> bookOpt = books.stream()
            .filter(b -> b.getIsbn().equals(isbn) && b.isAvailable() && !(b instanceof Ebook))
            .findFirst();

        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setAvailable(false);
            fileService.saveBooks(books);
            fileService.saveTransaction(currentUser.getUsername(), isbn, "BORROW");
            return true;
        }
        return false;
    }

    public boolean returnBook(String isbn) {
        if (currentUser == null || currentUser.getType() != UserType.READER) {
            return false;
        }

        Optional<Book> bookOpt = books.stream()
            .filter(b -> b.getIsbn().equals(isbn) && !b.isAvailable())
            .findFirst();

        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setAvailable(true);
            fileService.saveBooks(books);
            fileService.saveTransaction(currentUser.getUsername(), isbn, "RETURN");
            return true;
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }
} 