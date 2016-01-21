package concurrencypractice;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentPings {
    private static final int MYTHREADS = 30;

    public static void main(String args[]) throws Exception {
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
            final Runnable worker = new MyRunnable(url);
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
            final Callable<Result> callable = new MyCallable(host);
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

    public static class MyRunnable implements Runnable {
        private final String url;

        MyRunnable(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            String result;
            int code;
            try {
                final URL siteURL = new URL(url);
                final HttpURLConnection connection = (HttpURLConnection) siteURL
                        .openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                code = connection.getResponseCode();
                if (code == 200) {
                    result = "Green\t";
                } else {
                    result = "code " + code;
                }
            } catch (Exception e) {
                result = "->Red<-\t";
            }
            System.out.println(url + "\t\tStatus:" + result);
        }
    }

    public static class MyCallable implements Callable<Result> {
        private final String url;

        MyCallable(String url) {
            this.url = url;
        }

        @Override
        public Result call() throws Exception {
            return pingAndGetResult();
        }

        private Result pingAndGetResult() {
            Result result;
            int code;
            try {
                final URL siteURL = new URL(url);
                final HttpURLConnection connection = (HttpURLConnection) siteURL
                        .openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                code = connection.getResponseCode();
                if (code == 200) {
                    result = new Result(url, "Green");
                } else {
                    result = new Result(url, "code " + code);
                }
            } catch (Exception e) {
                result = new Result(url, "->Red<-");
            }
            return result;
        }
    }

    private static class Result {
        private final String url;
        private final String status;

        public Result(final String url, final String status) {
            this.url = url;
            this.status = status;
        }

        public String getUrl() {
            return url;
        }

        public String getStatus() {
            return status;
        }
    }
}