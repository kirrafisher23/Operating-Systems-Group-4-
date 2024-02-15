
public class BoundedBuffer implements Buffer {
    private static final int BUFFER_SIZE = 5; // derived from code example in slides, this is arbitrary and can be changed
    private int count = 0; // number of items in the buffer
    private int in = 0; // points to the next free position
    private int out = 0; // points to the next full position
    private static final int EMPTY = 0; // setting the elements of an array of integers to EMPTY
    private static final int FULL = 1; // the job of FULL is accomplished by items variable so we don't need this
    private int[] buffer = new int[BUFFER_SIZE];

    // producers calls this method
    // apparently we need to put "synchronized" into the methods, i didn't know that!
    public synchronized void insert(int item) {
        while (count == BUFFER_SIZE) {
            try {
                // do nothing but wait, no free buffers
                System.out.println("Buffer is full. Producer is waiting...");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // add an item to the buffer
        count++;
        buffer[in] = item;
        in = (in + 1) % BUFFER_SIZE;
        System.out.println("Produced. Buffer count: " + count);
        notify();
    }

    // consumers calls this method
    public synchronized int remove() {
        while (count == 0) {
            try {
                // do nothing but wait, nothing to consume
                System.out.println("Buffer is empty. Consumer is waiting...");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // remove an item from the buffer
        count--;
        int item = buffer[out];
        buffer[out] = EMPTY; // mark slot as empty
        out = (out + 1) % BUFFER_SIZE;
        System.out.println("Consumed. Buffer count: " + count);
        notify();

        return item;
    }
}

// this was in the slides "implements Buffer" so i just put this in
interface Buffer {
    void insert(int item);
    int remove();
}
