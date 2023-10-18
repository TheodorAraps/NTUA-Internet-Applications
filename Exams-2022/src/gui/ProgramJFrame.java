package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
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
//import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
//import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.JScrollPane;
import javax.swing.JTextField;
//import javax.swing.ScrollPaneConstants;


class ProgramJFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	static ArrayList<myCircle> myCircles;
	final Dimension screen;
	final JPanel mainPanel;
	final JButton button;
	final JTextField textf;
	final JButton save;
	final JButton clear;
	static Canvas canvas;
	static String filename = "savedStream/MyCirclesList.ser";
	static File mySerFile = new File(filename);
	
	//Frame Dimensions
	private static final int WIDTH = 700;
	private static final int HEIGHT = 700; 
	private static int rad = 10;
	private static boolean colorSwitch = true;  // button
	
	public ProgramJFrame() {
		
		// Set the Title of the window (Frame)
		super("Growing Disks");
		
		// Set Frame in the middle of our screen with the given dimensions
	    screen = Toolkit.getDefaultToolkit().getScreenSize();
	    final int x = (screen.width - WIDTH) / 2;
	    final int y = (screen.height - HEIGHT) / 2;
	    setBounds(x, y, WIDTH, HEIGHT);
	    
	    // Main Panel of the Frame
	    mainPanel = new JPanel();
	    final GridBagLayout layout = new GridBagLayout();
	    mainPanel.setLayout(layout);
	    
	    // Button
	    button = new JButton("Set Radius");
	    button.setFont(new Font("Fonts Times New Roman", Font.BOLD, 19));
	    button.addActionListener(this);
	    
	    // TextField
	    textf = new JTextField("10");
	    textf.setFont(new Font("Fonts Times New Roman", Font.ITALIC, 19));
	    textf.setHorizontalAlignment(JTextField.CENTER); 					// Center text inside TextField
  	    
	    // Canvas
	    canvas = new Canvas("Canvas");
	    canvas.setBackground(Color.white);
		addCircle(0, 0, 10); 						// Initial Circle
		
		// Save Button
		save = new JButton("Save Canvas");	
		save.setFont(new Font("Fonts Times New Roman", Font.BOLD, 19));
		save.addActionListener(this);
		
		// Clear Button
		clear = new JButton("Clear Canvas");
		clear.setFont(new Font("Fonts Times New Roman", Font.BOLD, 19));
		clear.addActionListener(this);
		
		// Add Components to MainPanel with GridBagLayout
		addComponent(mainPanel, button, 0, 0, 1, 1, 1, 10);
		addComponent(mainPanel, textf,  0, 1, 1, 1, 1, 10);
		addComponent(mainPanel, canvas, 1, 0, 2, 1, 1, 100);
		addComponent(mainPanel, save,   2, 0, 1, 1, 1, 10);
		addComponent(mainPanel, clear,  2, 1, 1, 1, 1, 10);

		// Add MainPanel to Frame
		this.add(mainPanel);
		
		// Thread 4: Changing Colors of Clear button
		// Timer to change the colors of the clear button  every 5 sec.
		System.out.println("Starting \"Timer\" thread...");
 		new Timer().schedule(new TimerTask() {
 			 @Override
 			 public void run() {
 				//System.out.println("Timer running");
 				if (colorSwitch)  {
 					 clear.setForeground(Color.WHITE);
 					 clear.setBackground(Color.RED);
 					 colorSwitch = false;
 				} else {
 					 clear.setForeground(Color.BLACK);
 		  			 clear.setBackground(Color.GREEN);
 		  			 colorSwitch = true;
 		  	 	}
 			}
 		}, 0, 5000);	// Wait 0 milliseconds before the action and repeat it every 5000 milliseconds (= 5 seconds)
		
	}
	
	// Define inner class Canvas, which is a JPanel used for custom drawing
	public class Canvas extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		//private String message;
		
		public Canvas(String message) {
			myCircles = new ArrayList<>();
		}
		
		// Draw all circles of Array on Canvas
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

	}
	
	private void addComponent(final JPanel panel, final Component com, int row, int column, int width, int height, int wx, int wy) {
		final GridBagConstraints constraints = new GridBagConstraints();
		// Insets(int top, int left, int bottom, int right)
		constraints.insets = new Insets( 5, 5, 5, 5 );  
	    constraints.fill=GridBagConstraints.BOTH;
		// Specifies the cell containing the leading edge of the component's display area
		constraints.gridx=column;
		// Specifies the cell at the top of the component's display area
		constraints.gridy=row;
		// Specifies the number of cells in a row for the component's display area.
		constraints.gridwidth=width;
		// Specifies the number of cells in a column for the component's display area.
		constraints.gridheight=height;
		// Specifies how to distribute extra horizontal space.
		constraints.weightx=wx;
		// Specifies how to distribute extra vertical space.
		constraints.weighty=wy;
		// Add Constraints before adding Component to Panel
		// IF Panel Layout is not a GridBagLayout, a ClassCastException would be thrown
		final GridBagLayout panelGBL = (GridBagLayout) panel.getLayout();
		panelGBL.setConstraints(com, constraints);
		panel.add(com);
	}
	
	// Add a Circle to the Array of Circles (Random position)
	public static void addCircle(int velX, int velY, int rad) {
	    
	    int x = (int )(Math.random() * canvas.getWidth()); // Random Coordinates
	    int y = (int )(Math.random() * canvas.getHeight()); 

	    myCircles.add(new myCircle(x, y, velX, velY, rad));
	}
	
	// Circle Class
	public static class myCircle implements Serializable {

		private static final long serialVersionUID = 1L;
		
		int x;
	    int y;
	    int velX;
	    int velY;
	    int rad1;

	    public myCircle(int x, int y, int velX, int velY, int rad) {
	    	if (myCircles.isEmpty() && canvas.getWidth() == 0 && canvas.getHeight() == 0)
	    	{
	    		this.x = (int )(Math.random() * 680);
		        this.y = (int )(Math.random() * 400);
	    	}
	    	else
	    	{
		        this.x = (int )(Math.random() * canvas.getWidth());
		        this.y = (int )(Math.random() * canvas.getHeight());
	    	}
	        this.velX = velX;											// Shift on the x axis
	        this.velY = velY;											// Similarly on the y axis
	        this.rad1 = rad;
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
	        return "myCircle{ " + 
	                "X='" + x + '\'' +
	                ",Y='" + y + '\'' +
	                ", RAD='" + rad1 +'\''+
	                " }";
	    }
	}
	
	// For thread1 ("Starting hello thread...", 5 seconds. sleep, "hello")
	static class HelloThread implements Runnable {
		 
		 private String message;
	
		 public HelloThread(String message) {
		    this.message = message;
		 }
	
		 public void run() {
			 System.out.println("Starting hello thread...");
			 
			 try {
					Thread.sleep(5000);
			 } catch (InterruptedException e) {e.printStackTrace();}
			 
			 System.out.println(message); 
		 }
	}
	
	// For thread2 (paint new circles)
	static class NewCircle implements Runnable {
			 
		//private String message;
		
		public NewCircle(String message) {
			//this.message = message;
		}
		
		public void run() {
			
			while(true) {
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {e.printStackTrace();}
				
			      
				    rad = rad + 2; 
					addCircle(0, 0, rad);
					canvas.repaint();
					
//					System.out.println(message);
	//			    System.out.println("\t Number of circles: " + myCircles.size());
	//				Iterator<myCircle> itr = myCircles.iterator();
	//				System.out.print("\t Circle data\t  : ");
	//				while(itr.hasNext()) {
	//			    	  myCircle obj = itr.next();
	//			          System.out.print(obj);
	//			          System.out.print(", ");
	//			    }
	//				System.out.println();
			}
		}
	}
	
	// Thread 3 (print saved list)
	static class Data implements Runnable {
		 
		private String message;
		public Data(String message) {
			this.message = message;
		}

		public void run() {
						     
		while(true) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {e.printStackTrace();}

			
			if (mySerFile.exists() && !mySerFile.isDirectory())
				System.out.println(message);
				readList();
		    }
		}
	
	}
	
	public void removeCircles() {
		myCircles.clear();
	}
	
	static private void saveList() {
		 try {
			 FileOutputStream writeData = new FileOutputStream(mySerFile);
			 ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
			 
			 writeStream.writeObject(myCircles);
			 writeStream.flush();
			 writeStream.close();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	static private void readList() {
		 
		 try{

		 	FileInputStream readData = new FileInputStream(mySerFile);
			ObjectInputStream readStream = new ObjectInputStream(readData);
			 
			@SuppressWarnings("unchecked")
			ArrayList<myCircle> readSavedCircles = (ArrayList<myCircle>) readStream.readObject();
			readStream.close();
			
			System.out.println(readSavedCircles);  //.toString());
			
		} catch (IOException | ClassNotFoundException e) {e.printStackTrace();}
		 
	}
	
	// handler ActionListener
	@Override
	
	public void actionPerformed(ActionEvent ev) {
		
		if (ev.getSource() == clear) {
			
			System.out.println("\n**** CLEAR CLICKED: Removing circles... ****\n");
			if (clear.getBackground() == Color.GREEN)
			{
				removeCircles();				
				rad=10;
				addCircle(0, 0, 10);
				repaint();	
			}
			else
			{
				// Show Warning Dialog
				JOptionPane.showMessageDialog(null, "Warning", "Clear button is red!", JOptionPane.WARNING_MESSAGE);
			}
			
		} else if (ev.getSource() == save){

			System.out.println("\n**** SAVE CLICKED: Saving circles... ****\n");	
			saveList();
			
		} else if (ev.getSource() == button){
			String newRad = textf.getText();
			rad = Integer.parseInt(newRad);
			
			// Show Warning Dialog
			JOptionPane.showMessageDialog(null, "Successfully Updated Radius", "Radius Update", JOptionPane.WARNING_MESSAGE);
		}
		
    }
	
	
	// MAIN METHOD
	public static void main(String[] args) {
		
		System.out.println(" >> ProgramWindowMain: START");
		
		// Create and Show a window
		final JFrame frame = new ProgramJFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setVisible(true);
		
		System.out.println(" >> ProgramWindowMain: END");	

		// THREADS
		
		// Thread 1: "Hello"
		Runnable hello = new HelloThread("Hello");
		Thread thread1 = new Thread(hello);
		thread1.setName("Hello");
		thread1.setDaemon(true);
		thread1.setPriority(10);		
		thread1.start();
		
		// Thread 2: "New Circle"
		Runnable newcircle = new NewCircle("New circle");
		Thread thread2 = new Thread(newcircle);
		thread2.setName("New Circle");
		try { thread1.join(); } catch (InterruptedException ie) { ie.printStackTrace(); }	// join (wait for) thread1 to finish 
		System.out.println("Starting \"" + thread2.getName() + "\" thread...");		
	    thread2.start();
	    
	    Runnable data = new Data("Data of saved circles");
		Thread thread4 = new Thread(data);
		thread4.setPriority(Thread.MIN_PRIORITY);
		thread4.setName("Data");
		System.out.println("Starting \"" + thread4.getName() +"\" thread...");
		thread4.start();
 		
	}
	
}
