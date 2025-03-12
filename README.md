# Library Management System

A Java-based console application for managing a library system with different user roles, book types, and file-based data persistence.

## Features

- User Management (Admin)
  - Add users (Admin, Librarian, Reader)
  - Remove users
  - List all users

- Book Management (Librarian)
  - Add books (Printed Books and E-books)
  - Remove books
  - List all books

- Book Borrowing (Reader)
  - Borrow printed books
  - Return books
  - View available books

## Requirements

- Java 11 or higher

## Setup and Running

### Using javac (Simpler)
1. Compile the code:
```bash
javac src/model/*.java src/service/*.java src/LibraryManagementSystem.java
```

2. Run the application:
```bash
java -cp src LibraryManagementSystem
```

## First Time Setup

The system will automatically create these files on first run:
- users.txt: User accounts
- books.txt: Book inventory
- transactions.txt: Borrowing history

## Default Admin Account

On first run, a default admin account is created:
- Username: admin
- Password: admin123

## Usage Guide

1. Log in using the default admin account
2. Create necessary user accounts:
   - Create Librarians to manage books
   - Create Readers who can borrow books
3. Log in as Librarian to:
   - Add new books
   - Remove books
   - View book inventory
4. Log in as Reader to:
   - View available books
   - Borrow books
   - Return books