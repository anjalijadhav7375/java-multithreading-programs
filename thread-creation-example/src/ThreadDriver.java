class NumberPrinter {
    void print() {
        try {
            synchronized (this) {
                for (int i = 1; i <= 10; i++) {
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + " " + i);
                }
            }
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}

class ThreadExample extends Thread {
    NumberPrinter numberPrinter;

    public ThreadExample(NumberPrinter numberPrinter) {
        this.numberPrinter = numberPrinter;
    }

    @Override
    public void run() {
        System.out.println("Name " + Thread.currentThread().getName());
        this.numberPrinter.print();
    }
}

public class ThreadDriver {
    public static void main(String[] args) {
        NumberPrinter numberPrinter = new NumberPrinter();

        ThreadExample thread1 = new ThreadExample(numberPrinter);
        thread1.setName("Thread-1");
        thread1.start();

        ThreadExample thread2 = new ThreadExample(numberPrinter);
        thread2.setName("Thread-2");
        thread2.start();

        ThreadExample thread3 = new ThreadExample(numberPrinter);
        thread3.setName("Thread-3");
        thread3.start();

        ThreadExample thread4 = new ThreadExample(numberPrinter);
        thread4.setName("Thread-4");
        thread4.start();

        System.out.println("main Thread Terminated");
    }
}

