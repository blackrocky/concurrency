package concurrencypractice.countdownlatch;

import concurrencypractice.countdownlatch.checkers.AbstractChecker;
import concurrencypractice.countdownlatch.checkers.impl.CreditChecker;
import concurrencypractice.countdownlatch.checkers.impl.IncomeChecker;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import static java.util.Arrays.asList;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class Loan {
    public static void main(String[] args) throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(2);

        final CreditChecker creditChecker = new CreditChecker(countDownLatch);
        final IncomeChecker incomeChecker = new IncomeChecker(countDownLatch);

        final List<AbstractChecker> checkers = asList(creditChecker, incomeChecker);
        final ExecutorService executorService = newFixedThreadPool(checkers.size());

        for (final AbstractChecker checker : checkers) {
            executorService.submit(checker);
        }

        countDownLatch.await();

        System.out.println("Loan approved");

        executorService.shutdown();
    }
}
