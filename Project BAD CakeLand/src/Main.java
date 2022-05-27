
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Main extends JFrame implements MouseListener ,ActionListener{

	Vector<TransactionHeader> th = new Vector<>();
	Vector<User> users = new Vector<>();
	Vector<TransactionDetail> td = new Vector<>();
	Vector<Cake> cake = new Vector<>();
	Vector<TransactionCakeDetail> tcd = new Vector<>();
	
	FormRegistrasi formregis;
	
	JFrame LoginFrame = new JFrame("Login");
	JFrame mainMenuAdmin = new JFrame("CakeLAnd");
	JFrame mainMenuUser = new JFrame("CakeLAnd");
	JFrame updateProfile = new JFrame("CakeLAnd");
	JFrame viewAllMenu = new JFrame("CakeLAnd");
	JFrame checkOut = new JFrame("CakeLAnd");
	JFrame TransactionHistory = new JFrame("CakeLAnd");
	JFrame manageMenuForm = new JFrame ("CakeLAnd");
	
	JPanel pTitle, pUsername, pPassword, pButton;
	JLabel lTitle, lUsername, lPassword, lRegister;
	JTextField tUsername;
	JPasswordField tPassword;
	JButton btnLogin;

	JMenuBar menubar = new JMenuBar();
	JMenu manageAccount = new JMenu("Manage Account"), cakeMenu = new JMenu("Cake Menu");
	JMenuItem profile, logout, manageMenu;
	JLabel hello, welcome;
	
	JMenuBar menubarUser = new JMenuBar();
	JMenu manageAccountUser = new JMenu("Manage Account"), transactionUser = new JMenu("Transaction");
	JMenuItem profileUser, logoutUser, viewAllMenuUser, manageCartUser, viewTransactionHistoryUser;
	JLabel helloUser, welcomeUser;
	
	JPanel pnlRegisterButton, pnlGender, pnlDOB;
	JRadioButton rbMale, rbFemale;
	JButton btnSave;
	JTextField txtUsername, txtEmail, txtDate, txtMonth, txtYear, txtPhoneNumber; 
	JPasswordField txtPassword, txtConfirmPassword, txtOldPassword;
	JLabel lblCakeLandUpdate, lblChangeProfile, lblUsernameUpdate, lblEmail, lblGender, lblDOB, lblPhoneNumber, lblAddress, lblPasswordUpdate, lblConfirmPassword, lblOldPassword;
	JTextArea txtAddress;
	ButtonGroup grpGender;
	JLabel strip1, strip2;
	
	Vector<Cart> cart = new Vector<>();
	JLabel quantity, lblcakeLand, lblcakeList;
	JPanel pnlButtonViewMenu ,pnlQuantity, pnlTableMenu;
	JSpinner spnQuantity;
	SpinnerModel sm = new SpinnerNumberModel(0, 0, 100, 1);
	JTable table;
	JScrollPane scrollPane;
	DefaultTableModel dtm;
	JButton backToMenu, addToCart, viewCart;
	
	JLabel quantityCheckOut,lblcakeLandCheckOut, lblyourCart;
	JPanel pnlButtonCheckOut ,pnlQuantityCheckOut, pnlTableCheckout;
	JSpinner spnQuantityCheckOut;
	JTable tableCheckOut;
	JScrollPane scrollPaneCheckOut;
	DefaultTableModel dtmCheckOut;
	JButton backToMenuCheckout, btnRemoveFromCart, btnUpdateCake, btnCheckOut, btnViewAllMenu;
	String TransactionIDInput;
	
	JFrame ConfirmCheckoutPage;
	JLabel lblTitleConfirmCheckout, lblOrderConfirmCheckout, lblTotalConfirmCheckout, lblDateConfirmCheckout;
	JPanel pnlTableConfirmCheckout;
	JTextField txtTotalConfirmCheckout, txtDateConfirmCheckout;
	JButton btnCheckoutConfirmCheckout, btnBackConfirmCheckout;
	int totalPrice;
	
	
	JLabel lblCakeTH, lblTH, lblSelectedID, lblTotalTH;
	JTextField txtSelectedID, txtTotalTH;
	JTable tableHeaderTH, tableDetailTH;
	JButton backToMenuTH;
	JScrollPane scrollPaneHeader, scrollPaneDetail ;
	JPanel pnlTableHeaderTH, pnlTableDetailTH;
	int totalPriceHistory;
	String userID;
	DefaultTableModel dtmHeader;
	
	
	JPanel pnlTableUpdateCake;
	JLabel lblTitleMM, lblListMM, lblCakeNameMM, lblCakePriceMM, lblShapeMM, lblOvalSizeMM, lblRectangleSizeMM;
	JTextField txtCakeNameMM, txtCakePriceMM; 
	JButton btnBackMM, btnRemoveMM, btnAddMM;
	JTable tableUpdateCake;
	JComboBox rectanglesize, ovalsize, shapes;
	String cakeIDInput,selectedCakeID, cakeSizeInput, cakeShapeInput;
	
	
	public User getOneUser(String username, String password) {
		User user = null;
		ResultSet rs = Connect.getConnection().executeQuery(
				
						"SELECT * FROM user WHERE Username= '" + username + "' AND UserPassword = '" + password + "' "
				);
			try {
				while (rs.next()) {
					user = new User(rs.getString("UserID"), rs.getString("Username"), rs.getString("UserEmail"), rs.getString("UserPassword"), rs.getString("UserGender"), rs.getString("UserDOB"), rs.getString("UserPhoneNumber"), rs.getString("UserAddress"), rs.getString("UserRole"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			users.add(user);
			return user;
	}
	
	public Cake getOneCake(String cakeID, String cakeName, String cakePrice, String cakeSize, String cakeShape) {
		Cake cake = null;
		ResultSet rs = Connect.getConnection().executeQuery(
				
						"SELECT * FROM cake WHERE CakeName= '" + cakeName + "'"
				);
			try {
				while (rs.next()) {
					cake = new Cake(rs.getString("CakeID"), rs.getString("CakeName"), rs.getString("CakePrice"), rs.getString("CakeSize"), rs.getString("CakeShape"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			return cake;
	}
	
	public boolean insertTransactionDetail(TransactionDetail td) {
		PreparedStatement ps = Connect.getConnection().prepareStatement(
					"INSERT INTO transactiondetail(TransactionID, CakeID, Quantity) VALUES(?,?,?)"
				
				);
		try {
			ps.setString(1, td.getTransactionID());
			ps.setString(2, td.getCakeID());
			ps.setString(3, td.getQuantity());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	
	public boolean insertTransaction(TransactionHeader th) {
		PreparedStatement ps = Connect.getConnection().prepareStatement(
					"INSERT INTO transactionheader(TransactionID, UserID, TransactionDate, PickUpDate) VALUES(?,?,?,?)"
				
				);
		try {
			ps.setString(1, th.getTransactionID());
			ps.setString(2, th.getUserID());
			ps.setString(3, th.getTransactionDate());
			ps.setString(4, th.getPickUpDate());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	
	
	public Vector<Cake> getAllCake(){
		
		
		PreparedStatement ps = Connect.getConnection().prepareStatement(
				"SELECT * FROM cake"
			
			);
		try {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String cakeID = rs.getString("CakeID");
				String cakeName = rs.getString("CakeName");
				String cakePrice = rs.getString("CakePrice");
				String cakeSize = rs.getString("CakeSize");
				String cakeShape = rs.getString("CakeShape");
				cake.add(new Cake(cakeID, cakeName, cakePrice, cakeSize, cakeShape));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return cake;
	}
	
	public Vector<TransactionHeader> getAllTransactionHeader(){
		PreparedStatement ps = Connect.getConnection().prepareStatement(
				"SELECT * FROM transactionheader WHERE UserID = '" + userID +"'"
			
			);
		try {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String transactionID = rs.getString("TransactionID");
				String userID = rs.getString("UserID");
				String transactionDate = rs.getString("TransactionDate");
				String pickUpDate = rs.getString("PickUpDate");
				th.add(new TransactionHeader(transactionID, userID, transactionDate, pickUpDate));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return th;
	}
	
	public Vector<TransactionCakeDetail> getAllTransactionDetail(String TransactionID){
		
		
		PreparedStatement ps = Connect.getConnection().prepareStatement(
				"SELECT CakeName, CakeSize, CakeShape, CakePrice, Quantity "
				+ "FROM `transactionheader` th "
				+ "INNER JOIN transactiondetail td ON th.TransactionID = td.TransactionID "
				+ "INNER JOIN cake c ON td.CakeID = c.CakeID "
				+ "WHERE td.TransactionID = '" + TransactionID + "'"
			
			);
		try {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String cakeName = rs.getString("CakeName");
				String cakeSize = rs.getString("CakeSize");
				String cakeShape = rs.getString("CakeShape");
				String cakePrice = rs.getString("CakePrice");
				String quantity = rs.getString("Quantity");
				int subTotal = Integer.parseInt(cakePrice) * Integer.parseInt(quantity);
				tcd.add(new TransactionCakeDetail(cakeName, cakeSize, cakeShape, cakePrice, quantity, subTotal));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return tcd;
	}
	
	public boolean deleteCake() {
		int index = tableUpdateCake.getSelectedRow();
		TableModel model = tableUpdateCake.getModel();
		
		PreparedStatement ps = Connect.getConnection().prepareStatement(
					"DELETE FROM cake WHERE cake.CakeID = '" + (model.getValueAt(index, 0).toString()) + "'"
				
				);
		try {		
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean updateProfile() {
		
		String username = txtUsername.getText().toString();
		String email = txtEmail.getText().toString();
		String password = txtPassword.getText().toString();
		String gender = "";
		String dob = txtYear.getText().toString() + "-" +txtMonth.getText().toString() + "-" + txtDate.getText().toString();
		String phone = txtPhoneNumber.getText().toString();
		String address = txtAddress.getText().toString();
		
		if(rbMale.isSelected()) {
			gender = rbMale.getText();
		}
		
		if(rbFemale.isSelected()) {
			gender = rbFemale.getText();
		} 
		
		PreparedStatement ps = Connect.getConnection().prepareStatement(
				"UPDATE user SET Username = '" + username + "', UserEmail = '" + email + "', UserPassword ='" + password + "', UserGender = '" + gender + "', UserDOB= '" + dob + "', UserPhoneNumber ='" + phone + "', UserAddress = '" + address + "'  WHERE UserID = '" + userID +"'"
				
				);
		try {		
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	public boolean insertNewCake(Cake cake) {
		PreparedStatement ps = Connect.getConnection().prepareStatement(
					"INSERT INTO cake(CakeID, CakeName, CakePrice, CakeSize, CakeShape) VALUES(?,?,?,?,?)"
				
				);
		try {
			ps.setString(1, cake.getCakeID());
			ps.setString(2, cake.getCakeName());
			ps.setString(3, cake.getCakePrice());
			ps.setString(4, cake.getCakeSize());
			ps.setString(5, cake.getCakeShape());			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	
	void setupTable() {
		Vector<Object> headerTable, dataTable;
		Vector<Cake>  viewCake = getAllCake();

		headerTable = new Vector<>();
		headerTable.add("Cake Name");
		headerTable.add("Cake Price");
		headerTable.add("Cake Shape");
		headerTable.add("Cake Size");
		
		dtm = new DefaultTableModel(headerTable, 0);
		
		
		for (Cake cake : viewCake) {
			dataTable = new Vector<>();
			dataTable.add(cake.getCakeName());
			dataTable.add(cake.getCakePrice());
			dataTable.add(cake.getCakeShape());
			dataTable.add(cake.getCakeSize());
			dtm.addRow(dataTable);
		}
		
		table = new JTable(dtm);
		table.setSize(250,250);
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
		table.setFont(new Font("Arial", Font.PLAIN, 16));
		table.setRowHeight(35);
		scrollPane = new JScrollPane(table);
		scrollPane.setSize(575,300);
		scrollPane.setLocation(75, 300);
		pnlTableMenu.setSize(575,300);
		pnlTableMenu.setLocation(75, 300);
		pnlTableMenu.setLayout(new GridLayout(1,1));
		pnlTableMenu.add(scrollPane);
	}
	
	void setupTableCheckOut() {
		Vector<Object> headerTable, dataTable;
		

		headerTable = new Vector<>();
		headerTable.add("Cake Name");
		headerTable.add("Cake Price");
		headerTable.add("Cake Shape");
		headerTable.add("Cake Size");
		headerTable.add("Quantity");
		
		dtmCheckOut = new DefaultTableModel(headerTable, 0);
		
		
		for (int i = 0 ; i < cart.size() ; i++) {
			dataTable = new Vector<>();
			dataTable.add(cart.get(i).getCakeName());
			dataTable.add(cart.get(i).getCakePrice());
			dataTable.add(cart.get(i).getCakeShape());
			dataTable.add(cart.get(i).getCakeSize());
			dataTable.add(cart.get(i).getCakeQuantity());
			dtmCheckOut.addRow(dataTable);
		}
		
		tableCheckOut = new JTable(dtmCheckOut);
		tableCheckOut.setSize(250,250);
		tableCheckOut.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
		tableCheckOut.setFont(new Font("Arial", Font.PLAIN, 16));
		tableCheckOut.setRowHeight(35);
		scrollPaneCheckOut = new JScrollPane(tableCheckOut);
		scrollPaneCheckOut.setSize(250,250);
		pnlTableCheckout.setSize(575,300);
		pnlTableCheckout.setLocation(75, 300);
		pnlTableCheckout.setLayout(new GridLayout(1,1));
		pnlTableCheckout.add(scrollPaneCheckOut);
	}
	
	void setupTableConfimCheckout() {
		Vector<Object> headerTable, dataTable;
		

		headerTable = new Vector<>();
		headerTable.add("Cake Name");
		headerTable.add("Cake Price");
		headerTable.add("Cake Shape");
		headerTable.add("Cake Size");
		headerTable.add("Quantity");
		
		dtmCheckOut = new DefaultTableModel(headerTable, 0);
		
		
		for (int i = 0 ; i < cart.size() ; i++) {
			dataTable = new Vector<>();
			dataTable.add(cart.get(i).getCakeName());
			dataTable.add(cart.get(i).getCakePrice());
			dataTable.add(cart.get(i).getCakeShape());
			dataTable.add(cart.get(i).getCakeSize());
			dataTable.add(cart.get(i).getCakeQuantity());
			dtmCheckOut.addRow(dataTable);
		}
		
		tableCheckOut = new JTable(dtmCheckOut);
		tableCheckOut.setSize(250,250);
		tableCheckOut.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
		tableCheckOut.setFont(new Font("Arial", Font.PLAIN, 16));
		tableCheckOut.setRowHeight(35);
		scrollPaneCheckOut = new JScrollPane(tableCheckOut);
		scrollPaneCheckOut.setSize(250,250);
		pnlTableConfirmCheckout.setSize(575,300);
		pnlTableConfirmCheckout.setLocation(75, 200);
		pnlTableConfirmCheckout.setLayout(new GridLayout(1,1));
		pnlTableConfirmCheckout.add(scrollPaneCheckOut);
	}
	
	void setupTableDetail() {
		int index = tableHeaderTH.getSelectedRow();
		TableModel model = tableHeaderTH.getModel();

		Vector<Object> headerTable, dataTable;

		headerTable = new Vector<>();
		headerTable.add("Cake Name");
		headerTable.add("Cake Size");
		headerTable.add("Cake Shape");
		headerTable.add("Cake Price");
		headerTable.add("Quantity");
		headerTable.add("Subtotal");
		
		dtm = new DefaultTableModel(headerTable, 0);
		
		if (index != -1) {
			
			String TransactionID = model.getValueAt(index, 0).toString();
			Vector<TransactionCakeDetail>  viewTransactionDetail = getAllTransactionDetail(TransactionID);
			
			for (TransactionCakeDetail tcd : viewTransactionDetail) {
			dataTable = new Vector<>();
			dataTable.add(tcd.getCakeName());
			dataTable.add(tcd.getCakeSize());
			dataTable.add(tcd.getCakeShape());
			dataTable.add(tcd.getCakePrice());
			dataTable.add(tcd.getQuantity());				
			dataTable.add(tcd.getSubTotal());				
			dtm.addRow(dataTable);
		}
			
			
		}
		tableDetailTH = new JTable(dtm);
		tableDetailTH.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
		tableDetailTH.setFont(new Font("Arial", Font.PLAIN, 16));
		tableDetailTH.setRowHeight(35);
		scrollPaneDetail = new JScrollPane(tableDetailTH);
		
		pnlTableDetailTH.setSize(680,200);
		pnlTableDetailTH.setLocation(75, 500);
		pnlTableDetailTH.setLayout(new GridLayout(1,1));
		pnlTableDetailTH.add(scrollPaneDetail);
	}
	

	void setupTableTransactionHeader() {
		Vector<Object> headerTable, dataTable = null;
		

		headerTable = new Vector<>();
		headerTable.add("Transaction ID");
		headerTable.add("Transaction Date");
		headerTable.add("Pick UP Date");
		
		dtmHeader = new DefaultTableModel(headerTable, 0);

		th.removeAllElements();
			Vector<TransactionHeader>  viewTransactionHeader = getAllTransactionHeader();
			for (TransactionHeader th : viewTransactionHeader) {
				dataTable = new Vector<>();
				dataTable.add(th.getTransactionID());
				dataTable.add(th.getTransactionDate());
				dataTable.add(th.getPickUpDate());
				dtmHeader.addRow(dataTable);
			}
		
		
	
		tableHeaderTH = new JTable(dtmHeader);
		tableHeaderTH.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
		tableHeaderTH.setFont(new Font("Arial", Font.PLAIN, 16));
		tableHeaderTH.setRowHeight(35);
		scrollPaneHeader = new JScrollPane(tableHeaderTH);
		pnlTableHeaderTH.setSize(680,200);
		pnlTableHeaderTH.setLocation(75, 225);
		pnlTableHeaderTH.setLayout(new GridLayout(1,1));
		pnlTableHeaderTH.add(scrollPaneHeader);
	}
	
	void setupTableUpdateCake() {
		
		cake.removeAllElements();
		
		Vector<Object> headerTable, dataTable;
		Vector<Cake>  viewCake = getAllCake();

		headerTable = new Vector<>();
		headerTable.add("Cake ID");
		headerTable.add("Cake Name");
		headerTable.add("Cake Price");
		headerTable.add("Cake Shape");
		headerTable.add("Cake Size");
		
		dtm = new DefaultTableModel(headerTable, 0);
		
		
		for (Cake cake : viewCake) {
			dataTable = new Vector<>();
			dataTable.add(cake.getCakeID());
			dataTable.add(cake.getCakeName());
			dataTable.add(cake.getCakePrice());
			dataTable.add(cake.getCakeShape());
			dataTable.add(cake.getCakeSize());
			dtm.addRow(dataTable);
		}
		
		tableUpdateCake = new JTable(dtm);
		tableUpdateCake.setSize(250,250);
		tableUpdateCake.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
		tableUpdateCake.setFont(new Font("Arial", Font.PLAIN, 16));
		tableUpdateCake.setRowHeight(35);
		scrollPane = new JScrollPane(tableUpdateCake);
		scrollPane.setSize(575,300);
		scrollPane.setLocation(75, 300);
		pnlTableUpdateCake.setSize(548,280);
		pnlTableUpdateCake.setLocation(70, 160);
		pnlTableUpdateCake.setLayout(new GridLayout(1,1));
		pnlTableUpdateCake.add(scrollPane);
	}
	
	
	void login() {
	
		//Frame Login
		LoginFrame = new JFrame("Login");
		pTitle = new JPanel();
		pUsername = new JPanel();
		pPassword = new JPanel();
		pButton = new JPanel();
		
		LoginFrame.setLayout(new GridLayout(7,1,5,5));
		
		lTitle = new JLabel("CakeLAnd");
		lTitle.setFont(new Font("Arial", Font.BOLD, 24));
		lTitle.setSize(300, 30);
		lTitle.setLocation(130, 20);
		lTitle.setHorizontalAlignment(JLabel.CENTER);
		LoginFrame.add(lTitle);
		
		lUsername = new JLabel("Username");
		lUsername.setSize (170, 30);
		lUsername.setLocation(155, 90);
		lUsername.setHorizontalAlignment(JLabel.CENTER);
		tUsername = new JTextField();
		tUsername.setSize(220, 30);
		tUsername.setLocation(80, 125);
		LoginFrame.add(lUsername);
		LoginFrame.add(tUsername);
		
		lPassword = new JLabel("Password");
		lPassword.setSize(170, 150);
		lPassword.setLocation(155, 100);
		lPassword.setHorizontalAlignment(JLabel.CENTER);
		tPassword = new JPasswordField();
		tPassword.setSize(220, 30);
		tPassword.setLocation(80, 195);
		LoginFrame.add(lPassword);
		LoginFrame.add(tPassword);
		
		btnLogin = new JButton("Login");
		btnLogin.setSize(75,35);
		btnLogin.setLocation(150, 255);
		btnLogin.addMouseListener(this);
		LoginFrame.add(btnLogin);
		
		
		lRegister = new JLabel("Don't have account? Register");
		lRegister.setFont(new Font("Arial", Font.ITALIC, 13));
		lRegister.addMouseListener(this);
		lRegister.setSize(170, 150);
		lRegister.setLocation(185, 330);
		lRegister.setHorizontalAlignment(JLabel.RIGHT);
		LoginFrame.add(lRegister);
		
		LoginFrame.setSize(400, 500);
		LoginFrame.setLocationRelativeTo(null);
		LoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LoginFrame.setVisible(true);
		LoginFrame.setResizable(false);
		
		
		//Frame MainMenuAdmin
		mainMenuAdmin.setLayout(new BorderLayout());
		
		profile = new JMenuItem("Profile");
		logout = new JMenuItem("Logout");
		manageMenu = new JMenuItem("Manage Menu");
		
		profile.addActionListener(this);
		logout.addActionListener(this);
		manageMenu.addActionListener(this);
		
		manageAccount.add(profile);
		manageAccount.add(logout);
		
		cakeMenu.add(manageMenu);
		
		menubar.add(manageAccount);
		menubar.add(cakeMenu);
		
		mainMenuAdmin.setJMenuBar(menubar);
		
		welcome = new JLabel("Welcome to CakeLAnd");
		welcome.setFont(new Font("Arial", Font.BOLD, 72));
		welcome.setSize(2000,200);
		welcome.setLocation(100, 200);
		welcome.setHorizontalAlignment(JLabel.CENTER);
		mainMenuAdmin.add(welcome);
		
		mainMenuAdmin.setSize(1000,700);
		mainMenuAdmin.setLocationRelativeTo(null);
		mainMenuAdmin.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		//Frame MainMenuUser
		mainMenuUser.setLayout(new BorderLayout());
		
		profileUser = new JMenuItem("Profile");
		logoutUser = new JMenuItem("Logout");
		viewAllMenuUser = new JMenuItem("View All Menu");
		manageCartUser = new JMenuItem("Manage Cart");
		viewTransactionHistoryUser = new JMenuItem("View Transaction History");
		
		manageAccountUser.add(profileUser);
		manageAccountUser.add(logoutUser);
		
		profileUser.addActionListener(this);
		logoutUser.addActionListener(this);
		viewAllMenuUser.addActionListener(this);
		manageCartUser.addActionListener(this);
		viewTransactionHistoryUser.addActionListener(this);
		
		transactionUser.add(viewAllMenuUser);
		transactionUser.add(manageCartUser);
		transactionUser.add(viewTransactionHistoryUser);
		
		menubarUser.add(manageAccountUser);
		menubarUser.add(transactionUser);
		
		mainMenuUser.setJMenuBar(menubarUser);
		
		helloUser = new JLabel("Hello hehehehe!");
		helloUser.setFont(new Font("Arial", Font.BOLD, 24));
		helloUser.setSize(200,100);
		helloUser.setLocation(100, 160);
		helloUser.setHorizontalAlignment(JLabel.CENTER);
		mainMenuUser.add(helloUser);
		
		welcomeUser = new JLabel("Welcome to CakeLAnd");
		welcomeUser.setFont(new Font("Arial", Font.BOLD, 72));
		welcomeUser.setSize(2000,200);
		welcomeUser.setLocation(100, 240);
		welcomeUser.setHorizontalAlignment(JLabel.CENTER);
		mainMenuUser.add(welcomeUser);
		
		mainMenuUser.setSize(1000,700);
		mainMenuUser.setLocationRelativeTo(null);
		mainMenuUser.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		//Frame Update Profile
		updateProfile.setLayout(new BorderLayout());
		
		lblCakeLandUpdate = new JLabel("CakeLAnd");
		lblCakeLandUpdate.setFont(new Font("Arial", Font.BOLD, 24));
		lblCakeLandUpdate.setSize(300, 30);
		lblCakeLandUpdate.setLocation(190, 20);
		lblCakeLandUpdate.setHorizontalAlignment(JLabel.CENTER);
		updateProfile.add(lblCakeLandUpdate, BorderLayout.PAGE_START);
        
        lblChangeProfile = new JLabel("Change Profile");
        lblChangeProfile.setFont(new Font("Arial", Font.BOLD, 18));
        lblChangeProfile.setSize(300, 30);
        lblChangeProfile.setLocation(185, 100);
        lblChangeProfile.setHorizontalAlignment(JLabel.CENTER);
        
        JPanel pnlForm = new JPanel();
        pnlForm.setLayout(new GridLayout(9,2));
        
        lblUsernameUpdate = new JLabel("Username");
        lblUsernameUpdate.setSize(170, 30);
        lblUsernameUpdate.setLocation(35,155);
        txtUsername = new JTextField();
        txtUsername.setSize(180, 30);
        txtUsername.setLocation(270, 155);
        pnlForm.add(lblUsernameUpdate);
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
		
		lblOldPassword = new JLabel("Old Password");
		lblOldPassword.setSize(170,30);
		lblOldPassword.setLocation(35, 417);
		pnlForm.add(lblOldPassword);
		
		txtOldPassword = new JPasswordField();
		txtOldPassword.setSize(180,30 );
		txtOldPassword.setLocation(270,417);
		pnlForm.add(txtOldPassword);
		
		lblPasswordUpdate = new JLabel("New Password");
		lblPasswordUpdate.setSize(170,30);
		lblPasswordUpdate.setLocation(35, 452);
		pnlForm.add(lblPasswordUpdate);
		
		txtPassword = new JPasswordField();
		txtPassword.setSize(180,30 );
		txtPassword.setLocation(270,452);
		pnlForm.add(txtPassword);
		
		lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setSize(180,30);
		lblConfirmPassword.setLocation(35, 487);
		pnlForm.add(lblConfirmPassword);
		
		txtConfirmPassword = new JPasswordField();
		txtConfirmPassword.setSize(180,30);
		txtConfirmPassword.setLocation(270, 487);
		pnlForm.add(txtConfirmPassword);
		
		JPanel pnlForm2 = new JPanel();
		pnlForm2.setLayout(new BorderLayout());
		
		pnlForm2.add(lblChangeProfile, BorderLayout.PAGE_START);
		pnlForm2.add(pnlForm, BorderLayout.CENTER);
		
		updateProfile.add(pnlForm2, BorderLayout.CENTER);
		
		btnSave = new JButton("Save Changes");
		btnSave.setFont(new Font("Arial", Font.BOLD, 14));
		btnSave.setLocation(175,550);
		btnSave.setSize(150,50);
		btnSave.addMouseListener(this);
		updateProfile.add(btnSave, BorderLayout. PAGE_END);
		
		updateProfile.setSize(500,700);
		updateProfile.setLocationRelativeTo(null);
		updateProfile.setResizable(false);
		
		//viewAllMenu
		viewAllMenu.setLayout(null);
		
		pnlTableMenu = new JPanel();
	
		setupTable();
		
		viewAllMenu.add(pnlTableMenu);
		
		backToMenu = new JButton("Back to Main Menu");
		backToMenu.setFont(new Font("Arial", Font.BOLD, 11));
		backToMenu.setLocation(750,35);
		backToMenu.setSize(150,40);
		backToMenu.addActionListener(this);
		viewAllMenu.add(backToMenu);
		
		pnlButtonViewMenu = new JPanel();
		
		lblcakeLand = new JLabel("CakeLAnd");
		lblcakeLand.setFont(new Font("Arial", Font.BOLD, 45));
		lblcakeLand.setSize(300, 30);
		lblcakeLand.setLocation(350, 150);
		viewAllMenu.add(lblcakeLand);
		
		lblcakeList = new JLabel("Cake List");
		lblcakeList.setFont(new Font("Arial", Font.BOLD, 32));
		lblcakeList.setSize(300, 30);
		lblcakeList.setLocation(385, 200);
		viewAllMenu.add(lblcakeList);
		
		addToCart = new JButton("Add to Cart");
		addToCart.setFont(new Font("Arial", Font.BOLD, 16));
		addToCart.addMouseListener(this);
		pnlButtonViewMenu.add(addToCart);
		
		viewCart = new JButton("View Cart");
		viewCart.setFont(new Font("Arial", Font.BOLD, 16));
		viewCart.addMouseListener(this);
		pnlButtonViewMenu.add(viewCart);
		
		pnlButtonViewMenu.setLayout(new GridLayout(2,1,10,10));
		pnlButtonViewMenu.setSize(250,125);
		pnlButtonViewMenu.setLocation(690,400);
		viewAllMenu.add(pnlButtonViewMenu);
		
		quantity = new JLabel("Quantity");
		quantity.setFont(new Font("Arial", Font.BOLD, 12));
		quantity.setSize(200,200);
		quantity.setLocation(720,237);
		viewAllMenu.add(quantity);
		
		spnQuantity = new JSpinner(sm);
		spnQuantity.setSize(100,25);
		spnQuantity.setLocation(800,325);
		viewAllMenu.add(spnQuantity);
		
		viewAllMenu.setSize(1000,700);
		viewAllMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		viewAllMenu.setLocationRelativeTo(null);
		viewAllMenu.setResizable(false);
		
		checkOut.setLayout(null);
		checkOut.setResizable(false);
		
		//Manage Cart Page
		pnlTableCheckout = new JPanel();
				
		checkOut.setLayout(null);
				
		setupTableCheckOut();
		checkOut.add(pnlTableCheckout);
				
		pnlButtonCheckOut = new JPanel();
				
		lblcakeLandCheckOut = new JLabel("CakeLAnd");
		lblcakeLandCheckOut.setFont(new Font("Arial", Font.BOLD, 45));
		lblcakeLandCheckOut.setSize(300, 30);
		lblcakeLandCheckOut.setLocation(350, 150);
		checkOut.add(lblcakeLandCheckOut);
				
		lblyourCart = new JLabel("Your Cart");
		lblyourCart.setFont(new Font("Arial", Font.BOLD, 32));
		lblyourCart.setSize(300, 30);
		lblyourCart.setLocation(385, 200);
		checkOut.add(lblyourCart);

		backToMenuCheckout = new JButton("Back to Main Menu");
		backToMenuCheckout.setFont(new Font("Arial", Font.BOLD, 11));
		backToMenuCheckout.setLocation(750,35);
		backToMenuCheckout.setSize(150,40);
		backToMenuCheckout.addActionListener(this);
		checkOut.add(backToMenuCheckout);		

		btnRemoveFromCart = new JButton("Remove From Cart");
		btnRemoveFromCart.setFont(new Font("Arial", Font.BOLD, 16));
		btnRemoveFromCart.addMouseListener(this);
		pnlButtonCheckOut.add(btnRemoveFromCart);

		btnUpdateCake = new JButton("Update Cake Order");
		btnUpdateCake.setFont(new Font("Arial", Font.BOLD, 16));
		btnUpdateCake.addMouseListener(this);
		pnlButtonCheckOut.add(btnUpdateCake);

		btnCheckOut = new JButton("Check Out");
		btnCheckOut.setFont(new Font("Arial", Font.BOLD, 16));
		btnCheckOut.addMouseListener(this);
		pnlButtonCheckOut.add(btnCheckOut);

		btnViewAllMenu = new JButton("View All Menu");
		btnViewAllMenu.setFont(new Font("Arial", Font.BOLD, 16));
		btnViewAllMenu.addMouseListener(this);
		pnlButtonCheckOut.add(btnViewAllMenu);

		pnlButtonCheckOut.setLayout(new GridLayout(4,1,10,10));
		pnlButtonCheckOut.setSize(250,200);
		pnlButtonCheckOut.setLocation(690,400);
		checkOut.add(pnlButtonCheckOut);

		quantityCheckOut = new JLabel("Quantity");
		quantityCheckOut.setFont(new Font("Arial", Font.BOLD, 12));
		quantityCheckOut.setSize(200,200);
		quantityCheckOut.setLocation(720,237);
		checkOut.add(quantityCheckOut);

		spnQuantityCheckOut = new JSpinner(sm);
		spnQuantityCheckOut.setSize(100,25);
		spnQuantityCheckOut.setLocation(800,325);
		checkOut.add(spnQuantityCheckOut);

		checkOut.setSize(1000,700);
		checkOut.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		checkOut.setLocationRelativeTo(null);		
		
		//CheckoutPage
		ConfirmCheckoutPage = new JFrame ("CakeLAnd");
		
		pnlTableConfirmCheckout = new JPanel();
		
		setupTableConfimCheckout();
		
		ConfirmCheckoutPage.add(pnlTableConfirmCheckout);
		
		lblTitleConfirmCheckout = new JLabel("CakeLAnd");
		lblTitleConfirmCheckout.setFont(new Font("Arial", Font.BOLD, 33));
		lblTitleConfirmCheckout.setSize(300, 30);
		lblTitleConfirmCheckout.setLocation(510, 40);
		ConfirmCheckoutPage.add(lblTitleConfirmCheckout);
		
		lblOrderConfirmCheckout = new JLabel("Your Order");
		lblOrderConfirmCheckout.setFont(new Font("Arial", Font.BOLD, 33));
		lblOrderConfirmCheckout.setSize(330, 35);
		lblOrderConfirmCheckout.setLocation(506, 95);
		ConfirmCheckoutPage.add(lblOrderConfirmCheckout);
		
		lblTotalConfirmCheckout = new JLabel("Total");
		lblTotalConfirmCheckout.setFont(new Font("Arial", Font.BOLD, 16));
		lblTotalConfirmCheckout.setSize(170, 50);
		lblTotalConfirmCheckout.setLocation(750, 190);
		txtTotalConfirmCheckout = new JTextField();
		txtTotalConfirmCheckout.setEditable(false);
		txtTotalConfirmCheckout.setSize(170, 30);
		txtTotalConfirmCheckout.setLocation(870, 200);
		ConfirmCheckoutPage.add(lblTotalConfirmCheckout);
		ConfirmCheckoutPage.add(txtTotalConfirmCheckout);
		
		lblDateConfirmCheckout = new JLabel("Pick Up Date");
		lblDateConfirmCheckout.setFont(new Font("Arial", Font.BOLD, 16));
		lblDateConfirmCheckout.setSize(170, 50);
		lblDateConfirmCheckout.setLocation(750, 230);
		txtDateConfirmCheckout = new JTextField();
		txtDateConfirmCheckout.setSize(170, 30);
		txtDateConfirmCheckout.setLocation(870,240);
		ConfirmCheckoutPage.add(lblDateConfirmCheckout);
		ConfirmCheckoutPage.add(txtDateConfirmCheckout);
		
		btnCheckoutConfirmCheckout = new JButton("Check Out");
		btnCheckoutConfirmCheckout.setSize(100, 45);
		btnCheckoutConfirmCheckout.setLocation(850, 320);
		btnCheckoutConfirmCheckout.addActionListener(this);
		btnBackConfirmCheckout = new JButton("Back to Main Menu");
		btnBackConfirmCheckout.setSize(150, 46);
		btnBackConfirmCheckout.setLocation(825, 380);
		btnBackConfirmCheckout.addActionListener(this);
		ConfirmCheckoutPage.add(btnCheckoutConfirmCheckout);
		ConfirmCheckoutPage.add(btnBackConfirmCheckout);
	
		ConfirmCheckoutPage.setLayout(null);
		ConfirmCheckoutPage.setSize(1200, 650);
		ConfirmCheckoutPage.setLocationRelativeTo(null);
		ConfirmCheckoutPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//TransactionHistory
		pnlTableDetailTH = new JPanel();
		pnlTableHeaderTH = new JPanel();

		TransactionHistory.setLayout(null);

		
		
		lblSelectedID = new JLabel("Selected ID");
		lblSelectedID.setFont(new Font("Arial", Font.BOLD, 14));
		lblSelectedID.setLocation(75, 415);
		lblSelectedID.setSize(100,100);
		TransactionHistory.add(lblSelectedID);
		
		txtSelectedID = new JTextField();
		txtSelectedID.setLocation(175, 450);
		txtSelectedID.setSize(100,30);
		txtSelectedID.setEditable(false);
		TransactionHistory.add(txtSelectedID);
		
		
		lblTotalTH = new JLabel("Total");
		lblTotalTH.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalTH.setLocation(75, 680);
		lblTotalTH.setSize(100,100);
		TransactionHistory.add(lblTotalTH);
		
		txtTotalTH = new JTextField();
		txtTotalTH.setLocation(125, 715);
		txtTotalTH.setSize(145,30);
		txtTotalTH.setEditable(false);
		TransactionHistory.add(txtTotalTH);
		
		lblCakeTH = new JLabel("CakeLAnd");
		lblCakeTH.setFont(new Font("Arial", Font.BOLD, 33));
		lblCakeTH.setSize(300, 30);
		lblCakeTH.setLocation(340, 60);
		TransactionHistory.add(lblCakeTH);
		
		lblTH = new JLabel("Transaction History");
		lblTH.setFont(new Font("Arial", Font.BOLD, 33));
		lblTH.setSize(330, 35);
		lblTH.setLocation(265, 115);
		TransactionHistory.add(lblTH);
		
		backToMenuTH = new JButton("Back to Main Menu");
		backToMenuTH.setFont(new Font("Arial", Font.BOLD, 11));
		backToMenuTH.setLocation(600,760);
		backToMenuTH.setSize(150,40);
		backToMenuTH.addActionListener(this);
		TransactionHistory.add(backToMenuTH);		
		
		TransactionHistory.setSize(850,900);
		TransactionHistory.setLocationRelativeTo(null);
		TransactionHistory.setResizable(false);
		TransactionHistory.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//ManageMenuForm
		
		
		pnlTableUpdateCake = new JPanel();
		
		setupTableUpdateCake();
		manageMenuForm.add(pnlTableUpdateCake);
		
		lblTitleMM = new JLabel("CakeLAnd");
		lblTitleMM.setFont(new Font("Arial", Font.BOLD, 33));
		lblTitleMM.setSize(250, 30);
		lblTitleMM.setLocation(275, 50);
		manageMenuForm.add(lblTitleMM);
		
		lblListMM = new JLabel("CakeList");
		lblListMM.setFont(new Font("Arial", Font.BOLD, 33));
		lblListMM.setSize(250,30);
		lblListMM.setLocation(285, 100);
		manageMenuForm.add(lblListMM);
		
		lblCakeNameMM = new JLabel("Cake Name");
		lblCakeNameMM.setFont(new Font("Arial", Font.BOLD, 14));
		lblCakeNameMM.setSize(170, 30);
		lblCakeNameMM.setLocation(70, 465);
		txtCakeNameMM = new JTextField();
		txtCakeNameMM.setSize(170, 21);
		txtCakeNameMM.setLocation(230, 468);
		manageMenuForm.add(lblCakeNameMM);
		manageMenuForm.add(txtCakeNameMM);
		
		lblCakePriceMM = new JLabel("Cake Price");
		lblCakePriceMM.setFont(new Font("Arial", Font.BOLD, 14));
		lblCakePriceMM.setSize(170, 30);
		lblCakePriceMM.setLocation(70, 495);
		txtCakePriceMM = new JTextField();
		txtCakePriceMM.setSize(170, 21);
		txtCakePriceMM.setLocation(230, 498);
		manageMenuForm.add(lblCakePriceMM);
		manageMenuForm.add(txtCakePriceMM);
		
		lblShapeMM = new JLabel ("Shape");
		lblShapeMM.setFont(new Font("Arial", Font.BOLD, 14));
		lblShapeMM.setSize(170, 30);
		lblShapeMM.setLocation(70, 525);
		manageMenuForm.add(lblShapeMM);
		
		lblOvalSizeMM = new JLabel ("OvalSize");
		lblOvalSizeMM.setFont(new Font("Arial", Font.BOLD, 14));
		lblOvalSizeMM.setSize(170, 30);
		lblOvalSizeMM.setLocation(70, 555);
		manageMenuForm.add(lblOvalSizeMM);
		
		lblRectangleSizeMM = new JLabel ("RectangleSize");
		lblRectangleSizeMM.setFont(new Font("Arial", Font.BOLD, 14));
		lblRectangleSizeMM.setSize(170, 30);
		lblRectangleSizeMM.setLocation(70, 585);
		manageMenuForm.add(lblRectangleSizeMM);
		
		String Shape[] = {"Oval", "Rectangle"};
		shapes = new JComboBox(Shape);
		shapes.setSize(170, 21);
		shapes.setLocation(230, 528);
		shapes.addActionListener(this);
		manageMenuForm.add(shapes);
		
		String Oval[] = {"15 cm", "20 cm", "25 cm"};
		ovalsize = new JComboBox(Oval);
		ovalsize.setSize(170, 21);
		ovalsize.setLocation(230, 558);
		manageMenuForm.add(ovalsize);
		
		String Rectangle[] = {"10 x 10 cm", "20 x 20 cm", "30 x 30 cm"};
		rectanglesize = new JComboBox(Rectangle);
		rectanglesize.setSize(170,21);
		rectanglesize.setLocation(230, 588);
		rectanglesize.setEnabled(false);
		manageMenuForm.add(rectanglesize);
		
		btnBackMM = new JButton("Back to Main Menu");
		btnBackMM.setSize(155, 30);
		btnBackMM.setLocation(500, 20);
		btnBackMM.addActionListener(this);
		manageMenuForm.add(btnBackMM);
		
		btnRemoveMM = new JButton("Remove Cake");
		btnRemoveMM.setSize(150, 68);
		btnRemoveMM.setLocation(467, 463);
		btnRemoveMM.addActionListener(this);
		btnAddMM = new JButton("Add Cake");
		btnAddMM.setSize(150, 68);
		btnAddMM.setLocation(467, 540);
		btnAddMM.addActionListener(this);
		manageMenuForm.add(btnRemoveMM);
		manageMenuForm.add(btnAddMM);
		
		manageMenuForm.setLayout(null);
		manageMenuForm.setSize(700, 700);
		manageMenuForm.setLocationRelativeTo(null);
		manageMenuForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Main() {
		login();
	}
	
	
	public static void main (String []args) {
		new Main();
	}

	void refreshCheckOut() {
		pnlTableCheckout.removeAll();
		setupTableCheckOut();
		pnlTableCheckout.repaint();
		pnlTableCheckout.revalidate();
			
	}
	
	void refreshConfirmCheckout() {
		pnlTableConfirmCheckout.removeAll();
		setupTableConfimCheckout();
		pnlTableConfirmCheckout.repaint();
		pnlTableConfirmCheckout.revalidate();
		
	}
	
	void refreshMenuTable() {		
		pnlTableMenu.removeAll();
		setupTable();
		pnlTableMenu.repaint();
		pnlTableMenu.revalidate();
	}
	
	void refreshTransactionHistoryTable() {		
		pnlTableHeaderTH.removeAll();
		setupTableTransactionHeader();
		pnlTableHeaderTH.repaint();
		pnlTableHeaderTH.revalidate();
	}
	
	void refreshTransactionHistoryDetailTable() {		
		pnlTableDetailTH.removeAll();
		setupTableDetail();
		pnlTableDetailTH.repaint();
		pnlTableDetailTH.revalidate();
	}
	
	void refreshTableUpdateCake() {
		pnlTableUpdateCake.removeAll();
		setupTableUpdateCake();
		pnlTableUpdateCake.repaint();
		pnlTableUpdateCake.revalidate();
	}
	
	void cakePriceSum() {
		totalPrice = 0;
		for(int i = 0; i < cart.size(); i++) {
			totalPrice = totalPrice + cart.get(i).getCakeSubTotal();
		}
		
	}

	void totalPriceHistory() {
		totalPriceHistory = 0;
		int index = tableHeaderTH.getSelectedRow();
		TableModel model = tableHeaderTH.getModel();
		String TransactionID = model.getValueAt(index, 0).toString();
		
		getAllTransactionDetail(TransactionID);
		
		for (int i = 0; i < tcd.size(); i++) {
			totalPriceHistory = totalPriceHistory + tcd.get(i).getSubTotal();
		}
	}
	
	void generateCakeID() {
		String shape1 = "";
		String size1 = "";
		
		cakeShapeInput = shapes.getSelectedItem().toString();
		
		if (shapes.getSelectedIndex() == 0) {
			shape1 = "O";
			
			cakeSizeInput = ovalsize.getSelectedItem().toString();
			
			if (ovalsize.getSelectedIndex() == 0) {
				size1 = "F";
			}
			
			if (ovalsize.getSelectedIndex() == 1) {
				size1 = "N";
				
			}
			
			if (ovalsize.getSelectedIndex() == 2) {
				size1 = "V";
			}
			
		}
		
		if (shapes.getSelectedIndex() == 1) {
			shape1 = "R";
			
			cakeSizeInput = rectanglesize.getSelectedItem().toString();
			
			if (rectanglesize.getSelectedIndex() == 0) {
				size1 = "T";
			}
			
			if (rectanglesize.getSelectedIndex() == 1) {
				size1 = "W";
			}
			
			if (rectanglesize.getSelectedIndex() == 2) {
				size1 = "H";
			}
		}

		Random rn = new Random();
		int a = rn.nextInt(9) + 1;
		int b = rn.nextInt(9) + 1;
		int c = rn.nextInt(9) + 1;
		int d = rn.nextInt(9) + 1;
		
		cakeIDInput = "C" + shape1 + size1 + a + b + c + d;
	}
	
	void generateTransactionID() {
		Random rn = new Random();
		int a = rn.nextInt(9) + 1;
		int b = rn.nextInt(9) + 1;
		int c = rn.nextInt(9) + 1;
		int d = rn.nextInt(9) + 1;
		
		TransactionIDInput = "T" + a + b + c + d;
	}
	
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		
		if (e.getSource() == btnLogin) {
			
			
			
			String username = tUsername.getText();
			String password = tPassword.getText();
			
			User user = getOneUser(username, password);
			if (user == null) {
				JOptionPane.showMessageDialog(this, "Wrong credentials" , "Error", JOptionPane.ERROR_MESSAGE );
			} else {
				JOptionPane.showMessageDialog(this, "Welcome " + user.getUsername() + "!");
				
				if(user.getRole().toString().contains("Admin")) {
					mainMenuAdmin.setVisible(true);
					LoginFrame.dispose();
					userID = user.getUserID().toString();
				}
				else if(user.getRole().toString().contains("User")) {
					mainMenuUser.setVisible(true);
					LoginFrame.dispose();
					userID = user.getUserID().toString();
					
				}
			}
			
			
		
		}
		
		if (e.getSource() == lRegister) {
			LoginFrame.dispose();
			formregis = new FormRegistrasi();
		}
		
		if (e.getSource() == btnSave) {
			
			
			String username = txtUsername.getText().toString();
			int phonenumber = -1;
			String address = txtAddress.getText().toString();
			String oldPassword =txtOldPassword.getText().toString();
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
			} catch (Exception ex) {
				
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
			
			if (!oldPassword.equals(users.get(0).getPassword())) {
				JOptionPane.showMessageDialog(this, "Password doesn't match", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (!confirmpassword.equals(password)) {
				JOptionPane.showMessageDialog(this, "New Password doesn't match", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			updateProfile();
			updateProfile.dispose();
			LoginFrame.setVisible(true);
			tUsername.setText(null);
			tPassword.setText(null);
			
			
		}
		
		if (e.getSource() == addToCart) {
			
			int index = table.getSelectedRow();
			TableModel model = table.getModel();
			int quantity = (int) spnQuantity.getValue();
			
			
			if(quantity == 0) {
				JOptionPane.showMessageDialog(this, "Quantity must be more than 0!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			else {
			if(index == -1) {
				JOptionPane.showMessageDialog(this, "Please Select Cake!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			else {
				getAllCake();
				
				String cartCakeName = null;
				
				if (cart.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Cake Successfuly added to cart!", "Success", JOptionPane.INFORMATION_MESSAGE);
					cart.add(new Cart(cake.get(index).getCakeID(), model.getValueAt(index, 0).toString(), model.getValueAt(index, 1).toString(), model.getValueAt(index, 2).toString(), model.getValueAt(index, 3).toString(), spnQuantity.getValue().toString(), Integer.parseInt(model.getValueAt(index, 1).toString()) * Integer.parseInt(spnQuantity.getValue().toString())));
				}
				
				else {

					for (int i = 0 ; i < cart.size() ; i++) {
					cartCakeName = cart.get(i).getCakeName();
					}
				
					if (cartCakeName.contains(model.getValueAt(index, 0).toString())) {
					JOptionPane.showMessageDialog(this, "This cake is already in your cart! if you want to change the quantity, do in on the update page!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				
					else {
					JOptionPane.showMessageDialog(this, "Cake Successfuly added to cart!", "Success", JOptionPane.INFORMATION_MESSAGE);
					cart.add(new Cart(cake.get(index).getCakeID(), model.getValueAt(index, 0).toString(), model.getValueAt(index, 1).toString(), model.getValueAt(index, 2).toString(), model.getValueAt(index, 3).toString(), spnQuantity.getValue().toString(), Integer.parseInt(model.getValueAt(index, 1).toString()) * Integer.parseInt(spnQuantity.getValue().toString())));
					
					}
				}	
			}
		}
	}
		if(e.getSource() == viewCart) {
			refreshCheckOut();
			viewAllMenu.dispose();
			checkOut.setVisible(true);
		}
		
		if(e.getSource() == btnViewAllMenu) {
			refreshMenuTable();
			checkOut.dispose();
			viewAllMenu.setVisible(true);
		}
		
		if (e.getSource() == btnUpdateCake) {
			int index = tableCheckOut.getSelectedRow();
			int quantity = (int) spnQuantityCheckOut.getValue();
			
			if (cart.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No Item in Cart", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				if(quantity == 0) {
					JOptionPane.showMessageDialog(this, "Quantity must be more than 0!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				else {
				if(index == -1) {
					JOptionPane.showMessageDialog(this, "Please Select Cake to Update!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
					else {
					cart.get(index).setCakeQuantity(spnQuantityCheckOut.getValue().toString());
					refreshCheckOut();
					JOptionPane.showMessageDialog(this, "Cake Successfuly Updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}
	
		if(e.getSource() == btnRemoveFromCart) {
			int index = tableCheckOut.getSelectedRow();
			
			if (cart.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No Item in Cart", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			else {
				if(index == -1) {
					JOptionPane.showMessageDialog(this, "Please select cake you want cancel!", "Error", JOptionPane.ERROR_MESSAGE);
				}
					else {
					cart.remove(index);
					refreshCheckOut();
					JOptionPane.showMessageDialog(this, "Cake Successfuly Removed!", "Success", JOptionPane.INFORMATION_MESSAGE);		
					}
			}
		}
	
		if(e.getSource() == btnCheckOut) {
			refreshConfirmCheckout();
			checkOut.dispose();
			ConfirmCheckoutPage.setVisible(true);
			
			for (int i = 0 ; i < cart.size() ; i++) {
				cart.get(i).getCakeSubTotal();
			}
			
			cakePriceSum();
			txtTotalConfirmCheckout.setText(String.valueOf(totalPrice));
		
		}
		
		if (e.getSource() == tableHeaderTH) {
			int index = tableHeaderTH.getSelectedRow();
			TableModel model = tableHeaderTH.getModel();
			
			tcd.removeAll(tcd);
			refreshTransactionHistoryDetailTable();
			tcd.removeAll(tcd);
			totalPriceHistory();
			
			txtTotalTH.setText(String.valueOf(totalPriceHistory));
			txtSelectedID.setText(model.getValueAt(index, 0).toString());
		}
		
		if (e.getSource() == btnSave) {
			
		}
		
		
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == logout) {
			users.removeAll(users);
			mainMenuAdmin.dispose();
			LoginFrame.setVisible(true);
			tUsername.setText(null);
			tPassword.setText(null);
		}
		
		if(e.getSource() == logoutUser) {
			users.removeAll(users);
			mainMenuUser.dispose();
			LoginFrame.setVisible(true);
			cart.removeAll(cart);
			tUsername.setText(null);
			tPassword.setText(null);
		}		
		
		if(e.getSource() == profile) {
			updateProfile.setVisible(true);
			mainMenuAdmin.dispose();
		}
		
		if(e.getSource() == profileUser) {
			updateProfile.setVisible(true);
			mainMenuUser.dispose();
		}
		
		if(e.getSource() == viewAllMenuUser) {
			refreshMenuTable();
			mainMenuUser.dispose();
			viewAllMenu.setVisible(true);
			
		}
		
		if(e.getSource() == manageCartUser) {
			refreshCheckOut();
			mainMenuUser.dispose();
			checkOut.setVisible(true);
		}
		
		if(e.getSource() == backToMenu) {
			viewAllMenu.dispose();
			mainMenuUser.setVisible(true);
		}
		
		if(e.getSource() == backToMenuCheckout) {
			checkOut.dispose();
			mainMenuUser.setVisible(true);
		}
		
		if(e.getSource() == btnBackConfirmCheckout) {
			ConfirmCheckoutPage.dispose();
			mainMenuUser.setVisible(true);
			cart.removeAll(cart);
		}
		
		if(e.getSource() == manageMenu) {
			mainMenuAdmin.dispose();
			refreshTableUpdateCake();
			manageMenuForm.setVisible(true);
		}
		
		if(e.getSource() == btnBackMM) {
			manageMenuForm.dispose();
			mainMenuAdmin.setVisible(true);
		}
		
		if(e.getSource() == viewTransactionHistoryUser) {
			mainMenuUser.dispose();
			refreshTransactionHistoryTable();
			refreshTransactionHistoryDetailTable();
			TransactionHistory.add(pnlTableHeaderTH);
			TransactionHistory.add(pnlTableDetailTH);
			tableHeaderTH.addMouseListener(this);
			TransactionHistory.setVisible(true);
		}
		
		if(e.getSource() == backToMenuTH) {
			TransactionHistory.dispose();
			mainMenuUser.setVisible(true);
		}
		
		if(e.getSource() == btnCheckoutConfirmCheckout) {
			
			if(txtDateConfirmCheckout.getText().toString().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Fill the pickup date in format yyyy/MM/dd!");
				return;
			}
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
			LocalDateTime now = LocalDateTime.now();  
			
			generateTransactionID();
			
			insertTransaction(new TransactionHeader(TransactionIDInput, users.get(0).getUserID() , dtf.format((now)).toString(), txtDateConfirmCheckout.getText().toString()));
			
			for (int i = 0 ; i < cart.size() ; i++) {
				insertTransactionDetail(new TransactionDetail(TransactionIDInput, cart.get(i).getCakeID(), cart.get(i).getCakeQuantity()));
			}
			
			JOptionPane.showMessageDialog(this, "Transaction Successful! Remember to pick up your order!");
			ConfirmCheckoutPage.dispose();
			mainMenuUser.setVisible(true);
			
			cart.removeAll(cart);
			
		}
		
		if (e.getSource() == shapes) {
			
			if(shapes.getSelectedItem().toString().contains("Rectangle")) {
				rectanglesize.setEnabled(true);
				ovalsize.setEnabled(false);
			}
			
			if(shapes.getSelectedItem().toString().contains("Oval")) {
				rectanglesize.setEnabled(false);
				ovalsize.setEnabled(true);
			}		
		}
		
		if (e.getSource() == btnAddMM) {
			
			String cakePriceS = txtCakePriceMM.getText().toString();
			int cakePricei = -1;
			try {
				cakePricei = Integer.parseInt(cakePriceS);
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
			if (txtCakeNameMM.getText().toString().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Cake name cannot be empty","Error", JOptionPane.ERROR_MESSAGE);
			}
	
			else {
				if (!txtCakeNameMM.getText().toString().endsWith("Cake")) {
					JOptionPane.showMessageDialog(this, "Cake name must ends with Cake","Error", JOptionPane.ERROR_MESSAGE);
					
				}
				else {
					
				
				if (cakePriceS.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Cake price cannot be empty","Error", JOptionPane.ERROR_MESSAGE);
				}
					else {
						
						if(cakePricei == -1) {
						JOptionPane.showMessageDialog(this, "Cake price must be number","Error", JOptionPane.ERROR_MESSAGE);
					}
						else {
							if (cakePricei < 100000 || cakePricei > 500000 ) {
								JOptionPane.showMessageDialog(this, "Cake price must be number and between 100000 - 500000 ","Error", JOptionPane.ERROR_MESSAGE);
							}
								else {
									generateCakeID();
									insertNewCake(new Cake(cakeIDInput, txtCakeNameMM.getText().toString(), txtCakePriceMM.getText().toString(), cakeSizeInput , cakeShapeInput));
									JOptionPane.showMessageDialog(this, "Cake succesfully inputted to the database");
									refreshTableUpdateCake();
								}
						}
					}
				}
			}
		}
		
		if(e.getSource() == btnRemoveMM) {
			int index = tableUpdateCake.getSelectedRow();
			
			
			if  (index == -1) {
				JOptionPane.showMessageDialog(this, "Please Select Cake to Remove!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			deleteCake();
			JOptionPane.showMessageDialog(this, "Cake successfully removed from the database!");
			refreshTableUpdateCake();
		}	
	}
}
