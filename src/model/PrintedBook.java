package model;

public class PrintedBook extends Book {
    private int numberOfPages;

    public PrintedBook(String isbn, String title, String author, int numberOfPages) {
        super(isbn, title, author);
        this.numberOfPages = numberOfPages;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    @Override
    public String getType() {
        return "PRINTED";
    }

    @Override
    public String toString() {
        return super.toString() + "," + numberOfPages;
    }

    public static PrintedBook fromString(String line) {
        String[] parts = line.split(",");
        return new PrintedBook(parts[1], parts[2], parts[3], Integer.parseInt(parts[5]));
    }
} 