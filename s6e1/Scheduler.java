package s6e1;
import java.util.*;

public class Scheduler implements Runnable {
    private Queue<MethodRequest> producerQueue = new LinkedList<>();
    private Queue<MethodRequest> consumerQueue = new LinkedList<>();
    private ActivationQueue queue = new ActivationQueue();

    private Thread scheduler;

    public void enqueue(MethodRequest request) {
        try {
            queue.enqueue(request);
        }
        catch(InterruptedException exc) {
            exc.printStackTrace();
        }
    }

    private MethodRequest dequeue() {
        MethodRequest request = null;

        try {
            request = queue.dequeue();
        }
        catch(InterruptedException exc) {
            exc.printStackTrace();
        }

        return request;
    }

    @Override
    public void run() {
        while(true) {
            MethodRequest request = dequeue();

            /*
            if(request == null)
                continue;
            */

            if(request instanceof  ProduceMethodRequest) {
                if(!producerQueue.isEmpty()) {
                    producerQueue.add(request);
                    while(!producerQueue.isEmpty() && producerQueue.peek().guard())
                        producerQueue.poll().execute();
                }
                else {
                    if(request.guard())
                        request.execute();
                    else
                        producerQueue.add(request);
                }
            }
            else {
                if(!consumerQueue.isEmpty()) {
                    consumerQueue.add(request);
                    while(!consumerQueue.isEmpty() && consumerQueue.peek().guard())
                        consumerQueue.poll().execute();
                }
                else {
                    if(request.guard())
                        request.execute();
                    else
                        consumerQueue.add(request);
                }
            }
        }
    }

    public void start() {
        if(scheduler == null) {
            scheduler = new Thread(this);
            scheduler.start();
        }
    }
}
