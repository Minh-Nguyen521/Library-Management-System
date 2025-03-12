package model;

public class Ebook extends Book {
    private String fileFormat;

    public Ebook(String isbn, String title, String author, String fileFormat) {
        super(isbn, title, author);
        this.fileFormat = fileFormat;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    @Override
    public String getType() {
        return "EBOOK";
    }

    @Override
    public String toString() {
        return super.toString() + "," + fileFormat;
    }

    public static Ebook fromString(String line) {
        String[] parts = line.split(",");
        return new Ebook(parts[1], parts[2], parts[3], parts[5]);
    }

    @Override
    public void setAvailable(boolean available) {
        // Ebooks are always available
        super.setAvailable(true);
    }
} 