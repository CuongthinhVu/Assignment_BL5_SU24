package model;

public class QuizResult {
    private int quizId;
    private int studentId;
    private int grade;
    private boolean passed;

    public QuizResult() {
    }

    public QuizResult(int quizId, int studentId, int grade, boolean passed) {
        this.quizId = quizId;
        this.studentId = studentId;
        this.grade = grade;
        this.passed = passed;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
