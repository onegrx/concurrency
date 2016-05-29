package s3e1;

import java.util.concurrent.locks.*;

/**
 * Created by onegrx on 28.05.16.
 */
public class PrinterMonitor {

    private final Lock lock = new ReentrantLock();
    private final Condition allOccupied = lock.newCondition();

    public PrinterMonitor(int numberOfPrinters) {

    }

}
