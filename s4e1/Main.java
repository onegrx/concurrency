package s4e1;

import java.util.*;

/**
 * Created by onegrx on 30.05.16.
 */
public class Main {

    private static Buffer monitor = new Buffer();
    private static Set<Thread> threads = new HashSet<>();
    private static Random rand = new Random();
    private static final int noOfProcesses = 5;

    public static void main(String[] args) {

        Thread producer =
                new Thread(new Producer(monitor, rand.nextInt(10) + 1, 0));
        threads.add(producer);
        producer.start();

        for (int i = 0; i < noOfProcesses; i++) {
            Thread process =
                    new Thread(new Process(monitor, rand.nextInt(10) + 1, 0));
            threads.add(process);
            process.start();
        }

        Thread consumer =
                new Thread(new Consumer(monitor, rand.nextInt(2) + 1, 0));
        threads.add(consumer);
        consumer.start();

        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println("The end.");
    }

}
