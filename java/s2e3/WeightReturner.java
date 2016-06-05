package s2e3;

import java.util.*;

/**
 * Created by onegrx on 15.03.16.
 */

public class WeightReturner implements Runnable {
    private Weight shop;
    CountingSemaphore sem1;
    CountingSemaphore sem2;

    public WeightReturner(Weight s, CountingSemaphore cs1, CountingSemaphore cs2) {
        shop = s;
        sem1 = cs1;
        sem2 = cs2;
    }

    public void run(){
        Random rand = new Random();
        for(int i = 0; i < 10; i++)
        {
            //returnWeight();
            try {
                Thread.sleep(rand.nextInt(50));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}