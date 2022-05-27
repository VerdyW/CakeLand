

public class Cart {
	
	private String cakeID;
	private String cakeName;
	private String cakePrice;
	private String cakeSize;
	private String cakeShape;
	private String cakeQuantity;
	private int cakeSubTotal;
	
	
	
	
	public Cart(String cakeID, String cakeName, String cakePrice, String cakeSize, String cakeShape,
			String cakeQuantity, int cakeSubTotal) {
		super();
		this.cakeID = cakeID;
		this.cakeName = cakeName;
		this.cakePrice = cakePrice;
		this.cakeSize = cakeSize;
		this.cakeShape = cakeShape;
		this.cakeQuantity = cakeQuantity;
		this.cakeSubTotal = cakeSubTotal;
	}
	public String getCakeName() {
		return cakeName;
	}
	public void setCakeName(String cakeName) {
		this.cakeName = cakeName;
	}
	public String getCakePrice() {
		return cakePrice;
	}
	public void setCakePrice(String cakePrice) {
		this.cakePrice = cakePrice;
	}
	public String getCakeSize() {
		return cakeSize;
	}
	public void setCakeSize(String cakeSize) {
		this.cakeSize = cakeSize;
	}
	public String getCakeShape() {
		return cakeShape;
	}
	public void setCakeShape(String cakeShape) {
		this.cakeShape = cakeShape;
	}
	public String getCakeQuantity() {
		return cakeQuantity;
	}
	public void setCakeQuantity(String cakeQuantity) {
		this.cakeQuantity = cakeQuantity;
	}

	public int getCakeSubTotal() {
		return cakeSubTotal;
	}

	public void setCakeSubTotal(int cakeSubTotal) {
		this.cakeSubTotal = cakeSubTotal;
	}
	public String getCakeID() {
		return cakeID;
	}
	public void setCakeID(String cakeID) {
		this.cakeID = cakeID;
	}
	
	
	
	
}
