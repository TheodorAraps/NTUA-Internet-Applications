package database;

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


public class DbConnector {

	// DB URL with HOST IP, PORT and DB NAME
	private static final String DB_URL = "jdbc:mysql://localhost:3306/exam2022";
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
	
	private static final String UPDATE_USER_PASS_SQL_QUERY = 
		"UPDATE USERS SET PASSWORD_HASH = ? WHERE USERNAME = ?";
	
	public int updateUserPassword(User user) throws SQLException {
		final PreparedStatement ps = conn.prepareStatement(UPDATE_USER_PASS_SQL_QUERY);
		ps.setString(1, user.getPasswordHash());
		ps.setString(2, user.getUsername());
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
			final Integer roleID = rs.getInt("ROLE_ID");
			user = new User(id, username, passwordHash, new java.util.Date(dateCreated.getTime()), roleID);
			break;
		}
		rs.close();
		ps.close();
		return user;
	}
	
	private static final String SELECT_NAME_AND_TOTAL_COUNT = 
			"SELECT dm.NAME, count(*) FROM CASES c, DIAGNOSIS_METHODS dm \r\n"
			+ "WHERE c.DIAGNOSIS_METHOD_ID = dm.ID GROUP BY dm.NAME";
	
	public List<CountPerCategory> getAdminInfo() throws SQLException {
		final List<CountPerCategory> productList = new ArrayList<>();
		final Statement st = conn.createStatement();
		final ResultSet rs = st.executeQuery(SELECT_NAME_AND_TOTAL_COUNT);
		while (rs.next()) {
			final String name = rs.getString("NAME");
			final Integer count = rs.getInt("count(*)");
			productList.add(new CountPerCategory(name, count));
		}
		rs.close();
		st.close();
		return productList;
	}
	
	private static final String SELECT_USERS_COVID_TESTS = 
			"SELECT * FROM CASES c, DIAGNOSIS_METHODS dm \r\n"
			+ "WHERE c.DIAGNOSIS_METHOD_ID = dm.ID AND c.USER_ID = ?";
		
	public List<Cases> getCases(final Integer userID) throws SQLException {
		final List<Cases> usersCases = new ArrayList<>();
		final PreparedStatement ps = conn.prepareStatement(SELECT_USERS_COVID_TESTS);
		ps.setInt(1, userID);
		final ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			final int id = rs.getInt("ID");
			final Date diagnosisDate = rs.getDate("DIAGNOSIS_DATE");
			final Integer diagnosisMethodID = rs.getInt("DIAGNOSIS_METHOD_ID");
			final String diagnosisLocation = rs.getString("DIAGNOSIS_LOCATION");
			final String diagnosiseportUID = rs.getString("DIAGNOSIS_REPORT_UID");
			//final int id2 = rs.getInt("ID");
			final String name = rs.getString("NAME");
			usersCases.add(new Cases(id, userID, diagnosisDate, diagnosisMethodID, diagnosisLocation, diagnosiseportUID, name));
		}
		rs.close();
		ps.close();
		return usersCases;
	}

	private static final String INSERT_CASES_SQL_QUERY = 
			"INSERT INTO CASES VALUES (null, ?, ?, ?, ?, ?)";
		
	public int insertCase(Cases userCase) throws SQLException {
		final PreparedStatement ps = conn.prepareStatement(INSERT_CASES_SQL_QUERY);
		ps.setInt(1, userCase.getUserId());
		ps.setDate(2, new Date(userCase.getDiagnosisDate().getTime()));
		ps.setInt(3, userCase.getDiagnosisMethodId());
		ps.setString(4,userCase.getDiagnosisLocation());
		ps.setString(5, userCase.getDiagnosisReportUid());
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
//		System.out.println();
//		
//		final DbConnector db = DbConnector.getInstance();
//		db.openDbConnection();
//		
//		// View Data
//		final List<Reservation> resList = db.getReservations(1);
//		System.out.println("View-Response: resList.size(): " + resList.size());
//		for (Reservation res : resList) {
//			System.out.println(" - " + res);
//		}
//		
//		db.closeDbConnection();
//		
//		System.out.println();
		System.out.println(" >> ProgramDB - Testing Place - END");
	}


}
