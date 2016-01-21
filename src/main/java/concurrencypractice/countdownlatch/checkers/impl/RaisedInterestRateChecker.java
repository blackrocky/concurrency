package concurrencypractice.countdownlatch.checkers.impl;

import concurrencypractice.countdownlatch.checkers.AbstractChecker;

import java.util.concurrent.CountDownLatch;

public class RaisedInterestRateChecker extends AbstractChecker {
    public RaisedInterestRateChecker(final CountDownLatch countDownLatch) {
        super(countDownLatch);
    }

    @Override
    public Boolean call() throws Exception {
        System.out.println("Checking higher interest rate calculation...");
        countDownLatch.countDown();
        return Boolean.TRUE;
    }
}
