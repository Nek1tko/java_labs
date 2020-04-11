package lab3;

public class Robot extends Thread {
    Queue queue;

    Robot(Queue queue) {
        this.queue = queue;
    }

    private void process(Student student) {
        System.out.println("Start process " + student);

        while (student.getLabsCount() != 0) {
            try {
                sleep(1000);
            }
            catch (InterruptedException exception) {
                System.out.println(exception.getMessage());
                return;
            }
            student.passFiveLabs();
        }

        System.out.println("Pass " + student);
    }


    @Override
    public void run() {
        while (!queue.isEmpty()) {
            Student student = queue.getStudent(this);
            if (student != null) {
                process(student);
            }
        }
    }
}

