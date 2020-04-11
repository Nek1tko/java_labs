package lab3;

import java.util.Random;

public class Generator extends Thread {
    private static int amount = 20;
    private static Random random = new Random();
    private Queue queue;

    Generator(Queue queue) {
        this.queue = queue;
    }

    private void generate() {
        int num = random.nextInt(3);
        Course course;
        switch (num) {
            case 0:
                course = Course.Math;
                break;
            case 1:
                course = Course.OOP;
                break;
            case 2:
                course = Course.Physic;
                break;
            default:
                return;
        }

        num = random.nextInt(3);
        int labsCount;
        switch (num) {
            case 0:
                labsCount = 10;
                break;
            case 1:
                labsCount = 20;
                break;
            case 2:
                labsCount = 100;
                break;
            default:
                return;
        }

        Student student = new Student(course, labsCount);
        queue.addStudent(student);
        amount--;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (amount > 0) {
                generate();
            }
        }
    }
}
