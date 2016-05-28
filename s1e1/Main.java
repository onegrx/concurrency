package s1e1;

/**
 * Created by onegrx on 08.05.16.
 */
public class Main {

    public static void main(String[] args) {

        Counter c = new Counter();

        Incrementator inc = new Incrementator(c);
        Thread tInc = new Thread(inc);
        tInc.start();

        Decrementator dec = new Decrementator(c);
        Thread tDec = new Thread(dec);
        tDec.start();

        try {
            tInc.join();
            tDec.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(c.value());

    }

}
