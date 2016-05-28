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
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        returnBasket();
    }

    synchronized void takeBasket() {
        cs.acquire();
        System.out.println("Taking basket. Available: " + cs.getCurrent());
    }

    synchronized void returnBasket()
    {
        cs.release();
        System.out.println("Returning basket. Available: " + cs.getCurrent());
    }


}



