package concurrencypractice.countdownlatch.checkers.impl;

import concurrencypractice.countdownlatch.checkers.AbstractChecker;

import java.util.concurrent.CountDownLatch;

public class IncomeChecker extends AbstractChecker {
    public IncomeChecker(final CountDownLatch countDownLatch) {
        super(countDownLatch);
    }

    @Override
    public Boolean call() throws Exception {
        System.out.println("Checking income...");
        countDownLatch.countDown();
        return Boolean.TRUE;
    }
}
