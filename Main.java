import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        final ProducerConsumer pc = new ProducerConsumer();

        // Create producer thread
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Create consumer thread
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    public static class ProducerConsumer {

        LinkedList<Integer> list = new LinkedList<>();
        int capacity = 2;

        public void produce() throws InterruptedException {
            int value = 0;
            while (true) {
                synchronized (this) {

                    while (list.size() == capacity) {
                        System.out.println("Buffer is full. Producer is waiting.");
                        wait();
                    }
                    System.out.println("Producer produced-" + value);

                    list.add(value++);

                    notify();

                    Thread.sleep(1000);
                }
            }
        }

        // Function called by consumer thread
        public void consume() throws InterruptedException {
            while (true) {
                synchronized (this) {
                    // consumer thread waits while list is empty
                    while (list.size() == 0) {
                        System.out.println("Buffer is empty. Consumer is waiting.");
                        wait();
                    }

                    int val = list.removeFirst();

                    System.out.println("Consumer consumed-" + val);

                    notify();

                    Thread.sleep(1000);
                }
            }
        }
    }
}