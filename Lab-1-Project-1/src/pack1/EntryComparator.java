package pack1;

import java.util.Comparator;
import java.util.Map.Entry;

/**
 * An {@link Entry} {@link Comparator}. 
 * 
 * The key of the Entry should be a {@link Character} whereas the value should be an {@link Integer}
 */
public class EntryComparator implements Comparator<Entry<Character, Integer>> {

	@Override
	public int compare(Entry<Character, Integer> e1, Entry<Character, Integer> e2) {
		// Returns a value < 0 if when e1.getKey() < e2.getKey() (numerically)
		return e1.getKey().compareTo(e2.getKey());
	}

}
