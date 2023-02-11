public class DeadLockSolutionDriver {
    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread1 thread1 = new Thread1(lock1,lock2);
        thread1.setName("Thread-1");
        thread1.start();

        Thread2 thread2 = new Thread2(lock1,lock2);
        thread2.setName("Thread-2");
        thread2.start();
    }
}
class ThreadExp1 extends Thread{
    final Object lock1;
    final Object lock2;

    public ThreadExp1(Object lock1, Object lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        synchronized (lock1){
            System.out.println("Thread-1 is running and acquired lock1");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread-1 is waiting for lock2");
            synchronized (lock2){
                System.out.println("Thread-1 is running and acquired lock2");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Thread-1 released lock2");
        }
        System.out.println("Thread-1 is released lock1");
    }
}
class ThreadExp2 extends Thread{
    final Object lock1;
    final Object lock2;

    public ThreadExp2(Object lock1, Object lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        synchronized (lock1){
            System.out.println("Thread-2 is running and acquired lock1");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread-2 is waiting for lock2");
            synchronized (lock2){
                System.out.println("Thread-2 is running and acquired lock2");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Thread-2 is released lock1");
        }
        System.out.println("Thread-2 is released lock2");
    }
}
