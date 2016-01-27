package concurrencypractice.cyclicbarrier.bidders.impl;

import concurrencypractice.cyclicbarrier.bidders.AbstractRegisterBidder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CyclicBarrier;

public class RegisterBidderThree extends AbstractRegisterBidder {
    private static final Logger logger = LoggerFactory.getLogger(RegisterBidderThree.class);

    public RegisterBidderThree(CyclicBarrier cyclicBarrier) {
        super(cyclicBarrier);
    }

    @Override
    public Boolean call() throws Exception {
        logger.info("RegisterBidderThree waiting at the barrier");
        Thread.sleep(1000L);
        cyclicBarrier.await();
        logger.info("RegisterBidderThree registered");
        return Boolean.TRUE;
    }
}
