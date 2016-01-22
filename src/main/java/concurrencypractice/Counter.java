package concurrencypractice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private static final Logger logger = LoggerFactory.getLogger(Counter.class);

    private AtomicInteger atomicCount;
    private int binarySemaphoreCount;
    private Semaphore binarySemaphore;

    public Counter() {
        binarySemaphoreCount = 0;
        atomicCount = new AtomicInteger(0);
        binarySemaphore = new Semaphore(1);
    }

    public void countUpWithBinarySempahore() {
        try {
            binarySemaphore.acquire();
            logger.info("binarySemaphoreCount: {}", binarySemaphoreCount);
            Thread.sleep(2000);
            binarySemaphoreCount++;
            logger.info("binarySemaphoreCount added: {}", binarySemaphoreCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            binarySemaphore.release();
        }
    }

    public void countUpWithAtomicCounter() {
        try {
            logger.info("atomicCount: {}", atomicCount.get());
            Thread.sleep(2000);
            atomicCount.getAndAdd(1);
            logger.info("atomicCount added: {}", atomicCount.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public int getBinarySemaphoreCount() {
        return binarySemaphoreCount;
    }

    public int getAtomicCount() { return atomicCount.get(); }

    public static class MyBinarySemaphoreCounterRunnable implements Runnable {
        private final Counter counter;

        public MyBinarySemaphoreCounterRunnable(final Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            counter.countUpWithBinarySempahore();
        }
    }

    public static class MyAtomicCounterRunnable implements Runnable {
        private final Counter counter;

        public MyAtomicCounterRunnable(final Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            counter.countUpWithAtomicCounter();
        }
    }
}
