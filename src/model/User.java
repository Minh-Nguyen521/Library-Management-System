package model;

public class User {
    private String username;
    private String password;
    private UserType type;

    public User(String username, String password, UserType type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getType() {
        return type;
    }

    @Override
    public String toString() {
        return username + "," + password + "," + type;
    }

    public static User fromString(String line) {
        String[] parts = line.split(",");
        return new User(parts[0], parts[1], UserType.valueOf(parts[2]));
    }
} 