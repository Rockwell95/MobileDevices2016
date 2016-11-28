package ca.uoit.csci4100.a100517944_lab7;


import java.io.Serializable;

public class Grade implements Serializable{
    private int studentId;
    private String courseComponent;
    private float mark;

    public Grade(int studentId, String courseComponent, float mark) {
        this.studentId = studentId;
        this.courseComponent = courseComponent;
        this.mark = mark;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getCourseComponent() {
        return courseComponent;
    }

    public float getMark() {
        return mark;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setCourseComponent(String courseComponent) {
        this.courseComponent = courseComponent;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return studentId + ", " + courseComponent + ", " + mark;
    }
}
