package concurrencypractice;

/**
 * DeadLock Example
 *
 * Deadlock happens when two or more threads are waiting for each other to release lock and get stuck forever
 * Indication of deadlock code:
 * - check for nested synchronized block, or calling one synchronized method
 *   from other or trying to get lock on different object
 * - after deadlock happens, do kill -3 in Linux to print status of all thread in application log file
 * - use jconsole to show which threads are locked
 * How to fix this deadlock code: use the same ordered access (eg. synchronize Integer then String on both threads)
 *
 * @author blackrocky
 *
 */
public class DeadLock {
    public static void main(String[] args) {
        final Thread t1 = new Thread(() -> {
            synchronized(Integer.class) {
                System.out.println("thread 1: lock on Integer");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized(String.class) {
                    System.out.println("thread 1: lock on String");
                }
            }
        });
        final Thread t2 = new Thread(() -> {
            synchronized(String.class) {
                System.out.println("thread 2: lock on String");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized(Integer.class) {
                    System.out.println("thread 2: lock on Integer");
                }
            }
        });

        t1.start();
        t2.start();
    }
}
