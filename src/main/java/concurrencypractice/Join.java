package concurrencypractice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Join {
    private static final Logger logger = LoggerFactory.getLogger(Join.class);

    public static void main(String[] args) throws InterruptedException {
        logger.info("Running main thread");

        final Thread t1 = new Thread(() -> {
            try {
                logger.info("Running thread t1...");
                Thread.sleep(5000);
                logger.info("...finished running thread t1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        final Thread t2 = new Thread(() -> {
            try {
                logger.info("Running thread t2...");
                Thread.sleep(3000);
                logger.info("...finished running thread t2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        logger.info("Pause current thread until t1 is finished");
        t1.join();
        logger.info("Pause current thread until t2 is finished");
        t2.join();

        logger.info("Finished");
    }
}
