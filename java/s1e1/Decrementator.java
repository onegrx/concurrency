package s1e1;

/**
 * Created by onegrx on 08.05.16.
 */
public class Decrementator implements Runnable {

    Counter counter;

    public Decrementator(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for(int i = 0; i < counter.getSize(); i++) {
            counter.decrement();
        }
    }

}
