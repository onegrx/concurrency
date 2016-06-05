package s6e1;

public class ProduceMethodRequest implements MethodRequest {
    private Future returningValue;
    private Servant servant;
    private int size;

    public ProduceMethodRequest(Future returningValue, Servant servant, int size) {
        this.returningValue = returningValue;
        this.servant = servant;
        this.size = size;
    }

    @Override
    public boolean guard() {
        return (servant.getCurrentSize() + size <= servant.getBufferSize());
    }

    @Override
    public void execute() {
        servant.produce(size);
        returningValue.setResult(size);
        returningValue.setProcessed();
    }
}
