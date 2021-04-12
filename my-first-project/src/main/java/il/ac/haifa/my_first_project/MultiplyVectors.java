package il.ac.haifa.my_first_project;
import java.util.Vector;
import java.util.Random;

class threads implements Runnable{
		Vector<Integer> v1 = new Vector<Integer>();
		Vector<Integer> v2 = new Vector<Integer>();
		public static int[] threadsArr = new int [8000] ;
		int limit;
		@Override
	    public void run() {
	        int total = 0;
	        for (int i = 0; i < v1.size(); i++)
	            total += (v1.get(i) * v2.get(i));
	        threadsArr[limit] = total;
	    }
		public threads(Vector<Integer> v1, Vector<Integer> v2, int limit)
		{
			this.v1 = v1;
			this.v2 = v2;
			this.limit = limit;
		}
	}
public class MultiplyVectors{
	public static void main(String[] args) 
	{   
		int total = 0;
		int n = Integer.parseInt(args[0]);
        int N = Integer.parseInt(args[1]);
		//int n=8000;
		//int N=1000;
        Vector<Integer> v1 = generateVec(n);
        Vector<Integer> v2 = generateVec(n);
        Vector<Integer> portion1 = new Vector<>(n/N);
        Vector<Integer> portion2 = new Vector<>(n/N);
        for(int i=0; i<n/N; i++) {
        	portion1.add(0);
        	portion2.add(0);
        }
        Thread[] myThreads = new Thread[N];
        for(int i=0; i<N; i++)
        {
        	for(int j=0 ; j<n/N ; j++)
        	{
        		portion1.set(j, v1.get(j +(i*(n/N))));
        		portion2.set(j, v2.get(j +(i*(n/N))));
        		
        	}
        	myThreads[i] = new Thread(new threads(portion1, portion2, i));
        }
        for (int i = 0; i < N; i++) {
        	
            myThreads[i].start();
        }
        for (int i = 0; i < N; i++) {
            try{
                myThreads[i].join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        for(int i=0; i<N ; i++)
        {
        	total += threads.threadsArr[i];
        }
        System.out.print("( ");
        for (int i = 0; i < n-1 ; i++)
            System.out.print(v1.get(i) + ", ");
        System.out.print(v1.get(n-1));
        System.out.println(" )");
        System.out.print("( ");
        for (int i = 0; i < n-1 ; i++)
            System.out.print(v2.get(i) + ", ");
        System.out.print(v2.get(n-1) );
        System.out.println(" )");
        System.out.println("The total value =" + total);
        
	}
		
		private static Vector<Integer> generateVec(int n)
		{
			Random rd = new Random(); // creating Random object
			Vector<Integer> vec = new Vector<Integer>(n);
		      for (int i = 0; i < n; i++) {
		         vec.add(rd.nextInt()); // storing random integers in an array
		      }
		      return vec;
		}
	
	
}



