package concurrencypractice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;

public class Counter {
    private static final Logger logger = LoggerFactory.getLogger(Counter.class);

    private int count;
    private Semaphore binarySemaphore;

    public Counter() {
        count = 0;
        binarySemaphore = new Semaphore(1);
    }

    public void countUp() {
        try {
            logger.info("count: {}", count);
            binarySemaphore.acquire();
            Thread.sleep(2000);
            count++;
            logger.info("count added: {}", count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            binarySemaphore.release();
        }
    }

    public int getCount() {
        return count;
    }

    public static class MyRunnable implements Runnable {
        private final Counter counter;

        public MyRunnable(final Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            counter.countUp();
        }
    }
}
