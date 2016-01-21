package concurrencypractice.countdownlatch.checkers.impl;

import concurrencypractice.countdownlatch.checkers.AbstractChecker;

import java.util.concurrent.CountDownLatch;

public class CreditChecker extends AbstractChecker {

    public CreditChecker(final CountDownLatch countDownLatch) {
        super(countDownLatch);
    }

    @Override
    public Boolean call() throws Exception {
        System.out.println("Checking credit file...");
        countDownLatch.countDown();
        return Boolean.TRUE;
    }
}
