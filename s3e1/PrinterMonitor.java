package s3e1;

import java.util.*;
import java.util.concurrent.locks.*;

/**
 * Created by onegrx on 28.05.16.
 */
public class PrinterMonitor {

    private final Lock lock = new ReentrantLock();
    private final Condition waitingForPrinter = lock.newCondition();
    private final Set<Integer> printers = new HashSet<>();
    private int availablePrinter = 0;

    public PrinterMonitor(int numberOfPrinters) {
        for (int i = 0; i < numberOfPrinters; i++) {
            printers.add(i);
        }
    }

    public int getPrinter() {
        lock.lock();
        try {

            while(printers.isEmpty()) {
                waitingForPrinter.await();
            }

            Iterator<Integer> it = printers.iterator();
            if(it.hasNext()) {
                availablePrinter = it.next();
                printers.remove(availablePrinter);
            } else {
                System.out.println("No printers available!");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return availablePrinter;

    }

    public void returnPrinter(int printerNumber) {
        lock.lock();
        printers.add(printerNumber);
        waitingForPrinter.signal();
        lock.unlock();
    }

}
