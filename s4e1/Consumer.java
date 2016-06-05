package s4e1;

import java.util.*;

/**
 * Created by onegrx on 05.04.16.
 */
public class Consumer implements Runnable {

    private Buffer monitor;
    public int speed = 0;
    public int number = 0;
    private String name = "Consumer";

    private int bufferSize;
    private final Random rand = new Random();

    public Consumer(Buffer monitor, int speed, int number) {
        this.monitor = monitor;
        this.speed = speed;
        this.number = number;
        bufferSize = monitor.getSize();
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            //if(rand.nextInt(10) % speed == 0) {
                monitor.takeItem(number % bufferSize, name);
                number = (number + 1) % bufferSize;
            //}
        }
    }

    public void process(int i) {
        System.out.println("Processing cell: " + i);
    }

}
