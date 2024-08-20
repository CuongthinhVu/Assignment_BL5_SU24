package model;

import java.time.LocalDateTime;
import java.util.List;

public class Course {
    private int id;
    private String title;
    private String description;
    private List<Category> categories;
    private int originalPrice;
    private int salePrice;
    private String imagePath;
    private boolean active;
    private LocalDateTime createdAt;

    public Course() {
    }

    public Course(int id, String title, String description, List<Category> categories, int originalPrice, int salePrice, String imagePath, boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.categories = categories;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        this.imagePath = imagePath;
        this.active = active;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
