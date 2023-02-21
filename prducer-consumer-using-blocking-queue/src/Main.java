import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(100, true);

        Random random = new Random();
        Runnable producerRunnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int i = random.nextInt();
                    blockingQueue.add(i);
                    System.out.println("Producer Thread added " + i);

                    try {
                        Thread.sleep(random.nextInt(2000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread producerThread = new Thread(producerRunnable);
        producerThread.setName("Producer Thread");
        producerThread.start();

        Runnable consumerRunnable = new Runnable() {
            @Override
            public void run() {
                while (true){
                    try{
                       Integer i = blockingQueue.take();
                        System.out.println("Consumer Thread Consume " + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(random.nextInt(2000));
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread consumerThread = new Thread(consumerRunnable);
        consumerThread.setName("Consumer Thread");
        consumerThread.start();
    }
}