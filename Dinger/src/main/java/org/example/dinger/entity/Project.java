package org.example.dinger.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    private User user;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project(String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Project() {
    }
}
