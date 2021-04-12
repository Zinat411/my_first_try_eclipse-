package il.ac.haifa.my_first_project;
import java.lang.Math;
import java.util.concurrent.TimeUnit;
		public class SumThreads implements Runnable {
			private long sum, value; 
			 @Override
			 public void run() {
			   for(int i=0; i<= value; i++) {
				   sum+=1;
			   }
			 }
			 public SumThreads(long value) {
				 this.value = value;
			 }
			 public long getSum()
			 {
				 return this.sum;
			 }
		
			
		public static void main(String args[]) {
			long startTime = System.nanoTime(); // Computation start time
			 /* Do computation Here */
			SumThreads[] tenThreads = new SumThreads[10];
			Thread[] threads = new Thread[10];
			long total = 0;
			long divide = (long) (Math.pow(2, 32)/10);
			for(int i=0; i<9; i++)
			{
				tenThreads[i] = new SumThreads(divide);
				threads[i] = new Thread(tenThreads[i]);
				threads[i].run();
			}
			for(int i=0; i<9; i++)
			{
				total += tenThreads[i].getSum();
			}
			tenThreads[9] = new SumThreads((long)(Math.pow(2, 32)) - total);
			threads[9] = new Thread(tenThreads[9]);
			threads[9].run();
			total += tenThreads[9].getSum();
			 // The difference between the start time and the end time
			 long difference = System.nanoTime() - startTime;
			 // Print it out
			 long minutesInDifference = TimeUnit.NANOSECONDS.toMinutes(difference);
			 long secondsInDifference =
			 TimeUnit.NANOSECONDS.toSeconds(difference) - TimeUnit.MINUTES.toSeconds(minutesInDifference);
			 System.out.format(
			 "Total execution time: %d min, %d sec\n",
			 minutesInDifference,
			 secondsInDifference
			 );
			 System.out.println(total);
		}
		}
		
		
		

