package s2e3;

/**
 * Created by onegrx on 15.03.16.
 */
public class Main {

    public static void main(String [ ] args)
    {
        CountingSemaphore cs1 = new CountingSemaphore();
        CountingSemaphore cs2 = new CountingSemaphore();
        Weight s = new Weight(3);

        WeightTaker bt = new WeightTaker(s, cs1, cs2);
        Thread t1 = new Thread(bt);
        t1.start();

        WeightReturner br = new WeightReturner(s, cs1, cs2);
        Thread t2 = new Thread(br);
        t2.start();

        try{
            t1.join();
        } catch(InterruptedException e) {}
        try {
            t2.join();
        } catch(InterruptedException e) {}
    }

}