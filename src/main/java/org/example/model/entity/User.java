package org.example.model.entity;

import java.util.List;

public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private List<Order> orders; // One user → Many orders

    public User() {}

    public User(Long id, String username, String email, String password, List<Order> orders) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.orders = orders;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
}
