import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

class Producer implements Runnable{
    private final List<String> buffer;
    private final ReentrantLock lock;

    public Producer(List<String> buffer, ReentrantLock lock) {
        this.buffer = buffer;
        this.lock = lock;
    }

    @Override
    public void run() {
        String[] numbers = {"1","2","3"};
        for (String number : numbers) {
        Main.waitForMillis(Main.MILLIS);
            System.out.println(Thread.currentThread().getName() + " added " + number);
            addItemToBuffer(number);
        }
        System.out.println(Thread.currentThread().getName() + " added " + Main.EOB);
        addItemToBuffer(Main.EOB);
    }
    private void addItemToBuffer (String item) {
        try {
            this.lock.lock();
            Main.waitForMillis(Main.MILLIS);
            buffer.add(item);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            this.lock.unlock();
        }
    }
}

class Consumer implements Runnable{
    private final List<String> buffer;
    private final ReentrantLock lock;

    public Consumer(List<String> buffer, ReentrantLock lock) {
        this.buffer = buffer;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true){
            try {
                this.lock.lock();
                if (buffer.isEmpty()){
                    Main.waitForMillis(Main.MILLIS);
                    System.out.println(Thread.currentThread().getName() + " buffer is empty "
                            + " holdCount: " + this.lock.getHoldCount());
                    continue;
                }
                if (buffer.get(0).equals(Main.EOB)){
                    Main.waitForMillis(Main.MILLIS);
                    System.out.println(Thread.currentThread().getName() + " exiting ");
                    break;
                }else {
                    Main.waitForMillis(Main.MILLIS);
                    System.out.println(Thread.currentThread().getName() + " remove " + buffer.remove(0));
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                this.lock.unlock();
            }
        }
    }
}
public class Main {
    public static final String EOB = "EOB";
    public static final long MILLIS = 100;

    public static void waitForMillis ( long millis) {
        try {
            Thread.sleep(millis);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        List<String> buffer = new ArrayList<>();
        ReentrantLock lock = new ReentrantLock(true);

        Thread producerThread =new Thread(new Producer(buffer,lock));
        producerThread.setName("Producer Thread");
        producerThread.start();

        Thread consumerThread1 = new Thread(new Consumer(buffer,lock));
        consumerThread1.setName("Consumer Thread1");
        consumerThread1.start();

        Thread consumerThread2 = new Thread(new Consumer(buffer,lock));
        consumerThread2.setName("Consumer Thread2");
        consumerThread2.start();


    }
    }
