package lab3;

enum Course {
    Math,
    OOP,
    Physic
}

public class Student {
    private final Course course;
    private int labsCount;

    Student(Course course, int amount) {
        this.course = course;
        this.labsCount = amount;
    }

    public Course getCourse() {
        return course;
    }

    public int getLabsCount() {
        return labsCount;
    }

    public void passFiveLabs() {
        labsCount -= 5;
    }

    @Override
    public String toString() {
        return "Student {" + "course = " + course + ", labsCount = " + labsCount + "} ";
    }
}

