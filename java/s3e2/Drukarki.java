package s3e2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Drukarki {

    public static void main(String [ ] args)
    {

        BoundedBuffer b = new BoundedBuffer();

        Druk d = new Druk(b);
        Thread t1 = new Thread(d);
        t1.start();
        Druk2 d2 = new Druk2(b);
        Thread t2 = new Thread(d2);
        t2.start();

        try{
            t1.join();
        }catch(InterruptedException e) {}
        try{
            t2.join();
        }catch(InterruptedException e) {}

    }


    public static class BoundedBuffer {
        final Lock lock = new ReentrantLock();
        final Condition notFull  = lock.newCondition();
        final Condition notEmpty = lock.newCondition();

        final Object[] items = new Object[3];
        int putptr, takeptr, count;


        public void put(Object x) throws InterruptedException {
            lock.lock();
            try {
                while (count == items.length)
                    notFull.await();
                items[putptr] = x;
                if (++putptr == items.length) putptr = 0;
                ++count;
                notEmpty.signal();

                System.out.println("Daje slowo ");
            } finally {
                lock.unlock();
            }
        }

        public Object take() throws InterruptedException {
            lock.lock();
            try {
                while (count == 0)
                    notEmpty.await();
                Object x = items[takeptr];
                if (++takeptr == items.length) takeptr = 0;
                --count;
                notFull.signal();
                System.out.println("Biore slowo ");
                return x;
            } finally {
                lock.unlock();
            }
        }
    }


    public static class Druk implements Runnable{

        BoundedBuffer b;

        public Druk(BoundedBuffer b){
            this.b = b;
        }

        public void run() {
            for (int i = 0; i < 10; i++){
                try {
                    b.put(Integer.toString(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static class Druk2 implements Runnable{

        BoundedBuffer b;

        public Druk2(BoundedBuffer b){
            this.b = b;
        }

        public void run() {
            for (int i = 0; i < 10; i++){
                String s = null;
                try {
                    s = (String) b.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}