package s1e1;

/**
 * Created by onegrx on 08.05.16.
 */
public class Incrementator implements Runnable {

    Counter counter;

    public Incrementator(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for(int i = 0; i < counter.getSize(); i++) {
            counter.increment();
        }
    }

}
