/*
 *  A simple program that demonstrates how threads can run one after another.
 *  In 'Block 1', three (3) threads run simultaneously and in random order.
 *  In 'Block 2', three (3) threads run in sequential order.
 */
package mythreadsjoin;

class MyThread implements Runnable {
 
    @Override
    public void run() {
    	Thread t = Thread.currentThread();
        System.out.println(
        	"Thread \""+t.getName()+"\" started");
        try {
        	Thread.sleep(4000);
        } catch (InterruptedException ie) {
        	ie.printStackTrace();
        }
        System.out.println(
        	"Thread \""+t.getName()+"\" ended");    
    }
}

public class MyThreadsJoin {
	
	public static void main(String[] args) {

		// Block 1: Random thread execution		
		
		// Thread th1 = new Thread(new MyThread(), "th1");
		  
		// Thread th2 = new Thread(new MyThread(), "th2");
		  
		// Thread th3 = new Thread(new MyThread(), "th3");
		  
		// th1.start();
		  
		// th2.start();
		  
		// th3.start();
		

		
		// Block 2: Sequential thread execution
		  
		Thread th1 = new Thread(new MyThread(), "th1");
		  
		Thread th2 = new Thread(new MyThread(), "th2");
		  
		Thread th3 = new Thread(new MyThread(), "th3");
		  
		// Start first thread immediately
		  
		th1.start();
		  
		//Start second thread(th2) once first thread(th1) is dead
		  
		try { th1.join(); } catch (InterruptedException ie) { ie.printStackTrace(); }
		  
	    th2.start();
		  
	    // Start third thread(th3) once second thread(th2) is dead
		  
	    try { th2.join(); } catch (InterruptedException ie) { ie.printStackTrace(); }
		  
	    th3.start();
		  
	    // Displaying a message once third thread is dead
		  
	    try { th3.join(); } catch (InterruptedException ie) { ie.printStackTrace(); }
		  
	    // Try it with Block 1
	    System.out.println("All three threads have finished execution.");
		 
		
   }
}




