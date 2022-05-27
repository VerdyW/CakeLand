import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Formattable;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.*;

public class FormRegistrasi extends JFrame implements MouseListener{
		JPanel pnlRegisterButton, pnlGender, pnlDOB;
		JRadioButton rbMale, rbFemale;
		JButton btnRegister;
		JTextField txtUsername, txtEmail, txtDate, txtMonth, txtYear, txtPhoneNumber; 
		JPasswordField txtPassword, txtConfirmPassword;
		JLabel lblCakeLand, lblCreateAccount, lblUsername, lblEmail, lblGender, lblDOB, lblPhoneNumber, lblAddress, lblPassword, lblConfirmPassword, lblLogin;
		JCheckBox cbTerms;
		JTextArea txtAddress;
		ButtonGroup grpGender;
		JLabel strip1, strip2;
		String userIDInput;
		
		public boolean insertUser(User user) {
			PreparedStatement ps = Connect.getConnection().prepareStatement(
						"INSERT INTO user(UserID, Username, UserEmail, UserPassword, UserGender, UserDOB, UserPhoneNumber, UserAddress, UserRole) VALUES(?,?,?,?,?,?,?,?,?)"
					
					);
			try {
				ps.setString(1, user.getUserID());
				ps.setString(2, user.getUsername());
				ps.setString(3, user.getUserEmail());
				ps.setString(4, user.getPassword());
				ps.setString(5, user.getGender());
				ps.setString(6, user.getDob());
				ps.setString(7, user.getPhoneNumber());
				ps.setString(8, user.getAddress());
				ps.setString(9, user.getRole());
				ps.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
			
			return true;
		}
		
		public Vector<User> getAllUser(){
			Vector<User> users = new Vector<>();
			
			PreparedStatement ps = Connect.getConnection().prepareStatement(
					"SELECT * FROM user"
				
				);
			try {
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					String userID = rs.getString("UserID");
					String username = rs.getString("Username");
					String userEmail = rs.getString("UserEmail");
					String password = rs.getString("UserPassword");
					String gender = rs.getString("UserGender");
					String dob = rs.getString("UserDOB");
					String phoneNumber = rs.getString("UserPhoneNumber");
					String address = rs.getString("UserAddress");
					String role = rs.getString("UserRole");
					users.add(new User(userID, username, userEmail, password, gender, dob, phoneNumber, address, role) );
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
			return users;
		}
		
		
		public FormRegistrasi() {
			
			setLayout(new BorderLayout());
			
			JPanel pnlForm = new JPanel();
			pnlForm.setSize(500,500);
		    pnlForm.setLayout(new GridLayout(9,2));
			
			lblCakeLand = new JLabel("CakeLAnd");
			lblCakeLand.setFont(new Font("Arial", Font.BOLD, 24));
			lblCakeLand.setSize(300, 30);
	        lblCakeLand.setLocation(190, 20);
	        lblCakeLand.setHorizontalAlignment(JLabel.CENTER);
	        add(lblCakeLand, BorderLayout.PAGE_START);
	        
	        lblCreateAccount = new JLabel("Create your own account!");
	        lblCreateAccount.setFont(new Font("Arial", Font.BOLD, 18));
			lblCreateAccount.setSize(300, 30);
	        lblCreateAccount.setLocation(140, 100);
	       	lblCreateAccount.setHorizontalAlignment(JLabel.CENTER);
	       	add(lblCreateAccount);
	        
	        lblUsername = new JLabel("Username");
	        lblUsername.setSize(170, 30);
	        lblUsername.setLocation(35,155);
	        txtUsername = new JTextField();
	        txtUsername.setSize(180, 30);
	        txtUsername.setLocation(270, 155);
	        pnlForm.add(lblUsername);
	        pnlForm.add(txtUsername);
	        
	        
	        lblEmail = new JLabel("Email");
	        lblEmail.setSize(170,30);
	        lblEmail.setLocation(35,190);
	        txtEmail = new JTextField();
	        txtEmail.setSize(180, 30);
	        txtEmail.setLocation(270,190);
	        pnlForm.add(lblEmail);
	        pnlForm.add(txtEmail);
	        
	      
	        
	        lblGender = new JLabel("Gender");
	        lblGender.setSize(190,30);
			lblGender.setLocation(35,225);
			pnlForm.add(lblGender);
			
			pnlGender = new JPanel();
			pnlGender.setLayout(new GridLayout(1,2));
			pnlGender.add(rbMale = new JRadioButton("Male"));
			pnlGender.add(rbFemale = new JRadioButton("Female"));
			grpGender = new ButtonGroup();
			grpGender.add(rbFemale);
			grpGender.add(rbMale);
			pnlGender.setSize(170,30);
			pnlGender.setLocation(265, 225);
			pnlForm.add(pnlGender);


			lblDOB = new JLabel("Date of Birth");
			lblDOB.setSize(190,30);
			lblDOB.setLocation(35,260);			
			pnlForm.add(lblDOB);
			
			pnlDOB = new JPanel();
			pnlDOB.setSize(180,30);
			strip1 = new JLabel("-");
			strip1.setSize(50,50);
			strip1.setLocation(323,247);
			strip2 = new JLabel("-");
			strip2.setSize(50,50);
			strip2.setLocation(389,247);
			pnlDOB.setLayout(new GridLayout(1,5,20,30));
			pnlDOB.add(txtDate = new JTextField("date"));
			pnlDOB.add(strip1);
			pnlDOB.add(txtMonth = new JTextField("month"));
			pnlDOB.add(strip2);
			pnlDOB.add(txtYear = new JTextField("year"));
			pnlDOB.setLocation(270,260);
			pnlForm.add(pnlDOB);
			
			lblPhoneNumber = new JLabel("Phone Number");
			lblPhoneNumber.setSize(170,30);
			lblPhoneNumber.setLocation(35,295);
			pnlForm.add(lblPhoneNumber);
			
			txtPhoneNumber = new JTextField();
			txtPhoneNumber.setSize(180, 30);
			txtPhoneNumber.setLocation(270,295);
			pnlForm.add(txtPhoneNumber);
			
			lblAddress = new JLabel("Address");
			lblAddress.setSize(170,30);
			lblAddress.setLocation(35, 355);
			pnlForm.add(lblAddress);
			
			txtAddress = new JTextArea();
			txtAddress.setSize(180,80);
			txtAddress.setLocation(270,330);
			pnlForm.add(txtAddress);
			
			lblPassword = new JLabel("Password");
			lblPassword.setSize(170,30);
			lblPassword.setLocation(35, 417);
			pnlForm.add(lblPassword);
			
			txtPassword = new JPasswordField();
			txtPassword.setSize(180,30 );
			txtPassword.setLocation(270,417);
			pnlForm.add(txtPassword);
			
			lblConfirmPassword = new JLabel("Confirm Password");
			lblConfirmPassword.setSize(180,30);
			lblConfirmPassword.setLocation(35, 452);
			pnlForm.add(lblConfirmPassword);
			
			txtConfirmPassword = new JPasswordField();
			txtConfirmPassword.setSize(180,30);
			txtConfirmPassword.setLocation(270, 452);
			pnlForm.add(txtConfirmPassword);
			
			cbTerms = new  JCheckBox("Agree to Terms and Conditions");
			cbTerms.setSize(250,30);
			cbTerms.setLocation(155, 485);
			cbTerms.setHorizontalAlignment(JLabel.CENTER);
			add(cbTerms);
			
			add(pnlForm);
			
			JPanel pnl1 = new JPanel();
			pnl1.setLayout(new BorderLayout());
			pnl1.add(lblCreateAccount, BorderLayout.NORTH);
			pnl1.add(pnlForm, BorderLayout.CENTER);
			pnl1.add(cbTerms, BorderLayout.SOUTH);
			
			pnl1.setSize(400,400);
			add(pnl1);
			
			btnRegister = new JButton("Register");
			btnRegister.setFont(new Font("Arial", Font.BOLD, 14));
			btnRegister.setLocation(185,530);
			btnRegister.setPreferredSize(new Dimension(130,50));
			btnRegister.addMouseListener(FormRegistrasi.this);
			add(btnRegister);
			
			lblLogin = new JLabel("Already have an account? Login");
			lblLogin.setFont(new Font("Arial", Font.ITALIC, 11));
			lblLogin.setSize(200,50);
			lblLogin.setLocation(280,600);
			lblLogin.addMouseListener(FormRegistrasi.this);
			lblLogin.setHorizontalAlignment(JLabel.RIGHT);
			add(lblLogin);
			
			JPanel pnl2 = new JPanel();
			pnl2.setSize(400,400);
			pnl2.setLayout(new BorderLayout());
			pnl2.add(lblCakeLand, BorderLayout.NORTH);
			pnl2.add(pnl1, BorderLayout.CENTER);
			pnl2.add(btnRegister, BorderLayout.SOUTH);
			add(pnl2);
			
			add(pnl2, BorderLayout.CENTER);
			add(lblLogin, BorderLayout.SOUTH);
			
			
			setSize(500,700);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
			setVisible(true);
			setResizable(false);
		}

		
		public static void main (String []args) {
			new FormRegistrasi();
		}

		
		void register() {
			String username = txtUsername.getText().toString();
			int phonenumber = -1;
			String address = txtAddress.getText().toString();
			String password = txtPassword.getText().toString();
			String confirmpassword = txtConfirmPassword.getText().toString();
			String dob = txtYear.getText().toString() + "-" +txtMonth.getText().toString() + "-" + txtDate.getText().toString();
			String gender = "";
			
			if(rbMale.isSelected()) {
				gender = rbMale.getText();
			}
			
			if(rbFemale.isSelected()) {
				gender = rbFemale.getText();
			}
			
			
			if (username.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Username cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(!rbMale.isSelected() && !rbFemale.isSelected()) {
				JOptionPane.showMessageDialog(this, "Please Select Gender", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (username.length() > 15 || username.length() < 5) {
				JOptionPane.showMessageDialog(this, "Username length must be 5-15", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (txtDate.getText().toString().isEmpty()) {
				JOptionPane.showMessageDialog(this, "DOB cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (txtMonth.getText().toString().isEmpty()) {
				JOptionPane.showMessageDialog(this, "DOB cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (txtYear.getText().toString().isEmpty()) {
				JOptionPane.showMessageDialog(this, "DOB cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				phonenumber = Integer.parseInt(txtPhoneNumber.getText().toString());
			} catch (Exception e) {
				
			} 
			
			if (txtPhoneNumber.getText().toString().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Phone Number cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (phonenumber == -1) {
				JOptionPane.showMessageDialog(this, "Phone Number must be an integer", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (txtAddress.getText().toString().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Address cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (!address.endsWith(" Street")) {
				JOptionPane.showMessageDialog(this, "Address must ends with Street", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (!confirmpassword.equals(password)) {
				JOptionPane.showMessageDialog(this, "Password doesn't match", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
			if (!cbTerms.isSelected()){
				JOptionPane.showMessageDialog(this, "You must aggree with the term and condition", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			generateUserID();
			insertUser(new User(userIDInput, username, txtEmail.getText().toString(), password, gender,  dob, txtPhoneNumber.getText().toString(), txtAddress.getText().toString(), "User"));
			
			JOptionPane.showMessageDialog(this, "Register Success");
			this.dispose();
			Main main = new Main();
			
		}
		
		
		void generateUserID() {
			Random rn = new Random();
			int a = rn.nextInt(9) + 1;
			int b = rn.nextInt(9) + 1;
			int c = rn.nextInt(9) + 1;
			int d = rn.nextInt(9) + 1;
			
			userIDInput = "U" + a + b + c + d;
		}
		



		@Override
		public void mouseClicked(MouseEvent e) {
			
			
			if (e.getSource() == this.lblLogin) {
				this.dispose();
				Main main = new Main();
			}
			
			if (e.getSource() == this.btnRegister) {
				register();
				
			}
			
			
		}


		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	
		
		
	
}
