package lab3;

import java.util.concurrent.Semaphore;

public class Robot extends Thread {
    private Queue queue;
    private Semaphore semaphore;
    private Generator generator;

    Robot(Queue queue, Semaphore semaphore, Generator generator) {
        this.semaphore = semaphore;
        this.queue = queue;
        this.generator = generator;
    }

    private void process(Student student) {
        System.out.println("Start process " + student);

        while (student.getLabsCount() != 0) {
            student.passFiveLabs();
        }

        System.out.println("Pass student with course " + this.getName());
    }


    @Override
    public void run() {
        while (!queue.isEmpty() || generator.isAlive()) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Student student = queue.getStudent(this);
            semaphore.release();
            if (student != null) {
                process(student);
            }
        }
    }
}

