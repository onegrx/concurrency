package s2e3;

/**
 * Created by onegrx on 15.03.16.
 */
import java.util.Random;

public class WeightTaker implements Runnable {
    private Weight w;
    CountingSemaphore sem1;
    CountingSemaphore sem2;

    public WeightTaker(Weight s, CountingSemaphore cs1, CountingSemaphore cs2) {
        w = s;
        sem1 = cs1;
        sem2 = cs2;
    }
    public void run(){
        Random rand = new Random();
        for(int i =0;i<10;i++)
        {
            //takeWeight();
            try {
                Thread.sleep(rand.nextInt(50));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}