package s4e1;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainPB {

    public static void main(String [] args){

        int processNumber = 4;
        int buffSize = 8;
        Buffer b = new Buffer(buffSize, processNumber);
        BufferMonitor buffMonitor = new BufferMonitor(processNumber,b, buffSize);

        Thread myThreads[] = new Thread[processNumber];
        for (int i = 0; i < processNumber; i++) {
            Process p = new Process(i, buffSize, buffMonitor);
            myThreads[i] = new Thread(p);
            myThreads[i].start();
        }

        for (int i = 0; i < processNumber; i++) {
            try {
                myThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Buffer{

        int buff[];
        int processNumber;

        public Buffer(int buffSize, int processNumber){
            this.processNumber = processNumber;
            buff = new int[buffSize];
            for(int i = 0; i < buffSize; i++){
                buff[i] = -1;
            }
        }

        public void process(int j, int i){
            if(i == processNumber - 1) buff[j] = -1; // cause processes start form 0
            else buff[j] = i;
            System.out.println(Arrays.toString(buff));
        }

    }

    public static class BufferMonitor{

        final Lock lock = new ReentrantLock();
        final Condition[] queue; // to stop process
        int howMuch[]; // number of portion that process can process
        int processNumber;
        Buffer b;

        public BufferMonitor(int processNumber, Buffer b, int buffSize){

            this.processNumber = processNumber;
            queue = new Condition[processNumber];
            howMuch = new int[processNumber];
            for(int i=0; i < processNumber; i++){
                queue[i] = lock.newCondition();
                howMuch[i] = 0;
            }
            howMuch[0] = buffSize;
            this.b = b;
        }

        public void take(int number, int j) throws InterruptedException{
            lock.lock();
            try {
                while(howMuch[number] == 0){
                    queue[number].await();
                }
                howMuch[number] -= 1;
                b.process(j, number);
            }finally{
                lock.unlock();
            }
        }

        public void put(int number){
            lock.lock();
            howMuch[(number + 1) % processNumber] += 1;
            queue[(number + 1) % processNumber].signal();
            lock.unlock();
        }
    }

    public static class Process implements Runnable{

        int number;
        int buffSize;
        BufferMonitor buffMonitor;
        Random r = new Random();

        public Process(int i, int buffSize, BufferMonitor buffMonitor){
            this.number = i;
            this.buffSize = buffSize;
            this.buffMonitor = buffMonitor;
        }

        public void run(){
            int j = 0;
            int k = 0;
            while(k < 10){
                try {
                    buffMonitor.take(number, j); //my number and place in buff that i want to process
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                buffMonitor.put(number);
                try {
                    Thread.sleep(r.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                j = (j + 1) % buffSize;
                k++;
            }
        }
    }
}