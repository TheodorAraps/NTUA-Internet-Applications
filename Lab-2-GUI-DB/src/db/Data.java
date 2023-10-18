package db;

public class Data {
	
	private final Integer id;
	private final String code;
	private final String name;
	private final Float price;
	
	public Data(String code, String name, float price) {
		this.id = null;
		this.code = code;
		this.name = name;
		this.price = price;
		// Check that Data is Valid
		check();
	}
	
	public Data(Integer id, String code, String name, Float price) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.price = price;
		// Check that Data is Valid
		check();
	}

	/** Ensure that Data is Valid */
	private void check() {
		// Code
		if (code == null) throw new RuntimeException("code == null");
		if (code.length() > 10) throw new RuntimeException(code + " :: code.length() > 10");
		// Name
		if (name == null) throw new RuntimeException("name == null");
		if (name.length() > 200) throw new RuntimeException(name + " :: name.length() > 200");
		// Price 
		if (price < 0) throw new RuntimeException(price + " :: price < 0");
	}
	
	public Integer getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public float getPrice() {
		return price;
	}

	public String asString() {
		return code + " : " + name + " --> " + price;
	}
	
	@Override
	public String toString() {
		return "Data [code=" + code + ", name=" + name + ", price=" + price + "]";
	}
	
}
