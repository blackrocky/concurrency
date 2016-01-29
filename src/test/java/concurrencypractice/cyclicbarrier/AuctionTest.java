package concurrencypractice.cyclicbarrier;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AuctionTest {
    @Test
    public void should_wait_for_all_bidders_before_starting_auction() throws Exception {
        final Auction auction = Auction.getInstance();
        assertThat(auction.getCyclicBarrier().getParties(), is(3));
        assertThat(auction.startAuction(), is(true));
        assertThat(auction.getCyclicBarrier().isBroken(), is(false));
    }
}