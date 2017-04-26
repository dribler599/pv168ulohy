package cz.muni.fi;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Lukas on 26.4.2017.
 */
public class Counter4 implements Runnable{
    static AtomicInteger counter = new AtomicInteger(0);

    static ReentrantLock counterLock = new ReentrantLock(true);

    static void increment(){
        counterLock.lock();

        try{
            if (counter.get() <= 50) {
                System.out.println(Thread.currentThread().getName() + ": " + counter.getAndIncrement());
            }
        }finally{
            counterLock.unlock();
        }
    }

    @Override
    public void run() {
        while(counter.get() <= 50) {
            increment();
        }
    }

    public static void main(String[] args) {
        Runnable counter = new Counter4();

        Thread thread1 = new Thread(counter);
        thread1.setName("vlákno 1");
        Thread thread2 = new Thread(counter);
        thread2.setName("vlákno 2");
        Thread thread3 = new Thread(counter);
        thread3.setName("vlákno 3");

        thread1.start();
        thread2.start();
        thread3.start();
        /**Executor executor1 = Executors.newCachedThreadPool();
         executor1.execute(counter);
         executor1.execute(counter);
         executor1.execute(counter);**/
    }
}
