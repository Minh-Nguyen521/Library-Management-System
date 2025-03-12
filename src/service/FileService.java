package service;

import model.*;
import java.io.*;
import java.util.*;

public class FileService {
    private static final String USERS_FILE = "data/users.txt";
    private static final String BOOKS_FILE = "data/books.txt";
    private static final String TRANSACTIONS_FILE = "data/transactions.txt";

    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(User.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not load users file");
        }
        return users;
    }

    public void saveUsers(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                writer.write(user.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: Could not save users");
        }
    }

    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals("PRINTED")) {
                    books.add(PrintedBook.fromString(line));
                } else if (parts[0].equals("EBOOK")) {
                    books.add(Ebook.fromString(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not load books file");
        }
        return books;
    }

    public void saveBooks(List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                writer.write(book.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: Could not save books");
        }
    }

    public void saveTransaction(String username, String isbn, String action) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE, true))) {
            writer.write(username + "," + isbn + "," + action + "," + System.currentTimeMillis());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error: Could not save transaction");
        }
    }
}