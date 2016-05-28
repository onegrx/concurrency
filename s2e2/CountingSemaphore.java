package s2e2;

/**
 * Created by onegrx on 15.03.16.
 */
public class CountingSemaphore {
    private int counter = 0;

    public CountingSemaphore(int limit) {
        counter = limit;
    }

    public synchronized void acquire() {
        while(this.counter <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        counter--;

    }

    public synchronized void release() {
        counter++;
        this.notifyAll();
    }

    public int getCurrent() {
        return counter;
    }
}
