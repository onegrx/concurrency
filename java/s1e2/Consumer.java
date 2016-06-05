package s1e2;

/**
 * Created by onegrx on 14.03.16.
 */
public class Consumer implements Runnable {
    private Buffer buffer;
    private final int QUANTITY = 10;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0; i < QUANTITY; i++) {
            String message = buffer.take();
            //System.out.println("Consuming: " + message);
        }

    }
}


