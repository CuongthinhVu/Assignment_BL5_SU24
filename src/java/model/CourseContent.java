package model;

import java.time.LocalDateTime;

public class CourseContent {
    public enum Type { Lesson, Quiz };
    private int id;
    private int courseId;
    private Type type;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public CourseContent() {
    }

    public CourseContent(int id, int courseId, Type type, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.courseId = courseId;
        this.type = type;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
