package s1e2;

/**
 * Created by onegrx on 14.03.16.
 */
public class Main {

    public static void main(String[] args) {

        Buffer buffer = new Buffer();

        Producer p1 = new Producer(buffer);
        new Thread(p1).start();
        Consumer c1 = new Consumer(buffer);
        new Thread(c1).start();

        Producer p2 = new Producer(buffer);
        new Thread(p2).start();
        Consumer c2 = new Consumer(buffer);
        new Thread(c2).start();

        Producer p3 = new Producer(buffer);
        new Thread(p3).start();
        Consumer c3 = new Consumer(buffer);
        new Thread(c3).start();


    }

}
