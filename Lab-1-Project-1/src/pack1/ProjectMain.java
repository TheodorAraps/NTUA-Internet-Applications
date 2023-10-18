package pack1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectMain {

	public static void main(String[] args) throws Exception {

		System.out.println("Write something: ");
		
		final Map<Character, Integer> map = new HashMap<Character, Integer>();
		final String str = getUserInput();
		for (int i = 0; i < str.length(); i++) {
			char c = Character.toLowerCase(str.charAt(i));
			//System.out.println(c);
			if (map.containsKey(c)) {
				int count = map.get(c);
				count++;
				map.put(c, count);
			}
			else {
				map.put(c, 1);
			}
		}
		
		
		final List<Map.Entry<Character, Integer>> entryList = new ArrayList<Map.Entry<Character, Integer>>(map.entrySet());
		Collections.sort(entryList, new Comparator<Map.Entry<Character, Integer>>() {

			@Override
			public int compare(java.util.Map.Entry<Character, Integer> e1, java.util.Map.Entry<Character, Integer> e2) {
				return e1.getKey().compareTo(e2.getKey());
			}
			
		});
		
		for (Map.Entry<Character, Integer> entry : entryList) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		} 
	}
	
	public static String getUserInput() throws IOException {
		
		// InputStreamReader: Reads bytes from Standard Input and Decodes them into characters
		// BufferedReader: Buffers the characters - Enable efficient reading of text data
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 

		// Read a line of text
		final String line = reader.readLine(); 
        
		return line;
	}

}
