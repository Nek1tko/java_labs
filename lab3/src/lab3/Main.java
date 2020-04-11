package lab3;

import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {
        final int amountRobots = 3;
        Robot[] robots = new Robot[amountRobots];
        Queue queue = new Queue();

        Generator generator = new Generator(queue);
        generator.setDaemon(true);
        generator.start();

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        Semaphore semaphore = new Semaphore(1);
        for (int i = 0; i < amountRobots; ++i) {
            robots[i] = new Robot(queue, semaphore);
        }
        robots[0].setName("Math");
        robots[1].setName("OOP");
        robots[2].setName("Physic");
        for (int i = 0; i < amountRobots; ++i) {
            robots[i].start();
        }
    }
}
