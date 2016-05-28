package s1e1;

/**
 * Created by onegrx on 08.05.16.
 */
public class Counter {

    private int c = 0;

    synchronized public void increment() {
        c++;
    }

    synchronized public void decrement() {
        c--;
    }

    public int value() {
        return c;
    }

    public int getSize() {
        return 10000000;
    }
}
