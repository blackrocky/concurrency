package concurrencypractice.cyclicbarrier;

import com.google.common.collect.Lists;
import concurrencypractice.cyclicbarrier.bidders.AbstractRegisterBidder;
import concurrencypractice.cyclicbarrier.bidders.impl.RegisterBidderOne;
import concurrencypractice.cyclicbarrier.bidders.impl.RegisterBidderThree;
import concurrencypractice.cyclicbarrier.bidders.impl.RegisterBidderTwo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class Auction {
    private static final Logger logger = LoggerFactory.getLogger(Auction.class);
    private static Auction auction;
    private final List<AbstractRegisterBidder> bidders;
    private final CyclicBarrier cyclicBarrier;

    public Auction(final List<AbstractRegisterBidder> bidders, final CyclicBarrier cyclicBarrier) {
        this.bidders = bidders;
        this.cyclicBarrier = cyclicBarrier;
    }

    static {
        final CyclicBarrier cb = new CyclicBarrier(3, new Runnable(){
            @Override
            public void run(){
                //This task will be executed once all thread reaches barrier
                logger.info("All bidders registered, lets start auction");
            }
        });
        auction = new Auction(Lists.newArrayList(new RegisterBidderOne(cb), new RegisterBidderTwo(cb), new RegisterBidderThree(cb)), cb);
    }

    public static Auction getInstance() {
        return auction;
    }

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public boolean startAuction() throws ExecutionException, InterruptedException, BrokenBarrierException {
        final ExecutorService executorService = newFixedThreadPool(bidders.size());
        logger.info("Parties {}", cyclicBarrier.getParties());
//        executorService.invokeAll(bidders);

        final Set<Future<Boolean>> futures = newHashSet();
        for (final AbstractRegisterBidder bidder : bidders) {
            final Future<Boolean> submit = executorService.submit(bidder);
            futures.add(submit);
        }

        for (final Future<Boolean> future : futures) {
            final Boolean isRegistered = future.get();
            logger.info("isRegistered {} ", isRegistered);
        }
        return true;
    }

}
