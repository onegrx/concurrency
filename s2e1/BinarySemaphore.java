package s2e1;

/**
 * Created by onegrx on 27.05.16.
 */
public class BinarySemaphore implements Semaphore {

    int state = 1;

    @Override
    public synchronized void acquire() {
        while(state == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        state = 0;
    }

    @Override
    public synchronized void release() {
        state = 1;
        this.notifyAll();
    }

}
