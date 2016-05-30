package s4e1;

import java.util.*;

/**
 * Created by onegrx on 30.05.16.
 */
public class Process implements Runnable {

    private Buffer monitor;
    public int speed = 0;
    public int number = 0;

    private int bufferSize;
    private final Random rand = new Random();

    public Process(Buffer monitor, int speed, int number) {
        this.monitor = monitor;
        this.speed = speed;
        this.number = number;
        bufferSize = monitor.getSize();
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            if(rand.nextInt(10) % speed == 0) {
                monitor.takeItem(number % bufferSize);
                process(number % bufferSize);
                monitor.returnItem(number % bufferSize);
                number = (number + 1) % bufferSize;
            }
        }
    }

    public void process(int i) {
        System.out.println("Processing cell: " + i);
    }
}
