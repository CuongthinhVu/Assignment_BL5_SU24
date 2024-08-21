package model;

public class CourseReport {
    private int id;
    private int courseId;
    private int purchaseCount;
    private int completedCount;
    private int profit;

    public CourseReport() {
    }

    public CourseReport(int id, int courseId, int purchaseCount, int completedCount, int profit) {
        this.id = id;
        this.courseId = courseId;
        this.purchaseCount = purchaseCount;
        this.completedCount = completedCount;
        this.profit = profit;
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

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }
}
