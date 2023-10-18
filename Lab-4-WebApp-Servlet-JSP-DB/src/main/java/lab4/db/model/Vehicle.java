package lab4.db.model;

public class Vehicle {

	private final Integer id;
	private final String name;
	private final String description;
	private final Integer productType;
	private final String imagePath;
	
	public Vehicle(Integer id, String name, String description, Integer productType, String imagePath) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.productType = productType;
		this.imagePath = imagePath;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Integer getProductType() {
		return productType;
	}

	public String getProductTypeAsString() {
		if (productType == 1) return "Car";
		if (productType == 2) return "Moto";
		return "Other";
	}
	
	public String getImagePath() {
		return imagePath;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", productType=" + productType
				+ ", imagePath=" + imagePath + "]";
	}
	
	
}
