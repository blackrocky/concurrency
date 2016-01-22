package concurrencypractice.countdownlatch.checkers.impl;

import concurrencypractice.countdownlatch.checkers.AbstractChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class IncomeChecker extends AbstractChecker {
    private static final Logger logger = LoggerFactory.getLogger(IncomeChecker.class);

    public IncomeChecker(final CountDownLatch countDownLatch) {
        super(countDownLatch);
    }

    @Override
    public Boolean call() throws Exception {
        logger.info("Checking income...");
        countDownLatch.countDown();
        return Boolean.TRUE;
    }
}
