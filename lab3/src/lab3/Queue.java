package lab3;

import java.util.concurrent.*;

public class Queue {
    private LinkedBlockingQueue<Student> queue;

    Queue() {
        this.queue = new LinkedBlockingQueue<>(10);
    }

    public void addStudent(Student student) {
        try {
            queue.put(student);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Added " + student);
    }

    public Student getStudent(Robot robot) {
        Student student = queue.peek();
        if (student != null) {
            if (student.getCourse().toString().equals(robot.getName())) {
                queue.remove();
                return student;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
