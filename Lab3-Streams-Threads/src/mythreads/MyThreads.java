package mythreads;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


/** The program demonstrates how several threads can run in parallel.
 *  Upon running the program, circles appear and move on a canvas.
 *  The 1st thread just prints "Hello" to the console, the 2nd one creates new circle objects,
 *  adds them to an array list, and paints them, a 3rd one (re)paints the circles to the canvas based on
 *  the said list and animates their motion, and the 4th one prints the saved state of the canvas (see "SAVE" button).
 *  Also, a Timer (a 5th thread) is used for changing the "CLEAR" button color.
 * 
 *  @author O. Voutyras
 *  @author N. Kapsoulis
 * 
 */


public class MyThreads extends JFrame implements ActionListener {
	
	// Define named-constants
	private static final int CANVAS_WIDTH = 640;
	private static final int CANVAS_HEIGHT = 480;
	
	// Attributes of moving object
	private int size = 100; // width and height
	private int rad = 10;
	
	private static boolean colorSwitch = true;  // button
	
	//Object types
	Canvas canvas;
	JButton clear;
	JButton save;
	
	ArrayList<myCircle> myCircles;
	
	private static final long serialVersionUID = -1989891553971170424L;

	String filename = "circledata.ser";
	File mySerFile = new File(filename);

	// Constructor to setup the GUI components, event handlers, and threads
		
	MyThreads() {
		
		// GUI components and event handlers
		
		canvas = new Canvas("Canvas on Circle motion thread");
		addCircle();
		clear = new JButton("CLEAR");
		clear.setForeground(Color.RED);
		clear.setBackground(Color.BLUE);
		clear.addActionListener(this);
		
		save = new JButton("SAVE");
		save.addActionListener(this);
		
		canvas.add(clear);
		canvas.add(save);
		
		this.getContentPane().add(new JPanel(), BorderLayout.NORTH);
		this.getContentPane().add(canvas);
		
		this.setSize(640, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Moving Circles");
		this.setVisible(true);
		this.setResizable(false);
		
		
		// Threads
		
		Runnable hello = new DisplayMessage("Hello");
		Thread thread1 = new Thread(hello);
		thread1.setName("Hello");
		thread1.setDaemon(true);
		thread1.setPriority(Thread.MIN_PRIORITY);
		System.out.println("Starting \"" + thread1.getName() +"\" thread...");		
		thread1.start();
		
				
		Runnable newcircle = new NewCircle("New circle");
		Thread thread2= new Thread(newcircle);
		thread2.setName("New circle");
		System.out.println("Starting \"" + thread2.getName() +"\" thread...");		
		thread2.start();
		
		Thread thread3 = new Thread(canvas, "Circle motion");
		thread3.setName("Circle motion");
		System.out.println("Starting \"" + thread3.getName() +"\" thread...");
		thread3.start();
		
		
		Runnable data = new Data("Data of saved circles");
		Thread thread4 = new Thread(data);
		thread4.setPriority(Thread.MIN_PRIORITY);
		thread4.setName("Data");
		System.out.println("Starting \"" + thread4.getName() +"\" thread...");
		thread4.start();
		

		// thread5: Timer to change color of button 'CLEAR'.
		System.out.println("Starting \"Timer\" thread...");
		new Timer().schedule(new TimerTask() {
			 @Override
			 public void run() {
				System.out.println("Timer running");
				if (colorSwitch)  {
					 clear.setForeground(Color.RED);
					 clear.setBackground(Color.BLUE);
					 colorSwitch = false;
				} else {
					 clear.setForeground(Color.BLACK);
		  			 clear.setBackground(Color.GREEN);
		  			 colorSwitch = true;
		  	 	}
			}
		}, 10, 4000);	// Wait 10 ms before the action and repeat it every 4000 ms (= 4 seconds)
		
	}

	public void addCircle() {
    
	    int x = (int )(Math.random() * 560);
	    int y = (int )(Math.random() * 360);
	    int velX = -5;
	    int velY = 5;
	    //int rad=10;
	    myCircles.add(new myCircle(x, y, velX, velY, rad));
	}

	// handler ActionListener
	@Override
	
	public void actionPerformed(ActionEvent ev) {
		
		if (ev.getSource() == clear) {
			
			System.out.println("\n**** CLEAR CLICKED: Removing circles... ****\n");
			removeCircles();

			rad=10;
			addCircle();
			repaint();
		} else {

			System.out.println("\n**** SAVE CLICKED: Saving circles... ****\n");	
			saveList();
		}
		
    }

	public void removeCircles() {
		myCircles.clear();
	}

	class myCircle implements Serializable {

		private static final long serialVersionUID = 1L;
		
		int x;
	    int y;
	    int velX;
	    int velY;
	    int rad1;

	    public myCircle(int x, int y, int velX, int velY, int rad) {
	        this.x = (int )(Math.random() * 560);
	        this.y = (int )(Math.random() * 360);
	        this.velX = velX;											// Shift on the x axis
	        this.velY = velY;											// Similarly on the y axis
	        this.rad1=rad;
	    }

	    public void move() {
	        x+=velX;
	        y+=velY;
	    }

	    public int getX() {
	        return x;
	    }

	    public int getY() {
	        return y;
	    }

	    public void reverseX() {
	        velX = -velX;
	    }

	    public void reverseY() {
	        velY = -velY;
	    }
	    
	    // "Formatter"
	    @Override
	    public String toString() {
	        return "myCircle{" +
	                "X='" + x + '\'' +
	                ",Y='" + y + '\'' +
	                ",velX='" + velX +'\'' +
	                ", velY='" + velY +'\'' +
	                ", RAD='" + rad1 +'\''+
	                "}";
	    }
	}

	private void saveList() {
		 try {
			 FileOutputStream writeData = new FileOutputStream(mySerFile);
			 ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
			 
			 writeStream.writeObject(myCircles);
			 writeStream.flush();
			 writeStream.close();
		} catch (IOException e) {e.printStackTrace();}
	}
	 
	// For thread1 ("hello")
	class DisplayMessage implements Runnable {
		 
		 private String message;

		 public DisplayMessage(String message) {
		    this.message = message;
		 }

		 public void run() {
		
		    while(true) {
		    	try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {e.printStackTrace();}
		    	
		       System.out.println();
		       System.out.println(message);
		    }
		 }
	 }
	 
	 // For thread2 (paint new circles)
	 class NewCircle implements Runnable {
			 
		private String message;
		
		public NewCircle(String message) {
			this.message = message;
		}
		
		public void run() {
			
			while(true) {
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {e.printStackTrace();}
				
				System.out.println(message);
				      
			    rad = rad + 2; 
				addCircle();
				canvas.repaint();
				 
			    System.out.println("\t Number of circles: " + myCircles.size());
				Iterator<MyThreads.myCircle> itr = myCircles.iterator();
				System.out.print("\t Circle data\t  : ");
				while(itr.hasNext()) {
			    	  MyThreads.myCircle obj = itr.next();
			          System.out.print(obj);
			          System.out.print(", ");
			    }
				System.out.println();
			
			}
		
		}
	
	 }

	// For thread3 (paint circles motion)
	@SuppressWarnings("serial")
	// Define inner class Canvas, which is a JPanel used for custom drawing
	class Canvas extends JPanel implements Runnable {
		
		private String message;
		
		public Canvas(String message) {
			myCircles = new ArrayList<>();
			this.message = message;
		}
					
		// Draw a circle
		public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D) g;
	   	        
	        for (myCircle circle: myCircles) {
	            int x = circle.getX();
	            int y = circle.getY();
	       
	            Ellipse2D ellipse = new Ellipse2D.Double(x-rad, y-rad, 2*rad, 2*rad);
	            g2.fill(ellipse); 
	        }
	    }
	
	    
		@Override
	    public void run() {
			
			while(true) {
				
				collision();
				repaint();								// Refresh the JFrame, callback paintComponent()
		        System.out.println(message);
		
				try {
					Thread.sleep(100); 					//Time for next repaint
				} catch (InterruptedException e) {System.out.println("interrupted");}
			
			}   
		}
	
	}

	// Collision with borders check for moving objects and direction update    
	public void collision() {
        for (myCircle circle: myCircles) {
            int x = circle.getX();
            int y = circle.getY();

            if(x < 0 || x >  CANVAS_WIDTH - size) {
            	circle.reverseX();
            }
            
            if(y < 0 || y > CANVAS_HEIGHT - size) {
            	circle.reverseY();
            }

            circle.move();
        }
    }
	

	//For thread4 (print saved list)
	class Data implements Runnable {
		 
		private String message;
		public Data(String message) {
			this.message = message;
		}

		public void run() {
						     
		while(true) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {e.printStackTrace();}

			System.out.println(message);
			
			if (mySerFile.exists() && !mySerFile.isDirectory())
				readList();
		   }
		}
	
	}
	 
	private void readList() {
		 
		 try{

		 	FileInputStream readData = new FileInputStream(mySerFile);
			ObjectInputStream readStream = new ObjectInputStream(readData);
			 
			@SuppressWarnings("unchecked")
			ArrayList<myCircle> readSavedCircles = (ArrayList<myCircle>) readStream.readObject();
			readStream.close();
			
			System.out.println(readSavedCircles);  //.toString());
			
		} catch (IOException | ClassNotFoundException e) {e.printStackTrace();}
		 
	}

	// Main
	public static void main(String args[]) {
		new MyThreads();
	}
		
}