package s6e1;
import java.util.concurrent.*;

public class ActivationQueue {

    private BlockingQueue<MethodRequest> queue =
            new LinkedBlockingDeque<>();

    public void enqueue(MethodRequest request) throws InterruptedException {
        queue.put(request);
    }

    public MethodRequest dequeue() throws InterruptedException {
        return queue.take();
    }
}
