package s4e1;

import java.util.*;

/**
 * Created by onegrx on 30.05.16.
 */
public class Process implements Runnable {

    private Buffer monitor;
    public int speed = 0;
    public int number = 0;
    private String name;

    private int bufferSize;
    private final Random rand = new Random();

    public Process(Buffer monitor, int speed, int number, int id) {
        this.monitor = monitor;
        this.speed = speed;
        this.number = number;
        bufferSize = monitor.getSize();
        this.name = "Process #" + Integer.toString(id);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            //if(rand.nextInt(10) % speed == 0) {
                monitor.takeItem(number % bufferSize, name);
                process(number % bufferSize);
                monitor.returnItem(number % bufferSize, name);
                number = (number + 1) % bufferSize;
            //}
        }
    }

    public void process(int i) {
        System.out.println("Processing cell: " + i);
    }
}
