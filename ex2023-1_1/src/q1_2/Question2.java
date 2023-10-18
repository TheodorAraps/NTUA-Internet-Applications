package q1_2;

import java.time.LocalTime;


class Message {
	
	private String msg;
	
	public Message(String str) {
		this.msg=str;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String str) {
		this.msg=str;
	}
	
}

public class Question2 {
	public static void main(String[] args) {
	      
		Message msg = new Message("Process it");
      
        Waiter waiter1 = new Waiter(msg);
        new Thread(waiter1,"waiter1").start();

        Waiter waiter2 = new Waiter(msg);
        new Thread(waiter2, "waiter2").start();
        
        Waiter waiter3 = new Waiter(msg);
        new Thread(waiter2, "waiter3").start();
        
        Notifier notifier1 = new Notifier(msg);
        new Thread(notifier1, "notifier1").start();
        
        Notifier notifier2 = new Notifier(msg);
        new Thread(notifier2, "notifier2").start();
        
        Notifier notifier3 = new Notifier(msg);
        new Thread(notifier3, "notifier3").start();
        
    }
}



class Waiter implements Runnable {
		
		private Message msg;
		
		public Waiter(Message m){
			this.msg=m;
		}

	    @Override
	    public void run() {
	        String name = Thread.currentThread().getName();
	        synchronized (msg) {
	            try {
	                System.out.println(LocalTime.now()+": "+name+" waiting to get notified");
	                msg.wait();
	                //msg.wait(200); // Max time to wait
	            }
	            
	            catch(InterruptedException e){
	                e.printStackTrace();
	            }
	            
	            System.out.println(LocalTime.now()+": "+name+" thread got notified");
	            //process the message now
	            System.out.println(LocalTime.now()+": "+name+" processed: " + msg.getMsg());
	        }
	    }

}

class Notifier implements Runnable {

    private Message msg;
    
    public Notifier(Message msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(LocalTime.now()+": "+name+" started");
        try {
            Thread.sleep(2000);
            synchronized (msg) {
                msg.setMsg(name+": Notifier work done");
                //msg.notifyAll();
                msg.notify();
            }
        }
        
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}

