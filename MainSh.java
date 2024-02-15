
public class Main {
    public static void main(String[] args) {

        // inspiration for this part of the code by Aliya Sarman's initial solution
        BoundedBuffer buffer = new BoundedBuffer();
        final int[] value = {1};

        // producer thread
        Thread producerThread = new Thread(() -> {
            // 10 is an arbitrary number; i didn't want the process to go on indefinitely, but we can easily change that
            for (int i = 0; i < 10; i++) {
                System.out.println("Producer produced => " + value[0]); // counts iteratively until 10
                buffer.insert(value[0]);
                value[0]++;
                try {
                    // meant to simulate some random processing time instead of fixed value
                    Thread.sleep((int) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // consumer thread
        Thread consumerThread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                buffer.remove();
                try {
                    Thread.sleep((int) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producerThread.start();
        consumerThread.start();
    }
}
