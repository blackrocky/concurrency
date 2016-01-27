package concurrencypractice.cyclicbarrier;

import org.junit.Test;

public class AuctionTest {
    @Test
    public void should() throws Exception {
        Auction auction = Auction.getInstance();
        auction.startAuction();
    }
}