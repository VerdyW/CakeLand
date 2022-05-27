

public class TransactionHeader {
	private String transactionID;
	private String userID;
	private String transactionDate;
	private String pickUpDate;
	
	public TransactionHeader(String transactionID, String userID, String transactionDate, String pickUpDate) {
		super();
		this.transactionID = transactionID;
		this.userID = userID;
		this.transactionDate = transactionDate;
		this.pickUpDate = pickUpDate;
	}
	
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getPickUpDate() {
		return pickUpDate;
	}
	public void setPickUpDate(String pickUpDate) {
		this.pickUpDate = pickUpDate;
	}
	
	
}
