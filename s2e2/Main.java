package s2e2;

/**
 * Created by onegrx on 15.03.16.
 */
public class Main {

    public static void main(String[] args) {
        CountingSemaphore cs = new CountingSemaphore(2);

        Customer c1 = new Customer(cs);
        new Thread(c1).start();

        Customer c2 = new Customer(cs);
        new Thread(c2).start();

        Customer c3 = new Customer(cs);
        new Thread(c3).start();
    }
}



