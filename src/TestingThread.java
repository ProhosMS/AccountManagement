/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class TestingThread {
    public static void main(String[] args) {
        Test a = new Test();
    }
}

final class Counter {
    private long value = 0;

    public synchronized long getValue() {
        return value;
    }

    public synchronized long increment() {
        if (value == Long.MAX_VALUE)
            throw new IllegalStateException("counter overflow");
        return ++value;
    }
}

class Test {
    Counter counter = new Counter();

    Test() {
        Runnable task = () -> {
            while (counter.getValue() < 100) {
                Thread t = Thread.currentThread();
                counter.increment();
                System.out.println(t.getName() + " is printing the counter = " + counter.getValue());
                try {
                    Thread.sleep(0);
                } catch (Exception e) {}
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
//        Thread t3 = new Thread(task);
//        Thread t4 = new Thread(task);
//        Thread t5 = new Thread(task);
//        Thread t6 = new Thread(task);
//        Thread t7 = new Thread(task);
//        Thread t8 = new Thread(task);
//        Thread t9 = new Thread(task);

        t1.start();
        t2.start();
//        t3.start();
//        t4.start();
//        t5.start();
//        t6.start();
//        t7.start();
//        t8.start();
//        t9.start();
    }
}
