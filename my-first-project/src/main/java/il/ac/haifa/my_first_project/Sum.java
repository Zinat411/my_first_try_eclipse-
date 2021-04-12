package il.ac.haifa.my_first_project;
import java.lang.Math;
import java.util.concurrent.TimeUnit;
	public class Sum implements Runnable {
		private long first, last, sum;
		 @Override
		 public void run() {
		   for(long i=first; i<= last; i++) {
			   sum+=1;
		   }
		 }
		 public Sum(long first, long last) {
			 this.first = first;
			 this.last = last;
			 sum = 0;
		 }
		 public long getSum()
		 {
			 return this.sum;
		 }
		
	public static void main(String args[]) {
		long startTime = System.nanoTime(); // Computation start time
		 /* Do computation Here */
		Sum one = new Sum(0,(long)(Math.pow(2, 32)));
		Thread thread1 = new Thread (one);
		one.run();
		long thsum = one.getSum();
		System.out.println(thsum);
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
	}
	
	}
	


