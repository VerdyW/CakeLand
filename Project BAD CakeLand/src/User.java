
public class User {
	
	private String userID;
	private String username;
	private String userEmail;
	private String password;
	private String gender;
	private String dob;
	private String phoneNumber;
	private String address;
	private String role;
	
	public User(String userID, String username, String userEmail, String password, String gender, String dob,
			String phoneNumber, String address, String role) {
		super();
		this.userID = userID;
		this.username = username;
		this.userEmail = userEmail;
		this.password = password;
		this.gender = gender;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.role = role;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	
	
	
}
