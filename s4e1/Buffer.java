package s4e1;

import java.util.concurrent.locks.*;

/**
 * Created by onegrx on 05.04.16.
 */
public class Buffer {

    private final Lock lock = new ReentrantLock();
    private final int size = 20;

    private Condition[] condition = new Condition[size];
    private int[] available = new int[size];

    public Buffer() {
        for (int i = 0; i < size; i++) {
            condition[i] = lock.newCondition();
            available[i] = 0;
        }
    }

    public void takeItem(int i, String name) {
        lock.lock();
        try {
            if(available[i] == 0) {
                condition[i].await();
            }
            available[i]--;

            System.out.println("Running " + name);
            System.out.println("Taking from # " + i);
            System.out.println("State of # " + i + " : " + available[i]);
            System.out.println();

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void returnItem(int i, String name) {
        lock.lock();
        try {
            available[i]++;
            System.out.println("Running " + name);
            System.out.println("Giving to # " + i);
            System.out.println("State of # " + i + " : " + available[i]);
            System.out.println();
            condition[i].signal();
        } finally {
            lock.unlock();
        }
    }

    public int getSize() {
        return size;
    }
}
