package cz.muni.fi;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Lukas on 26.4.2017.
 */
public class Counter3 implements Runnable {
    static AtomicInteger counter = new AtomicInteger(0); // a global counter

    public void increment () {
        System.out.println(Thread.currentThread().getName() + ": " + counter.getAndIncrement());
    }

    @Override
    public void run() {
        while(counter.get() <= 50) {
            increment();
            for (long l = 0l; l < 1000; l++) {}
        }
    }

    public static void main(String[] args) {
        Runnable counter = new Counter3();

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
