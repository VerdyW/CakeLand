

public class TransactionCakeDetail {
		
	private String cakeName;
	private String cakeSize;
	private String cakeShape;
	private String cakePrice;
	private String quantity;
	private int SubTotal;
	public TransactionCakeDetail(String cakeName, String cakeSize, String cakeShape, String cakePrice, String quantity,
			int subTotal) {
		super();
		this.cakeName = cakeName;
		this.cakeSize = cakeSize;
		this.cakeShape = cakeShape;
		this.cakePrice = cakePrice;
		this.quantity = quantity;
		SubTotal = subTotal;
	}
	public String getCakeName() {
		return cakeName;
	}
	public void setCakeName(String cakeName) {
		this.cakeName = cakeName;
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
	public String getCakePrice() {
		return cakePrice;
	}
	public void setCakePrice(String cakePrice) {
		this.cakePrice = cakePrice;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public int getSubTotal() {
		return SubTotal;
	}
	public void setSubTotal(int subTotal) {
		SubTotal = subTotal;
	}
	

	
	
	
	
	
}
