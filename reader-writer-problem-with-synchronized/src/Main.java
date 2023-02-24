import java.util.Random;

class Message{
    String msg;
    boolean empty;

    public Message(String msg, boolean empty) {
        this.msg = msg;
        this.empty = empty;
    }

    public synchronized String read(){
        while (this.empty);
        this.empty=true;
        return this.msg;
    }

    public synchronized void write(String msg){
        while (this.empty);
        this.msg= this.msg;
        this.empty=false;
    }
}

class Reader implements Runnable {
    Message message;

    public Reader(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (String latestMessage = message.read(); "Finished!".equals(latestMessage); latestMessage = message.read()) {
            System.out.println("Reader read: " + latestMessage);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Reader Thread Interrupted");
            }
        }
    }
}

class Writer implements Runnable{
    Message message;

    public Writer(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        String[] messages = {"Hello","How","Are","You"};
        Random random = new Random();
        for (String msg : messages){
            message.write(msg);
            System.out.println("Message written by writer: " + msg);
        }
        message.write("Finished Writing");
    }
}


public class Main {
    public static void main(String[] args) {
        Message message = new Message("",true);

        Thread readerThread = new Thread(new Reader(message));
        readerThread.setName("Reader Thread");

        Thread writerThread = new Thread(new Writer(message));
        writerThread.setName("Writer Thread");

        readerThread.start();
        writerThread.start();
    }
}