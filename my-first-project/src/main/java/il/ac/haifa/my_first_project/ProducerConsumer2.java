package il.ac.haifa.my_first_project;
import java.util.concurrent.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
public class ProducerConsumer2 {
	 BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
	 public synchronized void produce(int num)throws InterruptedException  {
	 queue.put(num);
	 notifyAll();
	 }
	 public synchronized Integer consume() throws InterruptedException {
	 while (queue.isEmpty()) {
	 wait();
	 }
	 return queue.poll();
	 }
}
