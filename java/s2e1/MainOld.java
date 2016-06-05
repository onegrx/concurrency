package s2e1;

/**
 * Created by onegrx on 15.03.16.
 */
public class MainOld {

    public static void main(String [] args) {

        BinarySem bin = new BinarySem();

        Dec dec = new Dec(bin);
        Thread t1 = new Thread(dec);
        t1.start();

        Inc inc = new Inc(bin);
        Thread t2 = new Thread(inc);
        t2.start();

        try {
            t1.join();
        } catch(InterruptedException e) {}
        try {
            t2.join();
        } catch(InterruptedException e) {}
        System.out.println(value);
    }

    static int value = 0;

    public static class BinarySem {

        int state = 1;

        public synchronized void release(){

            state = 1;

            this.notifyAll();
        }

        public synchronized void acquire(){

            while(state == 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            state = 0;
        }
    }

    public static class Dec implements Runnable {

        BinarySem bin;

        public Dec(BinarySem bin) {this.bin = bin;}

        public void run() {
            for(int i = 0; i < 1000000; i++){
                bin.acquire();
                value -= 1;
                bin.release();
            }
        }
    }

    public static class Inc implements Runnable {

        BinarySem bin;

        public Inc(BinarySem bin) {this.bin = bin;}

        public void run() {
            for(int i = 0; i < 1000000; i++){
                bin.acquire();
                value += 1;
                bin.release();
            }

        }
    }

}