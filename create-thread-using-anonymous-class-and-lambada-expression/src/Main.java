public class Main {
    public static void main(String[] args) {
        //Creating runnable object using anonymous class.
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread-1 is Running.....");
            }
        };

        //creating thread using runnable
        Thread thread1 = new Thread(runnable);
        thread1.start();

        //Creating thread using lambda expression to provide method def for
        //run method of Runnable Interface.

        new Thread(()-> System.out.println("Thread-2 is Running...")).start();
    }
}