package concurrencypractice.countdownlatch;

import concurrencypractice.countdownlatch.checkers.AbstractChecker;
import concurrencypractice.countdownlatch.checkers.impl.CreditChecker;
import concurrencypractice.countdownlatch.checkers.impl.IncomeChecker;
import concurrencypractice.countdownlatch.checkers.impl.RaisedInterestRateChecker;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.Arrays.asList;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class Loan {
    public static void main(String[] args) throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(3);

        final CreditChecker creditChecker = new CreditChecker(countDownLatch);
        final IncomeChecker incomeChecker = new IncomeChecker(countDownLatch);
        final RaisedInterestRateChecker raisedInterestRateChecker = new RaisedInterestRateChecker(countDownLatch);

        final List<AbstractChecker> checkers = asList(creditChecker, incomeChecker, raisedInterestRateChecker);
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

        System.out.println("Loan approved? " + approved);

        executorService.shutdown();
    }
}
