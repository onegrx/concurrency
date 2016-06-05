package s6e1;
public class Future {
    private boolean processed;
    private int result;

    public Future() {
        this.processed = false;
        this.result = 0;
    }

    public void setProcessed() {
        this.processed = true;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public boolean isProcessed() {
        return this.processed;
    }

    public int getResult() {
        return this.result;
    }
}
