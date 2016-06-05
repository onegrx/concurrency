package s6e1;

public class ConsumeMethodRequest implements MethodRequest {
    private Future returningValue;
    private Servant servant;
    private int size;

    public ConsumeMethodRequest(Future returningValue, Servant servant, int size) {
        this.returningValue = returningValue;
        this.servant = servant;
        this.size = size;
    }

    @Override
    public boolean guard() {
        return (servant.getCurrentSize() - size >= 0);
    }

    @Override
    public void execute() {
        servant.consume(size);
        returningValue.setResult(size);
        returningValue.setProcessed();
    }
}
