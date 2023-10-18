package lab4.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import lab4.db.model.Vehicle;
import lab4.db.model.Reservation;
import lab4.db.model.User;


public class DbConnector {

	// DB URL with HOST IP, PORT and DB NAME
	private static final String DB_URL = "jdbc:mysql://localhost:3306/LAB4DB";
	// DB credentials
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";
	
	// DB Connection - Used by the other methods of this class
	private Connection conn;
	
	// Singleton Design Pattern
	private static DbConnector instance = null;
	/** Ensure that we will create only one instance of this class */
	public static DbConnector getInstance() {
		synchronized (DbConnector.class) {
			if (instance == null) {
				instance  = new DbConnector();
			}
			return instance;
		}
	}
	private DbConnector() {
		
	}
	
	/** Open DB Connection*/
	public void openDbConnection() throws SQLException, ClassNotFoundException {
		// DB Connection Properties
		final Properties DB_PROP = new Properties();
		DB_PROP.setProperty("user"	, DB_USERNAME);
		DB_PROP.setProperty("password", DB_PASSWORD);
		DB_PROP.setProperty("charSet", "UTF-8");
		
		// Ensure that the DB Connector (i.e., Java Class) is available in your CLASSPATH
		Class.forName("com.mysql.cj.jdbc.Driver");
				
		// Get DB Connection
		this.conn = DriverManager.getConnection(DB_URL, DB_PROP);
	}
	
	private static final String INSERT_USER_SQL_QUERY = 
		"INSERT INTO USERS VALUES (null, ?, ?, now(), 1)";
	
	public int insertUser(User user) throws SQLException {
		final PreparedStatement ps = conn.prepareStatement(INSERT_USER_SQL_QUERY);
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getPasswordHash());
		final int response = ps.executeUpdate();
		ps.close();
		return response;
	}
	
	private static final String SELECT_USER_SQL_QUERY = 
		"SELECT * FROM USERS WHERE username = ? and password_hash = ?";
	
	public User getUser(final String username, final String passwordHash) throws SQLException {
		User user = null;
		final PreparedStatement ps = conn.prepareStatement(SELECT_USER_SQL_QUERY);
		ps.setString(1, username);
		ps.setString(2, passwordHash);
		final ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			final int id = rs.getInt("ID");
			final Date dateCreated = rs.getDate("DATE_CREATED");
			final Integer userType = rs.getInt("USER_TYPE");
			user = new User(id, username, passwordHash, new java.util.Date(dateCreated.getTime()), userType);
			break;
		}
		rs.close();
		ps.close();
		return user;
	}
	
	private static final String SELECT_ALL_VEHICLES_SQL_QUERY = 
			"SELECT * FROM VEHICLES";

	public List<Vehicle> getVehicles() throws SQLException {
		final List<Vehicle> productList = new ArrayList<>();
		final Statement st = conn.createStatement();
		final ResultSet rs = st.executeQuery(SELECT_ALL_VEHICLES_SQL_QUERY);
		while (rs.next()) {
			final Integer id = rs.getInt("ID");
			final String name = rs.getString("NAME");
			final String description = rs.getString("DESCRIPTION");
			final Integer productType = rs.getInt("PRODUCT_TYPE");
			final String imagePath = rs.getString("IMAGE_PATH");
			productList.add(new Vehicle(id, name, description, productType, imagePath));
		}
		rs.close();
		st.close();
		return productList;
	}

	private static final String SELECT_RESERVATION_SQL_QUERY = 
			"SELECT * FROM RESERVATIONS WHERE user_id = ?";
		
	public List<Reservation> getReservations(final Integer userID) throws SQLException {
		final List<Reservation> reservationList = new ArrayList<>();
		final PreparedStatement ps = conn.prepareStatement(SELECT_RESERVATION_SQL_QUERY);	
		ps.setString(1, userID.toString());
		final ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			final int vehicleid = rs.getInt(3);
			final Date startdate = rs.getDate(4);
			final Date enddate = rs.getDate(5);
			final String comments = rs.getString(6);

			reservationList.add(new Reservation(userID, vehicleid, new java.util.Date(startdate.getTime()), new java.util.Date(enddate.getTime()), comments));
		}
		rs.close();
		ps.close();
		
		return reservationList;
	}
	
	private static final String INSERT_RESERVATION_SQL_QUERY = 
			"INSERT INTO RESERVATIONS VALUES (null, ?, ?, ?, ?, ?)";
		
	public int insertReservation(Reservation reservation) throws SQLException {
		final PreparedStatement ps = conn.prepareStatement(INSERT_RESERVATION_SQL_QUERY);
		ps.setInt(1, reservation.getUserId());
		ps.setInt(2, reservation.getProductId());
		ps.setDate(3, new Date(reservation.getStartDate().getTime()));
		ps.setDate(4, new Date(reservation.getEndDate().getTime()));
		ps.setString(5, reservation.getComments());
		final int response = ps.executeUpdate();
		ps.close();
		return response;
	}
	
	/** Close DB Connection */
	public void closeDbConnection() throws SQLException {
		if (conn != null && !conn.isClosed()) {
			this.conn.close();
		}
	}

	/**
	 * For Testing Purposes ...
	 */
	
	public static void main(String[] args) throws Exception {
	
		System.out.println(" >> ProgramDB - Testing Place - START");
		System.out.println();
		
		final DbConnector db = DbConnector.getInstance();
		db.openDbConnection();
		
		// View Data
		final List<Reservation> resList = db.getReservations(1);
		System.out.println("View-Response: resList.size(): " + resList.size());
		for (Reservation res : resList) {
			System.out.println(" - " + res);
		}
		
		db.closeDbConnection();
		
		System.out.println();
		System.out.println(" >> ProgramDB - Testing Place - END");
	}


}
