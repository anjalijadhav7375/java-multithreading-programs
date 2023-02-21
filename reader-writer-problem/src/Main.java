class Message {
    String msg;
    boolean empty;

    public Message(String msg, boolean empty) {
        this.msg = msg;
        this.empty = empty;
    }
    public String read(){
        while (this.empty);
        this.empty=true;
        return this.msg;
    }

    public void write(String msg){
        while (!this.empty);
        this.msg=msg;
        this.empty=false;
    }
}

class Reader implements Runnable{
    Message message;

    public Reader(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        for (String msg = this.message.read();
        !msg.equals("Finished Writing !!");
        msg = this.message.read())
        {
            System.out.println("Message Read by Reader: " + msg );
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

        for (String msg:messages) {
            this.message.write(msg);
            System.out.println("Message written by Writer: " + msg);
        }
        this.message.write("Finished Writing");
    }
}
public class Main {
    public static void main(String[] args) {
        Message message = new Message("",true);

        Thread readerThread = new Thread(new Reader(message));
        readerThread.setName("Reader Thread");
        readerThread.start();

        Thread writerThread = new Thread(new Writer(message));
        writerThread.setName("Writer Thread");
        writerThread.start();
    }
}