package concurrencypractice.countdownlatch.checkers;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public abstract class AbstractChecker implements Callable<Boolean> {
    protected final CountDownLatch countDownLatch;

    public AbstractChecker(final CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }
}
