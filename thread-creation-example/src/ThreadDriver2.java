class NumberPrinter2 {
    void print() {
        synchronized (this) {
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName());
            }
        }
    }
}
    class ThreadExample2 implements Runnable {
        NumberPrinter2 numberPrinter;

        public ThreadExample2(NumberPrinter2 numberPrinter) {
            this.numberPrinter = numberPrinter;
        }

        @Override
        public void run() {
            this.numberPrinter.print();
        }
    }
    public class ThreadDriver2 {
        public static void main(String[] args) {

            NumberPrinter2 numberPrinter = new NumberPrinter2();


            ThreadExample2 threadExample1 = new ThreadExample2(numberPrinter);
            Thread thread1 = new Thread(threadExample1);
            thread1.setName("Thread-1");
            thread1.start();

            ThreadExample2 threadExample2 = new ThreadExample2(numberPrinter);
            Thread thread2 = new Thread(threadExample2);
            thread2.setName("Thread-2");
            thread2.start();

            ThreadExample2 threadExample3 = new ThreadExample2(numberPrinter);
            Thread thread3 = new Thread(threadExample3);
            thread3.setName("Thread-3");
            thread3.start();

            ThreadExample2 threadExample4 = new ThreadExample2(numberPrinter);
            Thread thread4 = new Thread(threadExample4);
            thread4.setName("Thread-4");
            thread4.start();
        }
    }
