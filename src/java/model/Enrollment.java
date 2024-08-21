package model;

import java.time.LocalDateTime;

public class Enrollment {
    private int id;
    private int courseId;
    private int studentId;
    private String status;
    private LocalDateTime enrolledAt;

    public Enrollment() {
    }

    public Enrollment(int id, int courseId, int studentId, String status, LocalDateTime enrolledAt) {
        this.id = id;
        this.courseId = courseId;
        this.studentId = studentId;
        this.status = status;
        this.enrolledAt = enrolledAt;
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

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }
}
