package s2e1;

/**
 * Created by onegrx on 28.05.16.
 */

//The synchronized statement is not necessary because of using a semaphore
public class Counter {

    private int c = 0;

    public void increment() {
        c++;
    }

    public void decrement() {
        c--;
    }

    public int value() {
        return c;
    }

    public int getSize() {
        return 1000000;
    }
}
