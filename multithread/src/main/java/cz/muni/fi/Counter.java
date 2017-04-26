package cz.muni.fi;

import java.util.concurrent.Executors;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Lukas on 25.4.2017.
 */
public class Counter implements Runnable {

    static int counter = 0; // a global counter

    static ReentrantLock counterLock = new ReentrantLock(true); // enable fairness policy

    static void incrementCounter(){
        counterLock.lock();

        // Always good practice to enclose locks in a try-finally block
        try{
            if (counter <= 50) {
                System.out.println(Thread.currentThread().getName() + ": " + counter);
                counter++;
            }
        }finally{
            counterLock.unlock();
        }
    }

    @Override
    public void run() {
        while(counter <= 50){
            incrementCounter();
        }
    }

    public static void main(String[] args) {
        Runnable counter = new Counter();
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
