package cz.muni.fi;

import java.util.concurrent.Executors;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Lukas on 25.4.2017.
 */
public class Counter implements Runnable {

    static int counter = 1; // a global counter

    static ReentrantLock counterLock = new ReentrantLock(true); // enable fairness policy

    static void incrementCounter(){
        counterLock.lock();

        // Always good practice to enclose locks in a try-finally block
        try{
            System.out.println(Thread.currentThread().getName() + ": " + counter);
            counter++;
        }finally{
            counterLock.unlock();
        }
    }

    @Override
    public void run() {
        while(counter<50){
            incrementCounter();
        }
    }

    public static void main(String[] args) {
        Runnable counter = new Counter();
        // Vytvoříme executor, který bude recyklovat použitá vlákna
        // (nemá samozřejmě smysl vytvářet novýexecutor pro každou úlohu,
        // v reálném programu vytvoříme jeden na začátku, který pak budeme
        // používat pro všechny úlohy)
        Executor executor1 = Executors.newCachedThreadPool();
        // náš lambda výraz odešleme ke spuštění
        executor1.execute(counter);
        executor1.execute(counter);
        executor1.execute(counter);
    }
}
