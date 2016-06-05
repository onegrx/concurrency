package s2e3;

/**
 * Created by onegrx on 15.03.16.
 */
public class CountingSemaphore {

    private int counter = 0;
    int limit = 0;

    public CountingSemaphore() {
    }

    public CountingSemaphore(int limit) {
        this.limit = limit;
    }

    public synchronized void take() {
        this.counter++;
        this.notifyAll();
    }

    public synchronized void release() throws InterruptedException {
        while(this.counter <= limit)
        {
            this.wait();
        }
        this.counter--;
    }
}