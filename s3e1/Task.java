package s3e1;

/**
 * Created by onegrx on 29.05.16.
 */
public class Task implements Runnable{

    private final String message;
    private final PrinterMonitor monitor;

    public Task(String message, PrinterMonitor monitor) {
        this.message = message;
        this.monitor = monitor;
    }

    private void print(int printerNumber) {
        System.out.println("Printer no. " + printerNumber + ": " + message);
    }

    @Override
    public void run() {
        int printer = monitor.getPrinter();
        print(printer);
        monitor.returnPrinter(printer);
    }
}
