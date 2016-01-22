package concurrencypractice.countdownlatch.checkers.impl;

import concurrencypractice.countdownlatch.checkers.AbstractChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class CreditChecker extends AbstractChecker {
    private static final Logger logger = LoggerFactory.getLogger(CreditChecker.class);

    public CreditChecker(final CountDownLatch countDownLatch) {
        super(countDownLatch);
    }

    @Override
    public Boolean call() throws Exception {
        logger.info("Checking credit file...");
        countDownLatch.countDown();
        return Boolean.TRUE;
    }
}
