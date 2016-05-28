package s2e2;

/**
 * Created by onegrx on 15.03.16.
 */
public class CountingSemaphore {
    public int counter = 0;

    public CountingSemaphore(int limit) {
        counter = limit;
    }


    public synchronized void take() {
        counter++;
        this.notifyAll();
    }

    public synchronized void release() throws InterruptedException {
        while(this.counter <= 0) {
            this.wait();
        }
        counter--;
    }
}

//reles ++ i notify