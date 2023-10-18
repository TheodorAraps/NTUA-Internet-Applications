package q1_1;

import java.io.*;

//import javax.swing.JOptionPane;

// Read from Standard Input Write in file and read from file
public class Question1 {
	
	public static void main (String[] args){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String userInput = "";;
		
		// Read Standard Input
		try {
			System.out.println("Write something:");
			userInput = br.readLine();
			
		} catch (IOException ioe) {
			System.out.println(ioe + " --- 1 --- ");
		}
		
		// Write to file
		try {
			String filename = "file.txt";

			byte[] array = userInput.getBytes();
			FileOutputStream os = new FileOutputStream(filename);
			os.write(array);   													// Writes the bytes
			
			os.close();
			
			//JOptionPane.showMessageDialog(null, "Successfully updated: " + userInput,"File: "+ filename, JOptionPane.WARNING_MESSAGE);
			 
		}
		catch(Exception ex) {
			ex.getStackTrace();
			System.out.println(" --- 2 --- ");
		}
		
		// Read from file
		try {
			String filename = "file.txt";
			FileInputStream is = new FileInputStream(filename);
			
			int size = is.available();
			for(int i = 0; i < size; i++) {
				System.out.print((char)is.read());
			}
			
			is.close();
		}
		catch (IOException ex) {
			ex.getStackTrace();
			System.out.println(" --- 3 --- ");
		}
		
	}
	
	
}
