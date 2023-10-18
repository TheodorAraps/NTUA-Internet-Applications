package database;

public class CountPerCategory {
	
	private String name;
	private Integer count;

	public CountPerCategory(String name, Integer count) {
		this.name = name;
		this.count = count;
	}
	
	public Integer getCount() {
		return count;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return "[" + name + ", " + count + "]";
	}
}