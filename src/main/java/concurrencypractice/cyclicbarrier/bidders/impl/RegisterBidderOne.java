package concurrencypractice.cyclicbarrier.bidders.impl;

import concurrencypractice.cyclicbarrier.bidders.AbstractRegisterBidder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CyclicBarrier;

public class RegisterBidderOne extends AbstractRegisterBidder {
    private static final Logger logger = LoggerFactory.getLogger(RegisterBidderOne.class);

    public RegisterBidderOne(CyclicBarrier cyclicBarrier) {
        super(cyclicBarrier);
    }

    @Override
    public Boolean call() throws Exception {
        logger.info("RegisterBidderOne waiting at the barrier");
        Thread.sleep(2000L);
        cyclicBarrier.await();
        logger.info("RegisterBidderOne registered");
        return Boolean.TRUE;
    }
}
