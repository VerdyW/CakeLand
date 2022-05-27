

public class TransactionDetail {
		private String transactionID;
		private String cakeID;
		private String quantity;
		
		public TransactionDetail(String transactionID, String cakeID, String quantity) {
			super();
			this.transactionID = transactionID;
			this.cakeID = cakeID;
			this.quantity = quantity;
		}
		public String getTransactionID() {
			return transactionID;
		}
		public void setTransactionID(String transactionID) {
			this.transactionID = transactionID;
		}
		public String getCakeID() {
			return cakeID;
		}
		public void setCakeID(String cakeID) {
			this.cakeID = cakeID;
		}
		public String getQuantity() {
			return quantity;
		}
		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}
		
		
}
