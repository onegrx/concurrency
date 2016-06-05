package s2e1;

/**
 * Created by onegrx on 28.05.16.
 */
public class Incrementator implements Runnable {

    Semaphore semaphore;
    Counter counter;

    public Incrementator(Counter counter, Semaphore semaphore) {
        this.counter = counter;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for(int i = 0; i < counter.getSize(); i++) {
            semaphore.acquire();
            counter.increment();
            semaphore.release();
        }
    }

}