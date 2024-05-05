package ru.nsu.fit.pupynin.webhelper.model;

public class UserRegistrationRequest {
    private String email;
    private String role; // Добавляем поле для роли

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}