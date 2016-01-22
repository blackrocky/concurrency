package concurrencypractice.countdownlatch;

import concurrencypractice.countdownlatch.checkers.AbstractChecker;
import concurrencypractice.countdownlatch.checkers.impl.CreditChecker;
import concurrencypractice.countdownlatch.checkers.impl.IncomeChecker;
import concurrencypractice.countdownlatch.checkers.impl.RaisedInterestRateChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class Loan {
    private static final Logger logger = LoggerFactory.getLogger(Loan.class);

    private final List<AbstractChecker> checkers;
    private final CountDownLatch countDownLatch;
    private static final Loan loan;

    private Loan(final List<AbstractChecker> checkers, final CountDownLatch countDownLatch) {
        this.checkers = checkers;
        this.countDownLatch = countDownLatch;
    }

    static {
        final CountDownLatch cdl = new CountDownLatch(3);
        loan = new Loan(newArrayList(new CreditChecker(cdl), new IncomeChecker(cdl), new RaisedInterestRateChecker(cdl)), cdl);
    }

    public static Loan getInstance() {
        return loan;
    }

    public boolean apply() throws Exception {
        final ExecutorService executorService = newFixedThreadPool(checkers.size());
        boolean approved = true;
        final Set<Future<Boolean>> futures = new HashSet<>();

        for (final AbstractChecker checker : checkers) {
            final Future<Boolean> submit = executorService.submit(checker);
            futures.add(submit);
        }

        for (final Future<Boolean> future : futures) {
            try {
                final Boolean result = future.get();
                if (result != Boolean.TRUE) {
                    approved = false;
                    break;
                }
            } catch (Exception ex) {
                approved = false;
                break;
            }
        }

        countDownLatch.await();

        logger.info("Loan approved? {}", approved);
        executorService.shutdown();

        return approved;
    }
}
