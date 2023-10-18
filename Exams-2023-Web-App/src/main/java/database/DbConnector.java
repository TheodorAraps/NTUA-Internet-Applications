package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class DbConnector {

	// DB URL with HOST IP, PORT and DB NAME
	private static final String DB_URL = "jdbc:mysql://localhost:3306/exam0623db";
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
	
	private static final String UPDATE_TASK_STATUS_SQL_QUERY = 
		"UPDATE TASK SET STATUS_ID = ? , DATE_UPDATED = now() WHERE ID = ?";
	
	public void updateTaskStatus(Integer taskID, Integer statusID) throws SQLException {
		final PreparedStatement ps = conn.prepareStatement(UPDATE_TASK_STATUS_SQL_QUERY);
		ps.setInt(1, statusID);
		ps.setInt(2, taskID);
		ps.executeUpdate();
		ps.close();
	}
	
	private static final String SELECT_USER_SQL_QUERY = 
			"SELECT * FROM USER , ROLE WHERE USER.ROLE_ID=ROLE.ID AND USERNAME=? AND\r\n"
			+ "USERPASS=?";
	
	public User examineUser(String username, String password) throws Exception {
		
		final PreparedStatement ps = conn.prepareStatement(SELECT_USER_SQL_QUERY);
		ps.setString(1, username);
		ps.setString(2, password);
		final ResultSet rs = ps.executeQuery();
		
		// if something returned
		if (rs.next()) 
		{
			int userid = rs.getInt(1);
			int roleid = rs.getInt(4);
			return new User(username, password, roleid, userid);
			
		}
		rs.close();
		ps.close();
		
		return null;
	}
//	
//	private static final String SELECT_NAME_AND_TOTAL_COUNT = 
//			"SELECT dm.NAME, count(*) FROM CASES c, DIAGNOSIS_METHODS dm \r\n"
//			+ "WHERE c.DIAGNOSIS_METHOD_ID = dm.ID GROUP BY dm.NAME";
//	
//	public List<CountPerCategory> getAdminInfo() throws SQLException {
//		final List<CountPerCategory> productList = new ArrayList<>();
//		final Statement st = conn.createStatement();
//		final ResultSet rs = st.executeQuery(SELECT_NAME_AND_TOTAL_COUNT);
//		while (rs.next()) {
//			final String name = rs.getString("NAME");
//			final Integer count = rs.getInt("count(*)");
//			productList.add(new CountPerCategory(name, count));
//		}
//		rs.close();
//		st.close();
//		return productList;
//	}
//	
	private static final String SELECT_USERS_TASKS_TESTS = 
			"SELECT * FROM TASK , STATUS WHERE TASK.STATUS_ID = STATUS.ID AND USER_ID = ?";
		
	public List<Task> getTasks(final Integer userID) throws SQLException {
		final List<Task> taskList = new ArrayList<>();
		final PreparedStatement ps = conn.prepareStatement(SELECT_USERS_TASKS_TESTS);
		ps.setInt(1, userID);
		final ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			final int taskid = rs.getInt(1);
			final String title = rs.getString(3);
			final String desc = rs.getString(4);
			final int statusid = rs.getInt(5);
			final Date dateUpdated = rs.getDate(6);
			taskList.add(new Task(taskid, title, desc, statusid, dateUpdated));
		}
		rs.close();
		ps.close();
		return taskList;
	}
//
//	private static final String INSERT_CASES_SQL_QUERY = 
//			"INSERT INTO CASES VALUES (null, ?, ?, ?, ?, ?)";
//		
//	public int insertCase(Cases userCase) throws SQLException {
//		final PreparedStatement ps = conn.prepareStatement(INSERT_CASES_SQL_QUERY);
//		ps.setInt(1, userCase.getUserId());
//		ps.setDate(2, new Date(userCase.getDiagnosisDate().getTime()));
//		ps.setInt(3, userCase.getDiagnosisMethodId());
//		ps.setString(4,userCase.getDiagnosisLocation());
//		ps.setString(5, userCase.getDiagnosisReportUid());
//		final int response = ps.executeUpdate();
//		ps.close();
//		return response;
//	}
	
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
