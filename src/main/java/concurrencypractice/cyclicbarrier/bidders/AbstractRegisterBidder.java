package concurrencypractice.cyclicbarrier.bidders;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

public abstract class AbstractRegisterBidder implements Callable<Boolean> {
    protected final CyclicBarrier cyclicBarrier;

    public AbstractRegisterBidder(final CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }
}
