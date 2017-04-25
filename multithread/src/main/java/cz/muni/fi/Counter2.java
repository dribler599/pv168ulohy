package cz.muni.fi;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Lukas on 25.4.2017.
 */
public class Counter2 implements Runnable {

    int value = 0;

    public synchronized void increment () {
        System.out.println(Thread.currentThread().getName() + ": " + value);
        value += 1;
    }

    @Override
    public void run() {
        while(value <= 50) {
            increment();
            for (long l = 0l; l < 1000; l++) {}
        }
    }

    public static void main(String[] args) {
        Runnable counter = new Counter2();

        Thread thread1 = new Thread(counter);
        thread1.setName("vlákno 1");
        Thread thread2 = new Thread(counter);
        thread2.setName("vlákno 2");
        Thread thread3 = new Thread(counter);
        thread3.setName("vlákno 3");

        thread1.start();
        thread2.start();
        thread3.start();
        // Vytvoříme executor, který bude recyklovat použitá vlákna
        // (nemá samozřejmě smysl vytvářet novýexecutor pro každou úlohu,
        // v reálném programu vytvoříme jeden na začátku, který pak budeme
        // používat pro všechny úlohy)
        /**Executor executor1 = Executors.newCachedThreadPool();
        executor1.execute(counter);
        executor1.execute(counter);
        executor1.execute(counter);**/
    }
}
