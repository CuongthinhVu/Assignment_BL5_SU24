package model;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String email;
    private LocalDateTime time;

    public User() {
    }

    public User(int id, String email, LocalDateTime time) {
        this.id = id;
        this.email = email;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}