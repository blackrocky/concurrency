package concurrencypractice.cyclicbarrier.bidders.impl;

import concurrencypractice.cyclicbarrier.bidders.AbstractRegisterBidder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CyclicBarrier;

public class RegisterBidderTwo extends AbstractRegisterBidder {
    private static final Logger logger = LoggerFactory.getLogger(RegisterBidderTwo.class);

    public RegisterBidderTwo(CyclicBarrier cyclicBarrier) {
        super(cyclicBarrier);
    }

    @Override
    public Boolean call() throws Exception {
        logger.info("RegisterBidderTwo waiting at the barrier");
        Thread.sleep(4000L);
        cyclicBarrier.await();
        logger.info("RegisterBidderTwo registered");
        return Boolean.TRUE;
    }
}
