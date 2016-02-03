package concurrencypractice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaemonThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(DaemonThread.class);

    public static void main(String[] args) {
        final DaemonThread t = new DaemonThread();
        t.setDaemon(true);
        //t.setDaemon(false);

        t.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        logger.info("Main method exit");
    }

    public void run() {
        logger.info("run thread");

        try {
            logger.info("Thread.currentThread is {} ", Thread.currentThread());

            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }

                logger.info("In run method {}", Thread.currentThread());
            }
        } finally {
            logger.info("finally block");
        }
    }
}
