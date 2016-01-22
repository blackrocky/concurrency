package concurrencypractice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class ConcurrentPings {
    private static final Logger logger = LoggerFactory.getLogger(ConcurrentPings.class);

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
            logger.info("{} \t\tStatus: {}", url, result);
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

    public static class Result {
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