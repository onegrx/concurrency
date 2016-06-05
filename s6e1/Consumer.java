package s6e1;
import java.util.*;

public class Consumer implements Runnable {
    private Proxy proxy;

    private final List<Future> waitingTasks = Collections.synchronizedList(new LinkedList<>());
    private Thread consumer;
    private int bufferSize;

    public Consumer(int bufferSize, Proxy proxy) {
        this.bufferSize = bufferSize;
        this.proxy = proxy;
    }

    @Override
    public void run() {
        while(true) {
            Future result = proxy.consume(Math.abs(new Random().nextInt() % (bufferSize / 2)));
            waitingTasks.add(result);

            try {
                Thread.sleep(500);
            }
            catch(InterruptedException exc) {
                exc.printStackTrace();
            }

            synchronized(waitingTasks) {
                Iterator<Future> it = waitingTasks.iterator();
                while(it.hasNext()) {
                    Future temp = it.next();
                    if (temp.isProcessed()) {
                        temp.getResult();
                        it.remove();
                    }
                }
            }
        }
    }

    public void start() {
        if(consumer == null) {
            consumer = new Thread(this);
            consumer.start();
        }
    }
}
