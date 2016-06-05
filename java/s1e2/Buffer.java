package s1e2;

/**
 * Created by onegrx on 14.03.16.
 */
public class Buffer {
    private String message = "";
    private boolean isEmpty = true;


    public void put(String message) {
        synchronized (this) {
            while (!isEmpty) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Added message: " + message);
            this.message = message;
            isEmpty = false;
            this.notifyAll();
        }

    }


    public String take() {
        synchronized (this){
            while (isEmpty) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Took message: " + message);
            String readMessage =  this.message;
            this.message = "";
            this.isEmpty = true;
            this.notifyAll();
            return readMessage;

        }
    }
}