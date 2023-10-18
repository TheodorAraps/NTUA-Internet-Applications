package mystreams;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;

import java.util.Date;
import java.util.Scanner;


/**A program that demonstrates the use of several simple and processing streams to read and write files via a GUI.
 * The "output Streams" menu is used to put content into files, whereas
 * the "input Streams" menu is used to read (and print to the console) the content.
 *  
 * @author O. Voutyras
 * @author N. Kapsoulis
 *
 */


class MyStreams extends JFrame {
	
	private static final long serialVersionUID = 1L;
	//private static final long serialVersionUID = -3546466195370575469L;
	
	//Object types
	JPanel pl1;
	JTextArea textArea;
	// JScrollPane areaScrollPane,sp;
	Container cp; 
	FlowLayout fl; 
		
    JMenuBar menuBar;
    JMenu InputStreams, OutputStreams;
    JMenuItem readFileInput,readBufferedInput,readDataInput, readObjectInput,fileReader,readScanner ;
    JMenuItem writeFileOutput, writeBufferedOutput,writeDataOutput,writeObjectOutput,fileWriter,writePrintStream ;
    
    MenuItemHandler handler;
    
    public MyStreams() {

	    super("Java IO: Streams");
	    
	    menuBar = new JMenuBar();
 	    handler = new MenuItemHandler();
	    
	    
	    //Menu 1 - inputMenu, Menu 2- outputMenu
	    JMenu outputMenu = new JMenu("output Streams");
	    JMenu inputMenu = new JMenu("input Streams");
	    
	    //Item 1 of Menu 1 - readFileInput
	    readFileInput = new JMenuItem("readFileInput");					// Create menu item
	    readFileInput.addActionListener(handler);						// Add listener to the menu item
	    inputMenu.add(readFileInput);									// Add the menu item to the menu
	    //Item 1 of Menu 2 - writeFileOutput
	    writeFileOutput =new JMenuItem("writeFileOutput");
	    writeFileOutput.addActionListener(handler);
	    outputMenu.add(writeFileOutput);
	    
	    //Item 2 of Menu 1 - readBufferedInput
	    readBufferedInput = new JMenuItem("readBufferedInput");
	    readBufferedInput.addActionListener(handler);
	    inputMenu.add(readBufferedInput); 
	    //Item 2 of Menu 2 - writeBufferedOutput
	    writeBufferedOutput = new JMenuItem("writeBufferedOutput");
	    writeBufferedOutput.addActionListener(handler);
	    outputMenu.add(writeBufferedOutput);
	    
	    //Item 3 of Menu 1 - readDataInput
	    readDataInput =new JMenuItem("readDataInput");
	    readDataInput.addActionListener(handler);
	    inputMenu.add(readDataInput);
	    //Item 3 of Menu 2 - writeDataOutput
	    writeDataOutput =new JMenuItem("writeDataOutput");
	    writeDataOutput.addActionListener(handler);
	    outputMenu.add(writeDataOutput);
	    
	    //Item 4 of Menu 1 - fileReader
	    fileReader = new JMenuItem("fileReader");
	    fileReader.addActionListener(handler);
	    inputMenu.add(fileReader);
	    //Item 4 of Menu 2 - fileWriter
	    fileWriter =new JMenuItem("fileWriter");
	    fileWriter.addActionListener(handler);
	    outputMenu.add(fileWriter);
	    
	    //Item 5 of Menu 1 - readObjectInput
	    readObjectInput=new JMenuItem("readObjectInput");
	    readObjectInput.addActionListener(handler);
	    inputMenu.add(readObjectInput);
	    //Item 5 of Menu 2 - writeObjectOutput
	    writeObjectOutput =new JMenuItem("writeObjectOutput");
	    writeObjectOutput.addActionListener(handler);
	    outputMenu.add(writeObjectOutput);
	    
	    //Item 6 of Menu 1 - readScanner
	    readScanner =new JMenuItem("readScanner");
	    readScanner.addActionListener(handler);
	    inputMenu.add(readScanner);
	    //Item 6 of Menu 2 - writePrintStream
	    writePrintStream =new JMenuItem("writePrintStream");
	    writePrintStream.addActionListener(handler);
	    outputMenu.add(writePrintStream);
	    
	    
	    //Add the two menus to the JMenuBar
	    menuBar.add(inputMenu);
	    menuBar.add(outputMenu);

	    pl1 = new JPanel(new BorderLayout());
	    setJMenuBar(menuBar);
	    
	    
	    // textArea
	    textArea = new JTextArea("Hello");
	    textArea.setFont(new Font("Serif", Font.ITALIC, 16));
	    textArea.setLineWrap(true);
	    textArea.setWrapStyleWord(true);
	    textArea.setSize(100,150);
	    
	    cp = getContentPane();
	    pl1.add(textArea);
	    cp.add(pl1);
     
    }
    

	class MenuItemHandler implements ActionListener, Serializable {
		
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == readFileInput) {
				try {
					String filename = "streams/fileStream.txt";
					FileInputStream is = new FileInputStream(filename);
					
					int size = is.available();
					for(int i = 0; i < size; i++) {
						System.out.print((char)is.read() + " ");
					}
					
					is.close();
				}
				catch (IOException ex) {
					System.out.print("Exception");
					ex.getStackTrace();
				}
			}
			
			
			else if (e.getSource()==writeFileOutput) {
				try {
					String filename = "streams/fileStream.txt";
					String data = textArea.getText();
					byte[] array = data.getBytes();
					FileOutputStream os = new FileOutputStream(filename);
					os.write(array);   													// Writes the bytes
					
					os.close();
					
					JOptionPane.showMessageDialog(null, "Successfully updated: " + data,"File: "+filename,JOptionPane.WARNING_MESSAGE);
					 
				}
				catch(Exception ex) {
					ex.getStackTrace();
	           }
			}
			
			
			else if (e.getSource() == readBufferedInput) {
				
				try{
					FileInputStream file = new FileInputStream("streams/buffered.txt");			//Creates a FileInputStream
					BufferedInputStream input = new BufferedInputStream(file);			//Creates a BufferedInputStream
					
					int i = input.read();												//Reads first byte from file
					while (i != -1) {
						System.out.print((char) i);
						i = input.read();												//Reads next byte from the file
					}
					System.out.println();
					input.close();
				}
				
				catch (Exception ex) {
					ex.getStackTrace();
				}
			}
			
			
			else  if (e.getSource()==writeBufferedOutput) {
				
				try {
					
					//String data = "This is a line of text inside the file";
					String data = textArea.getText();								
					
					FileOutputStream file = new FileOutputStream("streams/buffered.txt");		// Creates a FileOutputStream
					BufferedOutputStream output = new BufferedOutputStream(file);		// Creates a BufferedOutputStream
					
					byte[] array = data.getBytes();
					output.write(array);												// Writes data to the output stream
					
					output.close();
				}
				catch (Exception ex) {
					ex.getStackTrace();
				}
			}
			
			
			else if (e.getSource() == readDataInput) {
				
				File file = new File("streams/data");
				FileInputStream fileInputStream = null;
				DataInputStream dataInputStream = null;
				
				try {
					fileInputStream = new FileInputStream(file);
					dataInputStream = new DataInputStream(fileInputStream);
					
					System.out.println(dataInputStream.readInt());
					System.out.println(dataInputStream.readDouble());
					System.out.println(dataInputStream.readChar());
					System.out.println(dataInputStream.readFloat());
					System.out.println(dataInputStream.readUTF());
					
				}
				
				catch (IOException ex) {
					ex.printStackTrace();
				}
				
				finally {
					try {
						if(fileInputStream!=null){
							fileInputStream.close();
						}
						if(dataInputStream!=null){
							dataInputStream.close();
						}
					}
					
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			
			}
			
			
			else if (e.getSource() == writeDataOutput) { 
				File file = new File("streams/data");
				//Write primitive data type to file
				FileOutputStream fileOutputStream = null;
				DataOutputStream dataOutputStream = null;
				try {
					fileOutputStream=new FileOutputStream(file);
					dataOutputStream=new DataOutputStream(fileOutputStream);
				
					dataOutputStream.writeInt(60);
					dataOutputStream.writeDouble(600.25);
					dataOutputStream.writeChar('A');
					dataOutputStream.writeFloat(100);
					dataOutputStream.writeUTF("Writing primitive Java data types to an output stream.");
					
					dataOutputStream.flush();
				}
				
				catch (IOException ex) {
					ex.printStackTrace();
				}
				
				finally {
					try {
						if(fileOutputStream!=null) {
							fileOutputStream.close();
						}
						if(dataOutputStream!=null){
							dataOutputStream.close();
						}
					}
					
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			
			
			else if (e.getSource() == fileReader) {
				
				try {
					Reader reader = new FileReader("streams/file.txt");
					//long theCharSkipped = reader.skip(4); //skip 4 characters
					int theCharNum = reader.read();
					
					while(theCharNum != -1) {
						char theChar = (char) theCharNum;
						System.out.print(theChar);
						theCharNum = reader.read();
					}
					reader.close();
					System.out.println();
				}
				
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


			}
			
			
			else if (e.getSource()== fileWriter) {
				try {
					FileWriter writer = new FileWriter("streams/file.txt");
					
					writer.write(textArea.getText());
					writer.write(System.lineSeparator());
					writer.write("Applications");
					writer.close();
				}
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
			else if (e.getSource() == readObjectInput) {       
				Dog dog1;
				try {
					FileInputStream file = new FileInputStream("streams/objectStream");
					ObjectInputStream input = new ObjectInputStream(file);				// Creates an ObjectInputStream
				
					dog1 = (Dog) input.readObject();									// Reads the object
					
					//output.close();
					input.close();
					file.close();
					
					System.out.println("Dog Name: " + dog1.name);
					System.out.println("Dog Breed: " + dog1.breed);
				}
				catch (IOException | ClassNotFoundException ex) {
					System.err.println(ex);
				}
			}
			
			
			else if (e.getSource() == writeObjectOutput) {
				Dog dog1;
				try {
	            	   dog1 = new Dog("Dante", "Labrador");
	            	   FileOutputStream file = new FileOutputStream("streams/objectStream");
	            	   ObjectOutputStream output = new ObjectOutputStream(file);		// Creates an ObjectOutputStream

	            	   // System.out.println(dog1);
	            	   
	            	   output.writeObject(dog1);										// Writes object to the output stream
	            	   
	            	   System.out.println("Dog Name: " + dog1.name);
	            	   System.out.println("Dog Breed: " + dog1.breed);
	            	   
	            	   // output.flush();           
	            	   output.close();
	            	   file.close();
	            	   
	            	   System.out.println(":= Serialization of Object Dog.");
	            }
				catch(Exception ex) {
					System.out.println("IOException is caught");
					ex.getStackTrace();
				} 
				finally {
					//output.close();
				}
			}
			
			else if (e.getSource()== readScanner) {
				
				try {
					Scanner f1 = new Scanner(System.in); // Creates a Scanner object
					System.out.println("Enter a file name:");
					String filename = f1.nextLine();
					File myFile = new File(filename);
					// while(!myFile.exists() || myFile.isDirectory()) {
					// 	System.out.println("Enter an existing file name:");
					// 	filename = f1.nextLine();	
					// 	myFile = new File(filename);
					// }
					
					Scanner myReader = new Scanner(myFile);
					
					while (myReader.hasNextLine()) {
						String data = myReader.nextLine();
						System.out.println(data);
					}
					
					myReader.close();
					f1.close();
					
				}
				catch (FileNotFoundException ev) {
					System.out.println("An error occurred.");
					ev.printStackTrace();
				}
			}

			else if (e.getSource()== writePrintStream) {
				try {
					FileOutputStream fileOutputStream = new FileOutputStream("streams/file.txt");
					PrintStream output = new PrintStream(fileOutputStream);
					
					output.println('A');
					output.println(100L);
					output.println(45.451);
					output.println(new Date());
					output.println("This is an example of the PrintStream class.");
					
					output.close();
				}
				catch(Exception ev) {
					ev.getStackTrace();
				}
			}
			
			
		}
	
	}
	
	
	public static void main(String[] args) {
		
		MyStreams app  = new MyStreams();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		app.setSize(300,300);
		app.setLocation(900,300);
		app.setResizable(false);
		app.setVisible(true);
	
	}
	
	
	class Dog implements Serializable {
		
		private static final long serialVersionUID = -7996291219151891528L;
		
		/*transient*/ String name;
		String breed;
		
		public Dog(String name, String breed) {
			this.name = name;
			this.breed = breed;
		}
	}
    
}