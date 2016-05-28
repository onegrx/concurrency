package s2e2;

/**
 * Created by onegrx on 15.03.16.
 */
public class Customer implements Runnable {

    CountingSemaphore cs;

    public Customer(CountingSemaphore semaphore) {
        cs = semaphore;
    }

    @Override
    public void run() {
        takeBasket();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        returnBasket();
    }
    synchronized void takeBasket()
    {
        try {
            cs.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Taking basket");
    }

    synchronized void returnBasket()
    {
        cs.take();
        System.out.println("Returning basket");
    }


}



