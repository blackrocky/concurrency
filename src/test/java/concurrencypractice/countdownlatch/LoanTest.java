package concurrencypractice.countdownlatch;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LoanTest {
    private static final Logger logger = LoggerFactory.getLogger(LoanTest.class);

    @Test
    public void should_pass_loan_test() throws Exception {
    	final Loan loan = Loan.getInstance();

        final ExecutorService executorService = Executors.newFixedThreadPool(6);
        final List<Future<Loan>> futures = executorService.invokeAll(
                newArrayList(new MyLoanLoaderCallable(), new MyLoanLoaderCallable(), new MyLoanLoaderCallable(),
                        new MyLoanLoaderCallable(), new MyLoanLoaderCallable(), new MyLoanLoaderCallable()));
        for (Future<Loan> future : futures) {
            assertThat(future.get(), is(loan));
        }

        executorService.shutdown();

        if (!executorService.awaitTermination(10L, TimeUnit.SECONDS)) {
            logger.error("Threads did not finish in 10 seconds");
        }

        assertThat(loan.apply(), is(true));
    }

    public class MyLoanLoaderCallable implements Callable<Loan> {
        @Override
        public Loan call() throws Exception {
            return Loan.getInstance();
        }
    }
}
