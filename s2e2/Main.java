package s2e2;

/**
 * Created by onegrx on 15.03.16.
 */
public class Main {

    public static void main(String[] args) {
        CountingSemaphore cs = new CountingSemaphore(3);

        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer(cs);
            new Thread(customer).start();
        }
    }
}



