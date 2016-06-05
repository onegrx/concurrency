package s6e1;
public class Proxy {
    private Scheduler scheduler = new Scheduler();
    private Servant servant;

    public Proxy(int bufferSize) {
        this.servant = new Servant(bufferSize);
        this.scheduler.start();
    }

    public synchronized Future produce(int size) {
        Future returningValue = new Future();
        ProduceMethodRequest request = new ProduceMethodRequest(returningValue, servant, size);
        scheduler.enqueue(request);

        return returningValue;
    }

    public synchronized Future consume(int size) {
        Future returningValue = new Future();
        ConsumeMethodRequest request = new ConsumeMethodRequest(returningValue, servant, size);
        scheduler.enqueue(request);

        return returningValue;
    }

    public int getProductionNumber() {
        return servant.getProductionNumber();
    }

    public int getConsumptionNumber() {
        return servant.getConsumptionNumber();
    }
}
