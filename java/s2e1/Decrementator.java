package s2e1;

/**
 * Created by onegrx on 27.05.16.
 */
public class Decrementator implements Runnable {

    Semaphore semaphore;
    Counter counter;

    public Decrementator(Counter counter, Semaphore semaphore) {
        this.counter = counter;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for(int i = 0; i < counter.getSize(); i++) {
            semaphore.acquire();
            counter.decrement();
            semaphore.release();
        }
    }

}
