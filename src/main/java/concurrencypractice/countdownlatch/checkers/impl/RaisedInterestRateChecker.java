package concurrencypractice.countdownlatch.checkers.impl;

import concurrencypractice.countdownlatch.checkers.AbstractChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class RaisedInterestRateChecker extends AbstractChecker {
    private static final Logger logger = LoggerFactory.getLogger(RaisedInterestRateChecker.class);

    public RaisedInterestRateChecker(final CountDownLatch countDownLatch) {
        super(countDownLatch);
    }

    @Override
    public Boolean call() throws Exception {
        logger.info("Checking higher interest rate calculation...");
        countDownLatch.countDown();
        return Boolean.TRUE;
    }
}
