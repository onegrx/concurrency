package s4e2;

/**
 * Created by onegrx on 05.06.16.
 */
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainPK {
    public static void main(String [] args){

        Random r = new Random();
        int maxPortionSize = 5;
        int producersNumber = 10;
        int consumersNumber = 10;
        Buffer b = new Buffer(2 * maxPortionSize);
        BufferMonitor bM = new BufferMonitor(b, consumersNumber, producersNumber);

        Thread myThreads[] = new Thread[producersNumber + consumersNumber];
        int i = 0;
        for (; i < producersNumber; i++) {
            int l = Math.max(1, r.nextInt(maxPortionSize + 1));
            Consumer c = new Consumer(l, bM);
            Producer p = new Producer(l, bM);
            myThreads[2 * i] = new Thread(new Thread(c));
            myThreads[2 * i].start();
            myThreads[2 * i + 1] = new Thread(new Thread(p));
            myThreads[2 * i + 1].start();
        }

        for (i = 0; i < producersNumber + consumersNumber; i++) {
            try {
                myThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Buffer{

        int buff[];
        int buffSize;

        public Buffer(int buffSize){
            this.buffSize = buffSize;
            buff = new int[buffSize];
            for(int i = 0; i < buffSize; i++){
                buff[i] = 0;
            }
        }

        public void put(int howMuchToPut){
            System.out.println("\nput  " + howMuchToPut);
            int k = 0;
            for(int i = 0; i < howMuchToPut; i++){
                while(buff[k] == 1){
                    k++;
                }
                buff[k] = 1;
                k++;

            }
            System.out.println(Arrays.toString(buff));
        }

        public void take(int howMuchToTake){
            System.out.println("\ntake " + howMuchToTake);
            int k = 0;
            for(int i = 0; i < howMuchToTake; i++){
                while(buff[k] == 0){
                    k++;
                }
                buff[k] = 0;
                k++;

            }
            System.out.println(Arrays.toString(buff));
        }
    }

    public static class BufferMonitor{

        final Lock lock = new ReentrantLock();
        Buffer b;
        int numberOfPortionInBuffer;
        final Condition producersQueue;
        final Condition consumersQueue;
        final Condition firstProducer;
        final Condition firstConsumer;
        int isFirstProducer, isFirstConsumer; //checks if at least one consumer or producer waits

        public BufferMonitor(Buffer b, int consumersNumber, int producersNumber){
            this.b = b;
            this.numberOfPortionInBuffer = 0;
            isFirstProducer = 0;
            isFirstConsumer = 0;
            firstProducer = lock.newCondition();
            firstConsumer = lock.newCondition();
            consumersQueue = lock.newCondition();
            producersQueue = lock.newCondition();
        }

        public void put(int howMuchToPut) throws InterruptedException{

            lock.lock();
            try {
                while(isFirstProducer != 0){
                    producersQueue.await();
                }
                isFirstProducer = 1;
                while(b.buffSize - numberOfPortionInBuffer < howMuchToPut) firstProducer.await();
                numberOfPortionInBuffer += howMuchToPut;
                b.put(howMuchToPut);
                isFirstProducer = 0;
                firstConsumer.signal();
                producersQueue.signal();
            }finally{
                lock.unlock();
            }
        }

        public void take(int howMuchToTake) throws InterruptedException{

            lock.lock();
            try {
                while(isFirstConsumer != 0){
                    consumersQueue.await();
                }
                isFirstConsumer = 1;
                while(numberOfPortionInBuffer < howMuchToTake) firstConsumer.await();
                numberOfPortionInBuffer -= howMuchToTake;
                b.take(howMuchToTake);
                isFirstConsumer = 0;
                firstProducer.signal();
                consumersQueue.signal();
            }finally{
                lock.unlock();
            }
        }
    }

    public static class Consumer implements Runnable{

        BufferMonitor bM;
        int sizeOfPortion;

        public Consumer(int portionSize, BufferMonitor bM){
            this.bM = bM;
            this.sizeOfPortion = portionSize;
        }

        public void run(){
            try{
                bM.take(sizeOfPortion);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }

    public static class Producer implements Runnable{

        BufferMonitor bM;
        int sizeOfPortion;

        public Producer(int portionSize, BufferMonitor bM){
            this.bM = bM;
            this.sizeOfPortion = portionSize;
        }

        public void run(){
            try{
                bM.put(sizeOfPortion);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}