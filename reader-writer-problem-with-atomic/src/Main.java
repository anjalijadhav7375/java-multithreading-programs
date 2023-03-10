import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A CPU can be dual or quad-core and each can run one thread.
 * Each core has its own cache and this cache is used by the thread that is running
 * in this core.
 * core1 --> Thread1 [core1 cache1]  <-- [Heap(Main Memory)]
 * core2 --> Thread2 [core2 cache2]  <-- [Heap(Main Memory)]
 * Each thread reads message object from heap and stores them into their cache.
 *
 * To solve the problem of local thread cache update issue, we can use
 * volatile keyword with variables that will enforce read/write directly to the
 * main memory and hence all the threads will have same values of volatile variables.
 *
 * volatile vairable can be accessed/updated by many threads at the same time.
 *
 * atomic internally usage volatile but it make sure that operation is atomic and
 * one thread is accessing/updating the atomic at a time
 *
 * Atomic -> volatile + thread safety for variables.
 *
 * Now we are using atomic instead of volatile but the problem still exist where
 * the message written by writer is overridden by itself and hence proper reader-writer
 * functioning is missing
 */
 class Message{
 AtomicReference<String> msg;
 AtomicBoolean empty;

    public Message(AtomicReference<String> msg, AtomicBoolean empty) {
        this.msg = msg;
        this.empty = empty;
    }

    public synchronized String read(){
 while (this.empty.get());
 this.empty.set(true);
 return this.msg.get();
 }

 public synchronized void write(String msg){
 while (this.empty.get());
 this.msg.set(msg);
 this.empty.set(false);
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
         Message message = new Message(new AtomicReference<>(""), new AtomicBoolean(true));

         Thread readerThread = new Thread(new Reader(message));
         readerThread.setName("Reader Thread");

         Thread writerThread = new Thread(new Writer(message));
         writerThread.setName("Writer Thread");

         readerThread.start();
         writerThread.start();
     }
 }
