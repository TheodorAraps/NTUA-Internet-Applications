package pack;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  


public class MainClass {
	// Colours
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public static void main(String[] args) throws IOException {
		System.out.println("Choose one (or more) of the following actions by typing its ID:");
		System.out.println(
				  "0: Show File Names in Project Folder\n"
				+ "1: Show Date and Time\n"
				+ "2: Create new File\n"
				+ "3: Delete file\n"
				+ "4: Show File contents\n"
				+ "\n" + ANSI_CYAN + "Insert your preferences:" + ANSI_RESET);
		
		int actions[] = getUserInput();
		
		for (int i = 0; i < actions.length; i++) {
			switch (actions[i]) {
			case 0: 
				System.out.println("\n" + ANSI_GREEN + "Action 0:" + ANSI_RESET);
				zero();
				break;
			case 1: 
				System.out.println("\n" + ANSI_GREEN + "Action 1:" + ANSI_RESET);
				one();
				break;
			case 2: 
				break;
			case 3: 
				break;
			case 4: 
				break;
			default:
				System.out.println("Invalid action ID");
			}
		}
		       
	}
	
	// Function that reads on line from system console and converts them into integers
	public static int[] getUserInput() throws IOException {
		// InputStreamReader: Reads bytes from Standard Input and Decodes them into characters
		// BufferedReader: Buffers the characters - Enable efficient reading of text data
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
	
		// Read a line of text
		final String line = reader.readLine(); 
		
		// Split String if space in between
		String[] strs = line.trim().split("\\s+");
		
		// Max 5 actions
		int[] actions = new int [strs.length];
		
		// Create integer array of actions
		for (int i = 0; i < strs.length; i++) {
		    actions[i] = Integer.parseInt(strs[i]);
		}
		
		return actions;
	}
	
	// Action 0
	public static void zero() {
		// Creates an array in which we will store the names of files and directories
        String[] pathnames;

        // Creates a new File instance by converting the given pathname string
        // into an abstract pathname
        File f = new File("C:\\Diadiktyo Labs\\LAB-2021\\ECLIPSE\\Eclipse-Workspace\\Lab-1-HW-2");

        // Populates the array with names of files and directories
        pathnames = f.list();

        // For each pathname in the pathnames array
        for (String pathname : pathnames) {
            // Print the names of files and directories
            System.out.println(pathname);
        }
	}
	
	// Action 1
	public static void one() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		System.out.println(dtf.format(now));  
	}
}
