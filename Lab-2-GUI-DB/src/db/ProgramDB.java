package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class ProgramDB {

	// DB URL with HOST IP, PORT and DB NAME
	private static final String DB_URL = "jdbc:mysql://localhost:3306/LAB2DB";
	// DB credentials
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";
	
	// DB Connection - Used by the other methods of this class
	private Connection conn;
	
	// Singleton Design Pattern
	private static ProgramDB instance = null;
	/** Ensure that we will create only one instance of this class */
	public static ProgramDB getInstance() {
		synchronized (ProgramDB.class) {
			if (instance == null) {
				instance  = new ProgramDB();
			}
			return instance;
		}
	}
	private ProgramDB() {
		
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
	
	/** Insert Data SQL query */
	private static final String INSERT_DATA_SQL_QUERY = 
		"INSERT INTO PRODUCT VALUES (null, ?, ?, ?)";
	
	/** Insert Data 
	 * @throws SQLException */
	public int insertData(Data data) throws SQLException {
		final PreparedStatement ps = conn.prepareStatement(INSERT_DATA_SQL_QUERY);
		ps.setString(1, data.getCode());
		ps.setString(2, data.getName());
		ps.setFloat(3, data.getPrice());
		final int response = ps.executeUpdate();
		ps.close();
		return response;
	}
	
	/** Select ALL SQL query */
	private static final String SELECT_ALL_DATA_SQL_QUERY = 
		"SELECT * FROM PRODUCT";
	
	/** GET ALL Data
	 * @throws SQLException */
	public List<Data> getAllData() throws SQLException {
		final List<Data> dataList = new ArrayList<>();
		final Statement st = conn.createStatement();
		final ResultSet rs = st.executeQuery(SELECT_ALL_DATA_SQL_QUERY);
		while (rs.next()) {
			final int ID = rs.getInt(1);
			final String code = rs.getString(2);
			final String name = rs.getString(3);
			final Float price = (Float) rs.getObject(4);
			// Add Product Data to Object and add Object to List
			dataList.add(new Data(ID, code, name, price));
		}
		rs.close();
		st.close();
		return dataList;
	}
	
	/** Close DB Connection */
	public void closeDbConnection() throws SQLException {
		if (conn != null && !conn.isClosed()) {
			this.conn.close();
		}
	}

	/** Testing Place ...  */
//	public static void main(String[] args) throws Exception {
//	
//		System.out.println(" >> ProgramDB - Testing Place - START");
//		System.out.println();
//		
//		final ProgramDB db = ProgramDB.getInstance();
//		
//		db.openDbConnection();
//		
//		// Insert Data
//		Data insertData = new Data("CV:" + new Random().nextInt(1000000), "Product Test Name", 99.9f);
//		int insertResponse = db.insertData(insertData);
//		System.out.println("Insert-Response: " + insertResponse + " (rows affected)");
//		
//		// View Data
//		final List<Data> dataList = db.getAllData();
//		System.out.println("View-Response: dataList.size(): " + dataList.size());
//		for (Data data : dataList) {
//			System.out.println(" - " + data.asString());
//		}
//		
//		db.closeDbConnection();
//		
//		System.out.println();
//		System.out.println(" >> ProgramDB - Testing Place - END");
//	}
	
}
