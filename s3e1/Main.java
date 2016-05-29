package s3e1;

import java.util.*;

/**
 * Created by onegrx on 04.04.16.
 */

public class Main {
    public static void main(String[] args) {

        int numberOfPrinters = 3;
        int numberOfTasks = 300;
        PrinterMonitor monitor = new PrinterMonitor(numberOfPrinters);
        List<Thread> threads = new LinkedList<>();

        for (int i = 0; i < numberOfTasks; i++) {
            Task task = new Task("Task " + String.valueOf(i), monitor);
            threads.add(new Thread(task));
        }

        threads.forEach(java.lang.Thread::start);

        //for(Thread thread : threads) thread.start();


    }
}
