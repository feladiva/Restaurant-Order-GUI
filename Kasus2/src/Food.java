
public class Food {
	private String foodID;
	private String foodName;
	private String foodDescription;
	private int price;
	private int quantity;
	public Food(String foodID, String foodName, String foodDescription, int price, int quantity) {
		super();
		this.foodID = foodID;
		this.foodName = foodName;
		this.foodDescription = foodDescription;
		this.price = price;
		this.quantity = quantity;
	}
	public String getFoodID() {
		return foodID;
	}
	public void setFoodID(String foodID) {
		this.foodID = foodID;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public String getFoodDescription() {
		return foodDescription;
	}
	public void setFoodDescription(String foodDescription) {
		this.foodDescription = foodDescription;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
}
