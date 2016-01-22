package concurrencypractice;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CounterTest {
    private static final Logger logger = LoggerFactory.getLogger(CounterTest.class);

    @Test
    public void should_get_correct_final_count_even_when_running_simultaneously_with_binary_semaphore() throws Exception {
        final Counter counter = new Counter();

        final ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new Counter.MyBinarySemaphoreCounterRunnable(counter));
        executorService.submit(new Counter.MyBinarySemaphoreCounterRunnable(counter));
        executorService.submit(new Counter.MyBinarySemaphoreCounterRunnable(counter));
        executorService.shutdown();

        if (!executorService.awaitTermination(10L, TimeUnit.SECONDS)) {
            logger.error("Threads did not finish in 10 seconds");
        }

        assertThat(counter.getBinarySemaphoreCount(), is(3));
    }

    @Test
    public void should_get_correct_final_count_even_when_running_simultaneously_with_atomic_counter() throws Exception {
        final Counter counter = new Counter();

        final ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new Counter.MyAtomicCounterRunnable(counter));
        executorService.submit(new Counter.MyAtomicCounterRunnable(counter));
        executorService.submit(new Counter.MyAtomicCounterRunnable(counter));
        executorService.shutdown();

        if (!executorService.awaitTermination(10L, TimeUnit.SECONDS)) {
            logger.error("Threads did not finish in 10 seconds");
        }

        assertThat(counter.getAtomicCount(), is(3));
    }
}