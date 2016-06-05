package s6e1;
public class Servant {
    private int productionNumber;
    private int consumptionNumber;

    private int currentSize;
    private int bufferSize;

    public Servant(int bufferSize) {
        productionNumber = 0;
        consumptionNumber = 0;

        this.currentSize = 0;
        this.bufferSize = bufferSize;
    }

    public void produce(int size) {
        productionNumber++;

        currentSize += size;
        System.out.println("PRODUCED: " + size + ". In buffer: " + currentSize + ".");
    }

    public void consume(int size) {
        consumptionNumber++;

        currentSize -= size;
        System.out.println("CONSUMED: " + size + ". In buffer: " + currentSize + ".");
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public int getBufferSize() {
        return bufferSize;
    }


    public int getProductionNumber() {
        return productionNumber;
    }

    public int getConsumptionNumber() {
        return consumptionNumber;
    }
}
