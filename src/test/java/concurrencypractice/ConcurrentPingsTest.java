package concurrencypractice;

import concurrencypractice.ConcurrentPings.Result;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentPingsTest {
    private static final int MYTHREADS = 30;
    @Test
    public void should_ping_multiple_servers_simultaneously() throws Exception {
        final ExecutorService runnablePool = Executors.newFixedThreadPool(MYTHREADS);
        final String[] hostList = { "http://crunchify.com", "http://yahoo.com",
                "http://www.ebay.com", "http://google.com",
                "http://www.example.co", "https://paypal.com",
                "http://bing.com/", "http://techcrunch.com/",
                "http://mashable.com/", "http://thenextweb.com/",
                "http://wordpress.com/", "http://wordpress.org/",
                "http://example.com/", "http://sjsu.edu/",
                "http://ebay.co.uk/", "http://google.co.uk/",
                "http://www.wikipedia.org/",
                "http://en.wikipedia.org/wiki/Main_Page" };

        System.out.println("Using Runnables..");
        for (final String url : hostList) {
            final Runnable worker = new ConcurrentPings.MyRunnable(url);
            runnablePool.execute(worker);
        }
        runnablePool.shutdown();
        while (!runnablePool.isTerminated()) {
        }

        System.out.println("\nFinished all threads 1");

        final ExecutorService callablePool = Executors.newFixedThreadPool(MYTHREADS);
        final Set<Future<Result>> futures = new HashSet<>();

        System.out.println("Using Callables..");
        for (final String host : hostList) {
            final Callable<Result> callable = new ConcurrentPings.MyCallable(host);
            final Future<Result> future = callablePool.submit(callable);
            futures.add(future);
        }

        for (final Future<Result> future : futures) {
            final Result result = future.get();
            System.out.println(result.getUrl() + "\t\tStatus:" + result.getStatus());
        }
        callablePool.shutdown();
        while (!callablePool.isTerminated()) {
        }

        System.out.println("\nFinished all threads 2");
    }
}