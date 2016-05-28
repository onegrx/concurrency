package s2e1;

/**
 * Created by onegrx on 28.05.16.
 */
public class Main {
    public static void main(String[] args) {
        Counter counter = new Counter();
        Semaphore semaphore = new BinarySemaphore();

        Incrementator inc = new Incrementator(counter, semaphore);
        Thread tInc = new Thread(inc);
        tInc.start();

        Decrementator dec = new Decrementator(counter, semaphore);
        Thread tDec = new Thread(dec);
        tDec.start();

        try {
            tInc.join();
            tDec.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(counter.value());
    }
}
